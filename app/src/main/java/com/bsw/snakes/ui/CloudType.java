package com.bsw.snakes.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;

import com.bsw.snakes.R;
import com.bsw.snakes.helpers.interfaces.BitmapMethods;
import com.bsw.snakes.main.MainActivity;

public enum CloudType implements BitmapMethods {

    CLOUD1(R.drawable.cloud1,26,13, 11),
    CLOUD2(R.drawable.cloud2,48,8, 11),
    CLOUD3(R.drawable.cloud3,13,5,11),
    CLOUD4(R.drawable.cloud4,36,14,11);

    private final int width;
    private final int height;
    private final int scale;
    private final Bitmap image;

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

    public Bitmap getTintedImg(){

        Bitmap finalBitmap = Bitmap.createBitmap(image.getWidth()+ 3*scale, image.getHeight(), image.getConfig());

        Canvas canvas = new Canvas(finalBitmap);

        Paint paint = new Paint();
        ColorFilter filter = new LightingColorFilter(0xBF404040, 0x00000000);
        paint.setColorFilter(filter);

        canvas.drawBitmap(image,new Matrix(),paint);



        return finalBitmap;
    }

}
