package mm.icl.llc.aer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import mm.icl.llc.MachineLearningTools.Classifications.WekaClassification;
import mm.icl.llc.MachineLearningTools.FeatureExtractions.TemporalFeatureExtraction;
import mm.icl.llc.MachineLearningTools.Utilities.WavFile;
import weka.classifiers.Classifier;
import weka.classifiers.functions.SMO;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;

public class AER_Training_Offline {
	public static final String[] LABELS = {"ANGER", "HAPPINESS", "SADNESS", "NEUTRAL"};
	
	public static void main(String[] args) {
//		training();
		
		long startTime = System.nanoTime(); 
		testing();
		long estimatedTime = System.nanoTime() - startTime;
		System.out.println("Elapsed Time - " + estimatedTime / 1000000 + " ms");
		
//		test("F:\\Le_Ba_Vui\\Working\\MiningMind\\mmdata_04\\BJH\\anger");
	}
	
	public static void test(String folderPath) {
		try {
			File folder = new File(folderPath);
			File[] listOfFiles = folder.listFiles();
		    for (int i = 0; i < listOfFiles.length; i++)
		    	if (listOfFiles[i].isFile())
		    		System.out.println(listOfFiles[i].getName());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public static void testing() {
		try {
			System.out.println("MINING MIND - INFORMATION CURATION LAYER - AUDIO BASED EMOTION RECOGNITION");
			System.out.println("TESTING PHASE");
			
			System.out.println("Loading data ...");
			double[] samples = readSamplesFromWav("F:\\Le_Ba_Vui\\Working\\MiningMind\\mmdata_04\\BJH\\anger\\Jaehun1_2015_09_18_15_06_27.731.wav");
			
			double rmsEnergy = TemporalFeatureExtraction.computeRMSE(samples);
			if (rmsEnergy < 0.01)
				return;
			
			System.out.println("Extracting features ...");
			AERFeatureExtraction fe = new AERFeatureExtraction();
			double[] features = fe.extractFeature(samples);
			
			System.out.println("Classifying label ...");
			WekaClassification classifier = new WekaClassification();
			classifier.loadModel("F:\\smo1.model");
			int labelIndex = classifier.classify(features);
			System.out.println("Classified label index - " + labelIndex);
			System.out.println("Classified label - " + LABELS[labelIndex]);
			
			System.out.println("Done");
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public static void training() {
		try {
			System.out.println("MINING MIND - INFORMATION CURATION LAYER - AUDIO BASED EMOTION RECOGNITION");
			System.out.println("TRAINING PHASE");
			
			System.out.println("Loading data ...");
			List<double[]> listFeatures = new ArrayList<double[]>();
			
		    loadDataFromFolder(listFeatures, "F:\\Le_Ba_Vui\\Working\\MiningMind\\mmdata_04\\BJH\\anger", 0);
		    loadDataFromFolder(listFeatures, "F:\\Le_Ba_Vui\\Working\\MiningMind\\mmdata_04\\BJH\\happiness", 1);
		    loadDataFromFolder(listFeatures, "F:\\Le_Ba_Vui\\Working\\MiningMind\\mmdata_04\\BJH\\sadness", 2);
		    loadDataFromFolder(listFeatures, "F:\\Le_Ba_Vui\\Working\\MiningMind\\mmdata_04\\BJH\\neutral", 3);
		    
		    loadDataFromFolder(listFeatures, "F:\\Le_Ba_Vui\\Working\\MiningMind\\mmdata_04\\CSH\\anger", 0);
		    loadDataFromFolder(listFeatures, "F:\\Le_Ba_Vui\\Working\\MiningMind\\mmdata_04\\CSH\\happiness", 1);
		    loadDataFromFolder(listFeatures, "F:\\Le_Ba_Vui\\Working\\MiningMind\\mmdata_04\\CSH\\sadness", 2);
		    loadDataFromFolder(listFeatures, "F:\\Le_Ba_Vui\\Working\\MiningMind\\mmdata_04\\CSH\\neutral", 3);
		    
		    loadDataFromFolder(listFeatures, "F:\\Le_Ba_Vui\\Working\\MiningMind\\mmdata_04\\HTH\\anger", 0);
		    loadDataFromFolder(listFeatures, "F:\\Le_Ba_Vui\\Working\\MiningMind\\mmdata_04\\HTH\\happiness", 1);
		    loadDataFromFolder(listFeatures, "F:\\Le_Ba_Vui\\Working\\MiningMind\\mmdata_04\\HTH\\sadness", 2);
		    loadDataFromFolder(listFeatures, "F:\\Le_Ba_Vui\\Working\\MiningMind\\mmdata_04\\HTH\\neutral", 3);

		    loadDataFromFolder(listFeatures, "F:\\Le_Ba_Vui\\Working\\MiningMind\\mmdata_04\\Kang\\anger", 0);
		    loadDataFromFolder(listFeatures, "F:\\Le_Ba_Vui\\Working\\MiningMind\\mmdata_04\\Kang\\happiness", 1);
		    loadDataFromFolder(listFeatures, "F:\\Le_Ba_Vui\\Working\\MiningMind\\mmdata_04\\Kang\\sadness", 2);
		    loadDataFromFolder(listFeatures, "F:\\Le_Ba_Vui\\Working\\MiningMind\\mmdata_04\\Kang\\neutral", 3);
		    
		    loadDataFromFolder(listFeatures, "F:\\Le_Ba_Vui\\Working\\MiningMind\\mmdata_04\\KimJH\\anger", 0);
		    loadDataFromFolder(listFeatures, "F:\\Le_Ba_Vui\\Working\\MiningMind\\mmdata_04\\KimJH\\happiness", 1);
		    loadDataFromFolder(listFeatures, "F:\\Le_Ba_Vui\\Working\\MiningMind\\mmdata_04\\KimJH\\sadness", 2);
		    loadDataFromFolder(listFeatures, "F:\\Le_Ba_Vui\\Working\\MiningMind\\mmdata_04\\KimJH\\neutral", 3);
		    
		    loadDataFromFolder(listFeatures, "F:\\Le_Ba_Vui\\Working\\MiningMind\\mmdata_04\\KimSan\\anger", 0);
		    loadDataFromFolder(listFeatures, "F:\\Le_Ba_Vui\\Working\\MiningMind\\mmdata_04\\KimSan\\happiness", 1);
		    loadDataFromFolder(listFeatures, "F:\\Le_Ba_Vui\\Working\\MiningMind\\mmdata_04\\KimSan\\sadness", 2);
		    loadDataFromFolder(listFeatures, "F:\\Le_Ba_Vui\\Working\\MiningMind\\mmdata_04\\KimSan\\neutral", 3);
		    
		    loadDataFromFolder(listFeatures, "F:\\Le_Ba_Vui\\Working\\MiningMind\\mmdata_04\\KMH\\anger", 0);
		    loadDataFromFolder(listFeatures, "F:\\Le_Ba_Vui\\Working\\MiningMind\\mmdata_04\\KMH\\happiness", 1);
		    loadDataFromFolder(listFeatures, "F:\\Le_Ba_Vui\\Working\\MiningMind\\mmdata_04\\KMH\\sadness", 2);
		    loadDataFromFolder(listFeatures, "F:\\Le_Ba_Vui\\Working\\MiningMind\\mmdata_04\\KMH\\neutral", 3);
		    
		    loadDataFromFolder(listFeatures, "F:\\Le_Ba_Vui\\Working\\MiningMind\\mmdata_04\\Kong\\anger", 0);
		    loadDataFromFolder(listFeatures, "F:\\Le_Ba_Vui\\Working\\MiningMind\\mmdata_04\\Kong\\happiness", 1);
		    loadDataFromFolder(listFeatures, "F:\\Le_Ba_Vui\\Working\\MiningMind\\mmdata_04\\Kong\\sadness", 2);
		    loadDataFromFolder(listFeatures, "F:\\Le_Ba_Vui\\Working\\MiningMind\\mmdata_04\\Kong\\neutral", 3);
		    
		    loadDataFromFolder(listFeatures, "F:\\Le_Ba_Vui\\Working\\MiningMind\\mmdata_04\\SJH\\anger", 0);
		    loadDataFromFolder(listFeatures, "F:\\Le_Ba_Vui\\Working\\MiningMind\\mmdata_04\\SJH\\happiness", 1);
		    loadDataFromFolder(listFeatures, "F:\\Le_Ba_Vui\\Working\\MiningMind\\mmdata_04\\SJH\\sadness", 2);
		    loadDataFromFolder(listFeatures, "F:\\Le_Ba_Vui\\Working\\MiningMind\\mmdata_04\\SJH\\neutral", 3);
		    
			System.out.println("Training model ...");
			Instances instances = createInstances(listFeatures);
			SMO smo = new SMO();
			smo.buildClassifier(instances);
			
			System.out.println("Saving model ...");
			saveModel(smo, "F:\\smo1.model");
			
			System.out.println("Done");
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public static void loadDataFromFolder(List<double[]> listFeatures, String folderPath, int labelIndex) {
		try {
			System.out.println(folderPath);
			File folder = new File(folderPath);
			File[] listOfFiles = folder.listFiles();
			if (listOfFiles != null) {
			    for (int i = 0; i < listOfFiles.length; i++)
			    	if (listOfFiles[i].isFile())
			    		listFeatures.add(extractFeatures(listOfFiles[i].getAbsolutePath(), labelIndex));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public static double[] readSamplesFromWav(String filename) {
		try {
			WavFile wavFile = WavFile.openWavFile(new File(filename));
			
			int numFrames = (int)wavFile.getNumFrames();
	        double[] samples = new double[numFrames];
	        wavFile.readFrames(samples, numFrames);
	        wavFile.close();
			
			return samples;
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
	
	public static double[] extractFeatures(String filename, int labelIndex) {
		try {
			WavFile wavFile = WavFile.openWavFile(new File(filename));
			
			int numFrames = (int)wavFile.getNumFrames();
	        double[] samples = new double[numFrames];
	        wavFile.readFrames(samples, numFrames);
	        wavFile.close();
			
			AERFeatureExtraction fe = new AERFeatureExtraction(labelIndex);
			double[] features = fe.extractFeature(samples);
	        
//			FeatureExtractor featureExtractor = new FeatureExtractor(labelIndex);
//			featureExtractor.setSamples(samples);
//			featureExtractor.setSamplingRate(8000);
//			featureExtractor.extractFeatures();
//			double[] features = featureExtractor.getFeatures();

			return features;
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	
	/**
	 * Last attribute should be the class index
	 * @param features
	 * @return
	 */
	public static Instances createInstances(List<double[]> listFeatures) {
		int numAttributes = listFeatures.get(0).length;
			
		// Create attribute set
		FastVector fvWekaAttributes = new FastVector(numAttributes);
		
		// Create numeric attributes
		for (int i = 0; i < numAttributes - 1; i++) {
			Attribute attribute = new Attribute("f" + i);
			fvWekaAttributes.addElement(attribute);
		}
		
		// Create class attribute
		FastVector fvClassVal = new FastVector(4);
		fvClassVal.addElement("anger");
		fvClassVal.addElement("happiness");
		fvClassVal.addElement("sadness");
		fvClassVal.addElement("neutral");
		
		Attribute classAttribute = new Attribute("class", fvClassVal);
		fvWekaAttributes.addElement(classAttribute);
		
		// Create an empty training set
		Instances dataset = new Instances("Rel", fvWekaAttributes, 5000);
		// Set class index
		dataset.setClassIndex(numAttributes - 1);
		
		// Read and add values
		for (int i = 0; i < listFeatures.size(); i++) {
			double[] features = listFeatures.get(i);
			Instance newInstance = new Instance(numAttributes);
			for (int j = 0; j < numAttributes; j++)
				newInstance.setValue((Attribute)fvWekaAttributes.elementAt(j), features[j]);
			// Add the instance
			dataset.add(newInstance);
		}
 
		return dataset;
	}
	
	public static void saveModel(Classifier cls, String filename) {
		try {
			weka.core.SerializationHelper.write(filename, cls);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
