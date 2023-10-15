package com.bsw.snakes.gamestates;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;

import com.bsw.snakes.helpers.interfaces.GameStateInterface;
import com.bsw.snakes.main.Game;

public class Paused extends BaseState implements GameStateInterface {

    private final Paint paint;
    public Paused(Game game) {
        super(game);
        paint = new Paint();
        paint.setTextSize(60);
        paint.setColor(Color.RED);
    }

    @Override
    public void update(double delta) {

    }

    @Override
    public void render(Canvas c) {
        c.drawText("Paused", 200, 800, paint);
        c.drawText("Current Score: ", 200, 1000, paint);
    }

    @Override
    public void touchEvents(MotionEvent event) {

    }
}
