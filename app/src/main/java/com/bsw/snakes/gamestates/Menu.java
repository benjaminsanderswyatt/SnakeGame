package com.bsw.snakes.gamestates;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;

import com.bsw.snakes.helpers.interfaces.GameStateInterface;
import com.bsw.snakes.main.Game;

public class Menu extends BaseState implements GameStateInterface {


    private Paint paint;
    public Menu(Game game){
        super(game);
        paint = new Paint();
        paint.setTextSize(60);
        paint.setColor(Color.WHITE);
    }



    @Override
    public void update(double delta) {

    }

    @Override
    public void render(Canvas c) {
        c.drawText("Menu", 200, 800, paint);

    }

    @Override
    public void touchEvents(MotionEvent event) {
        game.setCurrentGameState(Game.GameState.PLAYING);
    }
}
