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
public abstract class LowLevelContext {
    
    
    
   /** 
    * @return representation of your dataType as String
    */
   @Override
   public abstract String toString();
   
   /** Please define the notion of logical equal of your class
    * @param other object to compare
    * @return returns true when @param other and instance of your class is logically equal 
    */
   @Override
   public abstract boolean equals(Object other);

}
