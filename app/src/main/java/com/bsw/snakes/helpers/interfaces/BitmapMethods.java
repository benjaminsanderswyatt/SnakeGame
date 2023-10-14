package com.bsw.snakes.helpers.interfaces;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public interface BitmapMethods {

    BitmapFactory.Options options = new BitmapFactory.Options();

    default Bitmap getScaledBitmap(Bitmap bitmap, float scale){
        return Bitmap.createScaledBitmap(bitmap,(int) (bitmap.getWidth() * scale), (int) (bitmap.getHeight() * scale), false);
    }

}
