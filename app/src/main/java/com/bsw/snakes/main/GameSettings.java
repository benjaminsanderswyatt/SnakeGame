package com.bsw.snakes.main;

import com.bsw.snakes.helpers.GameConstants;

public class GameSettings {

    private boolean muted = false;

    private int gameSizeX = 10; //gamesize not including walls
    private int gameSizeY = 10; //gamesize not including walls

    private int gameSpeed = 2; //snake moves every 1 / gamespeed seconds
    private int startingLength = 3;
    private int numOfFruit = 1;

    private int gameScore = 0;

    public void setGameScore(int gameScore){this.gameScore = gameScore;}

    public int getGameScore(){return gameScore;}


    public float getScaler(){

        //(gameSizeX + 1f) gives half a wall on each side;
        //scale round down to the nearest 1/8th so that sprites load correctly
        float scaleX = (float)(Math.floor(GameConstants.GAME_WIDTH / ((gameSizeX + 1f) * 16) * 8) / 8f);
        float scaleY = (float)(Math.floor(GameConstants.GAME_HEIGHT / ((gameSizeY + 1f) * 16) * 8) / 8f);

        return Math.min(scaleX, scaleY); //when sizex is small & sizey is big have to scale for height
    }





    public void setMuted(boolean muted){this.muted = muted;}

    public boolean getMuted(){return muted;}



    public void setGameSizeX(int gameSizeX){this.gameSizeX = gameSizeX;}
    public int getGameSizeX(){return gameSizeX;}
    public static int getMinGameSizeX(){return 5;}

    public void setGameSizeY(int gameSizeY){this.gameSizeY = gameSizeY;}
    public int getGameSizeY(){return gameSizeY;}
    public static int getMinGameSizeY(){return 5;}

    public void setGameSpeed(int gameSpeed){
        this.gameSpeed = gameSpeed;
    }
    public int getGameSpeed(){
        return gameSpeed;
    }
    public static int getMinGameSpeed(){return 1;}


    public void setStartingLength(int startingLength){
        this.startingLength = startingLength;
    }
    public int getStartingLength(){
        return startingLength;
    }
    public static int getMinStartingLength(){return 2;}


    public void setNumOfFruit(int numOfFruit){
        this.numOfFruit = numOfFruit;
    }
    public int getNumOfFruit(){
        return numOfFruit;
    }
    public static int getMinNumOfFruit(){return 1;}

}
