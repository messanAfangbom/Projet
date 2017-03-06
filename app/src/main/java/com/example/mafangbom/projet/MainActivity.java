package com.example.mafangbom.projet;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import static android.graphics.Color.RGBToHSV;
import static android.graphics.Color.blue;
import static android.graphics.Color.green;
import static android.graphics.Color.red;
import static android.graphics.Color.rgb;

public class MainActivity extends AppCompatActivity {
    SeekBar sb;
    int progress = 50;
    private Toolbar toolbar;
    public   ImageView imageToUpload;
    public Bitmap reset;
    public  Bitmap currentBitmap;
    private static final int PICK_IMAGE = 100;
    Uri selectedImage;
    static int [][] LAPLACIEN4 = {{0,1,0},{1,-4,1},{0,1,0}};
    static int [][] LAPLACIEN8 = {{1,1,1},{1,-8,1},{1,1,1}};
    static int [][] SOBEL1 = {{-1,0,1},{-2 ,0, 2},{-1,0,1}};
    static int [][] SOBEL2 = {{-1,-2,-1},{0 ,0, 0},{1,2,1}};
    static int [][] PREWITT1 = {{-1,0,1},{-1,0,1},{-1,0,1}};
    static int [][] PREWITT2 = {{-1,-1,-1},{0,0,0},{1,1,1}};
    static int[][] gaussien = {{1, 2, 3, 2, 1}, {2,6,8,6,2},{3,8,10,8,3},{2,6,8,6,2},{1,2,3,2,1}};
    static int [][] MOYENNE = {{3,3,3},{3,3,3},{3,3,3}};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_activity);
        setTitle("Projet Android");
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        imageToUpload = (ImageView) findViewById(R.id.imageToUpload);

        Button myButton = ( Button) findViewById(R.id.button);
        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivity.this, Next_Activity.class);
                startActivity(myIntent);
            }
        });
        sb = (SeekBar) findViewById(R.id.seekbar_luminosite);



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

public void luminosite (final Bitmap b){

    progress = 50;
    sb.setProgress(progress);
    sb.setMax(99);
    sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int i, boolean fromUser) {
            if (i < 50) {
                i = i - 50;
                Bitmap r = b.copy(Bitmap.Config.ARGB_8888, true);
                imageToUpload.setImageBitmap(lumsat((i), r, "luminosite"));
            } else if (i > 50){
                i = i % 50;
                Bitmap r = b.copy(Bitmap.Config.ARGB_8888, true);
                imageToUpload.setImageBitmap(lumsat((i), r, "luminosite"));
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.griser:
                imageToUpload.setImageBitmap(toGrayTableau(currentBitmap));
                break;
            case R.id.contrasteCouleur:
                imageToUpload.setImageBitmap(Contraste(currentBitmap));
                break;
            case R.id.extensiongris:
                imageToUpload.setImageBitmap(extDgris(currentBitmap));
                break;
            case R.id.TeinteB:
                imageToUpload.setImageBitmap(garderTeinte("bleu",currentBitmap));
                break;
            case R.id.TeinteJ:
                imageToUpload.setImageBitmap(garderTeinte("jaune",currentBitmap));
                break;
            case R.id.TeinteM:
                imageToUpload.setImageBitmap(garderTeinte("magenta",currentBitmap));
                break;
            case R.id.TeinteR:
                imageToUpload.setImageBitmap(garderTeinte("rouge",currentBitmap));
                break;
            case R.id.TeinteV:
                imageToUpload.setImageBitmap(garderTeinte("vert",currentBitmap));
                break;
            case R.id.gallery:
                openGallery();
                break;
            case R.id.luminosite:






                Intent luminosite = new Intent(MainActivity.this, Luminosite.class);
                luminosite.putExtra("Bitmap",currentBitmap);
                startActivity(luminosite);
                break;
            case  R.id.laplacien:
                imageToUpload.setImageBitmap(convolute2(currentBitmap,LAPLACIEN8,"laplacien"));
                break;
            case R.id.sobel:
                imageToUpload.setImageBitmap(SobelPrewitt(currentBitmap,SOBEL1,SOBEL2));
                break;
            case R.id.prewitt:
                imageToUpload.setImageBitmap(SobelPrewitt(currentBitmap,PREWITT1,PREWITT2));
                break;
            case R.id.moyenne:
                imageToUpload.setImageBitmap(convolute2(currentBitmap,MOYENNE,"moyenne"));
                break;
            case R.id.gaussien:
                imageToUpload.setImageBitmap(convolute2(currentBitmap,gaussien,"gauss"));
                break;
            case R.id.action_reset:
                imageToUpload.setImageBitmap(currentBitmap);
                break;
            case R.id.camera:
                openCamera();
                break;
            case R.id.saturation:
                Intent saturation = new Intent(MainActivity.this, Luminosite.class);
                saturation.putExtra("Bitmap",currentBitmap);
                startActivity(saturation);
                break;


        }
        return super.onOptionsItemSelected(item);
    }

    private void openGallery(){
        Intent intent = new Intent();
        intent.setType("image/");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE);
    }

    private void openCamera() {
        Intent camera = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(camera, 0);
    }

