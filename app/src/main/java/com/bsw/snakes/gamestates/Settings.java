package com.bsw.snakes.gamestates;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.MotionEvent;

import com.bsw.snakes.enviroments.Floor;
import com.bsw.snakes.helpers.GameConstants;
import com.bsw.snakes.helpers.interfaces.GameStateInterface;
import com.bsw.snakes.main.Game;
import com.bsw.snakes.main.GameSettings;
import com.bsw.snakes.ui.ButtonImages;
import com.bsw.snakes.ui.CustomButton;
import com.bsw.snakes.ui.CustomSlider;
import com.bsw.snakes.ui.Images;
import com.bsw.snakes.ui.SliderImages;
import com.bsw.snakes.ui.TextImages;

public class Settings extends BaseState implements GameStateInterface {

    private Paint paint;

    private CustomButton menuBtn;

    private CustomSlider widthSld, heightSld, numOfFruitSld, gameSpeedSld, startingLengthSld;

    public Settings(Game game){
        super(game);
        paint = new Paint();
        paint.setTextSize(60);
        paint.setColor(Color.WHITE);


        menuBtn = new CustomButton(
                (GameConstants.GAME_WIDTH - ButtonImages.BACK_TO_MENU.getWidth() * ButtonImages.BACK_TO_MENU.getScale()) / 2,
                GameConstants.GAME_HEIGHT * 3/20
                , ButtonImages.BACK_TO_MENU.getWidth(),ButtonImages.BACK_TO_MENU.getHeight(), ButtonImages.BACK_TO_MENU.getScale());


        numOfFruitSld = new CustomSlider(GameConstants.GAME_WIDTH /21,GameConstants.GAME_HEIGHT* 6/20, SliderImages.SLIDER.getWidth(), SliderImages.SLIDER.getHeight(), SliderImages.SLIDER.getScale());
        gameSpeedSld = new CustomSlider(GameConstants.GAME_WIDTH /21,GameConstants.GAME_HEIGHT* 9/20, SliderImages.SLIDER.getWidth(), SliderImages.SLIDER.getHeight(), SliderImages.SLIDER.getScale());
        startingLengthSld = new CustomSlider(GameConstants.GAME_WIDTH /21,GameConstants.GAME_HEIGHT* 12/20, SliderImages.SLIDER.getWidth(), SliderImages.SLIDER.getHeight(), SliderImages.SLIDER.getScale());
        widthSld = new CustomSlider(GameConstants.GAME_WIDTH /21,GameConstants.GAME_HEIGHT* 15/20, SliderImages.SLIDER.getWidth(), SliderImages.SLIDER.getHeight(), SliderImages.SLIDER.getScale());
        heightSld = new CustomSlider(GameConstants.GAME_WIDTH /21,GameConstants.GAME_HEIGHT* 18/20, SliderImages.SLIDER.getWidth(), SliderImages.SLIDER.getHeight(), SliderImages.SLIDER.getScale());

        //numOfFruitSld.setMinValue(GameSettings.getMinNumOfFruit());
        //gameSpeedSld.setMinValue(GameSettings.getMinGameSpeed());
        //startingLengthSld.setMinValue(GameSettings.getMinStartingLength());
        //widthSld.setMinValue(GameSettings.getMinGameSizeX());
        //heightSld.setMinValue(GameSettings.getMinGameSizeY());


    }


    @Override
    public void update(double delta) {


    }

