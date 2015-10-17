package mm.icl.llc.aer;

import java.util.Arrays;

import mm.icl.llc.MachineLearningTools.FeatureExtractions.AudioFeatureExtraction;
import mm.icl.llc.MachineLearningTools.FeatureExtractions.StatisticalFunctions;
import mm.icl.llc.MachineLearningTools.FeatureExtractions.TemporalFeatureExtraction;
import mm.icl.llc.MachineLearningTools.Utilities.UtilityFunctions;

public class FeatureExtractor {
	private double[] samples;
	private double samplingRate;
	private double[] features;
	
	private boolean hasIndex;
	private int labelIndex;
	
	/**
	 * 
	 */
	public FeatureExtractor() {
		this.hasIndex = false;
		this.labelIndex = -1;		
	}
	
	public FeatureExtractor(int labelIndex) {
		if (labelIndex > -1)
			this.hasIndex = true;
		else
			this.hasIndex = false;
		
		this.labelIndex = labelIndex;
	}
	
	/**
	 * @param samples
	 */
	public void setSamples(double[] samples) {
		this.samples = samples;
	}

	/**
	 * @param samplingRate
	 */
	public void setSamplingRate(double samplingRate) {
		this.samplingRate = samplingRate;
	}

	public double[] getFeatures() {
		return features;
	}
	
	/**
	 * @return
	 */
	public void extractFeatures() {
		
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
			double[] zeroCrossing = TemporalFeatureExtraction.extractZeroCrossings(samples, samplingRate);
			
			System.arraycopy(mfcc, 0, matrix[i], 0, mfcc.length);
			System.arraycopy(zeroCrossing, 0, matrix[i], mfcc.length, zeroCrossing.length);
		}
		
		// 3. Compute Delta
		double[][] matrixDelta = computeDelta(matrix); // 14 * 2 = 28 features
		
		// 4. Compute Mean, Standard Deviation, Skewness, Kurtosis
		double[] mean = StatisticalFunctions.computeMean2D(matrixDelta);
		double[] std = StatisticalFunctions.computeStd2D(matrixDelta, mean);
		double[] skewness = StatisticalFunctions.computeSkewness2D(matrixDelta, mean, std);
		double[] kurtosis = StatisticalFunctions.computeKurtosis2D(matrixDelta, mean, std);
		
		// Total features 28 * 4 = 112 features
		
		features = UtilityFunctions.concat(UtilityFunctions.concat(UtilityFunctions.concat(mean, std), skewness), kurtosis);
		if (hasIndex) {
			features  = Arrays.copyOf(features, features.length + 1);
			features[features.length - 1] = labelIndex;
		}
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
	
}
