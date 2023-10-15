package com.bsw.snakes.ui;

import android.graphics.RectF;

public class CustomSlider {

    private int value = 3;
    //numOfSections is 21
    private final RectF hitbox;

    private boolean pushed;


    public CustomSlider(float x, float y, float width, float height, int scale){
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


    public int setTouchEventValue(float touchX){
        float posInSlider = touchX - hitbox.left;
        boolean found = false;

        for (int i = 0; i < 21; i++){
            float hi = i * hitbox.width() / 21 + hitbox.width() / 21;
            if (posInSlider < hi && !found){
                found = true;
                value = i;
            }
        }
        return value;
    }

}

