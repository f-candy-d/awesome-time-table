package com.d.candy.f.awesometimetable.structure;

import android.content.ContentValues;

import java.io.Serializable;

/**
 * Created by daichi on 7/14/17.
 */

abstract public class Entity implements Serializable {

    private int mID;

    public Entity() {}

    public Entity(final int id) {
        mID = id;
    }

    public int getID() {
        return mID;
    }

    public void setID(final int id) {
        mID = id;
    }

    /**
     * @return ContentValues object
     */
    abstract public ContentValues toContentValues();

    /**
     * @return An enum value which specify an entity type
     */
    abstract public EntityType getEntityType();

    /**
     * @return The name of a table where the entity belong to
     */
    abstract public String getAffiliation();
}
