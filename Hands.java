package complexability.motionmusicv2;

import android.util.Log;

/**
 * Created by Sorawis on 2/1/2016.
 */
public class Hands {
    /**
     * id
     * 0 = forward-back
     * 1 = up-down
     * 2 = left-right
     * 3 = pitch (tilt forward)
     * 4 = roll (tilt LR)
     */

    private static final int NONE       = 0;
    private static final int VOLUME     = 1;
    private static final int FREQUENCY  = 2;
    private static final int REVERB     = 3;
    private static final int DELAY      = 4;
    private static final int FLANGER    = 5;
    private static final int DISTORTION = 6;
    private static final int ROTARY     = 7;
    private static final int VIBRATO    = 8;

    private static final int SPACY      = 1000;
    private static final int GUITAR     = 1001;
    private static final int FLUTE      = 1002;
    private int[] Effects;
    private int Instrument;

    public Hands() {
        Effects = new int[5];
        Instrument = 1000;
        Effects[0] = 0;
        Effects[1] = 0;
        Effects[2] = 0;
        Effects[3] = 0;
        Effects[4] = 0;
    }

    public void setInstrument(int instrument){
        Instrument = instrument;
    }
    public int  getInstrument(){
        return Instrument;
    }
    public void setEffects(int id, int effect){
        Effects[id] = effect;
    }
    public int getEffect(int id){
        return Effects[id];
    }
    public int[] getAllEffect(){
        return Effects;
    }
    public void showSelected(){
        for(int i = 0 ; i < Effects.length;  i++){
            Log.d("TEST ","Effect["+ effectType(i) + "]: " + getDefinedString(Effects[i]) );
        }
        //Log.d("Hands ", getDefinedString(Instrument));
    }
    public String getDefinedString(int data){
        switch (data){
            case NONE:
                return "None";
            case VOLUME:
                return "Volume";
            case FREQUENCY:
                return "Frequency";
            case REVERB:
                return "Reverb";
            case DELAY:
                return "Delay";
            case FLANGER:
                return "Flanger";
            case DISTORTION:
                return "Distortion";
            case ROTARY:
                return "Rotary";
            case VIBRATO:
                return "Vibrato";
            case SPACY:
                return "Spacy";
            case GUITAR:
                return "Guitar";
            case FLUTE:
                return "Flute";

            default:
                return null;
        }
    }
    public String effectType(int type){
        switch(type){
            case 0:
                return "PITCH";
            case 1:
                return "ROLL";
            case 2:
                return "Y-AXIS";
            case 3:
                return "Z-AXIS";
            case 4:
                return "X-AXIS";
            default:
                return "NONE";

        }
    }
}
