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
    private DataModel()
    {

      MySQLiteHelper dbHelper = new MySQLiteHelper(ctx);
      dbHelper.getWritableDatabase();




      //Load data into logs


      //dbHelper.close();
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

    // .................... Public Methods ........................ //


    public void addLog(String phonenumber, String type, long date, int incoming, int outgoing)
    {
        //Check if log exists or else create it.
        Log newLog = new Log(phonenumber, type, date, incoming, outgoing);

        //Iterates through logs
        for (Log log : logs)
        {
            if (log.getPhonenumber() == phonenumber && log.getDate() == date && log.getType() == type)
            {
                updateLog(newLog, log.getId());
            }
            else
            {

                logs.add(newLog);
                //add to database
            }
        }




    }

    public void updateLog(Log newLog, long id)
    {

        //check for outgoing or incoming
        if (newLog.getIncoming() == 1)
        {
            for (Log log : logs)
            {
                if (log.getId() == id)
                {
                    //Increments incoming
                    int incoming = log.getIncoming();

                    log.setIncoming(incoming++);
                }
                else
                {
                    //Increments outgoing
                    int outgoing = log.getOutgoing();

                    log.setOutgoing(outgoing++);
                }
            }
        }


        //Update the list
        //Add to database
    }

    //Gets the sms log for that date for that specific person. Else returns an empty log
    public Log fetchSMSLogsForPeronOnSpecificDate(String phonenumber, long date)
    {

        Log emptyLog = new Log(phonenumber, "sms", date, 0, 0);

        for (Log log : logs)
        {
            if (log.getPhonenumber() == phonenumber && log.getDate() == date && log.getType() == "sms")
            {
                return log;
            }
        }

        return emptyLog;
    }

    //Gets the call log for that date for that specific person. Else returns an empty log
    public Log fetchCallLogsForPeronOnSpecificDate(String phonenumber, long date)
    {
        Log emptyLog = new Log(phonenumber, "call", date, 0, 0);

        for (Log log : logs)
        {
            if (log.getPhonenumber() == phonenumber && log.getDate() == date && log.getType() == "sms")
            {
                return log;
            }

        }

        return emptyLog;

    }

    public Log fetchSMSLogsForPersonToDate(String phonenumber, long date)
    {
        int incoming = 0;
        int outgoing = 0;
        String type = "sms";

        for (Log log : logs)
        {
            if (log.getPhonenumber() == phonenumber && log.getType() == "sms")
            {
                incoming = incoming + log.getIncoming();
                outgoing = outgoing + log.getOutgoing();
            }
        }

        Log logToDate = new Log(phonenumber, type, date, incoming, outgoing );

        return logToDate;
    }

    public Log fetchCallLogsForPersonToDate(String phonenumber, long date)
    {
        int incoming = 0;
        int outgoing = 0;
        String type = "call";

        for (Log log : logs)
        {
            if (log.getPhonenumber() == phonenumber && log.getType() == "call")
            {
                incoming = incoming + log.getIncoming();
                outgoing = outgoing + log.getOutgoing();
            }
        }

        Log logToDate = new Log(phonenumber, type, date, incoming, outgoing );

        return logToDate;
    }

    public void fetchLogsForLeastContacted()
    {

    }

    public void fetchLogsForMostContacted()
    {

    }

    public void fetchLogsForLeastContactedYou()
    {

    }

    public void fetchLogsForMostContactedYou()
    {

    }

}
