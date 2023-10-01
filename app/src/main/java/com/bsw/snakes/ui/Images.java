package com.bsw.snakes.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.bsw.snakes.helpers.GameConstants;
import com.bsw.snakes.main.MainActivity;
import com.bsw.snakes.R;
import com.bsw.snakes.helpers.interfaces.BitmapMethods;

public enum Images implements BitmapMethods {

    SIGN(R.drawable.snake_sign,52,32, 16),
    GAME_PLAY_SAMPLE(R.drawable.game_play_sample,144,96, 6),
    SIGNPOST(R.drawable.sign_post,78,101, 10),
    HEIGHT(R.drawable.height,24,7,10),
    WIDTH(R.drawable.width,22,7,10);

    private int width,height, scale;
    private Bitmap image;

    Images(int resID, int width, int height, int scale){
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

    public int getScale(){
        return scale;
    }


    public Bitmap getImg(){
        return image;
    }


}