    @Override
    public void render(Canvas c) {

        c.drawColor(0xFF7FC9FF);

        int size = 13 * Images.GAME_PLAY_SAMPLE.getScale();
        for (int j = 0; j <= GameConstants.GAME_HEIGHT * 9 / 10; j += size)
            for (int i = 0; i <= GameConstants.GAME_WIDTH; i += size) {
                if (j == 0) {
                    c.drawBitmap(Floor.OUTSIDE.getSprites(5, 5), i, GameConstants.GAME_HEIGHT * 1 / 10 + j, null);
                } else {
                    c.drawBitmap(Floor.OUTSIDE.getSprites(4, 5), i, GameConstants.GAME_HEIGHT * 1 / 10 + j, null);
                }
            }

        c.drawBitmap(Images.SIGNPOST.getImg(),GameConstants.GAME_WIDTH / 16, GameConstants.GAME_HEIGHT * 1 / 10 - Images.SIGNPOST.getHeight() * Images.SIGNPOST.getScale(), null);


        c.drawBitmap(ButtonImages.BACK_TO_MENU.getBtnImg(menuBtn.isPushed()),
                menuBtn.getHitbox().left, menuBtn.getHitbox().top, null);


        //NUM OF FRUIT SLIDER
        c.drawBitmap(SliderImages.SLIDER.getSliderImg(game.getNumOfFruit() - GameSettings.getMinNumOfFruit()),
                numOfFruitSld.getHitbox().left, numOfFruitSld.getHitbox().top, null);

        c.drawBitmap(Images.FRUIT.getImg(),GameConstants.GAME_WIDTH /21,GameConstants.GAME_HEIGHT* 5/20, null);

        char[] digitsF = String.valueOf(game.getNumOfFruit()).toCharArray();
        for (int i = 0; i < digitsF.length; i++){
            c.drawBitmap(TextImages.NUMBERS.getTextImg(Character.getNumericValue(digitsF[i])),GameConstants.GAME_WIDTH /21 + Images.FRUIT.getWidth()* Images.FRUIT.getScale() + i * TextImages.NUMBERS.getWidth() * TextImages.NUMBERS.getScale() * 4/3, GameConstants.GAME_HEIGHT* 5/20, null);
        }


        //GAME SPEED SLIDER
        c.drawBitmap(SliderImages.SLIDER.getSliderImg(game.getGameSpeed() - GameSettings.getMinGameSpeed()),
                gameSpeedSld.getHitbox().left, gameSpeedSld.getHitbox().top, null);

        c.drawBitmap(Images.SPEED.getImg(),GameConstants.GAME_WIDTH /21,GameConstants.GAME_HEIGHT* 8/20, null);

        char[] digitsS = String.valueOf(game.getGameSpeed()).toCharArray();
        for (int i = 0; i < digitsS.length; i++){
            c.drawBitmap(TextImages.NUMBERS.getTextImg(Character.getNumericValue(digitsS[i])),GameConstants.GAME_WIDTH /21 + Images.SPEED.getWidth()* Images.SPEED.getScale() + i * TextImages.NUMBERS.getWidth() * TextImages.NUMBERS.getScale() * 4/3, GameConstants.GAME_HEIGHT* 8/20, null);
        }



        //STARTING LENGTH SLIDER
        c.drawBitmap(SliderImages.SLIDER.getSliderImg(game.getStartingLength() - GameSettings.getMinStartingLength()),
                startingLengthSld.getHitbox().left, startingLengthSld.getHitbox().top, null);

        c.drawBitmap(Images.LENGTH.getImg(),GameConstants.GAME_WIDTH /21,GameConstants.GAME_HEIGHT* 11/20, null);

        char[] digitsL = String.valueOf(game.getStartingLength()).toCharArray();
        for (int i = 0; i < digitsL.length; i++){
            c.drawBitmap(TextImages.NUMBERS.getTextImg(Character.getNumericValue(digitsL[i])),GameConstants.GAME_WIDTH /21 + Images.LENGTH.getWidth()* Images.LENGTH.getScale() + i * TextImages.NUMBERS.getWidth() * TextImages.NUMBERS.getScale() * 4/3, GameConstants.GAME_HEIGHT* 11/20, null);
        }







        //WIDTH SLIDER
        c.drawBitmap(SliderImages.SLIDER.getSliderImg(game.getGameSizeX() - GameSettings.getMinGameSizeX()),
                widthSld.getHitbox().left, widthSld.getHitbox().top, null);

        c.drawBitmap(Images.WIDTH.getImg(),GameConstants.GAME_WIDTH /21,GameConstants.GAME_HEIGHT* 14/20, null);

        char[] digitsW = String.valueOf(game.getGameSizeX()).toCharArray();
        for (int i = 0; i < digitsW.length; i++){
            c.drawBitmap(TextImages.NUMBERS.getTextImg(Character.getNumericValue(digitsW[i])),GameConstants.GAME_WIDTH /21 + Images.WIDTH.getWidth()* Images.WIDTH.getScale() + i * TextImages.NUMBERS.getWidth() * TextImages.NUMBERS.getScale() * 4/3, GameConstants.GAME_HEIGHT* 14/20, null);
        }

        //HEIGHT SLIDER
        c.drawBitmap(SliderImages.SLIDER.getSliderImg(game.getGameSizeY() - GameSettings.getMinGameSizeY()),
                heightSld.getHitbox().left, heightSld.getHitbox().top, null);

        c.drawBitmap(Images.HEIGHT.getImg(),GameConstants.GAME_WIDTH /21,GameConstants.GAME_HEIGHT* 17/20, null);

        char[] digitsH = String.valueOf(game.getGameSizeY()).toCharArray();
        for (int i = 0; i < digitsH.length; i++){
            c.drawBitmap(TextImages.NUMBERS.getTextImg(Character.getNumericValue(digitsH[i])),GameConstants.GAME_WIDTH/21 + Images.HEIGHT.getWidth()* Images.HEIGHT.getScale() + i * TextImages.NUMBERS.getWidth() * TextImages.NUMBERS.getScale() * 4/3, GameConstants.GAME_HEIGHT* 17/20, null);
        }

    }

