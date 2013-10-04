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
    public void onCreate(SQLiteDatabase database) throws android.database.SQLException{

        try {
            database.execSQL(LogTable.DATABASE_CREATE);
            Log.i("SQL", "database created");
        }
        catch (android.database.SQLException e)
        {
            Log.i("SQL", "database creation exception",e);
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {

        // database.execSQL("DROP TABLE IF EXISTS " + LogTable.TABLE_NAME);

    }

    //........................... SQL Statements .....................................//



    //Create new entry for a specific person
    public long addLog(String phonenumber, String type, long date, int incoming, int outgoing)
    {

        SQLiteDatabase database = getWritableDatabase();
        long result = 0;

        try
        {

            ContentValues values = new ContentValues();

            values.put(LogTable.COLUMN_PHONENUMBER, phonenumber);
            values.put(LogTable.COLUMN_TYPE, type);
            values.put(LogTable.COLUMN_DATE, date);
            values.put(LogTable.COLUMN_INCOMING, incoming);
            values.put(LogTable.COLUMN_OUTGOING, outgoing);

            result = database.insert(LogTable.TABLE_NAME, null, values);
            Log.i("SQL", "Adding log");

        }

        catch (android.database.SQLException e)
        {
            Log.e("SQL", "Insert log exception", e);
        }

        finally {

            database.close();
        }

        return result;
    }

    //Update log for a specific person for a specific date
    public void updateLog(int id, int incoming, int outgoing)
    {
        SQLiteDatabase database = getWritableDatabase();

        try{
            //Checks to see whether to increment incoming or outgoing
            if (incoming != 0)
            {
                database.execSQL("UPDATE logs SET incoming=incoming + 1 WHERE _id =" + id);
                Log.i("SQL", "Updating incoming log");
            }
            else
            {
                database.execSQL("UPDATE logs SET outgoing=outgoing + 1 WHERE _id =" + id);
                Log.i("SQL", "Updating outgoing log");
            }
        }
        catch (android.database.SQLException e)
        {
            Log.i("SQL", "Update log exception");
        }
        finally {

            database.close();
        }

    }

    //Populate list with data from database
    public Cursor loadLogs()
    {
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = null;

        try
        {
            cursor = database.rawQuery("SELECT * FROM " + LogTable.TABLE_NAME, null);
        }
        catch (android.database.SQLException e)
        {
            Log.i("SQL", "Loading log exception");
        }
        finally
        {
            database.close();
        }

        return cursor;
    }





}
