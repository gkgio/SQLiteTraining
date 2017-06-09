package com.gkgio.android.sqlitetraining.app;

import android.app.Application;

import com.gkgio.android.sqlitetraining.AnimalsStorage;
import com.gkgio.android.sqlitetraining.db.AnimalsDao;
import com.gkgio.android.sqlitetraining.db.SQLiteAnimalsDao;

public class SQLiteTrainingApplication extends Application {

    private AnimalsStorage animalsStorage;

    @Override
    public void onCreate() {
        super.onCreate();
        AnimalsDao animalsDao = new SQLiteAnimalsDao(this);
        animalsStorage = new AnimalsStorage(animalsDao);
    }
    public AnimalsStorage getAnimalsStorage() {
        return animalsStorage;
    }
}