/*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            Uri imageUri = data.getData();
            imageToUpload.setImageURI(imageUri);
        }
        if (resultCode == RESULT_OK && requestCode == 0) {
            Bitmap bit = (Bitmap) data.getExtras().get("data");
            currentBitmap = bit.copy(bit.getConfig(),true);
            imageToUpload.setImageBitmap(currentBitmap);
        }

    }  */


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 0) {
            Bitmap bit = (Bitmap) data.getExtras().get("data");
            currentBitmap = bit.copy(bit.getConfig(),true);
            imageToUpload.setImageBitmap(currentBitmap);

        }
        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK && data!=null) {
                    selectedImage = data.getData();
                    InputStream imageStream = null;
                    try{
                        imageStream = getContentResolver().openInputStream(selectedImage);
                        currentBitmap = BitmapFactory.decodeStream(imageStream);
                        imageToUpload.setImageBitmap(currentBitmap);

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    finally {
                        {
                            if(imageStream != null){
                                try{
                                    imageStream.close();
                                } catch (IOException e) {
                                  //  e.printStackTrace();
                                }
                            }
                        }
                    }

                }


            }


    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float)width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }


    public Bitmap toGrayTableau(Bitmap bMap) {
        int[] Pixels = new int[bMap.getWidth() * bMap.getHeight()];
        Bitmap copie = bMap.copy(bMap.getConfig(),true);
        copie.getPixels(Pixels, 0, bMap.getWidth(), 0, 0, bMap.getWidth(), bMap.getHeight());
        for (int i = 0; i < Pixels.length; ++i) {
            int rd = Color.red(Pixels[i]);
            int vt = Color.green(Pixels[i]);
            int bl = Color.blue(Pixels[i]);
            rd = (int) (0.3 * rd + 0.59 * vt + 0.11 * bl);
            Pixels[i] = Color.rgb(rd, rd, rd);
        }
        copie.setPixels(Pixels,0,bMap.getWidth(),0,0,bMap.getWidth(),bMap.getHeight());
        return copie;
    }


    public Bitmap Contraste (Bitmap bMap){

        int [] tableau = new int [bMap.getWidth()*bMap.getHeight()];
        bMap.getPixels(tableau,0,bMap.getWidth(),0,0,bMap.getWidth(),bMap.getHeight());
        Bitmap copie = bMap.copy(bMap.getConfig(),true);
        toGrayTableau(copie);
        int [] Pixels = new int [bMap.getWidth() * bMap.getHeight()];
        int min = 255;
        int max = 0;
        copie.getPixels(Pixels,0,bMap.getWidth(),0,0,bMap.getWidth(),bMap.getHeight());

        for ( int ng = 0; ng < Pixels.length;ng++) { // je recupere le max et le min des niveaux gris
            if (red(Pixels[ng]) < min) {
                min = red(Pixels[ng]);
            }
            if ( red(Pixels[ng]) > max) {
                max = red(Pixels[ng]);
            }
        }
        int LUT [] = new int [256]; // je cree une LUT DE 256 nivreau de gris c'est a dire de 0 a 255
        int dif = max - min ;
        for ( int k = 0;  k < LUT.length ;++k) {
            int a = (255 * (k - min)) / dif;
            if ( a < 0){
                LUT[k] = 0;
            }
            if ( a > 255){
                LUT[k] = 255;
            }
            if ( a > 0 && a <256){
                LUT[k] = a;
            }
        }
        for ( int i = 0; i < tableau.length  ;++i){
            tableau[i] = Color.rgb(LUT[red(tableau[i])], LUT[green(tableau[i])],LUT[blue(tableau[i])]);
        }

        copie.setPixels(tableau,0,bMap.getWidth(),0,0,bMap.getWidth(),bMap.getHeight());
        return copie;
    }

    public Bitmap extDgris(Bitmap bMap){

        Bitmap copie = bMap.copy(bMap.getConfig(),true);
        toGrayTableau(copie);
        int [] Pixels = new int [bMap.getWidth() * bMap.getHeight()];
        int min = 255;
        int max = 0;

        copie.getPixels(Pixels,0,bMap.getWidth(),0,0,bMap.getWidth(),bMap.getHeight());

        for ( int ng = 0; ng < Pixels.length;ng++) { // Ceci est un debut de l'extension dynamique mais que j'ai pas termininé. toute la fonction extDgris marche
            if (red(Pixels[ng]) < min) {
                min = red(Pixels[ng]);
            }
            if ( red(Pixels[ng]) > max) {
                max = red(Pixels[ng]);
            }
        }
        int LUT [] = new int [256];
        int dif = max - min ;
        for ( int k = 0;  k < LUT.length ;++k) {
            LUT[k] = (255 * (k - min)) / dif;
        }
        for ( int i = 0; i < Pixels.length  ;++i){
            Pixels[i] = Color.rgb(LUT[red(Pixels[i])], LUT[red(Pixels[i])],LUT[red(Pixels[i])]);
        }

        copie.setPixels(Pixels,0,bMap.getWidth(),0,0,bMap.getWidth(),bMap.getHeight());
        return copie;
    }

    public Bitmap garderTeinte(String monchoix, Bitmap bMap) { // pour garder uniquement le rouge d'une image et extDgris le reste
        int height = bMap.getHeight();
        int width = bMap.getWidth();
        int[] pixelTab = new int[height * width]; // à l'aide d'un tableau
        Bitmap copie = bMap.copy(bMap.getConfig(),true);
        copie.getPixels(pixelTab, 0, bMap.getWidth(), 0, 0, width, height); // on prend les pixels
        for (int i = 0; i < pixelTab.length; ++i) {
            int rD = Color.red(pixelTab[i]);
            int bD = Color.blue(pixelTab[i]);
            int gD = Color.green(pixelTab[i]);
            int alpha = Color.alpha(pixelTab[i]);
            int x = (int) (0.3 * rD + 0.59 * gD + 0.11 * bD);
            float[] hs = new float[3];
            RGBToHSV(rD, bD, gD, hs);
            switch (monchoix) {
                case "rouge":

                    if (hs[0] < 15 || hs[0] > 345) {
                        pixelTab[i] = Color.HSVToColor(alpha, hs);
                    } else {
                        pixelTab[i] = Color.argb(alpha,x, x, x);
                    }
                    break;
                case "vert" :
                    if (hs[0]> 90 && hs[0] < 140) {
                        pixelTab[i] = Color.HSVToColor(alpha, hs);
                    }
                    else {
                        pixelTab[i] = Color.argb(alpha, x, x, x);
                    }
                    break;
                case "magenta" :
                    if ( hs[0] > 300 && hs[0] < 320){
                        pixelTab[i] = Color.HSVToColor(alpha, hs);
                    }
                    else {
                        pixelTab[i] = Color.argb(alpha,x, x, x);
                    }break;
                case "jaune" :
                    if (hs[0] > 50 && hs[0]<60){
                        pixelTab[i] = Color.HSVToColor(alpha, hs);
                    }
                    else {
                        pixelTab[i] = Color.argb(alpha,x, x, x);
                    }break;
                case "bleu" :
                    if (hs[0] > 210 && hs[0] < 250){
                        pixelTab[i] = Color.HSVToColor(alpha, hs);
                    }
                    else {
                        pixelTab[i] = Color.argb(alpha,x, x, x);
                    }break;

            }
        }
        copie.setPixels(pixelTab, 0, width, 0, 0, width, height); // on applique les changements à l'image
      return copie;
    }



    public Bitmap convolute2 (Bitmap b, int [][] filtre, String monchoix) {
        int s = 0, t = filtre.length;
        for (int i = 0; i < t; ++i) {
            for (int j = 0; j < t; ++j) {
                s = Math.abs(s + filtre[i][j]); // on recupere la somme des coefficients du filtre;
            }
        }
        int width = b.getWidth();
        int heigth = b.getHeight();
        int[] tableauPixel = new int[width * heigth];
        Bitmap clonage = b.copy(b.getConfig(),true);
        if (monchoix.equals("laplacien")) {
           clonage = toGrayTableau(clonage);
        }
        clonage.getPixels(tableauPixel, 0, width, 0, 0, width, heigth);
        int[] copie = tableauPixel.clone(); //on fait un clone du tableau pour ne pas faire la modification et repasser la dessus
        for (int i = t / 2; i < width - 2; ++i) {
            for (int j = t / 2; j < heigth - 2; ++j) {
                int sommer = 0, sommeg = 0, sommeb = 0;
                //int PixelPrincpl = copie[j + i * width];
                for (int k = 0; k < t; ++k) {
                    for (int l = 0; l < t; ++l) {
                        int indice = ((j - t / 2) + (i - t / 2) * width) + k * width + l;  //on recupere l'indice des voisin a commencer par le premier y compris le pixel pricipal lui-meme
                        int Pixel = copie[indice];
                        sommer += red(Pixel) * filtre[k][l];
                        sommeg += green(Pixel) * filtre[k][l];
                        sommeb += blue(Pixel) * filtre[k][l];
                    }

                }
                if (monchoix.equals("laplacien")) {

                    if (sommer > 255) {
                        sommer = 255;
                    }
                    if (sommeb > 255) {
                        sommeb = 255;
                    }
                    if (sommeg > 255) {
                        sommeg = 255;
                    }
                    if (sommeg < 0) {
                        sommeg = 0;
                    }
                    if (sommer < 0) {
                        sommer = 0;
                    }
                    if (sommeb < 0) {
                        sommeb = 0;
                    }
                    int couleur = rgb(sommer, sommeg, sommeb);
                    tableauPixel[(j - t / 2) + (i - t / 2) * width] = couleur;
                } else {
                    int couleur = rgb(sommer/s, sommeg/ s, sommeb/s);
                    tableauPixel[(j - t / 2) + (i - t / 2) * width] = couleur;
                }
            }
        }
        clonage.setPixels(tableauPixel, 0, width, 0, 0, width, heigth);
        return clonage;
    }


    public Bitmap SobelPrewitt (Bitmap b, int [][] filtre1, int [][] filtre2){
        int t = filtre1.length;
        int width = b.getWidth();
        int heigth = b.getHeight();
        int[] tableauPixel = new int[width * heigth];
        Bitmap retour  = b.copy(b.getConfig(),true);
        retour = toGrayTableau(retour);
        retour.getPixels(tableauPixel, 0, width, 0, 0, width, heigth);
        int[] copie = tableauPixel.clone(); //on fait un clone du tableau pour ne pas faire la modification et repasser la dessus
        for (int i = t / 2; i < width - 2; ++i) {
            for (int j = t / 2; j < heigth - 2; ++j) {
                int gradientRX = 0;
                int gradientGX = 0;
                int gradientBX = 0;
                int gradientRY = 0;
                int gradientGY = 0 ;
                int gradientBY = 0;
                //int PixelPrincpl = copie[j + i * width];
                for (int k = 0; k < t; ++k) {
                    for (int l = 0; l < t; ++l) {
                        int indice = ((j - t / 2) + (i - t / 2) * width) + k * width + l;  //on recupere l'indice des voisin a commencer par le premier y compris le pixel pricipal lui-meme
                        int Pixel = copie[indice];
                        gradientRX = (int) (gradientRX + red(Pixel) *(filtre1[k][l]));
                        gradientGX = (int) (gradientGX + green(Pixel) * (filtre1[k][l]));
                        gradientBX = (int) (gradientBX + blue(Pixel) * (filtre1[k][l]));

                        gradientRY = (int) (gradientRY + red(Pixel) * (filtre2[k][l]));
                        gradientGY = (int) (gradientGY + green(Pixel) * (filtre2[k][l]));
                        gradientBY = (int) (gradientBY + blue(Pixel) * (filtre2[k][l]));

                    }
                    int NormeR = (int) Math.sqrt(gradientRX * gradientRX + gradientRY * gradientRY);
                    int NormeV = (int) Math.sqrt(gradientGX * gradientGX + gradientGY * gradientGY);
                    int NormeB = (int) Math.sqrt(gradientBX * gradientBX + gradientBY * gradientBY);
                    if (NormeB > 255) {
                        NormeB = 255;
                    }
                    if (NormeR > 255) {
                        NormeR = 255;
                    }
                    if (NormeV > 255) {
                        NormeV = 255;
                    }

                    int couleur = Color.rgb(NormeR, NormeV, NormeB);

                    tableauPixel[(j - t / 2) + (i - t / 2) * width] = couleur;
                }
            }
        }
        retour.setPixels(tableauPixel, 0, width, 0, 0, width, heigth);
        return retour;
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