    @Override
    public void touchEvents(MotionEvent event) {

        if(event.getAction() == MotionEvent.ACTION_DOWN){
            if (isIn(event, menuBtn.getHitbox()))
                menuBtn.setPushed(true);
            if (isIn(event, numOfFruitSld.getHitbox()))
                numOfFruitSld.setPushed(true);
            if (isIn(event, gameSpeedSld.getHitbox()))
                gameSpeedSld.setPushed(true);
            if (isIn(event, startingLengthSld.getHitbox()))
                startingLengthSld.setPushed(true);
            if (isIn(event, widthSld.getHitbox()))
                widthSld.setPushed(true);
            if (isIn(event, heightSld.getHitbox()))
                heightSld.setPushed(true);



        } else if(event.getAction() == MotionEvent.ACTION_UP){
            if(isIn(event, menuBtn.getHitbox()))
                if(menuBtn.isPushed())
                    game.setCurrentGameState(Game.GameState.MENU);

            menuBtn.setPushed(false);
            numOfFruitSld.setPushed(false);
            gameSpeedSld.setPushed(false);
            startingLengthSld.setPushed(false);
            widthSld.setPushed(false);
            heightSld.setPushed(false);
        } else if (event.getAction() == MotionEvent.ACTION_MOVE){

            if(numOfFruitSld.isPushed())
                game.setNumOfFruit(numOfFruitSld.setTouchEventValue(event.getX()) + GameSettings.getMinNumOfFruit());
            if(gameSpeedSld.isPushed())
                game.setGameSpeed(gameSpeedSld.setTouchEventValue(event.getX()) + GameSettings.getMinGameSpeed());
            if(startingLengthSld.isPushed())
                game.setStartingLength(startingLengthSld.setTouchEventValue(event.getX()) + GameSettings.getMinStartingLength());
            if(widthSld.isPushed())
                game.setGameSizeX(widthSld.setTouchEventValue(event.getX()) + GameSettings.getMinGameSizeX());
            if(heightSld.isPushed())
                game.setGameSizeY(heightSld.setTouchEventValue(event.getX()) + GameSettings.getMinGameSizeY());


        }



    }

    private boolean isIn(MotionEvent e, RectF b){
        return b.contains(e.getX(),e.getY());
    }




}
