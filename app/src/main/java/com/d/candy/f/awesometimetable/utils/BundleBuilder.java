package com.d.candy.f.awesometimetable.utils;

import android.os.Bundle;

import java.io.Serializable;

/**
 * Created by daichi on 7/20/17.
 */

public class BundleBuilder {

    private final Bundle mBundle;

    public BundleBuilder() {
        this(null);
    }

    /**
     * Init with the passed bundle object
     * @param bundle
     */
    public BundleBuilder(final Bundle bundle) {
        mBundle = (bundle != null) ? new Bundle(bundle) : new Bundle();
    }

    public Bundle build() {
        return new Bundle(mBundle);
    }

    public BundleBuilder clear() {
        mBundle.clear();
        return this;
    }

    public BundleBuilder remove(final String key) {
        mBundle.remove(key);
        return this;
    }

    public BundleBuilder put(final String key, Serializable val) {
        mBundle.putSerializable(key, val);
        return this;
    }
}
