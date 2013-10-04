package uts.kmky;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by FrederikKastrup on 23/09/13.
 */
public class DataModel {


    private List<Log> logs = new ArrayList<Log>();
    private static DataModel instance;
    private Context mContext;
    private MySQLiteHelper mDbHelper;

    //Singleton constructor
    private DataModel()
    {
          mDbHelper = new MySQLiteHelper(mContext);

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

    private List<String> getUniquePhonenumbers()
    {
        List<String> phonenumberList = new ArrayList<String>();

        for (Log log : logs)
        {
            if (phonenumberList.contains(log.getPhonenumber()))
            {

            }
            else
            {
                phonenumberList.add(log.getPhonenumber());

            }
        }


        return phonenumberList;
    }

    public void addLog(String phonenumber, String type, long date, int incoming, int outgoing)
    {

        //Removes spacing in phonenumber
        phonenumber = phonenumber.replace(" ", "");

        //Check if log exists or else create it.
        Log newLog = new Log(phonenumber, type, date, incoming, outgoing);
        Log oldLog = null;

        //Checks if logs is empty
        if (logs.size() == 0)
            {
                android.util.Log.i("Datamodel", "Logs is empty");
                logs.add(newLog);
                mDbHelper.addLog(phonenumber, type, date, incoming, outgoing);

            }
        else
            {
                //Checks if the logs exists.
                boolean update = false;

                //Id for the log.
                long id = 0;

                android.util.Log.i("Datamodel", phonenumber + " " + type + " " + date);

                for (Log log : logs)
                {
                    if (log.getPhonenumber().equals(phonenumber) && log.getType().equals(type)  && log.getDate() == date)
                    {
                        update = true;
                        id = log.getId();
                        oldLog = new Log(log.getPhonenumber(), log.getType(), log.getDate(), log.getIncoming(), log.getOutgoing());
                    }
                    else
                    {
                        update = false;
                        oldLog = new Log(log.getPhonenumber(), log.getType(), log.getDate(), log.getIncoming(), log.getOutgoing());
                    }

                }

                if (update)
                {

                    android.util.Log.i("Datamodel", "Updating log");
                    updateLog(newLog, oldLog, id);
                }
                else
                {
                    android.util.Log.i("Datamodel", "Adding log");
                    logs.add(newLog);
                }
            }
    }

    public void updateLog(Log newLog, Log oldLog, long id)
    {
        String phonenumber = oldLog.getPhonenumber();
        String type = oldLog.getType();
        long date = oldLog.getDate();
        int incoming = oldLog.getIncoming();
        int outgoing = oldLog.getOutgoing();

        if (newLog.getIncoming() != 0)
        {
            logs.remove(oldLog);
            incoming++;
        }
        else
        {
            logs.remove(oldLog);
            outgoing++;

        }

        android.util.Log.i("Datamodel", "Adding new update");
        logs.add(new Log(phonenumber, type, date, incoming, outgoing));

    }


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

    public List<String> fetchNumbersForLeastContacted()
    {

        //Returns phone list of ten least contacted phone numbers

        List<String> phonenumberList = getUniquePhonenumbers();
        List<TopTen> SortList = new ArrayList<TopTen>();


        Date now = new Date();
        Long milliSeconds = now.getTime();

        //Find phone numbers for least contacted
        for (String phonenumber : phonenumberList)
        {



            int outgoingSMS = fetchSMSLogsForPersonToDate(phonenumber, milliSeconds).getOutgoing();
            int outgoingCall = fetchCallLogsForPersonToDate(phonenumber, milliSeconds).getOutgoing();

            //Calculating the total communication for each phonenumber
            int totalCommunication = outgoingCall + outgoingSMS;

            SortList.add(new TopTen(phonenumber, totalCommunication, 0));
            android.util.Log.i("Datamodel", Integer.toString(totalCommunication));

        }

        //Sort the list
        Collections.sort(SortList);

        //Clears phone number list
        phonenumberList.clear();


        //Add the phone numbers to list
        if (SortList.size() > 10)
        {
            for (int i = 0; i < 10; i++ )
            {
                String phonenumber = null;

                TopTen topTen = SortList.get(i);

                phonenumber = topTen.getPhonenumber();

                phonenumberList.add(phonenumber);
            }

        }
        else
        {

            for (TopTen topTen : SortList)
            {
                String phonenumner = null;

                phonenumberList.add(topTen.getPhonenumber());
            }

        }

        for (String number : phonenumberList)
        {
            android.util.Log.i("Datamodel", number);
        }

        //Returns the top ten least contacted numbers
        return phonenumberList;
    }

    public List<String> fetchNumbersForMostContacted()
    {
        //Returns phone list of ten most contacted phone numbers

        List<String> phonenumberList = getUniquePhonenumbers();
        List<TopTen> SortList = new ArrayList<TopTen>();


        Date now = new Date();
        Long milliSeconds = now.getTime();

        //Find phone numbers for least contacted
        for (String phonenumber : phonenumberList)
        {



            int outgoingSMS = fetchSMSLogsForPersonToDate(phonenumber, milliSeconds).getOutgoing();
            int outgoingCall = fetchCallLogsForPersonToDate(phonenumber, milliSeconds).getOutgoing();

            //Calculating the total communication for each phonenumber
            int totalCommunication = outgoingCall + outgoingSMS;

            SortList.add(new TopTen(phonenumber, totalCommunication, 0));

        }

        //Sort the list
        Collections.sort(SortList, Collections.reverseOrder());

        //Clears phone number list
        phonenumberList.clear();


        //Add the phone numbers to list
        if (SortList.size() > 10)
        {
            for (int i = 0; i < 10; i++ )
            {
                String phonenumber = null;

                TopTen topTen = SortList.get(i);

                phonenumber = topTen.getPhonenumber();

                phonenumberList.add(phonenumber);
            }

        }
        else
        {

            for (TopTen topTen : SortList)
            {
                String phonenumner = null;

                phonenumberList.add(topTen.getPhonenumber());
            }

        }


        for (String number : phonenumberList)
        {
            android.util.Log.i("Datamodel", number);
        }


        //Returns the top ten least contacted numbers
        return phonenumberList;
    }

    public List<String> fetchNumbersForLeastContactedYou()
    {
        //Returns phone list of ten most contacted phone numbers

        List<String> phonenumberList = getUniquePhonenumbers();
        List<TopTen> SortList = new ArrayList<TopTen>();


        Date now = new Date();
        Long milliSeconds = now.getTime();

        //Find phone numbers for least contacted
        for (String phonenumber : phonenumberList)
        {



            int incomingSMS = fetchSMSLogsForPersonToDate(phonenumber, milliSeconds).getIncoming();
            int incomingCall = fetchCallLogsForPersonToDate(phonenumber, milliSeconds).getIncoming();

            //Calculating the total communication for each phonenumber
            int totalCommunication = incomingCall + incomingSMS;

            SortList.add(new TopTen(phonenumber, totalCommunication, 0));

        }

        //Sort the list
        Collections.sort(SortList);

        //Clears phone number list
        phonenumberList.clear();


        //Add the phone numbers to list
        if (SortList.size() > 10)
        {
            for (int i = 0; i < 10; i++ )
            {
                String phonenumber = null;

                TopTen topTen = SortList.get(i);

                phonenumber = topTen.getPhonenumber();

                phonenumberList.add(phonenumber);
            }

        }
        else
        {

            for (TopTen topTen : SortList)
            {
                String phonenumner = null;

                phonenumberList.add(topTen.getPhonenumber());
            }

        }


        for (String number : phonenumberList)
        {
            android.util.Log.i("Datamodel", number);
        }


        //Returns the top ten least contacted numbers
        return phonenumberList;
    }

    public List<String> fetchNumbersForMostContactedYou()
    {
        //Returns phone list of ten most contacted phone numbers

        List<String> phonenumberList = getUniquePhonenumbers();
        List<TopTen> SortList = new ArrayList<TopTen>();


        Date now = new Date();
        Long milliSeconds = now.getTime();

        //Find phone numbers for least contacted
        for (String phonenumber : phonenumberList)
        {



            int incomingSMS = fetchSMSLogsForPersonToDate(phonenumber, milliSeconds).getIncoming();
            int incomingCall = fetchCallLogsForPersonToDate(phonenumber, milliSeconds).getIncoming();

            //Calculating the total communication for each phonenumber
            int totalCommunication = incomingCall + incomingSMS;

            SortList.add(new TopTen(phonenumber, totalCommunication, 0));

        }

        //Sort the list
        Collections.sort(SortList, Collections.reverseOrder());

        //Clears phone number list
        phonenumberList.clear();


        //Add the phone numbers to list
        if (SortList.size() > 10)
        {
            for (int i = 0; i < 10; i++ )
            {
                String phonenumber = null;

                TopTen topTen = SortList.get(i);

                phonenumber = topTen.getPhonenumber();

                phonenumberList.add(phonenumber);
            }

        }
        else
        {

            for (TopTen topTen : SortList)
            {
                String phonenumner = null;

                phonenumberList.add(topTen.getPhonenumber());
            }

        }


        for (String number : phonenumberList)
        {
            android.util.Log.i("Datamodel", number);
        }


        //Returns the top ten least contacted numbers
        return phonenumberList;
    }


    private void loadLogs()
    {
        //Load logs from database
        MySQLiteHelper dbHelper = new MySQLiteHelper(mContext);

        Cursor cursor = dbHelper.loadLogs();

        cursor.moveToFirst();

        while (cursor.moveToNext())
        {


            String phonenumber = cursor.getString(cursor.getColumnIndexOrThrow(LogTable.COLUMN_PHONENUMBER));
            String type =  cursor.getString(cursor.getColumnIndexOrThrow(LogTable.COLUMN_TYPE));
            long date = cursor.getLong(cursor.getColumnIndexOrThrow(LogTable.COLUMN_DATE));
            int incoming = cursor.getInt(cursor.getColumnIndexOrThrow(LogTable.COLUMN_INCOMING));
            int outgoing = cursor.getInt(cursor.getColumnIndexOrThrow(LogTable.COLUMN_OUTGOING));

            Log newLog = new Log(phonenumber, type, date, incoming, outgoing);

            logs.add(newLog);

        }


    }

}
