/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mm.icl.hlc.HLCReasoner;

import org.apache.jena.ontology.OntModel;

/**
 *
 * @author Nailbrainz
 */
public class ContextVerifier {
    OntModel ont;
    
    /**
     * 
     * @param _ont 
     */
    public ContextVerifier(OntModel _ont){
    
    }
    
    public boolean isValid(OntModel unClassifiedContext){
        return true;
    }
    
    
}
