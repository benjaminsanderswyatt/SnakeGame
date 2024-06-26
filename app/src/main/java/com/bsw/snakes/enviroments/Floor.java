package com.bsw.snakes.enviroments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.bsw.snakes.R;
import com.bsw.snakes.helpers.interfaces.BitmapMethods;
import com.bsw.snakes.main.MainActivity;

public enum Floor implements BitmapMethods {

    OUTSIDE(R.drawable.tileset_floor,2,4);

    private final Bitmap[] sprites;

    Floor(int resID, int tilesInWidth, int tilesInHeight){
        options.inScaled = false;
        sprites = new Bitmap[tilesInHeight * tilesInWidth];
        Bitmap spriteSheet = BitmapFactory.decodeResource(MainActivity.getGameContext().getResources(),resID, options);
        for(int j = 0; j < tilesInHeight; j++)
            for(int i = 0; i < tilesInWidth; i++){
                int index = j * tilesInWidth + i;
                sprites[index] = Bitmap.createBitmap(spriteSheet,16 * i,16 * j,16,16);
            }

    }

    public Bitmap getSprites(int id, float scale){
        return getScaledBitmap(sprites[id], scale);
    }


}
