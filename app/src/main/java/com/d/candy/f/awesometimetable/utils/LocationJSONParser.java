package com.d.candy.f.awesometimetable.utils;

import android.util.SparseArray;

import com.d.candy.f.awesometimetable.entity.Location;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by daichi on 7/12/17.
 */

public class LocationJSONParser extends BaseJSONParser {

    private static final String TAG = LogHelper.makeLogTag(LocationJSONParser.class);

    /**
     * JSON key names
     */
    private static final String KEY_LOCATIONS = "locations";
    private static final String KEY_NAME = "name";
    private static final String KEY_ID = "ID";

    public static SparseArray<Location> parse(String jsonStr) {

        if(jsonStr == null) {
            throw new IllegalArgumentException(TAG+"in parse(): jsonStr is null...");
        }
        SparseArray<Location> locations = new SparseArray<>();

        try {
            JSONArray jsonArray = new JSONObject(jsonStr).getJSONArray(KEY_LOCATIONS);
            for(int i = 0; i < jsonArray.length(); ++i) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                // Set data
                Location location = new Location();
                location.setName(jsonObject.getString(KEY_NAME));
                location.setID(jsonObject.getInt(KEY_ID));

                locations.put(location.getID(), location);
            }

        } catch (JSONException ex) {
            ex.printStackTrace();
            return null;
        }

        return locations;
    }
}
