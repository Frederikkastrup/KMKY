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

//Singleton class
public class MySQLiteHelper extends SQLiteOpenHelper {

    //Context
    Context ctx;

    //Instance to hold SQLiteOpenHelper
    public static MySQLiteHelper instance = null;

    public static MySQLiteHelper getInstance(Context context)
    {
        if (instance == null)
        {
            instance = new MySQLiteHelper(context.getApplicationContext());
        }
        return instance;
    }

    //Private Constructor
    private MySQLiteHelper(Context ctx)
    {
        super(ctx, LogTable.DATABASE_NAME, null, LogTable.DATABASE_VERSION);
        Log.i("SQL", LogTable.DATABASE_CREATE);
    }

    @Override
    //Method called during creation of the database
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(LogTable.DATABASE_CREATE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {

        database.execSQL("DROP TABLE IF EXISTS " + LogTable.TABLE_NAME);

    }

    //Get sum of incoming calls and texts for  a specific date for a specific person

    //Get sum of outgoing calls and texts for a specific date for a specific person

    //Get sum of incoming calls and texts to this date for a for a specific person

    //get sum on outgoing calls and texts to this data for a specific person

    //Get entries for to ten most contacted to this date
    //Both ways

    //Get entries for to ten least contacted to this date
    //Both ways

    //Create new entry for a specific person
    public void addLog(String phonenumber, String type, long date, int incoming, int outgoing)
    {
        //Check if entry is already there
//        if (checkLog(phonenumber, type, date, incoming, outgoing) == null)
//        {
//            String _id = checkLog(phonenumber, type, date, incoming, outgoing);
//
//            updateLog(_id, incoming, outgoing);
//        }

        SQLiteDatabase database = MySQLiteHelper.getInstance(ctx).getWritableDatabase();

        //Values to insert into database
        ContentValues valuesToInsert = new ContentValues();

        valuesToInsert.put(LogTable.COLUMN_PHONENUMBER, phonenumber);
        valuesToInsert.put(LogTable.COLUMN_TYPE, type);
        valuesToInsert.put(LogTable.COLUMN_DATE, date);
        valuesToInsert.put(LogTable.COLUMN_INCOMING, incoming);
        valuesToInsert.put(LogTable.COLUMN_OUTGOING, outgoing);

        database.insert(LogTable.TABLE_NAME, null, valuesToInsert);

        Log.i("Values", "Created");

        database.close();


    }

    //Update log for a specific person for a specific day
    public void updateLog(String _id, int incoming, int outgoing)
    {
        //Find log with ID = _id
        //Update log with either incoming or outgoing
    }

    //Check if log is there
    private String checkLog(String phonenumber, String type, long date, int incoming, int outgoing)
    {
        String _id = null;
        MySQLiteHelper.getInstance(ctx).getReadableDatabase();

        //Try {
        //Query Select * from Table_LOGS WHERE phonenumber = ? AND type = ? AND date = ?
        //Get id


        return _id;
    }



}
