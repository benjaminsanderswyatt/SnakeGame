package com.bsw.snakes;

import com.bsw.snakes.helpers.GameConstants;

public class GameLoop implements Runnable {

    private final Thread gameThread;
    private final GamePanel gamepanel;

    public GameLoop(GamePanel gamepanel){
        this.gamepanel = gamepanel;
        gameThread = new Thread(this);
    }

    @Override
    public void run() {

        long lastDelta = System.nanoTime();
        long nanoSec = 1_000_000_000;

        boolean stillAlive = true;
        double SecCounter = 0;

        while(stillAlive){

            long nowDelta = System.nanoTime();
            double timeSinceLastDelta = nowDelta - lastDelta;
            double delta = timeSinceLastDelta / nanoSec;


            SecCounter += delta;

            //snake moves every GAME_SPEED second
            if (SecCounter >= GameConstants.GAME_SPEED) {
                SecCounter = 0;

                gamepanel.checkFruitEaten();

                gamepanel.updateSnakeMove();
                gamepanel.render();
                stillAlive = gamepanel.checkStillAlive();

            }

            lastDelta = nowDelta;


        }





    }


    public void startGameLoop(){
        gameThread.start();
    }


}
