package com.bsw.snakes.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.bsw.snakes.R;
import com.bsw.snakes.helpers.interfaces.BitmapMethods;
import com.bsw.snakes.main.MainActivity;

public enum CloudType implements BitmapMethods {

    CLOUD1(R.drawable.cloud1,26,13, 11),
    CLOUD2(R.drawable.cloud2,48,8, 11),
    CLOUD3(R.drawable.cloud3,13,5,11),
    CLOUD4(R.drawable.cloud4,36,14,11);

    private int width,height, scale;
    private Bitmap image;

    CloudType(int resID, int width, int height, int scale){
        options.inScaled = false;

        this.width = width;
        this.height = height;
        this.scale = scale;

        Bitmap imageAtlas = BitmapFactory.decodeResource(MainActivity.getGameContext().getResources(),resID, options);

        image = getImageScaledBitmap(Bitmap.createBitmap(imageAtlas,0,0, width, height), scale);

    }

    private Bitmap getImageScaledBitmap(Bitmap bitmap, int scale){
        return Bitmap.createScaledBitmap(bitmap,bitmap.getWidth() * scale, bitmap.getHeight() * scale, false);
    }

    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }

    public int getScale() {return scale;}

    public Bitmap getImg(){
        return image;
    }

}
