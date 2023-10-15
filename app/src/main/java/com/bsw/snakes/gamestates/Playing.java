package com.bsw.snakes.gamestates;

import android.graphics.Canvas;
import android.graphics.PointF;
import android.view.MotionEvent;

import com.bsw.snakes.entities.Arrows;
import com.bsw.snakes.entities.Fruit;
import com.bsw.snakes.entities.FruitPoints;
import com.bsw.snakes.entities.GameCharacters;
import com.bsw.snakes.entities.SnakePoints;
import com.bsw.snakes.enviroments.GameMap;
import com.bsw.snakes.helpers.GameConstants;
import com.bsw.snakes.helpers.interfaces.GameStateInterface;
import com.bsw.snakes.main.Game;
import com.bsw.snakes.ui.ButtonImages;
import com.bsw.snakes.ui.CustomButton;
import com.bsw.snakes.ui.Images;
import com.bsw.snakes.ui.TextImages;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Playing extends BaseState implements GameStateInterface {

    //Uses the formula   Math.Ciel( GAMEWIDTH / BITSCALER ) = sizeOfMapX   to get the number of tiles in its respective orientation
    //then does    ( ( sizeOfMapX * BITSCALER ) - GAMEWIDTH ) / 2 = OffsetX     to get the offset required for the canvas to centre on the screen
    //float DRAWOFFSETX = ((((int) Math.ceil(GameConstants.GAME_WIDTH /(GameConstants.BITSCALER))) * GameConstants.BITSCALER) - GameConstants.GAME_WIDTH) / 2;
    //float DRAWOFFSETY = ((((int) Math.ceil(GameConstants.GAME_HEIGHT/(GameConstants.BITSCALER))) * GameConstants.BITSCALER) - GameConstants.GAME_HEIGHT) / 2;

    //float BScaler = (float)(GameConstants.GAME_WIDTH / game.getGameSizeX());
    float BScaler = game.getScaler() * 16;
    //float BScaler = (float)(GameConstants.GAME_WIDTH / (game.getGameSizeX() * 16));

    //gamesize + 1 for half the walls
    float OffsetX = ((game.getGameSizeX() +2f) * BScaler - GameConstants.GAME_WIDTH) /2;
    float OffsetY = ((game.getGameSizeY()+2f) * BScaler - GameConstants.GAME_HEIGHT) /2;


    public List<SnakePoints> snakePoints = new ArrayList<>();

    private PointF startPosition = new PointF();

    private List<FruitPoints> fruitPos = new ArrayList<>(game.getNumOfFruit());

    private int snakeCurrentlyFacing = GameConstants.FACE_Dir.UP, snakeMoveTo = GameConstants.FACE_Dir.UP;

    private final GameMap Map;//Map

    //for touch events
    private float xTDown, yTDown;
    private float xTouch,yTouch;
    private boolean touchDown;

    private CustomButton pauseBtn;




    public Playing(Game game) {
        super(game);

        //reset score
        game.setGameScore(0);

        //Snake head starting position. The snake starts moving UP and the body is generated bellow the head.
        startPosition = new PointF(5 * BScaler,5 * BScaler);

        for (int i = 0; i <= game.getNumOfFruit(); i++){
            fruitPos.add(new FruitPoints(0,0,0));
            createFruit(i);
        }


        int[][] gameMapIds = new int[game.getGameSizeY() + 2][game.getGameSizeX() + 2];

        for (int i = 0; i < gameMapIds.length; i++){
            for (int j = 0; j < gameMapIds[i].length; j++) {
                if (i == 0 || i == gameMapIds.length - 1 ||
                        j == 0 || j == gameMapIds[i].length - 1){
                    gameMapIds[i][j] = 2; //Wall
                } else if ((i + j) % 2 == 0){
                    gameMapIds[i][j] = 1;//Dark grass
                } else {
                    gameMapIds[i][j] = 0;//Light grass
                }
            }
        }


        Map = new GameMap(gameMapIds);

        for(int i = 0; i < game.getStartingLength(); i++){
            snakePoints.add(i,new SnakePoints(startPosition.x,startPosition.y));
            startPosition.y += BScaler;
        }





        //UI
        pauseBtn = new CustomButton(GameConstants.GAME_WIDTH * 1/100,GameConstants.GAME_HEIGHT * 1 /100, ButtonImages.PLAYING_PAUSE.getWidth(), ButtonImages.PLAYING_PAUSE.getHeight(), ButtonImages.PLAYING_PAUSE.getScale());


    }



    double SecCounter = 0;
    @Override
    public void update(double delta) {

        SecCounter += delta;


        //snake moves every (1 / gamespeed) seconds
        if (SecCounter >= 1f / game.getGameSpeed()) {
            SecCounter = 0;

            checkFruitEaten();
            moveSnake();
            checkStillAlive();
        }
    }

    public void checkFruitEaten(){
        //grow snake by 1 and create a new fruit
        for (int i = 0; i < fruitPos.size() - 1; i++){
            if (fruitPos.get(i).getxPos() == snakePoints.get(0).getxPosition() &&
                    fruitPos.get(i).getyPos() == snakePoints.get(0).getyPosition()) {

                //if fruit has been eaten add to snake size next time the snake moves
                snakePoints.add(snakePoints.size(), new SnakePoints(
                        snakePoints.get(snakePoints.size() - 1).getxPosition(), snakePoints.get(snakePoints.size() - 1).getyPosition()));

                game.setGameScore(game.getGameScore() + 1);

                createFruit(i);
            }
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

        switch (snakeMoveTo) {
            case GameConstants.FACE_Dir.UP:
                //y -= 16 * 6;
                snakePoints.get(0).setyPosition(snakePoints.get(0).getyPosition() - BScaler);
                snakeCurrentlyFacing = GameConstants.FACE_Dir.UP;
                break;
            case GameConstants.FACE_Dir.LEFT:
                //x += 16 * 6;
                snakePoints.get(0).setxPosition(snakePoints.get(0).getxPosition() + BScaler);
                snakeCurrentlyFacing = GameConstants.FACE_Dir.LEFT;
                break;
            case GameConstants.FACE_Dir.DOWN:
                //y += 16 * 6;
                snakePoints.get(0).setyPosition(snakePoints.get(0).getyPosition() + BScaler);
                snakeCurrentlyFacing = GameConstants.FACE_Dir.DOWN;
                break;
            case GameConstants.FACE_Dir.RIGHT:
                //x -= 16 * 6;
                snakePoints.get(0).setxPosition(snakePoints.get(0).getxPosition() - BScaler);
                snakeCurrentlyFacing = GameConstants.FACE_Dir.RIGHT;
                break;
        }

    }

    private void checkStillAlive() {
        //the snake is still alive as long as its head doesn't touch a wall or its tail
        float headX = snakePoints.get(0).getxPosition();
        float headY = snakePoints.get(0).getyPosition();
        //Touching wall?
        if (headY == 0 || headY == (game.getGameSizeY() + 2 - 1) * BScaler ||
                headX == 0 || headX == (game.getGameSizeX() + 2 - 1) * BScaler) {

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

        c.drawColor(0xFF7FC9FF);

        Map.draw(c, OffsetX, OffsetY, game.getScaler());

        //FRUIT
        for (int i = 0; i < fruitPos.size() - 1; i++){
            c.drawBitmap(Fruit.FRUIT.getSprite(fruitPos.get(i).getType(), game.getScaler()),fruitPos.get(i).getxPos() - OffsetX,fruitPos.get(i).getyPos() - OffsetY,null);
        }

        drawSnake(c);
        drawUI(c);
    }

    public void drawSnake(Canvas c){
        //Head of snake
        c.drawBitmap(GameCharacters.SNAKE.getSprite(0, snakeCurrentlyFacing, game.getScaler()),snakePoints.get(0).getxPosition() - OffsetX, snakePoints.get(0).getyPosition() - OffsetY,null);

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

            c.drawBitmap(GameCharacters.SNAKE.getSprite(spriteIdY, spriteIdX, game.getScaler()),snakePoints.get(i).getxPosition()  - OffsetX, snakePoints.get(i).getyPosition() - OffsetY,null);
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


        c.drawBitmap(GameCharacters.SNAKE.getSprite(3, spriteIdX, game.getScaler()),snakePoints.get(snakePoints.size() - 1).getxPosition() - OffsetX, snakePoints.get(snakePoints.size() - 1).getyPosition() - OffsetY,null);
    }

    private void drawUI(Canvas c){
        //PAUSE BUTTON
        c.drawBitmap(ButtonImages.PLAYING_PAUSE.getBtnImg(pauseBtn.isPushed()),
                pauseBtn.getHitbox().left, pauseBtn.getHitbox().top, null);

        //SCORE

        c.drawBitmap(Images.SCORE.getImg(),pauseBtn.getHitbox().right * 11/10,GameConstants.GAME_HEIGHT* 1/100 + (pauseBtn.getHitbox().height() - Images.SCORE.getHeight() * Images.SCORE.getScale()) / 2, null);

        char[] digitsS = String.valueOf(game.getGameScore()).toCharArray();
        for (int i = 0; i < digitsS.length; i++){
            c.drawBitmap(TextImages.NUMBERS.getTextImg(Character.getNumericValue(digitsS[i])),
                    pauseBtn.getHitbox().right * 11/10 + Images.SCORE.getWidth() * Images.SCORE.getScale() + i * TextImages.NUMBERS.getWidth() * TextImages.NUMBERS.getScale() * 4/3,
                    GameConstants.GAME_HEIGHT* 1/100 + (pauseBtn.getHitbox().height() - TextImages.NUMBERS.getHeight() * TextImages.NUMBERS.getScale()) / 2,
                    null);
        }

        //ARROWS
        switch (snakeMoveTo) {
            case GameConstants.FACE_Dir.UP:
                c.drawBitmap(Arrows.ARROWS.getSprite(0, 10),(GameConstants.GAME_WIDTH - 100)/2, GameConstants.GAME_HEIGHT * 18/20,null);
                break;
            case GameConstants.FACE_Dir.LEFT:
                c.drawBitmap(Arrows.ARROWS.getSprite(3, 10),(GameConstants.GAME_WIDTH - 100)/2, GameConstants.GAME_HEIGHT * 18/20,null);
                break;
            case GameConstants.FACE_Dir.DOWN:
                c.drawBitmap(Arrows.ARROWS.getSprite(2, 10),(GameConstants.GAME_WIDTH - 100)/2, GameConstants.GAME_HEIGHT * 18/20,null);
                break;
            case GameConstants.FACE_Dir.RIGHT:
                c.drawBitmap(Arrows.ARROWS.getSprite(1, 10),(GameConstants.GAME_WIDTH - 100)/2, GameConstants.GAME_HEIGHT * 18/20,null);
                break;
        }

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

    public void createFruit(int index){
        boolean validPos = false;

        Random rnd = new Random();

        fruitPos.get(index).setType(rnd.nextInt(2));

        while (!validPos){
            validPos = true;



            fruitPos.get(index).setxPos((rnd.nextInt(game.getGameSizeX() + 2 - 3 + 1) + 1) * BScaler);
            fruitPos.get(index).setyPos((rnd.nextInt(game.getGameSizeY() + 2 - 3 + 1) + 1) * BScaler);

            for (int i = 0; i < snakePoints.size(); i++) {

                if(fruitPos.get(index).getxPos() == snakePoints.get(i).getxPosition() &&
                        fruitPos.get(index).getyPos() == snakePoints.get(i).getyPosition()){

                    validPos = false;
                }
            }

        }

    }

    public void setSnakeMoveTo(int snakeMoveTo){
        int snakeOppositeFacting;
        snakeOppositeFacting = snakeCurrentlyFacing + 2;
        if (snakeOppositeFacting >= 4) {
            snakeOppositeFacting -= 4;
        }
        if (snakeOppositeFacting == snakeMoveTo) {
            snakeMoveTo = snakeCurrentlyFacing;
        }

        this.snakeMoveTo = snakeMoveTo;
    }

    private boolean isIn(MotionEvent e, CustomButton b){
        return b.getHitbox().contains(e.getX(),e.getY());
    }

}
