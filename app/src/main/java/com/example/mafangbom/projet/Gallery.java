package com.example.mafangbom.projet;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

/**
 * Created by mafangbom on 22/02/17.
 */

public class Gallery extends Activity {

    public ImageView imageView;
    public Uri imageBitmap;
    public int SELECT_IMAGE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gallery);

        imageView = (ImageView) findViewById(R.id.gallery);
        imageView.setVisibility(View.VISIBLE);

                openGallery();


    }


    private void openGallery(){
        Intent intent = new Intent();
        intent.setType("image/");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, SELECT_IMAGE);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    imageBitmap = data.getData();
                    // imageBitmap = imageBitmap.copy(Bitmap.Config.ARGB_8888, true);
                    imageView.setImageURI(imageBitmap);

                }
            }
        }

    }

}


