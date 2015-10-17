/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mm.icl.llc.LLCRecognizer;

import java.util.List;
import mm.icl.llc.LLC.Activity;
import mm.icl.llc.LLC.RecognizedLLC;
import mm.icl.llc.sensorydata.SensoryData;

/**
 *
 * @author Nailbrainz 
 * 
 */
public abstract class ActivityRecognizer extends LLCRecognizer{
    
    /**
     * @param input sensor data for recognizing. can't be modified for thread-safe purpose
     * @return recoznized activity
     */
    public abstract List<Activity> recognize(final SensoryData input);
}
