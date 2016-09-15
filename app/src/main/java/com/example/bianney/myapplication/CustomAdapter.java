package com.example.bianney.myapplication;

/**
 * Created by Bianney on 13/08/2016.
 */

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bianney.myapplication.others.Monument;

import java.util.List;
import java.util.Random;

/********* Adapter class extends with BaseAdapter and implements with OnClickListener ************/
public class CustomAdapter extends BaseAdapter implements OnClickListener {

    private Activity activity;
    private List<Monument> monuments;
    private static LayoutInflater inflater = null;
    private static final String ERROR = "Error";
    Monument monument = null;
    Integer indexPager = 0;
    Context context;
    int i = 0;

    /*************  CustomAdapter Constructor *****************/
    public CustomAdapter(Activity a, List monuments, Integer indexPager) {

        /********** Take passed values **********/
        activity = a;
        this.monuments = null;
        this.monuments = monuments;
        this.indexPager = indexPager;
        this.context = a;

        /***********  Layout inflator to call external xml layout () ***********/
        inflater = ( LayoutInflater )activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    /******** What is the size of Passed Arraylist Size ************/
    public int getCount() {

        if (monuments.isEmpty())
            return 1;
        return monuments.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    /********* Create a holder Class to contain inflated xml file elements *********/
    public static class ViewHolder{
        private TextView text;
        private TextView text1;
        private ImageView image;
    }

    /****** Depends upon monuments size called for each row , Create each ListView row *****/
    public View getView(int position, View convertView, ViewGroup parent) {

        View vi = convertView;
        ViewHolder holder;

        if (convertView == null) {

            /****** Inflate tab_item.xmll file for each row ( Defined below ) *******/
            vi = inflater.inflate(R.layout.tab_item, null);

            /****** View Holder Object to contain tab_item.xmll file elements ******/
            holder = new ViewHolder();
            holder.text = (TextView) vi.findViewById(R.id.text);
            holder.text1 = (TextView) vi.findViewById(R.id.text1);
            holder.image = (ImageView) vi.findViewById(R.id.image);

            /************  Set holder with LayoutInflater ************/
            vi.setTag(holder);
        } else {
            holder = (ViewHolder) vi.getTag();
        }

        if (monuments.isEmpty()) {
            holder.text.setText("No Data");
        } else {
            /***** Get each Model object from Arraylist ********/
            monument = null;
            monument = monuments.get(position);
            /************  Set Model values in Holder elements ***********/

            holder.text.setText(monument.getName());
            holder.text1.setText(monument.getTown());
            //Debajo va la imagen del monumento
            if (indexPager == 0) {
                //extraemos el drawable en un bitmap
                Drawable originalDrawable;
                Random rnd = new Random();
                String nameImage = "";
                int img = (int) (rnd.nextDouble() * 5 + 1);
                switch (img) {
                    case 1:
                        originalDrawable = context.getResources().getDrawable(R.drawable.historical1);
                        nameImage = "historical1";
                        break;
                    case 2:
                        originalDrawable = context.getResources().getDrawable(R.drawable.historical21);
                        nameImage = "historical21";
                        break;
                    case 3:
                        originalDrawable = context.getResources().getDrawable(R.drawable.historical3);
                        nameImage = "historical3";
                        break;
                    case 4:
                        originalDrawable = context.getResources().getDrawable(R.drawable.historical4);
                        nameImage = "historical4";
                        break;
                    case 5:
                        originalDrawable = context.getResources().getDrawable(R.drawable.historical5);
                        nameImage = "historical5";
                        break;
                    default:
                        originalDrawable = context.getResources().getDrawable(R.drawable.historical69);
                        nameImage = "historical69";
                        break;
                }
                monument.setImage(nameImage);
                Log.d(ERROR, "Custom adpter: " + nameImage);
                Bitmap originalBitmap = ((BitmapDrawable) originalDrawable).getBitmap();
                //creamos el drawable redondeado
                RoundedBitmapDrawable roundedDrawable =
                        RoundedBitmapDrawableFactory.create(context.getResources(), originalBitmap);
                //asignamos el CornerRadius
                roundedDrawable.setCornerRadius(originalBitmap.getHeight());
                holder.image.setImageDrawable(roundedDrawable);
            } else if (indexPager == 1) {
                Drawable originalDrawable;
                Random rnd = new Random();
                int img = (int) (rnd.nextDouble() * 5 + 1);
                String nameImage = "";
                switch (img) {
                    case 1:
                        originalDrawable = context.getResources().getDrawable(R.drawable.natural1);
                        nameImage = "natural1";
                        break;
                    case 2:
                        originalDrawable = context.getResources().getDrawable(R.drawable.natural21);
                        nameImage = "natural21";
                        break;
                    case 3:
                        originalDrawable = context.getResources().getDrawable(R.drawable.natural3);
                        nameImage = "natural3";
                        break;
                    case 4:
                        originalDrawable = context.getResources().getDrawable(R.drawable.natural4);
                        nameImage = "natural4";
                        break;
                    case 5:
                        originalDrawable = context.getResources().getDrawable(R.drawable.natural5);
                        nameImage = "natural5";
                        break;
                    default:
                        originalDrawable = context.getResources().getDrawable(R.drawable.natural2);
                        nameImage = "natural2";
                        break;
                }
                monument.setImage(nameImage);
                Log.d(ERROR, "Custom adpter: " + nameImage);
                Bitmap originalBitmap = ((BitmapDrawable) originalDrawable).getBitmap();
                //creamos el drawable redondeado
                RoundedBitmapDrawable roundedDrawable =
                        RoundedBitmapDrawableFactory.create(context.getResources(), originalBitmap);
                //asignamos el CornerRadius
                roundedDrawable.setCornerRadius(originalBitmap.getHeight());
                holder.image.setImageDrawable(roundedDrawable);
            }

            vi.setOnClickListener(new OnItemClickListener(position));
        }
        return vi;
    }

    @Override
    public void onClick(View v) {
        Log.d("TFGApplication", "=====Row button clicked=====");
    }

    /********* Called when Item click in ListView ************/
    private class OnItemClickListener  implements OnClickListener{
        private int mPosition;

        OnItemClickListener(int position){
            mPosition = position;
        }

        @Override
        public void onClick(View arg0) {
            MainActivity sct = (MainActivity) activity;
            sct.onItemClick(mPosition, indexPager);
        }
    }
}