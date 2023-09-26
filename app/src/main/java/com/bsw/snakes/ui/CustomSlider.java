package com.bsw.snakes.ui;

import android.graphics.RectF;

public class CustomSlider {

    private int value = 12;
    private int numOfSections = 21;
    private RectF hitbox;

    private boolean pushed;


    public CustomSlider(float x, float y, float width, float height, int scale){
        hitbox = new RectF(x,y,x + width * scale,y + height * scale);
    }

    public RectF getHitbox(){
        return hitbox;
    }

    public int getValue(){
        return value;
    }

    public void setValue(int value){
        this.value = value;
    }

    public boolean isPushed(){
        return pushed;
    }

    public void setPushed(boolean pushed){
        this.pushed = pushed;
    }


    public void setTouchEventValue(float touchX){
        float posInSlider = touchX - hitbox.left;
        boolean found = false;

        for (int i = 0; i < 21; i++){
            float hi = i * hitbox.width() / numOfSections + hitbox.width() / numOfSections;
            if (posInSlider < hi && !found){
                found = true;
                value = i;
            }
        }
    }

}

