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
    private String[] Effects;
    private String Instrument;

    public Hands() {
        Effects = new String[5];
        Instrument = "Space";
        Effects[0] = null;
        Effects[1] = null;
        Effects[2] = null;
        Effects[3] = null;
        Effects[4] = null;
    }

    public void setInstrument(String instrument){
        Instrument = instrument;
    }
    public String getInstrument(){
        return Instrument;
    }
    public void setEffects(int id, String effect){
        Effects[id] = effect;
    }
    public String getEffect(int id){
        return Effects[id];
    }
    public String[] getAllEffect(){
        return Effects;
    }
    public void showSelected(){
        for(int i = 0 ; i < Effects.length;  i++){
            Log.d("Hands ","["+ Integer.toString(i) + "]: " + Effects[i] );
        }
        Log.d("Hands ", Instrument);
    }

}
