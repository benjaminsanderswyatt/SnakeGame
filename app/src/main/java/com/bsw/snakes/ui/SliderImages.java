package com.bsw.snakes.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import com.bsw.snakes.R;
import com.bsw.snakes.helpers.interfaces.BitmapMethods;
import com.bsw.snakes.main.MainActivity;

public enum SliderImages implements BitmapMethods {

    SLIDER(R.drawable.slider, 61,7, 15);

    private final int sliderWidth;
    private final int height;
    private final int scale;
    private final Bitmap slider;


    SliderImages(int resID, int sliderWidth, int height, int scale){
        options.inScaled = false;
        this.sliderWidth = sliderWidth;
        this.height = height;
        this.scale = scale;

        Bitmap sliderAtlas = BitmapFactory.decodeResource(MainActivity.getGameContext().getResources(),resID, options);

        slider = getSdrScaledBitmap(Bitmap.createBitmap(sliderAtlas,0,0, sliderWidth, height),scale);

    }

    private Bitmap getSdrScaledBitmap(Bitmap bitmap, int scale){
        return Bitmap.createScaledBitmap(bitmap,bitmap.getWidth() * scale,bitmap.getHeight() * scale, false);
    }

    public int getWidth(){
        return sliderWidth;
    }

    public int getHeight(){
        return height;
    }

    public int getScale(){
        return scale;
    }


    public Bitmap getSliderImg(int value){

        Bitmap finalBitmap = Bitmap.createBitmap(slider.getWidth()+ 3*scale, slider.getHeight(), slider.getConfig());

        Canvas canvas = new Canvas(finalBitmap);

        canvas.drawBitmap(slider,null,new RectF((float)(1.5 * scale),0,slider.getWidth()+(float)(1.5 * scale),slider.getHeight()),null);


        Paint paint = new Paint();
        //paint.setColor(0xC0366AB3);
        paint.setColor(0xC0366AB3);


        canvas.drawRect((float)(1.5 * scale),(float)(3 * scale),(float)((value * 3 + 1.5)*scale),(float)(4 * scale),paint);


        paint.setColor(0xFF4674AF);
        canvas.drawCircle((float)((value * 3 + 0.5+1.5)*scale),(float)(height * 0.5 * scale),(float)(1.5 * scale),paint);

        paint.setColor(0xFF274C7F);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth((float)(0.4*scale));
        canvas.drawCircle((float)((value * 3 + 0.5+1.5)*scale),(float)(height * 0.5 * scale),(float)(1.5 * scale),paint);

        return finalBitmap;
    }

}
