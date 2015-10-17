package mm.icl.llc.aer;

import mm.icl.llc.MachineLearningTools.Classifications.WekaClassification;
import mm.icl.llc.MachineLearningTools.FeatureExtractions.TemporalFeatureExtraction;

public class AER {
	
	public static final double THRESHOLD = 1000;
	public static final double SAMPLING_RATE = 8000;	// 8kHz
	public static final String[] LABELS = {"HAPPY", "ANGRY", "SAD", "NEUTRAL"};
	
	private InputAdapter inputAdapter;
	private FeatureExtractor featureExtractor;
	private WekaClassification classifier;
	private OutputAdapter outputAdapter;
	
	public AER() {
		inputAdapter = new InputAdapter();
		featureExtractor = new FeatureExtractor();
		classifier = new WekaClassification();
		outputAdapter = new OutputAdapter();
	}
	
	public void process() {
		// 1. Get data from Notifier
		
		// 2. Parse data
		inputAdapter.parseRawData();
		
		// 3. Check energy
		double rmsEnergy = TemporalFeatureExtraction.computeRMSE(inputAdapter.getSamples());
		if (rmsEnergy < THRESHOLD)
			return;
		
		// 4. Extract Features
		featureExtractor.setSamples(inputAdapter.getSamples());
		featureExtractor.setSamplingRate(SAMPLING_RATE);
		featureExtractor.extractFeatures();
		double[] features = featureExtractor.getFeatures();
		
		// 5. Classifier
		classifier.loadModel("aer.smo.model");
		int labelIndex = classifier.classify(features);
		String label = LABELS[labelIndex];
		
		// 6. Attach metadata
		outputAdapter.setUserId(inputAdapter.getUserId());
		outputAdapter.setDeviceId(inputAdapter.getDeviceId());
		outputAdapter.setTimestamp(inputAdapter.getTimestamp());
		outputAdapter.setLabel(label);
		
		// 7. Save result to DCL
		
	}
}
