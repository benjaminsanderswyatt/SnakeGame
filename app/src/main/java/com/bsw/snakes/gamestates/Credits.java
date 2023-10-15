package com.bsw.snakes.gamestates;

import android.graphics.Canvas;
import android.view.MotionEvent;

import com.bsw.snakes.enviroments.Floor;
import com.bsw.snakes.helpers.GameConstants;
import com.bsw.snakes.helpers.interfaces.GameStateInterface;
import com.bsw.snakes.main.Game;
import com.bsw.snakes.ui.ButtonImages;
import com.bsw.snakes.ui.CustomButton;
import com.bsw.snakes.ui.Images;

public class Credits extends BaseState implements GameStateInterface {

    private final CustomButton menuBtn;

    public Credits(Game game){
        super(game);

        menuBtn = new CustomButton(
                (GameConstants.GAME_WIDTH - ButtonImages.BACK_TO_MENU.getWidth() * ButtonImages.BACK_TO_MENU.getScale()) / 2,
                GameConstants.GAME_HEIGHT * 1/10 + (Images.GAME_PLAY_SAMPLE.getHeight() + 32) * Images.GAME_PLAY_SAMPLE.getScale()
                , ButtonImages.BACK_TO_MENU.getWidth(),ButtonImages.BACK_TO_MENU.getHeight(), ButtonImages.BACK_TO_MENU.getScale());


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
                    c.drawBitmap(Floor.OUTSIDE.getSprites(5, 10), i, GameConstants.GAME_HEIGHT * 1 / 10 + j, null);
                } else {
                    c.drawBitmap(Floor.OUTSIDE.getSprites(4, 10), i, GameConstants.GAME_HEIGHT * 1 / 10 + j, null);
                }
            }

        c.drawBitmap(Images.GAME_PLAY_SAMPLE.getImg(),(GameConstants.GAME_WIDTH - Images.GAME_PLAY_SAMPLE.getWidth() * Images.GAME_PLAY_SAMPLE.getScale()) / 2, GameConstants.GAME_HEIGHT * 1/10 + 16 * Images.GAME_PLAY_SAMPLE.getScale(), null);


        c.drawBitmap(Images.SIGNPOST.getImg(),GameConstants.GAME_WIDTH / 16, GameConstants.GAME_HEIGHT * 1 / 10 - Images.SIGNPOST.getHeight() * Images.SIGNPOST.getScale(), null);


        c.drawBitmap(ButtonImages.BACK_TO_MENU.getBtnImg(menuBtn.isPushed()),
                menuBtn.getHitbox().left, menuBtn.getHitbox().top, null);

    }

    @Override
    public void touchEvents(MotionEvent event) {

        if(event.getAction() == MotionEvent.ACTION_DOWN){
            if (isIn(event, menuBtn))
                menuBtn.setPushed(true);


        } else if(event.getAction() == MotionEvent.ACTION_UP){
            if(isIn(event, menuBtn))
                if(menuBtn.isPushed())
                    game.setCurrentGameState(Game.GameState.MENU);

            menuBtn.setPushed(false);
        }
    }

    private boolean isIn(MotionEvent e, CustomButton b){
        return b.getHitbox().contains(e.getX(),e.getY());
    }


}
