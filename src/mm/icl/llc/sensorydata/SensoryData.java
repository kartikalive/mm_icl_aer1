package mm.icl.llc.sensorydata;

import java.io.Serializable;

import mm.icl.llc.config.DataType;

public class SensoryData implements Serializable {
    
	private static final long serialVersionUID = 1L;
	Long userID;
    byte[] data;

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }
    
    public SensoryData() {
        availTable[0] = true;
        availTable[1] = false;
        availTable[2] = true;
    }
    
    boolean availTable[] = new boolean[DataType.values().length];
    
    public boolean[] getAvailTable(){
        return availTable;
    }
    
    public void setAudioSensoryData(byte[] data){
    	this.data = data;
    }
    
    public byte[] getAudioSensoryData(){
    	return data;
    }
}
