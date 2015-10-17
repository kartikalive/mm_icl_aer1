package mm.icl.llc.aer;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

import mm.icl.llc.LLC.Emotion;
import mm.icl.llc.sensorydata.SensoryData;

public class AER_Server {
	public static final String[] LABELS = {"ANGER", "HAPPINESS", "SADNESS", "NEUTRAL"};

	public static void main(String[] args) throws IOException {
		int n = 0;
		ServerSocket listener = new ServerSocket(9090);
        try {
            while (true) {
                Socket s = listener.accept();
                try {
                	DataInputStream in = new DataInputStream(s.getInputStream());
                	int len = in.readInt();
                	System.out.println("Data length: " + len);
                	double[] samples = new double[len];
                	for(int i = 0; i < len; i++)
                		samples[i] = in.readDouble();
                	
                	n++;
                	AERThread thread = new AERThread("Thread " + n, samples);
                	thread.start();
                } finally {
                    s.close();
                }
            }
        }
        finally {
            listener.close();
        }
	}
	
	static class AERThread implements Runnable {
		private Thread t;
		private String threadName;
		private double[] samples;
		   
		public AERThread(String name, double[] samples) {
			this.samples = samples;
		    this.threadName = name;
		    System.out.println("Creating " +  threadName);
		}
		   
		public void run() {
			System.out.println("Running " +  threadName );
			try {
				long startTime = System.nanoTime();
				
//				double rmsEnergy = TemporalFeatureExtraction.computeRMSE(samples);
//				System.out.println("RMSE - " + rmsEnergy);
//				
//				if (rmsEnergy < 0.01)
//					System.out.println("Silence ...");
//				else {
//					System.out.println("Extracting features ...");
//					AERFeatureExtraction fe = new AERFeatureExtraction();
//					double[] features = fe.extractFeature(samples);
//					
//					System.out.println("Classifying label ...");
//					WekaClassification classifier = new WekaClassification();
//					classifier.loadModel("F:\\smo1.model");
//					int labelIndex = classifier.classify(features);
//					System.out.println("Classified label index: " + labelIndex);
//					System.out.println("Classified label: " + LABELS[labelIndex]);
//				}
				
				SensoryData sd = new SensoryData();
				sd.setAudioData(samples);
				AudioEmotionRecognizer aer = new AudioEmotionRecognizer();
				List<Emotion> emotions = aer.recognize(sd);
				System.out.println("Classified label: " + emotions.get(0).toString());
				
				long estimatedTime = System.nanoTime() - startTime;
				System.out.println("Elapsed Time - " + estimatedTime / 1000000 + " ms");
			}
			catch (Exception ex) {
				ex.printStackTrace();
			}
			System.out.println("Thread " +  threadName + " exiting.");
			System.out.println("----------------------------------------");
		}
		   
		public void start() {
			System.out.println("Starting " + threadName);
			if (t == null) {
				t = new Thread(this, threadName);
				t.start();
			}
		}
	}
	
}
