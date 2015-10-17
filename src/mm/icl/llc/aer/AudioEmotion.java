package mm.icl.llc.aer;

import mm.icl.llc.LLC.Emotion;

/**
 *
 * @author lebavui
 */
public class AudioEmotion extends Emotion {

	private String emotion;
	
	public AudioEmotion(String emotion) {
		this.emotion = emotion;
	}
    
    @Override
    public String toString() {
        return emotion;
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof Emotion){
            //need to be implemented
            
            return true;
        }
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
