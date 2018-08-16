package com.example.android.pets.data;

import android.provider.BaseColumns;

/**
 * Created by Ha3el on 15/08/2018.
 */

public final class PetContract {

    //private empty method to prevent anyone creating an instance of it
    private PetContract() {}

    //inner class to represent the pets table and its' columns
    public static abstract class PetEntry implements BaseColumns {

        public static final String TABLE_NAME = "pets";
        public static final String COLUMN_ID = BaseColumns._ID;
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_BREED = "breed";
        public static final String COLUMN_GENDER = "gender";
        public static final String COLUMN_WEIGHT = "weight";


        //fixed number of integer constants to represent gender
        public static final int GENDER_UNKNOWN = 0;
        public static final int GENDER_MALE = 1;
        public static final int GENDER_FEMALE = 2;
    }
}
