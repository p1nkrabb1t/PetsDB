package com.example.android.pets.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.android.pets.data.PetContract.PetEntry;

/**
 * Created by Ha3el on 16/08/2018.
 */

public class PetDbHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "shelter.db";

    private static final int DB_VERSION = 1;

    public PetDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase shelter) {

        String createPetsSqlTable = "CREATE TABLE " + PetEntry.TABLE_NAME + "(" + PetEntry.COLUMN_ID +
                " INTEGER PRIMARY KEY AUTOINCREMENT, " + PetEntry.COLUMN_NAME + " TEXT NOT NULL, " +
                PetEntry.COLUMN_BREED + " TEXT, " + PetEntry.COLUMN_GENDER + " INTEGER NOT NULL, " +
                PetEntry.COLUMN_WEIGHT + " INTEGER NOT NULL DEFAULT 0);";
        shelter.execSQL(createPetsSqlTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase shelter, int oldVersion, int newVersion) {

    }

}
