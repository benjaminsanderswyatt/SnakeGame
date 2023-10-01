package com.bsw.snakes.main;

import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import com.bsw.snakes.gamestates.Credits;
import com.bsw.snakes.gamestates.Death;
import com.bsw.snakes.gamestates.Menu;
import com.bsw.snakes.gamestates.Paused;
import com.bsw.snakes.gamestates.Playing;
import com.bsw.snakes.gamestates.Scores;
import com.bsw.snakes.gamestates.Settings;

public class Game {

    private SurfaceHolder holder;

    private GameLoop gameLoop;

    private Menu menu;
    private Settings settings;
    private Scores scores;
    private Credits credits;
    private Playing playing;
    private Paused paused;
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
            case SETTINGS:
                settings.update(delta);
                break;
            case SCORES:
                scores.update(delta);
                break;
            case CREDITS:
                credits.update(delta);
                break;
            case PLAYING:
                playing.update(delta);
                break;
            case PAUSED:
                paused.update(delta);
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
            case SETTINGS:
                settings.render(c);
                break;
            case SCORES:
                scores.render(c);
                break;
            case CREDITS:
                credits.render(c);
                break;
            case PLAYING:
                playing.render(c);
                break;
            case PAUSED:
                paused.render(c);
                break;
            case DEATH:
                death.render(c);
                break;
        }


        holder.unlockCanvasAndPost(c);
    }

    private void initGameStates() {
        menu = new Menu(this);
        settings = new Settings(this);
        scores = new Scores(this);
        credits = new Credits(this);
        playing = new Playing(this);
        paused = new Paused(this);
        death = new Death(this);

    }

    public boolean touchEvent(MotionEvent event) {
        switch (currentGameState){
            case MENU:
                menu.touchEvents(event);
                break;
            case SETTINGS:
                settings.touchEvents(event);
                break;
            case SCORES:
                scores.touchEvents(event);
                break;
            case CREDITS:
                credits.touchEvents(event);
                break;
            case PLAYING:
                playing.touchEvents(event);
                break;
            case PAUSED:
                paused.touchEvents(event);
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

    public void GameSettings(){

    }




    public enum GameState{
        MENU, SETTINGS, SCORES, CREDITS, PLAYING, PAUSED, DEATH;
    }

    public GameState getCurrentGameState(){
        return currentGameState;
    }

    public void setCurrentGameState(GameState currentGameState){
        this.currentGameState = currentGameState;
    }
}
