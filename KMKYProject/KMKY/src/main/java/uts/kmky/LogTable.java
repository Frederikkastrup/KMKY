package uts.kmky;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by FrederikKastrup on 24/09/13.
 */
public class LogTable {

    //Database name and Version
    public static final String DATABASE_NAME = "logs.db";
    public static final int DATABASE_VERSION = 1;


    //Database Table
    public static final String TABLE_NAME = "logs";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_PHONENUMBER = "phonenumber";
    public static final String COLUMN_TYPE = "type";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_INCOMING = "incoming";
    public static final String COLUMN_OUTGOING = "outgoing";


    //Preventing someone from accidentally instantiating the class
    //Giving it an empty constructor
    public LogTable(){}

    //Database creation SQL statement
    public static final String DATABASE_CREATE = "CREATE TABLE "
            + TABLE_NAME + "(" + COLUMN_ID + " INTEGER AUTOINCREMENT, "
            + COLUMN_PHONENUMBER + " TEXT, "
            + COLUMN_TYPE + " TEXT, "
            + COLUMN_DATE + " INTEGER, "
            + COLUMN_INCOMING + " INTEGER, "
            + COLUMN_OUTGOING + " INTEGER, "
            + "PRIMARY KEY (" + COLUMN_PHONENUMBER + "," + COLUMN_TYPE + "," + COLUMN_DATE + "));";




}
