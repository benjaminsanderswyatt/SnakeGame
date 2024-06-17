package com.bsw.snakes.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.bsw.snakes.R;
import com.bsw.snakes.helpers.interfaces.BitmapMethods;
import com.bsw.snakes.main.MainActivity;

public enum ButtonImages implements BitmapMethods {

    MENU_START(R.drawable.start_btn, 26,14, 10),
    MENU_SETTINGS(R.drawable.settings_btn, 36,14, 10),
    MENU_STAR(R.drawable.star_btn, 14,14, 10),
    MENU_MUTE(R.drawable.mute_btn, 14,14, 10),
    MENU_QUESTION(R.drawable.question_btn, 14,14, 10),

    MENU_INPUT_ARROW(R.drawable.input_arrow_btn, 22,23, 10),
    MENU_INPUT_SWIPE(R.drawable.input_swipe_btn, 22,23, 10),


    RESTART(R.drawable.restart_btn, 32,14, 10),


    BACK_TO_MENU(R.drawable.menu_btn, 23,14, 10),

    PLAYING_PAUSE(R.drawable.pause_btn, 14,14, 10),

    UP_ARROW(R.drawable.up_arrow_btn, 14,14, 10),
    RIGHT_ARROW(R.drawable.right_arrow_btn, 14,14, 10),
    DOWN_ARROW(R.drawable.down_arrow_btn, 14,14, 10),
    LEFT_ARROW(R.drawable.left_arrow_btn, 14,14, 10);

    private final int width;
    private final int height;
    private final int scale;
    private final Bitmap normal;
    private final Bitmap pushed;


    ButtonImages(int resID, int width, int height, int scale){
        options.inScaled = false;
        this.width = width;
        this.height = height;
        this.scale = scale;

        Bitmap buttonAtlas = BitmapFactory.decodeResource(MainActivity.getGameContext().getResources(),resID, options);

        normal = getBtnScaledBitmap(Bitmap.createBitmap(buttonAtlas,0,0, width, height), scale);
        pushed = getBtnScaledBitmap(Bitmap.createBitmap(buttonAtlas, width,0, width, height), scale);

    }

    private Bitmap getBtnScaledBitmap(Bitmap bitmap, int scale){
        return Bitmap.createScaledBitmap(bitmap,bitmap.getWidth() * scale, bitmap.getHeight() * scale, false);
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



    public Bitmap getBtnImg(boolean isBtnPushed){
        return isBtnPushed ? pushed : normal;
    }



}
