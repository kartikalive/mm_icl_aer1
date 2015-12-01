package mm.icl.llc.aer;

import java.io.IOException;
import java.io.ObjectInputStream;
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
                	n++;
                	AERThread thread = new AERThread(s, "Thread " + n);
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
		private Socket socket; 
		
		public AERThread(Socket socket, String name) {
			this.socket = socket;
		    this.threadName = name;
		}
		   
		public void run() {
			// System.out.println("Running " +  threadName );
			try {
//				long startTime = System.nanoTime();
				
				SensoryData sd = (SensoryData) (new ObjectInputStream(socket.getInputStream()).readObject());
				
				AudioEmotionRecognizer aer = new AudioEmotionRecognizer();
				List<Emotion> emotions = aer.recognize(sd);
				String recognizedLabel = emotions.get(0).toString();
				System.out.println("Classified label: " + recognizedLabel);
				
				if (recognizedLabel.equals("ANGER"))
					System.out.println("Classified Index: 1");
				else if (recognizedLabel.equals("HAPPINESS"))
					System.out.println("Classified Index: 2");
				else if (recognizedLabel.equals("NEUTRAL"))
					System.out.println("Classified Index: 3");
				else if (recognizedLabel.equals("SADNESS"))
					System.out.println("Classified Index: 4");
				
//				long estimatedTime = System.nanoTime() - startTime;
				// System.out.println("Elapsed Time - " + estimatedTime / 1000000 + " ms");
			}
			catch (Exception ex) {
				ex.printStackTrace();
			}
			// System.out.println("Thread " +  threadName + " exiting.");
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
