package mm.icl.llc.aer;

import jAudioFeatureExtractor.AudioFeatures.LPC;
import jAudioFeatureExtractor.AudioFeatures.MFCC;
import jAudioFeatureExtractor.AudioFeatures.MagnitudeSpectrum;
import jAudioFeatureExtractor.AudioFeatures.PowerSpectrum;
import jAudioFeatureExtractor.AudioFeatures.RMS;
import jAudioFeatureExtractor.AudioFeatures.SpectralCentroid;
import jAudioFeatureExtractor.AudioFeatures.SpectralRolloffPoint;
import jAudioFeatureExtractor.AudioFeatures.SpectralVariability;
import jAudioFeatureExtractor.AudioFeatures.ZeroCrossings;

import java.util.Arrays;

import mm.icl.llc.MachineLearningTools.FeatureExtraction;
import mm.icl.llc.MachineLearningTools.FeatureExtractions.AudioFeatureExtraction;
import mm.icl.llc.MachineLearningTools.FeatureExtractions.StatisticalFunctions;
import mm.icl.llc.MachineLearningTools.FeatureExtractions.TemporalFeatureExtraction;
import mm.icl.llc.MachineLearningTools.Utilities.UtilityFunctions;

public class AERFeatureExtraction extends FeatureExtraction<double[], double[]> {
	
	private static final double DEFAULT_SAMPLING_RATE = 8000;
	
	private double samplingRate;
	
	private boolean hasIndex;
	private int labelIndex;
	
	/**
	 * 
	 */
	public AERFeatureExtraction() {
		this.hasIndex = false;
		this.labelIndex = -1;
		this.samplingRate = DEFAULT_SAMPLING_RATE;
	}
	
	/**
	 * 
	 * @param labelIndex
	 */
	public AERFeatureExtraction(int labelIndex) {
		if (labelIndex > -1)
			this.hasIndex = true;
		else
			this.hasIndex = false;
		
		this.labelIndex = labelIndex;
		this.samplingRate = DEFAULT_SAMPLING_RATE;
	}
	
	/**
	 * 
	 */
	public double[] extractFeature(double[] samples) {
		// 1. Split frames
		double[][] frames = splitFrames(samples);
		
		int numFrames = frames.length;
		int numFeatures = 14;
		
		double[][] matrix = new double[numFrames][numFeatures];
		
		// 2. Extract features
		// a. MFCC
		// b. Zero Crossing
		// c. Power Spectrum 
		// d. Concatenate into 1 vector
		
		for (int i = 0; i < numFrames; i++) {
			double[] frame = frames[i];
			double[] mfcc = AudioFeatureExtraction.extractMFCC(frame, samplingRate);
			double[] zeroCrossing = TemporalFeatureExtraction.extractZeroCrossings(frame, samplingRate);
			
			System.arraycopy(mfcc, 0, matrix[i], 0, mfcc.length);
			System.arraycopy(zeroCrossing, 0, matrix[i], mfcc.length, zeroCrossing.length);
		}
		
		// 3. Compute Delta
		double[][] matrixDelta = computeDelta(matrix); // 14 * 2 = 28 features
		// double[][] matrixDeltaDelta = computeDeltaDelta(matrixDelta); // 14 * 3 = 42 features
		
		// 4. Compute Mean, Standard Deviation, Skewness, Kurtosis
		double[] mean = StatisticalFunctions.computeMean2D(matrixDelta);
		double[] std = StatisticalFunctions.computeStd2D(matrixDelta, mean);
		// double[] skewness = StatisticalFunctions.computeSkewness2D(matrix, mean, std);
		// double[] kurtosis = StatisticalFunctions.computeKurtosis2D(matrix, mean, std);
		
		// Total features 28 * 2 = 54 features
		
		// Concatenate all features to a vector
		double[] features = UtilityFunctions.concat(mean, std);
		
		if (hasIndex) {
			features  = Arrays.copyOf(features, features.length + 1);
			features[features.length - 1] = labelIndex;
		}
		
		return features;
	}
	
