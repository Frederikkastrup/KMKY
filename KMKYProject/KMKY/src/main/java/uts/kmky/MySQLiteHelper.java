package uts.kmky;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.sql.SQLException;

/**
 * Created by FrederikKastrup on 21/09/13.
 */


public class MySQLiteHelper extends SQLiteOpenHelper {

    //Context
    Context ctx;


    // Constructor
    public MySQLiteHelper(Context ctx)
    {
        super(ctx, LogTable.DATABASE_NAME, null, LogTable.DATABASE_VERSION);
        Log.i("SQL", LogTable.DATABASE_CREATE);
    }

    @Override
    //Method called during creation of the database
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(LogTable.DATABASE_CREATE);
        Log.i("SQL", "database created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {

       // database.execSQL("DROP TABLE IF EXISTS " + LogTable.TABLE_NAME);

    }

    //........................... SQL Statements .....................................//



    //Create new entry for a specific person
    public void addLog(String phonenumber, String type, long date, int incoming, int outgoing)
    {




    }

    //Update log for a specific person for a specific date
    public void updateLog(int id, int incoming, int outgoing)
    {

    }

    //Populate logs with database
    public void loadLogs()
    {}





}
