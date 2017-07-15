package com.d.candy.f.awesometimetable.structure;

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
     * @return An enum value which specify an entity type
     */
    abstract public EntityType getEntityType();

    /**
     * @return The name of a table where the entity belong to
     */
    abstract public String getAffiliation();
}
