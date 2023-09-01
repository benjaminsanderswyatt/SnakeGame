package com.bsw.snakes.inputs;

import static com.bsw.snakes.MainActivity.GAME_HEIGHT;
import static com.bsw.snakes.MainActivity.GAME_WIDTH;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;

import com.bsw.snakes.GamePanel;
import com.bsw.snakes.helpers.GameConstants;

public class TouchEvents {
    private final GamePanel gamePanel;
    private float xCenter = GAME_WIDTH / 2,yCenter = GAME_HEIGHT - (GAME_HEIGHT / 4),radius = 100;
    private final Paint circlePaint, yellowPaint;
    private float xTouch,yTouch;
    private boolean touchDown;



    public TouchEvents(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        circlePaint = new Paint();
        circlePaint.setStyle(Paint.Style.STROKE);
        circlePaint.setStrokeWidth(5);
        circlePaint.setColor(Color.CYAN);
        yellowPaint = new Paint();
        yellowPaint.setColor(Color.YELLOW);
        yellowPaint.setStrokeWidth(2);
    }


    //TESTER FOR TOUCH EVENTS
    public void draw (Canvas c){
        c.drawCircle(xCenter,yCenter,radius,circlePaint);
        if (touchDown) {
            c.drawLine(xCenter, yCenter, xTouch, yTouch, yellowPaint);
            c.drawLine(xCenter, yCenter, xTouch, yCenter, yellowPaint);
            c.drawLine(xTouch, yTouch, xTouch, yCenter, yellowPaint);
        }
    }

    public boolean touchEvent(MotionEvent event){

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:

                xCenter = event.getX();
                yCenter = event.getY();

                float x = event.getX();
                float y = event.getY();

                float a = Math.abs(x - xCenter);
                float b = Math.abs(y - yCenter);
                float c = (float) Math.hypot(a,b);

                if (c <= radius) {
                    touchDown = true;
                    xTouch = x;
                    yTouch = y;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if(touchDown){
                    int snakeMoveTo;
                    xTouch = event.getX();
                    yTouch = event.getY();

                    float xDiff = xTouch - xCenter;
                    float yDiff = yTouch - yCenter;

                    if (Math.abs(xDiff) >= Math.abs(yDiff)){
                        //Horizontal
                        if (xDiff > 0) {
                            //Left
                            snakeMoveTo = GameConstants.FACE_Dir.LEFT;
                        }else{
                            //Right
                            snakeMoveTo = GameConstants.FACE_Dir.RIGHT;
                        }
                    } else {
                        //Vertical
                        if (yDiff > 0) {
                            //Down
                            snakeMoveTo = GameConstants.FACE_Dir.DOWN;
                        }else{
                            //Up
                            snakeMoveTo = GameConstants.FACE_Dir.UP;
                        }
                    }




                    gamePanel.setSnakeMoveTo(snakeMoveTo);
                }
                break;
            case MotionEvent.ACTION_UP:
                touchDown = false;
                break;
        }

        return true;
    }




}

