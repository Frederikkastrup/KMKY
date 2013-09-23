package uts.kmky;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by FrederikKastrup on 23/09/13.
 */
public class DataModel {


    private List<Log> logs;
    private static DataModel instance;
    private static Context ctx;

    //Singleton constructor
    private  DataModel()
    {

      MySQLiteHelper dbHelper = new MySQLiteHelper(ctx);
      dbHelper.getWritableDatabase();



      //Create lists
      //Load data into logs


      dbHelper.close();
    }

    public static DataModel getInstance()
    {
        if (instance == null)
        {
            //Creates the instance
            instance = new DataModel();
        }
        return instance;
    }



    // .....................Operations on the database ............ //
    //Add log, update, list logs
    //InsertOrUpdate

    //A






    // .................... Public Methods ........................ //

    //Add logs, update logs / list of logs

    public void addLog(String phonenumber, String type, long date, int incoming, int outgoing)
    {
        //Check if log exists or create it.

        Log newLog = new Log(phonenumber, type, date, incoming, outgoing);
        logs.add(newLog);



        //Add to database
    }

    public void updateLog(String phonenumber, String type, long date, int incoming, int outgoing)
    {
        //Check for phonenumber, type and date
        //check for outgoing or incoming

        //increment value at that index. 
    }

    public void fetchLog()
    {

    }


}
