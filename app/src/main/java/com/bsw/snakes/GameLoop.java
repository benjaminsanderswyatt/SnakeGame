package com.bsw.snakes;

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

        while(stillAlive){

            long nowDelta = System.nanoTime();
            double timeSinceLastDelta = nowDelta - lastDelta;
            double delta = timeSinceLastDelta / nanoSec;

                gamepanel.update(delta);
                gamepanel.render();
                stillAlive = gamepanel.checkStillAlive();


            lastDelta = nowDelta;


        }





    }


    public void startGameLoop(){
        gameThread.start();
    }


}
