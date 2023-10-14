package com.bsw.snakes.ui;

import android.graphics.RectF;

public class CustomSlider {

    private int value = 3; //True value is + 5
    //private int minValue = 0;
    private int numOfSections = 21;
    private RectF hitbox;

    private boolean pushed;


    public CustomSlider(float x, float y, float width, float height, int scale){
        hitbox = new RectF(x,y,x + width * scale,y + height * scale);
    }

    public RectF getHitbox(){
        return hitbox;
    }

    //public int getMinValue(){
    //    return minValue;
    //}

    //public void setMinValue(int minValue){
    //    this.minValue = minValue;
    //}

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
            float hi = i * hitbox.width() / numOfSections + hitbox.width() / numOfSections;
            if (posInSlider < hi && !found){
                found = true;
                value = i;
            }
        }
        return value;
    }

}

