package mm.icl.llc.aer;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

import mm.icl.llc.LLC.Emotion;
import mm.icl.llc.sensorydata.SensoryData;

public class AER_Server {
	
	public static void main(String[] args) throws IOException {
		int n = 0;
		ServerSocket listener = new ServerSocket(9090);
        try {
        	System.out.println("Server is running.");
        	
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
		}
		   
		public void run() {
			System.out.println("Running " +  threadName );
			try {
				long startTime = System.nanoTime();
				
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
			if (t == null) {
				t = new Thread(this, threadName);
				t.start();
			}
		}
	}
	
}
