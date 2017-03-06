package com.example.mafangbom.projet;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;

/**
 * Created by mafangbom on 22/02/17.
 */

public class Luminosite extends AppCompatActivity {
    SeekBar sb;
    int progress = 50;
    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Bitmap bmap = (Bitmap) this.getIntent().getParcelableExtra("Bitmap");
        setContentView(R.layout.activity_luminosite);
        image = (ImageView) findViewById(R.id.imageAmodifier);
        image.setImageBitmap(bmap);


        /*BitmapFactory.Options options = new BitmapFactory.Options();// je cree une option qui sera attribué a un une de type bitmap
        options.inMutable = true;
        options.inScaled = false;
        setTitle("Projet Android");

        final Bitmap bMap = BitmapFactory.decodeResource(getResources(), R.drawable.lena_gris, options);*/
        sb = (SeekBar) findViewById(R.id.seekbar_luminosite);
        sb.setProgress(progress);
        sb.setMax(99);
        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean fromUser) {
                if (i < 50) {
                    i = i - 50;
                     Bitmap r = bmap.copy(Bitmap.Config.ARGB_8888, true);
                    image.setImageBitmap(lumsat((i), r, "luminosite"));
                } else if (i > 50){
                    i = i % 50;
                     Bitmap r = bmap.copy(Bitmap.Config.ARGB_8888, true);
                    image.setImageBitmap(lumsat((i), r, "luminosite"));
                }
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }


    public static Bitmap viewToBitmap(View view){
        int width = view.getWidth();
        int height = view.getHeight();
        Bitmap bitmap = Bitmap.createBitmap(width,height,Bitmap.Config.ARGB_8888);
        Bitmap copie = bitmap.copy(bitmap.getConfig(),true);
        Canvas canvas = new Canvas(copie);
        view.draw(canvas);
        return copie;

    }

    public Bitmap lumsat (int pourcentage, Bitmap b, String monchoix) {
        int [] pixelTab = new int [b.getHeight()*b.getWidth()];
        Bitmap copie = b.copy(b.getConfig(),true);
        copie.getPixels(pixelTab,0,b.getWidth(),0,0,b.getWidth(),b.getHeight());
        double d = (double) (pourcentage*0.01);
        for ( int i = 0; i < pixelTab.length ; ++i ) {
            int rd = Color.red(pixelTab[i]);
            int bd = Color.blue(pixelTab[i]);
            int gd = Color.green(pixelTab[i]);
            int alpha = Color.alpha(pixelTab[i]);
            float[] hsv = new float[3];
            Color.RGBToHSV(rd, gd, bd, hsv);
            switch (monchoix) {

                case "luminosite":
                    if (hsv[2] == 1 || hsv[2] == 0) {
                        pixelTab[i] = Color.HSVToColor(alpha,hsv);
                    }
                    hsv[2] = (float) (hsv[2] * d) + hsv[2];
                    if ( hsv [2] > 1){
                        hsv[2] = 1;
                    }
                    else if ( hsv[2] < 0) {
                        hsv[2] = 0;
                    }
                    pixelTab[i] = Color.HSVToColor(alpha,hsv);
                    break;
                case "saturation":

                    if (hsv[1] == 1 || hsv[1] == 0) {
                        pixelTab[i] = Color.HSVToColor(alpha,hsv);
                    }
                    hsv[1] = (float) (hsv[1] * d) + hsv[1];
                    if (hsv[1] > 1){
                        hsv[1] = 1;
                    }
                    else if(hsv[1] < 0){
                        hsv[1] = 0;
                    }
                    pixelTab[i] = Color.HSVToColor(alpha,hsv);

                    break;
            }
        }
        b.setPixels(pixelTab,0,b.getWidth(),0,0,b.getWidth(),b.getHeight());
        return  b;
    }
}
