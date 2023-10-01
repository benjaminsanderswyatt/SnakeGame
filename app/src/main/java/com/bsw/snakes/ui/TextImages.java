package com.bsw.snakes.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.bsw.snakes.R;
import com.bsw.snakes.helpers.interfaces.BitmapMethods;
import com.bsw.snakes.main.MainActivity;

public enum TextImages implements BitmapMethods {


    NUMBERS(R.drawable.numbers,3,6,10,10);

    private int width, scale;
    private Bitmap[] textImages;

    TextImages(int resID, int width, int height,int number, int scale) {
        options.inScaled = false;
        this.width = width;
        this.scale = scale;


        textImages = new Bitmap[number];


        Bitmap imageAtlas = BitmapFactory.decodeResource(MainActivity.getGameContext().getResources(),resID, options);

        for(int i = 0; i < textImages.length; i++)
            textImages[i] = getImageScaledBitmap(Bitmap.createBitmap(imageAtlas,width * i ,0, width, height), scale);

    }

    private Bitmap getImageScaledBitmap(Bitmap bitmap, int scale){
        return Bitmap.createScaledBitmap(bitmap,bitmap.getWidth() * scale, bitmap.getHeight() * scale, false);
    }

    public Bitmap getTextImg(int number){
        return textImages[number];
    }

    public int getWidth(){
        return width;
    }
    public int getScale(){
        return scale;
    }


}
