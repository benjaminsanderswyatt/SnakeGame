package com.bsw.snakes.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

import com.bsw.snakes.R;
import com.bsw.snakes.helpers.interfaces.BitmapMethods;
import com.bsw.snakes.main.MainActivity;

public enum SliderImages implements BitmapMethods {

    SLIDER(R.drawable.slider_all, 61,7, 15);

    private int sliderWidth, height, scale;
    private Bitmap slider;


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

    //public Bitmap getSliderImg(){return slider;}
    //^ return the overlayed bitmap image of the slider chosen and over after its value gotten


    public Bitmap getSliderImg(int value){
        //BITMAPS slider, over, chosen

        int sliderWidth = slider.getWidth();
        int sliderHeight = slider.getHeight();



        Bitmap finalBitmap = Bitmap.createBitmap(sliderWidth+ 3*scale, sliderHeight, slider.getConfig());

        Canvas canvas = new Canvas(finalBitmap);

        canvas.drawBitmap(slider,null,new RectF((float)(1.5 * scale),0,sliderWidth+(float)(1.5 * scale),sliderHeight),null);


        Paint paint = new Paint();
        paint.setColor(0xC0366AB3);


        canvas.drawRect((float)(1.5 * scale),(float)(height * 3/7 * scale),(float)((value * 3 + 1.5)*scale),(float)(height * 4/7 * scale),paint);
        //3/7 & 4/7 are the pixel location on the slider(which has a height of 7) e.g. top is 3 pixels down bottom is 4

        paint.setColor(0xFF4674AF);

        float marginleft = (float) ((value * 3* scale) +(0.5*scale)+(float)(1.5 * scale));
        float margintop = (float) (height * 0.5 * scale);

        canvas.drawCircle(marginleft,margintop,(float)(1.5 * scale),paint);

        paint.setColor(0xFF274C7F);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth((float)(0.4*scale));
        canvas.drawCircle(marginleft,margintop,(float)(1.5 * scale),paint);



        return finalBitmap;
    }

}
