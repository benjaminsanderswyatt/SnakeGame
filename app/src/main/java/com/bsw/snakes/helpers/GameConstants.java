package com.bsw.snakes.helpers;

public class GameConstants {

    public static int GAME_WIDTH;
    public static int GAME_HEIGHT;

    public static final float SCALER = 5f; //Make a multiple of 0.5.    as SCALER gets bigger map size decreases
    // Math.Ciel( GAMEWIDTH / BITSCALER ) = sizeOfMapX.    This is the formula to give you the map size which fits on the screen.
    //This can be reversed to input map size to get scaler but this is only in either X ^ Y
    //Math.Ciel( GAMEWIDTH / ( SIZEOFMAPX * 16 ) ) = SCALER
    public static final float BITSCALER = SCALER * 16; //The bitmap scale factor for the sprites

    //public static final float MAPSCALER = 4f + 5/8f; //decimal values of 1/8
    //public static final float MAPSCALER = 5.625f; //decimal values of 1/8
    // MAPSCALER = GAMEWIDTH / ((mapsizeX + 2)* 16)
    // get mapscaler for x and y and pick the smallest one



    public static final class FACE_Dir{
        public static final int UP = 0;
        public static final int LEFT = 1;
        public static final int DOWN = 2;
        public static final int RIGHT = 3;
    }
}
