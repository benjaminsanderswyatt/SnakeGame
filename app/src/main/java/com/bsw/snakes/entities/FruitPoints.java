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

    public float getxPos() {
        return xPos;
    }

    public void setxPos(float xPos) {
        this.xPos = xPos;
    }

    public float getyPos() {
        return yPos;
    }

    public void setyPos(float yPos) {
        this.yPos = yPos;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
