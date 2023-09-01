package com.bsw.snakes.helpers.interfaces;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.bsw.snakes.helpers.Scalers;

public interface BitmapMethods {

    BitmapFactory.Options options = new BitmapFactory.Options();


    default Bitmap getScaledBitmap(Bitmap bitmap){
        return Bitmap.createScaledBitmap(bitmap,(int) (bitmap.getWidth() * Scalers.SCALER), (int) (bitmap.getHeight() * Scalers.SCALER), false);
    }

}
