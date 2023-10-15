package com.bsw.snakes.gamestates;

import com.bsw.snakes.main.Game;

public abstract class BaseState {

    public final Game game;

    public BaseState(Game game) {
        this.game = game;
    }

    public Game getGame(){
        return game;
    }


}
