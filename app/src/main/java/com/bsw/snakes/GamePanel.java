package com.bsw.snakes;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import com.bsw.snakes.entities.GameCharacters;
import com.bsw.snakes.entities.SnakePoints;
import com.bsw.snakes.enviroments.GameMap;
import com.bsw.snakes.helpers.GameConstants;
import com.bsw.snakes.inputs.TouchEvents;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {

    private Paint redPaint = new Paint();
    private SurfaceHolder holder;
    private Random rand = new Random();
    private GameLoop gameLoop;
    private TouchEvents touchEvents;
    private PointF startPosition = new PointF(5 * (16 * 6),5 * (16 * 6));
    private int snakeCurrentlyFacing = GameConstants.FACE_Dir.UP, snakeMoveTo = GameConstants.FACE_Dir.UP;
    public List<SnakePoints> snakePoints = new ArrayList<>();
    private int gameMapSizeX = 10, gameMapSizeY = 16; //Size of game area counting the walls



    //Map
    private GameMap Map;

    public GamePanel(Context context) {
        super(context);
        holder = getHolder();
        holder.addCallback(this);
        redPaint.setColor(Color.RED);

        touchEvents = new TouchEvents(this);

        gameLoop = new GameLoop(this);



        int[][] gameMapIds = new int[gameMapSizeY][gameMapSizeX];

        int value;

        for (int i = 0; i < gameMapIds.length; i++){
            for (int j = 0; j < gameMapIds[i].length; j++) {

                if (i == 0 || i == gameMapIds.length - 1 ||
                        j == 0 || j == gameMapIds[i].length - 1){
                    value = 2; //Wall
                } else if ((i + j) % 2 == 0){
                    value = 1;//Dark grass
                } else {
                    value = 0;//Light grass
                }

                gameMapIds[i][j] = value;
            }
        }


        Map = new GameMap(gameMapIds);

        for(int i = 0; i < 5; i++){
            snakePoints.add(i,new SnakePoints(startPosition.x,startPosition.y));
            startPosition.y += 16 * 6;
        }






    }


    public void render(){

        Canvas c = holder.lockCanvas();
        c.drawColor(Color.BLACK);

        Map.draw(c);

        touchEvents.draw(c);

        //Head of snake
        c.drawBitmap(GameCharacters.SNAKE.getSprite(0, snakeCurrentlyFacing),snakePoints.get(0).getxPosition(),snakePoints.get(0).getyPosition(),null);

        //Middle of snake
        for (int i = 1; i < snakePoints.size() - 1; i++){

            //By finding out where the current body section is located in relation to the body section
            //before it and after it you can work out how the section should look.
            //bellow implements this
            //? = current body section,  -- or | are the previous and next body section
            //When displaying the current section whether the adjacent sections are the previous or next
            //doesn't matter as they display the same

            float previousBodyX = snakePoints.get(i - 1).getxPosition();
            float previousBodyY = snakePoints.get(i - 1).getyPosition();

            float bodyX = snakePoints.get(i).getxPosition();
            float bodyY = snakePoints.get(i).getyPosition();

            float nextBodyX = snakePoints.get(i + 1).getxPosition();
            float nextBodyY = snakePoints.get(i + 1).getyPosition();

            float nowXRelToPrev = bodyX - previousBodyX;
            float nowXRelToNext = bodyX - nextBodyX;

            float nowYRelToPrev = bodyY - previousBodyY;
            float nowYRelToNext = bodyY - nextBodyY;

            int spriteIdY = 0;
            int spriteIdX = 0;

            if (previousBodyX == bodyX && nextBodyX == bodyX){
                //  |
                //  ?
                //  |
                //5
                spriteIdY = 1;
                spriteIdX = 0;
            }else if (previousBodyY == bodyY && nextBodyY == bodyY){
                //
                //--?--
                //
                //6
                spriteIdY = 1;
                spriteIdX = 1;
            }else if (nowXRelToPrev <= 0 && nowXRelToNext <= 0 && nowYRelToPrev >= 0 && nowYRelToNext >= 0){
                //  |
                //  ?--
                //
                //9
                spriteIdY = 2;
                spriteIdX = 0;
            }else if (nowXRelToPrev <= 0 && nowXRelToNext <= 0 && nowYRelToPrev <= 0 && nowYRelToNext <= 0) {
                //
                //  ?--
                //  |
                //10
                spriteIdY = 2;
                spriteIdX = 1;
            }else if (nowXRelToPrev >= 0 && nowXRelToNext >= 0 && nowYRelToPrev <= 0 && nowYRelToNext <= 0){
                //
                //--?
                //  |
                //11
                spriteIdY = 2;
                spriteIdX = 2;
            }else if (nowXRelToPrev >= 0 && nowXRelToNext >= 0 && nowYRelToPrev >= 0 && nowYRelToNext >= 0){
                //  |
                //--?
                //
                //12
                spriteIdY = 2;
                spriteIdX = 3;
            }

            c.drawBitmap(GameCharacters.SNAKE.getSprite(spriteIdY, spriteIdX),snakePoints.get(i).getxPosition(),snakePoints.get(i).getyPosition(),null);
        }

        //Tail of snake

        float nowXRelToPrev = snakePoints.get(snakePoints.size() - 1).getxPosition() - snakePoints.get(snakePoints.size() - 2).getxPosition();
        float nowYRelToPrev = snakePoints.get(snakePoints.size() - 1).getyPosition() - snakePoints.get(snakePoints.size() - 2).getyPosition();

        int spriteIdX = 0;

        if (nowXRelToPrev > 0){
            //
            //--?
            //
            spriteIdX = 1;
        }else if (nowXRelToPrev < 0){
            //
            //  ?--
            //
            spriteIdX = 3;
        }else if (nowYRelToPrev < 0){
            //
            //  ?
            //  |
            spriteIdX = 0;
        }else if (nowYRelToPrev > 0){
            //  |
            //  ?
            //
            spriteIdX = 2;
        }


        c.drawBitmap(GameCharacters.SNAKE.getSprite(3, spriteIdX),snakePoints.get(snakePoints.size() - 1).getxPosition(),snakePoints.get(snakePoints.size() - 1).getyPosition(),null);



        holder.unlockCanvasAndPost(c);
    }


    double oneSecCounter = 0;
    public void update(double delta){
        oneSecCounter += delta;

        //snake moves every 1 second
        if (oneSecCounter >= 1) {
            oneSecCounter = 0;


            updateSnakeMove(delta);
        }


    }

    private void updateSnakeMove(double delta) {

        int snakeOppositeFacting;

        snakeOppositeFacting = snakeCurrentlyFacing + 2;
        if (snakeOppositeFacting >= 4) {
            snakeOppositeFacting -= 4;
        }

        if (snakeOppositeFacting == snakeMoveTo) {
            snakeMoveTo = snakeCurrentlyFacing;
        }


        //move body
        for (int i = snakePoints.size() - 1; i > 0; i--){
            float prevBodyX = snakePoints.get(i - 1).getxPosition();
            float prevBodyY = snakePoints.get(i - 1).getyPosition();


            snakePoints.get(i).setxPosition(prevBodyX);
            snakePoints.get(i).setyPosition(prevBodyY);


        }

        //move head
        switch (snakeMoveTo){
            case GameConstants.FACE_Dir.UP:
                //y -= 16 * 6;
                snakePoints.get(0).setyPosition(snakePoints.get(0).getyPosition() - 16 * 6);
                snakeCurrentlyFacing = GameConstants.FACE_Dir.UP;
                break;
            case GameConstants.FACE_Dir.LEFT:
                //x += 16 * 6;
                snakePoints.get(0).setxPosition(snakePoints.get(0).getxPosition() + 16 * 6);
                snakeCurrentlyFacing = GameConstants.FACE_Dir.LEFT;
                break;
            case GameConstants.FACE_Dir.DOWN:
                //y += 16 * 6;
                snakePoints.get(0).setyPosition(snakePoints.get(0).getyPosition() + 16 * 6);
                snakeCurrentlyFacing = GameConstants.FACE_Dir.DOWN;
                break;
            case GameConstants.FACE_Dir.RIGHT:
                //x -= 16 * 6;
                snakePoints.get(0).setxPosition(snakePoints.get(0).getxPosition() - 16 * 6);
                snakeCurrentlyFacing = GameConstants.FACE_Dir.RIGHT;
                break;
        }



    }


    public void setSnakeMoveTo(int snakeMoveTo){
        this.snakeMoveTo = snakeMoveTo;
    }



    public boolean checkStillAlive(){
        //the snake is still alive as long as its head doesn't touch its tail or a wall

        float headX = snakePoints.get(0).getxPosition();
        float headY = snakePoints.get(0).getyPosition();

        if (headY == 0 || headY == (gameMapSizeY - 1) * 16 * 6||
        headX == 0 || headX == (gameMapSizeX - 1) * 16 * 6){
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




    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return touchEvents.touchEvent(event);
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
        gameLoop.startGameLoop();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {

    }

}
