package com.example.alper_arik.quiz;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.util.Log;
import java.util.ArrayList;

/**
 * Uses for manipulating database
 */
public class DatabaseHelper extends SQLiteOpenHelper{
    //Database infos and table columns
    private static final int DATABASE_VERSION=1;
    private static final String DATABASE_NAME="competitors.db";
    private static final String TABLE_COMPETITORS="competitors";
    private static final String COLUMN_ID="_id";
    private static final String COLUMN_NAME="_name";
    private static final String COLUMN_SURNAME="_surname";
    private static final String COLUMN_BIRTHDAY="_birthday";
    private static final String COLUMN_USERNAME="_username";
    private static final String COLUMN_PASSWORD="_password";
    private static final String COLUMN_SCORE="_score";

    //uses for singleton retrieving database
    private static DatabaseHelper dbHelper;

    //Constructor
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /*Other classes must access Database with this method
    * not creating new DatabaseHelper
    * (Singleton)
    */
    public static synchronized DatabaseHelper getInstance(Context context) {
        //if there is DatabaseHelper instance will not create another instance
        if (dbHelper == null) {
            dbHelper = new DatabaseHelper(context.getApplicationContext());
        }
        return dbHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_COMPETITORS + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ," +
                COLUMN_NAME + " TEXT ," +
                COLUMN_SURNAME + " TEXT ," +
                COLUMN_BIRTHDAY + " TEXT ," +
                COLUMN_USERNAME + " TEXT ," +
                COLUMN_PASSWORD + " TEXT ," +
                COLUMN_SCORE + " INTEGER" +
                ");";

        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXIST " + TABLE_COMPETITORS);
        onCreate(db);
    }

    public void deleteDB(){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMPETITORS);
        onCreate(db);
        Log.d("TAG", "Table has deleted successfully");
    }

    /**
     * Adds a new tuple to table
     * @param competitor competitor's info
     */
    public void addCompetitor(Competitors competitor){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        //puts informations to ContentValues instance
        values.put(COLUMN_NAME, competitor.get_name());
        values.put(COLUMN_SURNAME, competitor.get_surname());
        values.put(COLUMN_BIRTHDAY, competitor.get_birthday());
        values.put(COLUMN_USERNAME, competitor.get_username());
        values.put(COLUMN_PASSWORD, competitor.get_password());
        values.put(COLUMN_SCORE, competitor.get_score());

        //insert new tuple to table
        db.insert(TABLE_COMPETITORS, null, values);
        //closing db
        db.close();
        Log.d("TAG", "competitor has added");
    }

    /**
     * Gets selected competitor which has passed id
     * @param id    competitor's id
     * @return      competitor which has got passed id
     */
    public Competitors getCompetitor(int id){
        //temp Competitors variable
        Competitors competitor = null;
        SQLiteDatabase db = this.getWritableDatabase();
        String query="SELECT * FROM "+ TABLE_COMPETITORS + " WHERE "+ COLUMN_ID + "=" + id+";";
        // executes query
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        if(!cursor.isAfterLast()){
            //initialize competitor object with empty constructor
            competitor = new Competitors();
            //sets competitor informations
            competitor.set_id(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
            competitor.set_name(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
            competitor.set_surname(cursor.getString(cursor.getColumnIndex(COLUMN_SURNAME)));
            competitor.set_birthday(cursor.getString(cursor.getColumnIndex(COLUMN_BIRTHDAY)));
            competitor.set_score(cursor.getInt(cursor.getColumnIndex(COLUMN_SCORE)));

        }
        //closing cursor and db
        cursor.close();
        db.close();
        return competitor;
    }

    /**
     * Gets selected competitor which has passed username
     * @param username  competitor's username
     * @return  competitor which has got passed username
     */
    public Competitors getCompetitor(String username){
        //temp Competitors variable
        Competitors competitor = null;
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + TABLE_COMPETITORS + " WHERE "+ COLUMN_USERNAME + "='"+ username +"';";

        // executes query
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        if(!cursor.isAfterLast()){
            //initialize competitor object with empty constructor
            competitor = new Competitors();
            competitor.set_id(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
            competitor.set_name(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
            competitor.set_surname(cursor.getString(cursor.getColumnIndex(COLUMN_SURNAME)));
            competitor.set_birthday(cursor.getString(cursor.getColumnIndex(COLUMN_BIRTHDAY)));
            competitor.set_score(cursor.getInt(cursor.getColumnIndex(COLUMN_SCORE)));
        }
        //closing cursor and db
        cursor.close();
        db.close();
        return competitor;
    }

    /**
     * Gets selected competitor which has passed username and password
     * @param username  competitor's username
     * @return  competitor which has got passed username and password
     */
    public Competitors getCompetitor(String username, String password){
        //temp Competitors variable
        Competitors competitor = null;
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + TABLE_COMPETITORS + " WHERE "+ COLUMN_USERNAME + "='"+ username +
                "' AND " + COLUMN_PASSWORD + "='" + password + "';";

        // executes query
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        if(!cursor.isAfterLast()){
            //initialize competitor object with empty constructor
            competitor = new Competitors();
            competitor.set_id(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
            competitor.set_name(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
            competitor.set_surname(cursor.getString(cursor.getColumnIndex(COLUMN_SURNAME)));
            competitor.set_birthday(cursor.getString(cursor.getColumnIndex(COLUMN_BIRTHDAY)));
            competitor.set_score(cursor.getInt(cursor.getColumnIndex(COLUMN_SCORE)));
        }
        //closing cursor and db
        cursor.close();
        db.close();
        return competitor;
    }

    /**
     * Updates competitor's score in database
     * @param id    competitor's id
     * @param score competitor's score
     */
    public void setScore(int id, int score){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_SCORE, score);
        db.update(TABLE_COMPETITORS, values, COLUMN_ID + "=" + Integer.toString(id), null);
    }

    /**
     * Retreive all existing competitors from database
     * @return  all competitors in database via arraylist
     */
    public ArrayList<Competitors> getAllCompetitorScores(){
        //temp Competitors variable
        Competitors competitor=null;
        //competitors will store in al
        ArrayList<Competitors> al = new ArrayList<Competitors>();

        SQLiteDatabase db = getReadableDatabase();
        String query="SELECT _name, _surname, _score FROM "+TABLE_COMPETITORS;
        //executes query
        Cursor cursor = db.rawQuery(query,null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()){
            //initialize competitor object with empty constructor
            competitor = new Competitors();
            //sets competitor's name, surname and score
            competitor.set_name(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
            competitor.set_surname(cursor.getString(cursor.getColumnIndex(COLUMN_SURNAME)));
            competitor.set_score(cursor.getInt(cursor.getColumnIndex(COLUMN_SCORE)));
            //adds to arraylist
            al.add(competitor);
            //iterate cursor
            cursor.moveToNext();
        }
        //closing cursor and db
        cursor.close();
        db.close();
        return al;
    }
}
