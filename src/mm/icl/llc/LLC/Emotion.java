/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mm.icl.llc.LLC;

/**
 *
 * @author Nailbrainz
 */
public class Emotion extends RecognizedLLC{

    @Override
    public RecognizedLLC.Type getType() {
        return RecognizedLLC.Type.Emotion;
    }

    @Override
    public String toString() {
        //need to be implemented
        return "I'm angry";
    }

    @Override
    public boolean equals(Object other) {
        if(other instanceof Emotion){
            //need to be implemented
            
            return true;
        }
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
