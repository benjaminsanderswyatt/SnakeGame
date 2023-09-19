package com.bsw.snakes.gamestates;

import android.graphics.Canvas;
import android.graphics.PointF;
import android.view.MotionEvent;

import com.bsw.snakes.entities.Fruit;
import com.bsw.snakes.entities.GameCharacters;
import com.bsw.snakes.entities.SnakePoints;
import com.bsw.snakes.enviroments.GameMap;
import com.bsw.snakes.helpers.GameConstants;
import com.bsw.snakes.helpers.interfaces.GameStateInterface;
import com.bsw.snakes.main.Game;
import com.bsw.snakes.ui.ButtonImages;
import com.bsw.snakes.ui.CustomButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Playing extends BaseState implements GameStateInterface {

    //Uses the formula   Math.Ciel( GAMEWIDTH / BITSCALER ) = sizeOfMapX   to get the number of tiles in its respective orientation
    //then does    ( ( sizeOfMapX * BITSCALER ) - GAMEWIDTH ) / 2 = OffsetX     to get the offset required for the canvas to centre on the screen
    float DRAWOFFSETX = ((((int) Math.ceil(GameConstants.GAME_WIDTH/(GameConstants.BITSCALER))) * GameConstants.BITSCALER) - GameConstants.GAME_WIDTH) / 2;
    float DRAWOFFSETY = ((((int) Math.ceil(GameConstants.GAME_HEIGHT/(GameConstants.BITSCALER))) * GameConstants.BITSCALER) - GameConstants.GAME_HEIGHT) / 2;

    public List<SnakePoints> snakePoints = new ArrayList<>();

    private int gameMapSizeX, gameMapSizeY;
    private int snakeStartSize;
    private PointF startPosition = new PointF();

    private final PointF fruitPos = new PointF(); //Pos set to 0 0 as gameloop does checkFruitEaten
    private int fruitType = 0;

    private int snakeCurrentlyFacing = GameConstants.FACE_Dir.UP, snakeMoveTo = GameConstants.FACE_Dir.UP;

    private final GameMap Map;//Map



    //for touch events
    private float xTDown, yTDown;
    private float xTouch,yTouch;
    private boolean touchDown;

    private CustomButton pauseBtn;




    public Playing(Game game) {
        super(game);


        //Map Size. This includes the walls (so playable area is -2 to each)
        //gameMapSizeX = 12;
        //gameMapSizeY = 16;
        gameMapSizeX = (int) Math.ceil(GameConstants.GAME_WIDTH/(GameConstants.BITSCALER));
        gameMapSizeY = (int) Math.ceil(GameConstants.GAME_HEIGHT/(GameConstants.BITSCALER));


        //Snake head starting position. The snake starts moving UP and the body is generated bellow the head.
        startPosition = new PointF(5 * GameConstants.BITSCALER,5 * GameConstants.BITSCALER);

        //Snake starting length
        snakeStartSize = 3;


        createFruit();



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

        for(int i = 0; i < snakeStartSize; i++){
            snakePoints.add(i,new SnakePoints(startPosition.x,startPosition.y));
            startPosition.y += GameConstants.BITSCALER;
        }





        //UI
        pauseBtn = new CustomButton(5,5, ButtonImages.PLAYING_PAUSE.getWidth(), ButtonImages.PLAYING_PAUSE.getHeight(), ButtonImages.PLAYING_PAUSE.getScale());


    }





    double SecCounter = 0;
    @Override
    public void update(double delta) {

        SecCounter += delta;

        //snake moves every GAME_SPEED seconds
        if (SecCounter >= GameConstants.GAME_SPEED) {
            SecCounter = 0;

            checkFruitEaten();
            moveSnake();
            checkStillAlive();
        }
    }

    public void checkFruitEaten(){
        //grow snake by 1 and create a new fruit
        if (fruitPos.x == snakePoints.get(0).getxPosition() &&
                fruitPos.y == snakePoints.get(0).getyPosition()) {

            //if fruit has been eaten add to snake size next time the snake moves
            snakePoints.add(snakePoints.size(), new SnakePoints(
                    snakePoints.get(snakePoints.size() - 1).getxPosition(), snakePoints.get(snakePoints.size() - 1).getyPosition()));

            createFruit();
        }
    }

    private void moveSnake() {
        //move the body part to the position of the body part in front
        //move body
        for (int i = snakePoints.size() - 1; i > 0; i--) {
            float prevBodyX = snakePoints.get(i - 1).getxPosition();
            float prevBodyY = snakePoints.get(i - 1).getyPosition();

            snakePoints.get(i).setxPosition(prevBodyX);
            snakePoints.get(i).setyPosition(prevBodyY);
        }

        //move head
        int snakeOppositeFacting;
        snakeOppositeFacting = snakeCurrentlyFacing + 2;
        if (snakeOppositeFacting >= 4) {
            snakeOppositeFacting -= 4;
        }
        if (snakeOppositeFacting == snakeMoveTo) {
            snakeMoveTo = snakeCurrentlyFacing;
        }

        switch (snakeMoveTo) {
            case GameConstants.FACE_Dir.UP:
                //y -= 16 * 6;
                snakePoints.get(0).setyPosition(snakePoints.get(0).getyPosition() - GameConstants.BITSCALER);
                snakeCurrentlyFacing = GameConstants.FACE_Dir.UP;
                break;
            case GameConstants.FACE_Dir.LEFT:
                //x += 16 * 6;
                snakePoints.get(0).setxPosition(snakePoints.get(0).getxPosition() + GameConstants.BITSCALER);
                snakeCurrentlyFacing = GameConstants.FACE_Dir.LEFT;
                break;
            case GameConstants.FACE_Dir.DOWN:
                //y += 16 * 6;
                snakePoints.get(0).setyPosition(snakePoints.get(0).getyPosition() + GameConstants.BITSCALER);
                snakeCurrentlyFacing = GameConstants.FACE_Dir.DOWN;
                break;
            case GameConstants.FACE_Dir.RIGHT:
                //x -= 16 * 6;
                snakePoints.get(0).setxPosition(snakePoints.get(0).getxPosition() - GameConstants.BITSCALER);
                snakeCurrentlyFacing = GameConstants.FACE_Dir.RIGHT;
                break;
        }

    }

    private void checkStillAlive() {
        //the snake is still alive as long as its head doesn't touch a wall or its tail
        float headX = snakePoints.get(0).getxPosition();
        float headY = snakePoints.get(0).getyPosition();
        //Touching wall?
        if (headY == 0 || headY == (gameMapSizeY - 1) * GameConstants.BITSCALER ||
                headX == 0 || headX == (gameMapSizeX - 1) * GameConstants.BITSCALER) {

            game.setCurrentGameState(Game.GameState.DEATH);
        }
        //Touching tail?
        for (int i = 1; i < snakePoints.size(); i++) {
            if (snakePoints.get(0).getxPosition() == snakePoints.get(i).getxPosition() &&
                    snakePoints.get(0).getyPosition() == snakePoints.get(i).getyPosition()) {

                game.setCurrentGameState(Game.GameState.DEATH);
            }
        }
    }

    @Override
    public void render(Canvas c) {

        Map.draw(c, DRAWOFFSETX,DRAWOFFSETY);



        c.drawBitmap(Fruit.FRUIT.getSprite(fruitType),fruitPos.x - DRAWOFFSETX,fruitPos.y - DRAWOFFSETY,null);


        //Head of snake
        c.drawBitmap(GameCharacters.SNAKE.getSprite(0, snakeCurrentlyFacing),snakePoints.get(0).getxPosition() - DRAWOFFSETX, snakePoints.get(0).getyPosition() - DRAWOFFSETY,null);

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

            c.drawBitmap(GameCharacters.SNAKE.getSprite(spriteIdY, spriteIdX),snakePoints.get(i).getxPosition()  - DRAWOFFSETX, snakePoints.get(i).getyPosition() - DRAWOFFSETY,null);
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


        c.drawBitmap(GameCharacters.SNAKE.getSprite(3, spriteIdX),snakePoints.get(snakePoints.size() - 1).getxPosition() - DRAWOFFSETX, snakePoints.get(snakePoints.size() - 1).getyPosition() - DRAWOFFSETY,null);


        drawUI(c);

    }

    private void drawUI(Canvas c){
        c.drawBitmap(ButtonImages.PLAYING_PAUSE.getBtnImg(pauseBtn.isPushed()),
                pauseBtn.getHitbox().left, pauseBtn.getHitbox().top, null);
    }



    @Override
    public void touchEvents(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:

                if(isIn(event, pauseBtn))
                    pauseBtn.setPushed(true);



                xTDown = event.getX();
                yTDown = event.getY();

                touchDown = true;

                break;
            case MotionEvent.ACTION_MOVE:
                if(touchDown){
                    int snakeMoveTo;

                    float xDiff = event.getX() - xTDown;
                    float yDiff = event.getY() - yTDown;

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


                    setSnakeMoveTo(snakeMoveTo);
                }
                break;
            case MotionEvent.ACTION_UP:
                if(isIn(event, pauseBtn))
                    if(pauseBtn.isPushed())
                        game.setCurrentGameState(Game.GameState.PAUSED);

                pauseBtn.setPushed(false);

                touchDown = false;
                break;
        }
    }

    public void createFruit(){
        boolean validPos = false;

        Random rnd = new Random();
        //rnd.nextInt(Max + 1);//random number between 0 & Max.   0,1,2,3,4

        fruitType = rnd.nextInt(2);


        while (!validPos){
            validPos = true;

            fruitPos.x = (rnd.nextInt(gameMapSizeX - 3 + 1) + 1) * GameConstants.BITSCALER;
            fruitPos.y = (rnd.nextInt(gameMapSizeY - 3 + 1) + 1) * GameConstants.BITSCALER;

            for (int i = 0; i < snakePoints.size(); i++) {

                if(fruitPos.x == snakePoints.get(i).getxPosition() &&
                        fruitPos.y == snakePoints.get(i).getyPosition()){
                    validPos = false;
                }
            }

        }

    }

    public void setSnakeMoveTo(int snakeMoveTo){
        this.snakeMoveTo = snakeMoveTo;
    }

    private boolean isIn(MotionEvent e, CustomButton b){
        return b.getHitbox().contains(e.getX(),e.getY());
    }

}
