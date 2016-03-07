package complexability.motionmusicv2;

import android.content.Context;
import android.graphics.Color;
import android.media.effect.Effect;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Sorawis on 2/21/2016.
 */
public class CustomAdapter extends BaseAdapter {
    String[] data;
    String[] description;
    String[] selector;
    Context context;
    int[] images;
    LayoutInflater layoutInflater;


    public CustomAdapter(Context context, String[] data, int[] images, String[] descriptions, String[] selector){
        super();
        this.data = data;
        this.context = context;
        this.images = images;
        this.description = descriptions;
        this.selector = selector;
        layoutInflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return data.length;
    }

    @Override
    public Object getItem(int position) {
        return data[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = layoutInflater.inflate(R.layout.layout_single_row, null);


        TextView txt = (TextView)convertView.findViewById(R.id.effect_text_view);
        ImageView img = (ImageView)convertView.findViewById(R.id.effect_image_view);
        TextView txt2 = (TextView)convertView.findViewById(R.id.effect_description_view);
        txt.setText(data[position]);
        txt2.setText(description[position]);
        img.setImageResource(images[position]);

        if(selector[position] != null){
            switch (selector[position]){
                case "R_fw_selected":
                    convertView.setBackgroundColor(Color.parseColor("#A1D490"));
                    break;
                case    "R_ud_selected":
                    convertView.setBackgroundColor(Color.parseColor("#D4A490"));
                    break;
                case    "R_lr_selected":
                    convertView.setBackgroundColor(Color.parseColor("#90C0D4"));
                    break;
                case    "R_pitch_selected":
                    convertView.setBackgroundColor(Color.parseColor("#CC90D4"));
                    break;
                case    "R_roll_selected":
                    convertView.setBackgroundColor(Color.parseColor("#CED490"));
                    break;

                case    "L_fw_selected":
                    convertView.setBackgroundColor(Color.parseColor("#A1D490"));
                    break;
                case    "L_ud_selected":
                    convertView.setBackgroundColor(Color.parseColor("#D4A490"));
                    break;
                case    "L_lr_selected":
                    convertView.setBackgroundColor(Color.parseColor("#90C0D4"));
                    break;
                case    "L_pitch_selected":
                    convertView.setBackgroundColor(Color.parseColor("#CC90D4"));
                    break;
                case    "L_roll_selected":
                    convertView.setBackgroundColor(Color.parseColor("#CED490"));
                    break;
                case    "instrument_selected":
                    convertView.setBackgroundColor(Color.parseColor("#767873"));
                    break;

                default:
                    break;
            }
        }
        return convertView;
    }
}
