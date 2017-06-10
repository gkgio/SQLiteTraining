package com.gkgio.android.sqlitetraining.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.gkgio.android.sqlitetraining.model.Animal;

import java.util.ArrayList;
import java.util.List;

public class SQLiteAnimalsDao extends SQLiteOpenHelper
        implements AnimalsDao {

    private static final int CURRENT_VERSION = 1;
    private static final String NAME = "animals.db";
    private static final long NO_ID = -1;
    private static final String TABLE_NAME = "animals";
    private static final String TAG = "SQLiteAnimals";

    public SQLiteAnimalsDao(Context context) {
        this(context, NAME, CURRENT_VERSION);
    }

    public SQLiteAnimalsDao(Context context, String name, int version) {
        super(context, name, null, version, null);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE_NAME + "(" +
                AnimalsContract.Animals._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                AnimalsContract.Animals.SPECIES + " TEXT NOT NULL, " +
                AnimalsContract.Animals.AGE + " INTEGER NOT NULL, " +
                AnimalsContract.Animals.NAME + " TEXT NOT NULL" +
                ");";
        Log.e(TAG, "sql = " + sql);
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    @Override
    public long insertAnimal(Animal animal) {
        long id = NO_ID;
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues values = createValuesFromAnimal(animal);
            id = db.insert(TABLE_NAME, null, values);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
            db.close();
        }
        return id;
    }

    @Override
    public List<Animal> getAnimals() {
        List<Animal> animals = null;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = db.query(TABLE_NAME, null, null, null, null, null, null);
            animals = new ArrayList<>();
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                animals.add(createAnimal(cursor));
                cursor.moveToNext();
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }
        return animals;
    }

    //Не используется
    @Override
    public Animal getAnimalById(long id) {
        Animal animal = null;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = db.query(TABLE_NAME, null, AnimalsContract.Animals._ID + " = ?",
                    new String[]{String.valueOf(id)}, null, null, null);
            cursor.moveToFirst();
            animal = createAnimal(cursor);

        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }
        return animal;
    }

    @Override
    public int updateAnimal(Animal animal) {
        int rowCount = 0;
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues values = createValuesFromAnimal(animal);
            rowCount = db.update(TABLE_NAME, values, AnimalsContract.Animals._ID + " = ?",
                    new String[]{String.valueOf(animal.getId())});
            db.setTransactionSuccessful();

        } finally {
            db.endTransaction();
            db.close();
        }
        return rowCount;
    }

    @Override
    public int deleteAnimal(Animal animal) {
        int rowCount = 0;
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {
            rowCount = db.delete(TABLE_NAME, AnimalsContract.Animals._ID + " = ?",
                    new String[]{String.valueOf(animal.getId())});
            db.setTransactionSuccessful();

        } finally {
            db.endTransaction();
            db.close();
        }
        return rowCount;
    }

    private static Animal createAnimal(Cursor cursor) {
        Animal animal = new Animal();
        animal.setId(getLong(cursor, AnimalsContract.Animals._ID));
        animal.setSpecies(getString(cursor, AnimalsContract.Animals.SPECIES));
        animal.setAge(getInt(cursor, AnimalsContract.Animals.AGE));
        animal.setName(getString(cursor, AnimalsContract.Animals.NAME));
        return animal;
    }

    private static ContentValues createValuesFromAnimal(Animal animal) {
        ContentValues values = new ContentValues();
        values.put(AnimalsContract.Animals.SPECIES, animal.getSpecies());
        values.put(AnimalsContract.Animals.AGE, animal.getAge());
        values.put(AnimalsContract.Animals.NAME, animal.getName());
        return values;
    }

    private static long getLong(Cursor cursor, String columnName) {
        return cursor.getLong(cursor.getColumnIndex(columnName));
    }

    private static String getString(Cursor cursor, String columnName) {
        return cursor.getString(cursor.getColumnIndex(columnName));
    }

    private static int getInt(Cursor cursor, String columnName) {
        return cursor.getInt(cursor.getColumnIndex(columnName));
    }
}