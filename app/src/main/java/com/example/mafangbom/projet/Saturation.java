package com.example.mafangbom.projet;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.SeekBar;

/**
 * Created by mafangbom on 06/03/17.
 */

public class Saturation extends Luminosite {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Bitmap bmap = (Bitmap) this.getIntent().getParcelableExtra("Bitmap");
        setContentView(R.layout.activity_saturation);
        image = (ImageView) findViewById(R.id.imageAmodifier);
        image.setImageBitmap(bmap);

        sb = (SeekBar) findViewById(R.id.seekbar_luminosite);
        sb.setProgress(progress);
        sb.setMax(99);
        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean fromUser) {
                if (i < 50) {
                    i = i - 50;
                    Bitmap r = bmap.copy(Bitmap.Config.ARGB_8888, true);
                    image.setImageBitmap(lumsat((i), r, "sauration"));
                } else if (i > 50){
                    i = i % 50;
                    Bitmap r = bmap.copy(Bitmap.Config.ARGB_8888, true);
                    image.setImageBitmap(lumsat((i), r, "saturation"));
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

}
