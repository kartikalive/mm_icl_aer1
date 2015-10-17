/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mm.icl.llc.LLCRecognizer;

import mm.icl.llc.MachineLearningTools.MachineLearning;

/**
 *
 * @author Nailbrainz
 */
public abstract class LLCRecognizer {
    
    //Pointer for the machine learning instance
    protected MachineLearning ml;
    
    /**
     * 
     * @param input : sensor input data
     * @return recognized result
     */
    //public abstract RecognizedLLC recognize(final SensoryData input);
}
