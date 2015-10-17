package mm.icl.llc.aer;

public class InputAdapter {
	private String userId;
	private String deviceId;
	private String timestamp;
	private double[] samples;
	
	public String getUserId() {
		return userId;
	}
	
	public String getDeviceId() {
		return deviceId;
	}
	
	public String getTimestamp() {
		return timestamp;
	}
	
	public double[] getSamples() {
		return samples;
	}
	
	public void parseRawData() {
		
	}
}
