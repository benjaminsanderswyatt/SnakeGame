package com.bsw.snakes.entities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.bsw.snakes.MainActivity;
import com.bsw.snakes.R;
import com.bsw.snakes.helpers.interfaces.BitmapMethods;

public enum GameCharacters implements BitmapMethods {

    SNAKE(R.drawable.snake_spritesheet);

    private final Bitmap spriteSheet;
    private final Bitmap[][] sprites = new Bitmap[4][4];



    GameCharacters(int resID) {
        options.inScaled = false;
        spriteSheet = BitmapFactory.decodeResource(MainActivity.getGameContext().getResources(),resID, options);
        for(int j = 0; j < sprites.length; j++)
            for(int i = 0; i < sprites[j].length; i++)
                sprites[j][i] = getScaledBitmap(Bitmap.createBitmap(spriteSheet,16*i,16*j,16,16));

    }

    public Bitmap getSpriteSheet() {
        return spriteSheet;
    }


    public Bitmap getSprite(int yPos, int xPos){
        return sprites[yPos][xPos];
    }

}
