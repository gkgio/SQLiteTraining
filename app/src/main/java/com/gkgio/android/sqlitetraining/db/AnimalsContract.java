package com.gkgio.android.sqlitetraining.db;

import android.provider.BaseColumns;

public class AnimalsContract {

    public static class Animals implements BaseColumns {

        public static final String SPECIES = "species";
        public static final String AGE = "age";
        public static final String NAME = "name";

    }
}