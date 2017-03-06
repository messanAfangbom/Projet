package com.example.mafangbom.projet;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.view.animation.PathInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import static android.graphics.Color.RGBToHSV;
import static android.graphics.Color.blue;
import static android.graphics.Color.green;
import static android.graphics.Color.red;
import static android.graphics.Color.rgb;

/**
 * Created by Messan on 27/01/2017.
 */

public class Next_Activity extends Activity {
    SeekBar sb;
    int progress = 50;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("vive du sujet");
        final ImageView image = (ImageView) findViewById(R.id.imageView3);
        BitmapFactory.Options options = new BitmapFactory.Options();// je cree une option qui sera attribué a un une de type bitmap
        options.inMutable = true;
        //options.inScaled = false;


        final Bitmap bMap = BitmapFactory.decodeResource(getResources(), R.drawable.lena, options);
        image.setImageBitmap(bMap);

        sb = (SeekBar) findViewById(R.id.seekBar);
        sb.setProgress(progress);
        sb.setMax(99);

        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean fromUser) {
                //i = progress;
                if (i < 50) {
                    i = i - 50;  // decider des valeur en fonction du chox sat ou lum
                    final Bitmap r = bMap.copy(Bitmap.Config.ARGB_8888, true);
                    image.setImageBitmap(lumsat((i), r, "saturation"));
                } else if (i > 50){
                    i = i % 50;
                    final Bitmap r = bMap.copy(Bitmap.Config.ARGB_8888, true);
                    image.setImageBitmap(lumsat((i), r, "saturation"));
                }

                //final Bitmap r = bMap.copy(Bitmap.Config.ARGB_8888, true); //pour la methode teinte il faut mettre le progress max a 360 et l'appeler comme suiit
                //image.setImageBitmap(Teinte(i,r));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        // l'option permet d'acceder a des attributs de l'image
         // je transforme  mon image en une bitmap image,

        TextView text = (TextView) findViewById(R.id.textView);
        text.setText("Me voici ! ");
        Button m = (Button) findViewById(R.id.button2);
        m.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toGrayTableau(bMap);
            }
        });
        Button m1 = (Button) findViewById(R.id.augmenter);
        m1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //egalHistoNivGris2(bMap);
                //extDgris(bMap);
                //egalHistoCouleur(bMap);
            }
        });
        Button m2 = (Button) findViewById(R.id.diminuer);
        m2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    //FiltreSobelOuPrewitt(bMap,SOBEL1,SOBEL2);
                   // FiltreSobelOuPrewitt(bMap,PREWITT1,PREWITT2);
                   // Convolution(bMap,gaussien,"gauss/moyenne");
                    convolute2(bMap,MOYENNE,"lap");
                //SobelPrewitt(bMap,PREWITT1,PREWITT2);
                //Convolution(bMap,LAPLACIEN8,"laplacien");
                //Contraste(bMap);

            }
        });
    }


    public void toGrayTableau(Bitmap bMap) {
        int[] Pixels = new int[bMap.getWidth() * bMap.getHeight()];
        int min = 255;
        int max = 0;

        bMap.getPixels(Pixels, 0, bMap.getWidth(), 0, 0, bMap.getWidth(), bMap.getHeight());
        for (int i = 0; i < Pixels.length; ++i) {
            int rd = Color.red(Pixels[i]);
            int vt = Color.green(Pixels[i]);
            int bl = Color.blue(Pixels[i]);
            rd = (int) (0.3 * rd + 0.59 * vt + 0.11 * bl);
            Pixels[i] = Color.rgb(rd, rd, rd);
        }
        bMap.setPixels(Pixels,0,bMap.getWidth(),0,0,bMap.getWidth(),bMap.getHeight());

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

    public void extDgris(Bitmap bMap){
        toGrayTableau(bMap);
        int [] Pixels = new int [bMap.getWidth() * bMap.getHeight()];
        int min = 255;
        int max = 0;

        bMap.getPixels(Pixels,0,bMap.getWidth(),0,0,bMap.getWidth(),bMap.getHeight());

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

        bMap.setPixels(Pixels,0,bMap.getWidth(),0,0,bMap.getWidth(),bMap.getHeight());
    }

    public void Contraste (Bitmap bMap){

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

        bMap.setPixels(tableau,0,bMap.getWidth(),0,0,bMap.getWidth(),bMap.getHeight());
    }


    public Bitmap Teinte(int teinte, Bitmap b) { //fait varier les teintes par la variable teinte qui represente l'angle associé a une couleur
        int [] Pixels = new int [b.getWidth() * b.getHeight()];
        b.getPixels(Pixels,0,b.getWidth(),0,0,b.getWidth(),b.getHeight()); // je recupere tous les pixels dans un tableau
        for (int i =0; i < Pixels.length;++i){
            int rd = Color.red(Pixels[i]);
            int vt = Color.green(Pixels[i]);
            int bl = Color.blue(Pixels[i]);
            int alpha = Color.alpha(Pixels[i]);
            float [] hsv = new float[3];
            Color.RGBToHSV(rd,vt,bl,hsv);
            if (teinte == 0){
                hsv[0] = hsv[0];
            }
            else {
                hsv[0] = teinte;
            }
            Pixels[i] = Color.HSVToColor(alpha,hsv);

        }
        b.setPixels(Pixels,0,b.getWidth(),0,0,b.getWidth(),b.getHeight());
        return  b;
    }
    static int [][] MOYENNE = {{1,1,1},{1,1,1},{1,1,1}};
    static int[][] gaussien = {{1, 2, 3, 2, 1}, {2,6,8,6,2},{3,8,10,8,3},{2,6,8,6,2},{1,2,3,2,1}};
    static int [][] SOBEL1 = {{-1,0,1},{-2 ,0, 2},{-1,0,1}};
    static int [][] SOBEL2 = {{-1,-2,-1},{0 ,0, 0},{1,2,1}};
    static int [][] PREWITT1 = {{-1,0,1},{-1,0,1},{-1,0,1}};
    static int [][] PREWITT2 = {{-1,-1,-1},{0,0,0},{1,1,1}};
    static int [][] LAPLACIEN4 = {{0,1,0},{1,-4,1},{0,1,0}};
    static int [][] LAPLACIEN8 = {{1,1,1},{1,-8,1},{1,1,1}};
    static int [][] LAPLACIENROB = {{1,-2,1},{-2,4,-2},{1,-2,1}};





    public Bitmap Convolution ( Bitmap b, int [][] filtre,String monchoix){

        int w = b.getWidth(), h = b.getHeight() , t = filtre.length;
        int s  = 0;
        for (int i = 0 ; i < t ; ++i){
            for ( int j = 0 ; j < t ; ++j){
                s = Math.abs(s + filtre[i][j]);
            }
        }
        Bitmap copie = b.copy(b.getConfig(),true);
        toGrayTableau(copie);
        for ( int i=(t/2); i< w-(t/2); ++i){
            for ( int j = t/2; j < h-(t/2) ; ++j) {
                int sommer = 0;
                int sommeg = 0;
                int sommeb = 0;
                for (int k = 0; k < t; ++k) {
                    for (int l = 0; l < t; ++l) {
                        int pixel = copie.getPixel(i - (t / 2) + k, j - (t / 2) + l);
                        int r = red(pixel);
                        int g = green(pixel);
                        int bd = blue(pixel);
                        sommer = (int) (sommer + r * (filtre[k][l]));
                        sommeg = (int) (sommeg + g * filtre[k][l]);
                        sommeb = (int) (sommeb + bd * filtre[k][l]);
                    }
                }
                switch (monchoix) {
                    case ("laplacien"):
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
                        int couleur = Color.rgb(sommer, sommeg, sommeb);
                        b.setPixel(i, j, couleur);
                        break;
                    case ("gauss/moyenne"):
                        int couleur1 = Color.rgb(sommer / s, sommeg / s, sommeb / s);
                        b.setPixel(i, j, couleur1);
                }
            }
        }
    return b;
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
        if (monchoix.equals("laplacien")) {
            toGrayTableau(b);
        }
        b.getPixels(tableauPixel, 0, width, 0, 0, width, heigth);
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
                    int couleur = rgb(sommer / s, sommeg / s, sommeb / s);
                    tableauPixel[(j - t / 2) + (i - t / 2) * width] = couleur;
                }
            }
        }
        b.setPixels(tableauPixel, 0, width, 0, 0, width, heigth);
        return b;
    }


    public Bitmap SobelPrewitt (Bitmap b, int [][] filtre1, int [][] filtre2){
        int t = filtre1.length;
        int width = b.getWidth();
        int heigth = b.getHeight();
        int[] tableauPixel = new int[width * heigth];
        toGrayTableau(b);
        b.getPixels(tableauPixel, 0, width, 0, 0, width, heigth);
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
        b.setPixels(tableauPixel, 0, width, 0, 0, width, heigth);
        return b;
    }

    public  Bitmap FiltreSobelOuPrewitt(Bitmap b, int [][] filtre, int filtre2[][]) {

        int w = b.getWidth(), h = b.getHeight(), t = filtre.length;
        Bitmap copie = b.copy(b.getConfig(), true);
            for (int i = (t / 2); i < w - (t / 2); ++i) {
                for (int j = t / 2; j < h - (t / 2); ++j) {
                    int gradientRX = 0;
                    int gradientGX = 0;
                    int gradientBX = 0;
                    int gradientRY = 0;
                    int gradientGY = 0 ;
                    int gradientBY = 0;
                    for (int k = 0; k < t; ++k) {
                        for (int l = 0; l < t; ++l) {

                            int pixel = copie.getPixel(i - (t / 2) + k, j - (t / 2) + l);
                            int r = red(pixel);
                            int g = green(pixel);
                            int bd = blue(pixel);

                            gradientRX = (int) (gradientRX + r *(filtre[k][l]));
                            gradientGX = (int) (gradientGX + g * (filtre[k][l]));
                            gradientBX = (int) (gradientBX + bd * (filtre[k][l]));

                            gradientRY = (int) (gradientRY + r * (filtre2[k][l]));
                            gradientGY = (int) (gradientGY + g * (filtre2[k][l]));
                            gradientBY = (int) (gradientBY + bd * (filtre2[k][l]));
                        }
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

                         int couleur1 = Color.rgb(NormeR, NormeV, NormeB);
                         b.setPixel(i, j, couleur1);

                }
            }

        return b.copy(b.getConfig(),true);
    }


    public double gauss ( int i , int j, double sigma){
        double a =  (1/(2* (Math.PI) * Math.pow(sigma,2)));
        double b = (Math.pow(i,2) + Math.pow(j,2))/(2* Math.pow(sigma,2));
        double r = a * b ;
        return r;
    }

    public  int[][] filtre ( double sigma,int valeurFilreMoyen, int taille, String monchoix){
        int[][] f = new int[taille][taille];

        if ( monchoix.equals("gaussien")) {

            for (int i = 0; i < taille; ++i) {
                for (int j = 0; j < taille; ++j) {
                    f[i][j] = (int) gauss (i -(taille/2),j-(taille/2), sigma);
                }
            }
        }
        else if (monchoix.equals("moyenne")){
            for ( int a = 0; a < taille; ++a){
                for ( int c =0; c < taille;++c){
                    f [a][c] = valeurFilreMoyen;
                }
            }
        }
        return f.clone();
    }




    public void garderTeinte(String monchoix, Bitmap bMap) { // pour garder uniquement le rouge d'une image et extDgris le reste
        int height = bMap.getHeight();
        int width = bMap.getWidth();
        int[] pixelTab = new int[height * width]; // à l'aide d'un tableau
        bMap.getPixels(pixelTab, 0, bMap.getWidth(), 0, 0, width, height); // on prend les pixels
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
                    if (hs[0] > 120 && hs[0] < 160) {
                        pixelTab[i] = Color.HSVToColor(0, hs);
                    } else {
                        pixelTab[i] = Color.argb(alpha,x, x, x);
                    }
                    break;
                case "magenta" :
                    if ( hs[0] > 285 && hs[0] < 315){
                        pixelTab[i] = Color.HSVToColor(alpha, hs);
                    }
                    else {
                        pixelTab[i] = Color.argb(alpha,x, x, x);
                    }break;
                case " jaune" :
                    if (hs[0] > 45 && hs[0] < 75){
                        pixelTab[i] = Color.HSVToColor(alpha, hs);
                    }
                    else {
                        pixelTab[i] = Color.argb(alpha,x, x, x);
                    }break;
                case "bleu" :
                    if (hs[0] > 165 && hs[0] < 255){
                        pixelTab[i] = Color.HSVToColor(alpha, hs);
                    }
                    else {
                        pixelTab[i] = Color.argb(alpha,x, x, x);
                    }break;

            }
        }
        bMap.setPixels(pixelTab, 0, width, 0, 0, width, height); // on applique les changements à l'image

    }

    public void egalHistoNivGris(Bitmap bMap) {

        extDgris(bMap);
        int [] Pixels = new int [bMap.getWidth() * bMap.getHeight()];

        bMap.getPixels(Pixels,0,bMap.getWidth(),0,0,bMap.getWidth(),bMap.getHeight());
        int h [] = new int [256];  // tableau qui va recuperer le nombre de chaque niveau gris

        for ( int i = 0; i < Pixels.length;++i){   // je compe l'effectif de  chaque niveau gris  qui est compris entre 0 et 255

            h[Color.red(Pixels[i])] = h [Color.red(Pixels[i])]+ 1;
        }

        for ( int j = 1; j < h.length;j++){
            h[j] =  h[j-1]+h[j] ;            //histogramme cumulé
        }
        // toGraydynamique2(bMap); // eon étire la dynamique en réecholonnant les niveaux gris entre 0 et 255 pas vraiment besoin
        int m = 0;
        int ng = 0;
        for  ( int k =0; k < Pixels.length;++k) {
            ng = Color.red(Pixels[k]); // je recupere l'ancien niveau gris sur un canal ici le canla red
            m = (h[ng]*255) / Pixels.length; // je prend le  pourcentage de l'ancien ng inferieur au niveau gris k que je multiplie par par 255
            Pixels[k] = Color.rgb(m,m,m); // je remets cette valeur dans le pixel
        }

        bMap.setPixels(Pixels,0,bMap.getWidth(),0,0,bMap.getWidth(),bMap.getHeight());
    }



    public void egalHistoNivGris2(Bitmap bMap) {
        Bitmap copie = bMap.copy(bMap.getConfig(),true);
        extDgris(copie);

        int [] Pixels = new int [bMap.getWidth() * bMap.getHeight()];
        int [] tableau  = Pixels.clone();
        bMap.getPixels(tableau,0,bMap.getWidth(),0,0,bMap.getWidth(),bMap.getHeight());
        copie.getPixels(Pixels,0,bMap.getWidth(),0,0,bMap.getWidth(),bMap.getHeight());
        int h [] = new int [256];  // tableau qui va recuperer le nombre de chaque niveau gris

        for ( int i = 0; i < Pixels.length;++i){   // je compe l'effectif de  chaque niveau gris  qui est compris entre 0 et 255

            h[Color.red(Pixels[i])] = h [Color.red(Pixels[i])]+ 1;
        }

        for ( int j = 1; j < h.length;j++){
            h[j] =  h[j-1]+h[j] ;            //histogramme cumulé
        }
        int mr = 0 , mg = 0 , mb = 0 , nr = 0 , ng =0, nb = 0 ;
        for (int k = 0 ; k < Pixels.length; ++ k ){
            nr = Color.red(tableau[k]);
            ng = Color.green((tableau[k]));
            nb = Color.blue(tableau[k]);
            mr = (h[nr] * 255) /Pixels.length;
            if ( mr > 255){
                mr = 255;
            }
            if ( mr < 0){
                mr = 0;
            }
            mg = (h[ng]*255)/Pixels.length;
            if (mg > 255){
                mg = 255;
            }
            if (mg < 0){
                mg = 0;
            }
            mb = (h[nb]*255)/Pixels.length;
            if ( mb > 255){
                mb = 255;
            }
            if ( mb < 0){
                mb = 0;
            }
            tableau[k] = Color.rgb(mr,mg,mb);

        }

        bMap.setPixels(tableau,0,bMap.getWidth(),0,0,bMap.getWidth(),bMap.getHeight());
    }






    public void egalHistoCouleur (Bitmap bMap) {
        int [] Pixels = new int [bMap.getWidth() * bMap.getHeight()];

        bMap.getPixels(Pixels,0,bMap.getWidth(),0,0,bMap.getWidth(),bMap.getHeight());
        int hr [] = new int [256], hg [] = new int [256] , hb [] =  new int [256];

        for (int i = 0; i < Pixels.length; ++i) {
            hr[Color.red(Pixels[i])] = hr[Color.red(Pixels[i])] + 1 ;
            hg[Color.green(Pixels[i])] = hg[Color.green(Pixels[i])] +1;
            hb[Color.blue(Pixels[i])] = hb[Color.blue(Pixels[i])] + 1;

        }

        for ( int j = 1 ; j <hr.length;++j){
            hr[j] = hr[j-1] + hr[j];
            hg[j] = hg[j-1] + hg[j];
            hb[j] = hb[j-1] + hb[j];

        }
            Contraste(bMap);
        int mr = 0 , mg = 0 , mb = 0 , nr = 0 , ng =0, nb = 0 ;
        for (int k = 0 ; k < Pixels.length; ++ k ){
            nr = Color.red(Pixels[k]);
            ng = Color.green((Pixels[k]));
            nb = Color.blue(Pixels[k]);
            mr = (hr[nr] * 255) /Pixels.length;
            if ( mr > 255){
                mr = 255;
            }
            if ( mr < 0){
                mr = 0;
            }
            mg = (hg[ng]*255)/Pixels.length;
            if (mg > 255){
                mg = 255;
            }
            if (mg < 0){
                mg = 0;
            }
            mb = (hb[nb]*255)/Pixels.length;
            if ( mb > 255){
                mb = 255;
            }
            if ( mb < 0){
                mb = 0;
            }
            Pixels[k] = Color.rgb(mr,mg,mb);

        }
        bMap.setPixels(Pixels,0,bMap.getWidth(),0,0,bMap.getWidth(),bMap.getHeight());

    }




}
