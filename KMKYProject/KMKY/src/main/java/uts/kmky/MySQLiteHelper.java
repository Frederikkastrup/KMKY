package uts.kmky;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.sql.SQLException;

/**
 * Created by FrederikKastrup on 21/09/13.
 */
public class MySQLiteHelper extends SQLiteOpenHelper {

    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    public static final String TABLE_LOGS = "logs";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_PHONENUMBER = "phonenumber";
    public static final String COLUMN_TYPE = "type";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_INCOMING = "incoming";
    public static final String COLUMN_OUTGOING = "outgoing";

    private static final String DATABASE_NAME = "logs.db";
    private static final int DATABASE_VERSION = 1;


    //Database creation SQL statement
    private static final String DATABASE_CREATE = "CREATE TABLE "
            + TABLE_LOGS + "(" + COLUMN_ID + " INTEGER, "
            + COLUMN_PHONENUMBER + " TEXT, "
            + COLUMN_TYPE + " TEXT, "
            + COLUMN_DATE + " INTEGER, "
            + COLUMN_INCOMING + " INTEGER, "
            + COLUMN_OUTGOING + " INTEGER, "
            + "PRIMARY KEY (" + COLUMN_PHONENUMBER + "," + COLUMN_TYPE + "," + COLUMN_DATE +")" + ");";



    //Constructor
    public MySQLiteHelper(Context ctx)
    {
        super(ctx, DATABASE_NAME, null, DATABASE_VERSION);


        Log.i("SQL", DATABASE_CREATE);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(DATABASE_CREATE);
        Log.i("SQL2", "Created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersions) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_LOGS);
        onCreate(sqLiteDatabase);
    }

    public SQLiteDatabase open() throws SQLException
    {
        return dbHelper.getWritableDatabase();
    }

    public void close()
    {
        dbHelper.close();
    }
}
