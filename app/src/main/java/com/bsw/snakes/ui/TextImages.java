package com.bsw.snakes.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.bsw.snakes.R;
import com.bsw.snakes.helpers.interfaces.BitmapMethods;
import com.bsw.snakes.main.MainActivity;

public enum TextImages implements BitmapMethods {


    NUMBERS(R.drawable.numbers,3,6,10,10);

    private int width, height, scale;
    private Bitmap[] textImages;

    TextImages(int resID, int width, int height,int number, int scale) {
        options.inScaled = false;
        this.width = width;
        this.height = height;
        this.scale = scale;


        textImages = new Bitmap[number];


        Bitmap imageAtlas = BitmapFactory.decodeResource(MainActivity.getGameContext().getResources(),resID, options);

        for(int i = 0; i < textImages.length; i++)
            textImages[i] = Bitmap.createBitmap(imageAtlas,width * i ,0, width, height);

    }

    private Bitmap getImageScaledBitmap(Bitmap bitmap, int scale){
        return Bitmap.createScaledBitmap(bitmap,bitmap.getWidth() * scale, bitmap.getHeight() * scale, false);
    }

    public Bitmap getTextImg(int number){
        return  getImageScaledBitmap(textImages[number] , scale);
    }

    public Bitmap getTintedTextImg(int number){
        //3D4E65
        Bitmap finalBitmap = Bitmap.createBitmap(width * scale, height * scale, textImages[number].getConfig());

        //replace pixel colour with another colour
        int[] pixels = new int[width * height];
        textImages[number].getPixels(pixels,0, width,0,0,width ,height);

        for (int y = 0; y < height; y++){
            for (int x = 0; x < width; x++){
                int index = y * width + x;
                if(pixels[index] == 0xFFFFFFFF){ //white
                    pixels[index] = 0xFF3D4E65;
                } else if (pixels[index] == 0xFF404040){ //grey shadow
                    pixels[index] = 0xFF596880;
                }
            }
        }
        finalBitmap.setPixels(pixels,0, width,0,0, width, height);

        return getImageScaledBitmap(finalBitmap, scale);
    }



    public int getWidth(){
        return width;
    }
    public int getHeight(){
        return height;
    }
    public int getScale(){
        return scale;
    }


}
