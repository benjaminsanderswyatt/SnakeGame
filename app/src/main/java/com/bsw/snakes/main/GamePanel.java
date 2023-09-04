package com.bsw.snakes.main;

import android.content.Context;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {

    private final Game game;

    public GamePanel(Context context) {
        super(context);
        SurfaceHolder holder = getHolder();
        holder.addCallback(this);
        game = new Game(holder);
    }

    /*
    public void checkFruitEaten(){

        if (fruitPos.x == snakePoints.get(0).getxPosition() &&
                fruitPos.y == snakePoints.get(0).getyPosition()){

            //if fruit has been eaten add to snake size next time the snake moves
            snakePoints.add(snakePoints.size(), new SnakePoints(
                    snakePoints.get(snakePoints.size() - 1).getxPosition(),snakePoints.get(snakePoints.size() - 1).getyPosition()));

            createFruit();
        }


    }
    */

    /*
    public boolean checkStillAlive(){
        //the snake is still alive as long as its head doesn't touch its tail or a wall

        float headX = snakePoints.get(0).getxPosition();
        float headY = snakePoints.get(0).getyPosition();

        if (headY == 0 || headY == (gameMapSizeY - 1) * GameConstants.BITSCALER ||
        headX == 0 || headX == (gameMapSizeX - 1) * GameConstants.BITSCALER){
            return false;
        }

        for (int i = 1; i < snakePoints.size(); i++){
            if (snakePoints.get(0).getxPosition() == snakePoints.get(i).getxPosition() &&
                    snakePoints.get(0).getyPosition() == snakePoints.get(i).getyPosition()){
                return false;
            }
        }

        return true;
    }
    */

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return game.touchEvent(event);
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
        game.startGameLoop();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {

    }

}
