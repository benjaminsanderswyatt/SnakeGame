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
import com.bsw.snakes.ui.ButtonImages;
import com.bsw.snakes.ui.CloudType;
import com.bsw.snakes.ui.Clouds;
import com.bsw.snakes.ui.CustomButton;
import com.bsw.snakes.ui.Images;

import java.util.ArrayList;
import java.util.Random;

public class Death extends BaseState implements GameStateInterface {

    private Paint paint;

    private CustomButton menuBtn, restartBtn;

    private ArrayList<Clouds> clouds = new ArrayList<>();
    private CloudType cloudTypeRnd;

    private Random rnd = new Random();




    public Death(Game game) {
        super(game);
        paint = new Paint();
        paint.setTextSize(60);
        paint.setColor(Color.RED);


        menuBtn = new CustomButton((GameConstants.GAME_WIDTH - ButtonImages.BACK_TO_MENU_TEXT.getWidth() * ButtonImages.BACK_TO_MENU.getScale()) / 2,GameConstants.GAME_HEIGHT * 13/16, ButtonImages.BACK_TO_MENU_TEXT.getWidth(), ButtonImages.BACK_TO_MENU_TEXT.getHeight(), ButtonImages.BACK_TO_MENU_TEXT.getScale());
        restartBtn = new CustomButton((GameConstants.GAME_WIDTH - ButtonImages.RESTART.getWidth() * ButtonImages.RESTART.getScale()) / 2,GameConstants.GAME_HEIGHT * 14/16, ButtonImages.RESTART.getWidth(), ButtonImages.RESTART.getHeight(), ButtonImages.RESTART.getScale());


        for (int i = 0; i <= 30; i++) {
            cloudTypeRnd = CloudType.values()[rnd.nextInt(CloudType.values().length)];
            clouds.add(new Clouds(rnd.nextInt(GameConstants.GAME_WIDTH), cloudTypeRnd.getHeight() + rnd.nextInt(GameConstants.GAME_HEIGHT / 2), cloudTypeRnd, 3 + rnd.nextInt(3)));
        }


    }

    int rainOffsetX = 0, rainOffsetY = 0;



    @Override
    public void update(double delta) {

        //MOVE RAIN
        if (rainOffsetY <= GameConstants.BITSCALER){
            rainOffsetY += 8;
        } else {
            rainOffsetY = 8;
        }
        if (rainOffsetX <= GameConstants.BITSCALER){
            rainOffsetX += 2;
        } else {
            rainOffsetX = 2;
        }


        //MOVE CLOUDS
        for(Clouds element : clouds){
            element.setX(element.getX() + element.getSpeed());

            if (element.getX() >= GameConstants.GAME_WIDTH){
                CloudType cloudTypeRnd = CloudType.values()[rnd.nextInt(CloudType.values().length)];

                element.setX(-cloudTypeRnd.getWidth() * cloudTypeRnd.getScale());
                element.setY(cloudTypeRnd.getHeight() + rnd.nextInt(GameConstants.GAME_HEIGHT / 2));
                element.setCloudType(cloudTypeRnd);
                element.setSpeed(3 + rnd.nextInt(3));
            }
        }

    }

    @Override
    public void render(Canvas c) {

        c.drawColor(0xFF38435B);


        //RAIN
        for (int j = 0; j <= GameConstants.GAME_HEIGHT * 2 / 3; j += GameConstants.BITSCALER)
            for (int i = 0; i <= GameConstants.GAME_WIDTH + GameConstants.BITSCALER; i += GameConstants.BITSCALER) {
                c.drawBitmap(Floor.OUTSIDE.getSprites(3, 5), i - rainOffsetX, j - GameConstants.BITSCALER + rainOffsetY, null);
            }


        //CLOUDS
        for(Clouds element : clouds){
            switch (element.getCloudType()){
                case CLOUD1:
                    c.drawBitmap(CloudType.CLOUD1.getTintedImg(), element.getX() , element.getY() , null);
                    break;
                case CLOUD2:
                    c.drawBitmap(CloudType.CLOUD2.getTintedImg(), element.getX() ,element.getY() , null);
                    break;
                case CLOUD3:
                    c.drawBitmap(CloudType.CLOUD3.getTintedImg(), element.getX() ,element.getY() , null);
                    break;
                case CLOUD4:
                    c.drawBitmap(CloudType.CLOUD4.getTintedImg(), element.getX() ,element.getY() , null);
                    break;
            }

        }


        //FLOOR
        for (int j = 0; j <= GameConstants.GAME_HEIGHT / 3; j += GameConstants.BITSCALER)
            for (int i = 0; i <= GameConstants.GAME_WIDTH; i += GameConstants.BITSCALER) {
                if (j == 0) {
                    c.drawBitmap(Floor.OUTSIDE.getSprites(5, 5), i, GameConstants.GAME_HEIGHT * 2 / 3 + j, null);
                } else {
                    c.drawBitmap(Floor.OUTSIDE.getSprites(4, 5), i, GameConstants.GAME_HEIGHT * 2 / 3 + j, null);
                }
            }


        //GRAVE
        c.drawBitmap(Images.GRAVE.getImg(),GameConstants.GAME_WIDTH / 16,GameConstants.GAME_HEIGHT * 2 / 3 - (Images.GRAVE.getHeight() - 4) * Images.GRAVE.getScale() , null);


        //BUTTONS
        c.drawBitmap(ButtonImages.BACK_TO_MENU_TEXT.getBtnImg(menuBtn.isPushed()),
                menuBtn.getHitbox().left, menuBtn.getHitbox().top, null);
        c.drawBitmap(ButtonImages.RESTART.getBtnImg(restartBtn.isPushed()),
                restartBtn.getHitbox().left, restartBtn.getHitbox().top, null);



        paint = new Paint();
        paint.setTextSize(60);
        paint.setColor(Color.RED);

        c.drawText(String.valueOf(game.getGameSpeed()), 200, 800, paint);








    }

    @Override
    public void touchEvents(MotionEvent event) {

        if(event.getAction() == MotionEvent.ACTION_DOWN){
            if (isIn(event, menuBtn.getHitbox()))
                menuBtn.setPushed(true);
            if(isIn(event, restartBtn.getHitbox()))
                restartBtn.setPushed(true);

        } else if(event.getAction() == MotionEvent.ACTION_UP){
            if(isIn(event, menuBtn.getHitbox()))
                if(menuBtn.isPushed()) {
                    game.setCurrentGameState(Game.GameState.MENU);
                }
            if(isIn(event, restartBtn.getHitbox()))
                if(restartBtn.isPushed())
                    //reset all values
                    game.setCurrentGameState(Game.GameState.PLAYING);

            menuBtn.setPushed(false);
            restartBtn.setPushed(false);


        }

    }

    private boolean isIn(MotionEvent e, RectF b){
        return b.contains(e.getX(),e.getY());
    }

}
