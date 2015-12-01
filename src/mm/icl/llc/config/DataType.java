/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mm.icl.llc.config;

import mm.icl.llc.LLCRecognizer.AudioEmotionRecognizer;
/**
 *
 * @author Nailbrainz
 */
public enum DataType{
    SmartphoneInertial(0), WearableInertial(1), Location(2), Emotion(3);
    private int value;
    private DataType(int _value){
        value = _value;
    }
    public int getValue(){
        return value;
    }
    public Runnable getRunnable(Object data){
        switch(value){
        }
        return null;
    }
};
