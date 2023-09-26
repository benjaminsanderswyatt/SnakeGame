package com.bsw.snakes.ui;

import android.graphics.RectF;

public class CustomButton{

    private RectF hitbox;

    private boolean pushed;
    public CustomButton(float x, float y, float width, float height, int scale){
        hitbox = new RectF(x,y,x + width * scale,y + height * scale);
    }

    public RectF getHitbox(){
        return hitbox;
    }

    public boolean isPushed(){
        return pushed;
    }

    public void setPushed(boolean pushed){
        this.pushed = pushed;
    }

}
