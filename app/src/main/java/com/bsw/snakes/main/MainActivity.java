package com.bsw.snakes.main;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bsw.snakes.helpers.GameConstants;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private static Context gameContext;

    //SharedPreferences settings;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gameContext = this;

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getRealMetrics(dm);

        GameConstants.GAME_WIDTH = dm.widthPixels;
        GameConstants.GAME_HEIGHT = dm.heightPixels;

        loadData();


        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
                        View.SYSTEM_UI_FLAG_FULLSCREEN |
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P){
            getWindow().getAttributes().layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
        }

        setContentView(new GamePanel(this));
    }

    private void loadData(){

        /*
        settings = getSharedPreferences("settings",Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = settings.edit();

        editor.putInt("xmapsize",3);
        editor.putInt("xmapsize",3);
        editor.commit();

        SharedPreferences set = getApplicationContext().getSharedPreferences("settings",Context.MODE_PRIVATE);

        int value = set.getInt("xmapsize",4);

        System.out.println(value);

         */




    }

    public static Context getGameContext(){
        return gameContext;
    }

}