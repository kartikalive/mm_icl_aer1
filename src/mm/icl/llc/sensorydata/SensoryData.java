package mm.icl.llc.sensorydata;

public class SensoryData {
    private double[] audioData;

	public double[] getAudioData() {
		return audioData;
	}

	public void setAudioData(double[] audioData) {
		this.audioData = audioData;
	}
    
	public void setAudioDataFromByteArray(byte[] data) {
		int dataLength = data.length / 2;
		audioData = new double[dataLength];
		for (int i = 0; i < dataLength; i++) {
			audioData[i] = (data[i * 2] + data[i * 2 + 1] * 256) / 32768;
		}
	}
}
