package com.bsw.snakes.enviroments;

import android.graphics.Canvas;

public class GameMap {

    private final int[][] spriteIds;

    public GameMap(int[][] spriteIds){
        this.spriteIds = spriteIds;
    }


    public void draw(Canvas c, float DRAWOFFSETX, float DRAWOFFSETY, float scale){
        for (int j = 0; j < spriteIds.length; j++)
            for (int i = 0; i < spriteIds[j].length; i++){
                c.drawBitmap(Floor.OUTSIDE.getSprites(spriteIds[j][i], scale),i * (scale * 16) - DRAWOFFSETX,j * (scale * 16) - DRAWOFFSETY, null);
            }
    }
}
