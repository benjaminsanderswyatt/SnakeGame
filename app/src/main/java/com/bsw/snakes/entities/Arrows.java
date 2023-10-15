package com.bsw.snakes.entities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.bsw.snakes.R;
import com.bsw.snakes.helpers.interfaces.BitmapMethods;
import com.bsw.snakes.main.MainActivity;

public enum Arrows implements BitmapMethods {

    ARROWS(R.drawable.arrows);

    private final Bitmap spriteSheet;
    private final Bitmap[] sprites = new Bitmap[4];



    Arrows(int resID) {
        options.inScaled = false;
        spriteSheet = BitmapFactory.decodeResource(MainActivity.getGameContext().getResources(),resID, options);

        for(int i = 0; i < sprites.length; i++)
            sprites[i] = Bitmap.createBitmap(spriteSheet,10*i,0,10,10);

    }

    public Bitmap getSpriteSheet() {
        return spriteSheet;
    }

    public Bitmap getSprite(int xPos, float scale){
        return getScaledBitmap(sprites[xPos], scale);
    }

}