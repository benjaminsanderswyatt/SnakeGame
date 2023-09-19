package com.bsw.snakes.ui;

import java.util.Random;

public class Clouds {

    int x,y, speed;
    CloudType cloudType;

    public Clouds(int x, int y, CloudType cloudType,int speed){
        this.x = x;
        this.y = y;
        this.cloudType = cloudType;
        this.speed = speed;

    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public CloudType getCloudType() {
        return cloudType;
    }

    public int getSpeed() { return speed; }


    public void setX(int x) {
        this.x = x;
    }
    public void setY(int y) {
        this.y = y;
    }
    public void setCloudType(CloudType cloudType) {
        this.cloudType = cloudType;
    }

    public void setSpeed(int speed){ this.speed = speed;}

}
