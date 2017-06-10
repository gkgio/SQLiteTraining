package com.gkgio.android.sqlitetraining;

import com.gkgio.android.sqlitetraining.model.Animal;

/**
 * Created by Георгий on 09.06.2017.
 * gio.com
 */

public interface OnContentChangeListener {
    void onAnimalAdded(AnimalsStorage sender, Animal animal);
}