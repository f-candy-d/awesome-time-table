package com.d.candy.f.awesometimetable.utils;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by daichi on 7/12/17.
 */

public class BaseJSONParser {

    public static String loadJSONFromAsset(Context context, String file) {

        String json;
        try {
            InputStream is = context.getAssets().open(file);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");

        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }

        return json;
    }
}
