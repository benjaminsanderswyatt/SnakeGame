package com.bsw.snakes.entities;

public class FruitPoints {
    float xPos;
    float yPos;
    public int type;

    public FruitPoints(float xPosition, float yPosition, int type) {
        this.xPos = xPosition;
        this.yPos = yPosition;
        this.type = type;
    }

    public float getXPos() {
        return xPos;
    }

    public void setXPos(float xPos) {
        this.xPos = xPos;
    }

    public float getYPos() {
        return yPos;
    }

    public void setYPos(float yPos) {
        this.yPos = yPos;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
