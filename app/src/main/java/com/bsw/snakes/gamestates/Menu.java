package com.bsw.snakes.gamestates;

import android.graphics.Canvas;
import android.graphics.RectF;
import android.view.MotionEvent;

import com.bsw.snakes.enviroments.Floor;
import com.bsw.snakes.helpers.GameConstants;
import com.bsw.snakes.helpers.interfaces.GameStateInterface;
import com.bsw.snakes.main.Game;
import com.bsw.snakes.ui.ButtonImages;
import com.bsw.snakes.ui.CloudType;
import com.bsw.snakes.ui.Clouds;
import com.bsw.snakes.ui.CustomButton;
import com.bsw.snakes.ui.Images;

import java.util.ArrayList;
import java.util.Random;

public class Menu extends BaseState implements GameStateInterface {

    private final CustomButton startBtn;
    private final CustomButton settingsBtn;
    private final CustomButton starBtn;
    private final CustomButton muteBtn;
    private final CustomButton questionBtn;

    private final ArrayList<Clouds> clouds = new ArrayList<>();

    private final Random rnd = new Random();

    public Menu(Game game){
        super(game);

        startBtn = new CustomButton(getSignPostLeft(33),getSignPostTop(15), ButtonImages.MENU_START.getWidth(), ButtonImages.MENU_START.getHeight(), ButtonImages.MENU_START.getScale());
        settingsBtn = new CustomButton(getSignPostLeft(33),getSignPostTop(38), ButtonImages.MENU_SETTINGS.getWidth(), ButtonImages.MENU_SETTINGS.getHeight(), ButtonImages.MENU_SETTINGS.getScale());
        starBtn = new CustomButton(getSignPostLeft(33),getSignPostTop(61), ButtonImages.MENU_STAR.getWidth(), ButtonImages.MENU_STAR.getHeight(), ButtonImages.MENU_STAR.getScale());
        muteBtn = new CustomButton(getSignPostLeft(9),getSignPostTop(30), ButtonImages.MENU_MUTE.getWidth(), ButtonImages.MENU_MUTE.getHeight(), ButtonImages.MENU_MUTE.getScale());
        questionBtn = new CustomButton(getSignPostLeft(9),getSignPostTop(53), ButtonImages.MENU_QUESTION.getWidth(), ButtonImages.MENU_QUESTION.getHeight(), ButtonImages.MENU_QUESTION.getScale());




        for (int i = 0; i <= 7; i++) {
            CloudType cloudTypeRnd = CloudType.values()[rnd.nextInt(CloudType.values().length)];
            clouds.add(new Clouds(rnd.nextInt(GameConstants.GAME_WIDTH), cloudTypeRnd.getHeight() + rnd.nextInt(GameConstants.GAME_HEIGHT / 2), cloudTypeRnd, 2 + rnd.nextInt(3)));
        }
    }

    private int getSignPostLeft(int x){
        return GameConstants.GAME_WIDTH / 16 + x * Images.SIGNPOST.getScale();
    }

    private int getSignPostTop(int y){
        return GameConstants.GAME_HEIGHT * 2 / 3 - (Images.SIGNPOST.getHeight() - y) * Images.SIGNPOST.getScale();
    }





    @Override
    public void update(double delta) {

        //MOVE CLOUDS
        for(Clouds element : clouds){
            element.setX(element.getX() + element.getSpeed());

            if (element.getX() >= GameConstants.GAME_WIDTH){
                CloudType cloudTypeRnd = CloudType.values()[rnd.nextInt(CloudType.values().length)];

                element.setX(-cloudTypeRnd.getWidth() * cloudTypeRnd.getScale());
                element.setY(cloudTypeRnd.getHeight() + rnd.nextInt(GameConstants.GAME_HEIGHT / 2));
                element.setCloudType(cloudTypeRnd);
                element.setSpeed(2 + rnd.nextInt(3));
            }
        }

    }





