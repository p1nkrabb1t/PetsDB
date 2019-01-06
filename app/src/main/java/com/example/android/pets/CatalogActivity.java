package com.example.android.pets;

import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.android.pets.data.PetContract.PetEntry;

//import com.example.android.pets.data.PetDbHelper; --no longer used

/**
 * Displays list of pets that were entered and stored in the app.
 */
public class CatalogActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    //PetDbHelper is no longer used
    //private PetDbHelper mDbHelper;

    private static final int PET_LOADER = 0;


    // This is the Adapter being used to display the list's data
    PetCursorAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);

        // Setup FAB to open EditorActivity
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CatalogActivity.this, EditorActivity.class);
                startActivity(intent);
            }
        });

        ListView displayView = (ListView) findViewById(R.id.list);
        // Find and set empty view on the ListView, so that it only shows when the list has 0 items.
        View emptyView = findViewById(R.id.empty_view);
        displayView.setEmptyView(emptyView);

        mAdapter = new PetCursorAdapter(this, null);

        displayView.setAdapter(mAdapter);

        displayView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //create new intent to go the the editor activity
                Intent intent = new Intent(CatalogActivity.this, EditorActivity.class);

                Uri currentPetUri = ContentUris.withAppendedId(PetEntry.CONTENT_URI, id);
                intent.setData(currentPetUri);
                startActivity(intent);
            }
        });

        //start the loader
        getLoaderManager().initLoader(PET_LOADER, null, this);

        //mDbHelper = new PetDbHelper(this);  --no longer used

        // Create and/or open a database to read from it
        //SQLiteDatabase db = mDbHelper.getReadableDatabase();
        //displayDatabaseInfo();
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        displayDatabaseInfo();
//    }

//    /**
//     * Temporary helper method to display information in the onscreen TextView about the state of
//     * the pets database.
//     */
//    private void displayDatabaseInfo() {
//        // To access our database, we instantiate our subclass of SQLiteOpenHelper
//        // and pass the context, which is the current activity.
//
//
//        // Create and/or open a database to read from it
//        //SQLiteDatabase db = mDbHelper.getReadableDatabase();
//
//        // Perform this raw SQL query "SELECT * FROM pets"
//        // to get a Cursor that contains all rows from the pets table.
//        //Cursor cursor = db.rawQuery("SELECT * FROM " + PetEntry.TABLE_NAME, null);
//
//
//        String[] projection = {
//                PetEntry._ID,
//                PetEntry.COLUMN_NAME,
//                PetEntry.COLUMN_BREED,
//                PetEntry.COLUMN_GENDER,
//                PetEntry.COLUMN_WEIGHT
//        };
//
//        Cursor cursor = getContentResolver().query(PetEntry.CONTENT_URI, projection, null, null,
//                null);
//
//        ListView displayView = (ListView) findViewById(R.id.list);
//        // Find and set empty view on the ListView, so that it only shows when the list has 0 items.
//        View emptyView = findViewById(R.id.empty_view);
//        displayView.setEmptyView(emptyView);
//
//        PetCursorAdapter adapter = new PetCursorAdapter(this, cursor);
//
//        displayView.setAdapter(adapter);
//
//
////            // Figure out the index of each column
////            int idColumnIndex = cursor.getColumnIndex(PetEntry._ID);
////            int nameColumnIndex = cursor.getColumnIndex(PetEntry.COLUMN_NAME);
////            int breedColumnIndex = cursor.getColumnIndex(PetEntry.COLUMN_BREED);
////            int genderColumnIndex = cursor.getColumnIndex(PetEntry.COLUMN_GENDER);
////            int weightColumnIndex = cursor.getColumnIndex(PetEntry.COLUMN_WEIGHT);
////
////            // Iterate through all the returned rows in the cursor
////            while (cursor.moveToNext()) {
////                // Use that index to extract the String or Int value of the word
////                // at the current row the cursor is on.
////                int currentID = cursor.getInt(idColumnIndex);
////                String currentName = cursor.getString(nameColumnIndex);
////                String currentBreed = cursor.getString(breedColumnIndex);
////                int currentGender = cursor.getInt(genderColumnIndex);
////                int currentWeight = cursor.getInt(weightColumnIndex);
////                // Display the values from each column of the current row in the cursor in the TextView
////                displayView.append(("\n" + currentID + " - " +
////                        currentName + " - " +
////                        currentBreed + " - " +
////                        currentGender + " - " +
////                        currentWeight));
////            }
////
////
////        } finally {
////            // Always close the cursor when you're done reading from it. This releases all its
////            // resources and makes it invalid.
////            cursor.close();
////        }
//    }

    private void insertPet() {
        //SQLiteDatabase db = mDbHelper.getWritableDatabase(); - no longer referenced directly
        ContentValues petValues = new ContentValues();

        petValues.put(PetEntry.COLUMN_NAME, "Toto");
        petValues.put(PetEntry.COLUMN_BREED, "Terrier");
        petValues.put(PetEntry.COLUMN_GENDER, PetEntry.GENDER_MALE);
        petValues.put(PetEntry.COLUMN_WEIGHT, 7);

        Uri newEntry = getContentResolver().insert(PetEntry.CONTENT_URI, petValues);

        //long newEntry = db.insert(PetEntry.TABLE_NAME, null, petValues);
        Log.v("Catalog Activity", " - new entry num: " + newEntry);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_catalog.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_catalog, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Insert dummy data" menu option
            case R.id.action_insert_dummy_data:
                insertPet();
                //displayDatabaseInfo();
                return true;
            // Respond to a click on the "Delete all entries" menu option
            case R.id.action_delete_all_entries:
                // Do nothing for now
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] Projection = new String[]{PetEntry._ID,
                PetEntry.COLUMN_NAME,
                PetEntry.COLUMN_BREED,
                PetEntry.COLUMN_WEIGHT,
                PetEntry.COLUMN_GENDER};
        return new CursorLoader(this, PetEntry.CONTENT_URI, Projection, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }
}
