package com.bsw.snakes.gamestates;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;

import com.bsw.snakes.helpers.GameConstants;
import com.bsw.snakes.helpers.interfaces.GameStateInterface;
import com.bsw.snakes.main.Game;
import com.bsw.snakes.ui.ButtonImages;
import com.bsw.snakes.ui.CustomButton;
import com.bsw.snakes.ui.Images;

public class Paused extends BaseState implements GameStateInterface {

    private final CustomButton playBtn;

    public Paused(Game game) {
        super(game);

        //UI
        playBtn = new CustomButton(GameConstants.GAME_WIDTH * 1/100,GameConstants.GAME_HEIGHT * 1 /100, ButtonImages.PLAYING_PAUSE.getWidth(), ButtonImages.PLAYING_PAUSE.getHeight(), ButtonImages.PLAYING_PAUSE.getScale());

    }

    @Override
    public void update(double delta) {

    }

    @Override
    public void render(Canvas c) {

        //PAUSE BUTTON
        c.drawBitmap(ButtonImages.PAUSED_PLAY.getBtnImg(playBtn.isPushed()),
                playBtn.getHitbox().left, playBtn.getHitbox().top, null);

        float middleX = (GameConstants.GAME_WIDTH - Images.PAUSED.getWidth() * Images.PAUSED.getScale()) / 2;
        float middleY = (GameConstants.GAME_HEIGHT - Images.PAUSED.getHeight() * Images.PAUSED.getScale()) / 2;

        c.drawBitmap(Images.PAUSED.getImg(),middleX, middleY, null);

    }

    @Override
    public void touchEvents(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                if (isIn(event, playBtn))
                    playBtn.setPushed(true);
                break;

            case MotionEvent.ACTION_UP:
                if (isIn(event, playBtn))
                    if (playBtn.isPushed())
                        game.setCurrentGameState(Game.GameState.PLAYING);

                playBtn.setPushed(false);

                break;
        }
    }

    private boolean isIn(MotionEvent e, CustomButton b){
        return b.getHitbox().contains(e.getX(),e.getY());
    }
}
