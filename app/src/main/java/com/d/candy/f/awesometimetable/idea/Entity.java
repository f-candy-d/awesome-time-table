package com.d.candy.f.awesometimetable.idea;

import android.content.ContentValues;

/**
 * Created by daichi on 7/14/17.
 */

abstract public class Entity {

    /**
     * @return ContentValues object
     */
    abstract public ContentValues toContentValues();

    /**
     * @return An unique integer which specify an entity type
     */
    abstract public int getEntityType();

    /**
     * @return The name of a table where the entity belong to
     */
    abstract public String getAffiliation();
}
