package com.bsw.snakes.main;

import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import com.bsw.snakes.gamestates.Death;
import com.bsw.snakes.gamestates.Menu;
import com.bsw.snakes.gamestates.Playing;
import com.bsw.snakes.helpers.interfaces.GameStateInterface;

public class Game {

    private SurfaceHolder holder;

    private GameLoop gameLoop;

    private Menu menu;
    private Playing playing;
    private Death death;

    private GameState currentGameState = GameState.MENU;

    public Game(SurfaceHolder holder){
        this.holder = holder;
        gameLoop = new GameLoop(this);
        initGameStates();
    }


    public void update(double delta){
        switch (currentGameState){
            case MENU:
                menu.update(delta);
                break;
            case PLAYING:
                playing.update(delta);
                break;
            case DEATH:
                death.update(delta);
                break;
        }
    }

    public void render() {

        Canvas c = holder.lockCanvas();
        c.drawColor(Color.BLACK);

        //Draw the game
        switch (currentGameState){
            case MENU:
                menu.render(c);
                break;
            case PLAYING:
                playing.render(c);
                break;
            case DEATH:
                death.render(c);
                break;
        }


        holder.unlockCanvasAndPost(c);
    }

    private void initGameStates() {
        menu = new Menu(this);
        playing = new Playing(this);
        death = new Death(this);

    }

    public boolean touchEvent(MotionEvent event) {
        switch (currentGameState){
            case MENU:
                menu.touchEvents(event);
                break;
            case PLAYING:
                playing.touchEvents(event);
                break;
            case DEATH:
                death.touchEvents(event);
                break;
        }

        return true;
    }

    public void startGameLoop() {
        gameLoop.startGameLoop();
    }

    public enum GameState{
        MENU, PLAYING, DEATH;
    }

    public GameState getCurrentGameState(){
        return currentGameState;
    }

    public void setCurrentGameState(GameState currentGameState){
        this.currentGameState = currentGameState;
    }
}
