package mm.icl.llc.aer;

import java.util.ArrayList;
import java.util.List;
import mm.icl.llc.LLC.Emotion;
import mm.icl.llc.LLCRecognizer.EmotionRecognizer;
import mm.icl.llc.MachineLearningTools.Classifications.WekaClassification;
import mm.icl.llc.MachineLearningTools.FeatureExtractions.TemporalFeatureExtraction;
import mm.icl.llc.sensorydata.SensoryData;

/**
 *
 * @author lebavui
 */
public class AudioEmotionRecognizer extends EmotionRecognizer {
    
	private static final String[] LABELS = {"ANGER", "HAPPINESS", "SADNESS", "NEUTRAL"};
	private static final double SILENCE_THRESHOLD = 0.01; 
	
	private static final String DEFAULT_MODEL_FILE = new java.io.File("").getAbsolutePath() + "/model/smo2.model";
	
	private String modelFile;
	
	public AudioEmotionRecognizer() {
		modelFile = DEFAULT_MODEL_FILE;
	}
	
	public void setModelFile(String modelFile) {
		this.modelFile = modelFile;
	}
	
    @Override
    public List<Emotion> recognize(SensoryData input) {
    	try {
    		double[] samples = input.getAudioData();
    		
	    	double rmsEnergy = TemporalFeatureExtraction.computeRMSE(samples);
			
			if (rmsEnergy < SILENCE_THRESHOLD) {
				AudioEmotion classifiedEmotion = new AudioEmotion("SILENCE");
				ArrayList<Emotion> emotions = new ArrayList<Emotion>();
				emotions.add(classifiedEmotion);
				return emotions;
			} else {
				AERFeatureExtraction fe = new AERFeatureExtraction();
				fe.setSamplingRate(44100);
				double[] features = fe.extractFeature(samples);
				
				WekaClassification classifier = new WekaClassification();
				classifier.loadModel(modelFile);
				int labelIndex = classifier.classify(features);
				
				AudioEmotion classifiedEmotion = new AudioEmotion(LABELS[labelIndex]);
				ArrayList<Emotion> emotions = new ArrayList<Emotion>();
				emotions.add(classifiedEmotion);
				
				return emotions;
			}
    	} catch (Exception ex) {
    		ex.printStackTrace();
    	}
    	return null;
    }
    
}