    @Override
    public void render(Canvas c) {

        c.drawColor(0xFF7FC9FF);

        //CLOUDS
        for(Clouds element : clouds){
            switch (element.getCloudType()){
                case CLOUD1:
                    c.drawBitmap(CloudType.CLOUD1.getImg(), element.getX() ,element.getY() , null);
                    break;
                case CLOUD2:
                    c.drawBitmap(CloudType.CLOUD2.getImg(), element.getX() ,element.getY() , null);
                    break;
                case CLOUD3:
                    c.drawBitmap(CloudType.CLOUD3.getImg(), element.getX() ,element.getY() , null);
                    break;
                case CLOUD4:
                    c.drawBitmap(CloudType.CLOUD4.getImg(), element.getX() ,element.getY() , null);
                    break;
            }
        }

        //SIGN
        c.drawBitmap(Images.SIGN.getImg(),(GameConstants.GAME_WIDTH - Images.SIGN.getWidth() * Images.SIGN.getScale()) / 2, 0, null);

        c.drawBitmap(Images.SIGN.getImg(),(GameConstants.GAME_WIDTH - Images.SIGN.getWidth() * Images.SIGN.getScale()) / 2, 0, null);


        //FLOOR
        for (int j = 0; j <= GameConstants.GAME_HEIGHT / 3; j += GameConstants.BITSCALER)
            for (int i = 0; i <= GameConstants.GAME_WIDTH; i += GameConstants.BITSCALER) {
                if (j == 0) {
                    c.drawBitmap(Floor.OUTSIDE.getSprites(5, 5), i, GameConstants.GAME_HEIGHT * 2 / 3 + j, null);
                } else {
                    c.drawBitmap(Floor.OUTSIDE.getSprites(4, 5), i, GameConstants.GAME_HEIGHT * 2 / 3 + j, null);
                }
            }

        //SIGNPOST
        c.drawBitmap(Images.SIGNPOST.getImg(),GameConstants.GAME_WIDTH / 16,GameConstants.GAME_HEIGHT * 2 / 3 - Images.SIGNPOST.getHeight() * Images.SIGNPOST.getScale(), null);



        //BUTTONS
        c.drawBitmap(ButtonImages.MENU_START.getBtnImg(startBtn.isPushed()),
                startBtn.getHitbox().left, startBtn.getHitbox().top, null);

        c.drawBitmap(ButtonImages.MENU_SETTINGS.getBtnImg(settingsBtn.isPushed()),
                settingsBtn.getHitbox().left, settingsBtn.getHitbox().top, null);

        c.drawBitmap(ButtonImages.MENU_STAR.getBtnImg(starBtn.isPushed()),
                starBtn.getHitbox().left, starBtn.getHitbox().top, null);

        c.drawBitmap(ButtonImages.MENU_MUTE.getBtnImg(muteBtn.isPushed()),
                muteBtn.getHitbox().left, muteBtn.getHitbox().top, null);

        c.drawBitmap(ButtonImages.MENU_QUESTION.getBtnImg(questionBtn.isPushed()),
                questionBtn.getHitbox().left, questionBtn.getHitbox().top, null);



    }

    @Override
    public void touchEvents(MotionEvent event) {

        if(event.getAction() == MotionEvent.ACTION_DOWN){
            if (isIn(event, startBtn.getHitbox()))
                startBtn.setPushed(true);
            if (isIn(event, settingsBtn.getHitbox()))
                settingsBtn.setPushed(true);
            if (isIn(event, starBtn.getHitbox()))
                starBtn.setPushed(true);
            if (isIn(event, muteBtn.getHitbox()))
                if (muteBtn.isPushed()){
                    muteBtn.setPushed(false);
                    game.setMuted(false);
                } else {
                    muteBtn.setPushed(true);
                    game.setMuted(true);
                }
            if (isIn(event, questionBtn.getHitbox()))
                questionBtn.setPushed(true);






        } else if(event.getAction() == MotionEvent.ACTION_UP){
            if(isIn(event, startBtn.getHitbox()))
                if(startBtn.isPushed()) {
                    game.setCurrentGameState(Game.GameState.PLAYING);
                }
            if(isIn(event, settingsBtn.getHitbox()))
                if(settingsBtn.isPushed())
                    game.setCurrentGameState(Game.GameState.SETTINGS);
            if(isIn(event, starBtn.getHitbox()))
                if(starBtn.isPushed())
                    game.setCurrentGameState(Game.GameState.SCORES);
            if(isIn(event, questionBtn.getHitbox()))
                if(questionBtn.isPushed())
                    game.setCurrentGameState(Game.GameState.CREDITS);

            startBtn.setPushed(false);
            settingsBtn.setPushed(false);
            starBtn.setPushed(false);
            questionBtn.setPushed(false);



        }
    }

    private boolean isIn(MotionEvent e, RectF b){
        return b.contains(e.getX(),e.getY());
    }



}
