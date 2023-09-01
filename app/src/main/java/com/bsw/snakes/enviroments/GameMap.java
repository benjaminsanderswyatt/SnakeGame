package com.bsw.snakes.enviroments;

import android.graphics.Canvas;

import com.bsw.snakes.helpers.GameConstants;

public class GameMap {

    private final int[][] spriteIds;

    public GameMap(int[][] spriteIds){
        this.spriteIds = spriteIds;
    }


    public void draw(Canvas c, float DRAWOFFSETX, float DRAWOFFSETY){
        for (int j = 0; j < spriteIds.length; j++)
            for (int i = 0; i < spriteIds[j].length; i++){
                c.drawBitmap(Floor.OUTSIDE.getSprites(spriteIds[j][i]),i * GameConstants.BITSCALER - DRAWOFFSETX,j * GameConstants.BITSCALER - DRAWOFFSETY, null);
            }
    }
}
