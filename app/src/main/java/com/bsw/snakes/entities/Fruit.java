package com.bsw.snakes.entities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.bsw.snakes.main.MainActivity;
import com.bsw.snakes.R;
import com.bsw.snakes.helpers.interfaces.BitmapMethods;

public enum Fruit implements BitmapMethods {

    FRUIT(R.drawable.fruit_sprites);

    private final Bitmap spriteSheet;
    private final Bitmap[] sprites = new Bitmap[2];



    Fruit(int resID) {
        options.inScaled = false;
        spriteSheet = BitmapFactory.decodeResource(MainActivity.getGameContext().getResources(),resID, options);

        for(int i = 0; i < sprites.length; i++)
            sprites[i] = getScaledBitmap(Bitmap.createBitmap(spriteSheet,16*i,0,16,16));

    }

    public Bitmap getSpriteSheet() {
        return spriteSheet;
    }


    public Bitmap getSprite(int xPos){
        return sprites[xPos];
    }

}