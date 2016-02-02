package complexability.motionmusicv2;

/**
 * Created by Sorawis on 2/1/2016.
 */
public class Hands {
    private String[] Effects;
    private String[] effect_tilt_lr;
    private String effect_tilt_fb;
    private String accelerate_x;
    private String accelerate_y;
    private String accelerate_z;

    public void assignEffect(int id, String effect){
        Effects[id] = effect;

        /*
        switch(id){
            case 0:
                effect_tilt_lr[id] = Effect;
                break;
            case 1:
                effect_tilt_fb = Effect;
                break;
            case 2:
                accelerate_x = Effect;
                break;
            case 3:
                accelerate_y = Effect;
                break;
            case 4:
                accelerate_z = Effect;
                break;
            default:
                break;

        }
        */
    }



}