	public double[] extractFeatureExt(double[] samples) {
		try {
			// 1. Split frames
			double[][] frames = splitFrames(samples);
			
			int numFrames = frames.length;
			int numFeatures = 28;
			
			double[][] matrix = new double[numFrames][numFeatures];
			
			// 2. Extract features
			// a. MFCC
			// b. Zero Crossing
			// c. Power Spectrum 
			// d. Concatenate into 1 vector
			
			for (int i = 0; i < numFrames; i++) {
				double[] frame = frames[i];
				
				MagnitudeSpectrum ms = new MagnitudeSpectrum();
				double[] msFeatures = ms.extractFeature(frame, samplingRate, null);
				double[][] otherFeaturesMagnitudeSpectrum = new double[1][msFeatures.length];
				for (int j = 0; j < msFeatures.length; j++)
					otherFeaturesMagnitudeSpectrum[0][j] = msFeatures[j];
				
				PowerSpectrum ps = new PowerSpectrum();
				double[] psFeatures = ps.extractFeature(frame, samplingRate, null);
				double[][] otherFeaturesPowerSpectrum = new double[1][psFeatures.length];
				for (int j = 0; j < psFeatures.length; j++)
					otherFeaturesPowerSpectrum[0][j] = psFeatures[j];
				
				MFCC mfcc = new MFCC();		// 13
				double[] mfccFeatures = mfcc.extractFeature(samples, samplingRate, otherFeaturesMagnitudeSpectrum);
				
				LPC lpc = new LPC();		// 10
				double[] lpcFeatures = lpc.extractFeature(samples, samplingRate, null);
				
				ZeroCrossings zc = new ZeroCrossings();			// 1
				double[] zcFeatures = zc.extractFeature(samples, samplingRate, null);
				
				RMS rms = new RMS();		// 1
				double[] rmsFeatures = rms.extractFeature(samples, samplingRate, null);
				
				SpectralCentroid sc = new SpectralCentroid();		// 1
				double[] scFeatures = sc.extractFeature(samples, samplingRate, otherFeaturesPowerSpectrum);
				
				SpectralRolloffPoint srp = new SpectralRolloffPoint();		// 1
				double[] srpFeatures = srp.extractFeature(samples, samplingRate, otherFeaturesPowerSpectrum);
				
				SpectralVariability sv = new SpectralVariability();			// 1
				double[] svFeatures = sv.extractFeature(samples, samplingRate, otherFeaturesMagnitudeSpectrum);
				
				System.arraycopy(mfccFeatures, 0, matrix[i], 0, 13);
				System.arraycopy(lpcFeatures, 0, matrix[i], 13, 10);
				System.arraycopy(zcFeatures, 0, matrix[i], 23, 1);
				System.arraycopy(rmsFeatures, 0, matrix[i], 24, 1);
				System.arraycopy(scFeatures, 0, matrix[i], 25, 1);
				System.arraycopy(srpFeatures, 0, matrix[i], 26, 1);
				System.arraycopy(svFeatures, 0, matrix[i], 27, 1);
			}
			
			// 3. Compute Delta
			double[][] matrixDelta = computeDelta(matrix); // 14 * 2 = 28 features
			// double[][] matrixDeltaDelta = computeDeltaDelta(matrixDelta); // 14 * 3 = 42 features
			
			// 4. Compute Mean, Standard Deviation, Skewness, Kurtosis
			double[] mean = StatisticalFunctions.computeMean2D(matrixDelta);
			double[] std = StatisticalFunctions.computeStd2D(matrixDelta, mean);
			// double[] skewness = StatisticalFunctions.computeSkewness2D(matrix, mean, std);
			// double[] kurtosis = StatisticalFunctions.computeKurtosis2D(matrix, mean, std);
			
			// Total features 28 * 2 = 54 features
			
			// Concatenate all features to a vector
			double[] features = UtilityFunctions.concat(mean, std);
			
			if (hasIndex) {
				features  = Arrays.copyOf(features, features.length + 1);
				features[features.length - 1] = labelIndex;
			}
			
			return features;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * 
	 * @param samplingRate
	 */
	public void setSamplingRate(double samplingRate) {
		this.samplingRate = samplingRate;
	}
	
	/**
	 * @param samples
	 * @return
	 */
	protected double[][] splitFrames(double[] samples) {
		// Window Size: 100 ms
		// Overlap 50ms
		int windowSizeInMs = 200;
		int overlapInMs = 50;
		
		int windowSize = (int)(windowSizeInMs * samplingRate / 1000);
		int overlap = (int)(overlapInMs * samplingRate / 1000);
		
		int numFrames = (int)Math.ceil(samples.length / (windowSize - overlap));
		
		double[][] frames = new double[numFrames][windowSize];
		
		for (int i = 0; i < numFrames; i++)
			for (int j = 0; j < windowSize; j++)
				if (i * (windowSize - overlap) + j < samples.length)
					frames[i][j] = samples[i * (windowSize - overlap) + j];
				else
					frames[i][j] = 0;
		
		return frames;
	}
	
	/**
	 * @param matrix
	 * @return
	 */
	protected double[][] computeDelta(double[][] matrix) {
		int numFeatures = matrix[0].length;
		int numFrames = matrix.length;
		
		double[][] newMatrix = new double[numFrames][numFeatures * 2];
		
		for (int i = 0; i < numFeatures * 2; i++)
			for (int j = 0; j < numFrames; j++)
				newMatrix[j][i] = 0;
		
		for (int i = 0; i < numFeatures; i++)
			for (int j = 0; j < numFrames; j++)
				newMatrix[j][i] = matrix[j][i];
		
		for (int i = 0; i < numFeatures; i++)
			for (int j = 0; j < numFrames - 1; j++)
					newMatrix[j][numFeatures + i] = matrix[j+1][i] - matrix[j][i];
		
		return newMatrix;
	}
	
	protected double[][] computeDeltaDelta(double[][] matrix) {
		int numFeatures = matrix[0].length / 2;
		int numFrames = matrix.length;
		
		double[][] newMatrix = new double[numFrames][numFeatures * 3];
		
		for (int i = 0; i < numFeatures * 3; i++)
			for (int j = 0; j < numFrames; j++)
				newMatrix[j][i] = 0;
		
		for (int i = 0; i < numFeatures * 2; i++)
			for (int j = 0; j < numFrames; j++)
				newMatrix[j][i] = matrix[j][i];
		
		for (int i = 0; i < numFeatures; i++)
			for (int j = 0; j < numFrames - 1; j++)
					newMatrix[j][numFeatures * 2 + i] = matrix[j+1][numFeatures + i] - matrix[j][numFeatures + i];
		
		return newMatrix;
	}
	
}
