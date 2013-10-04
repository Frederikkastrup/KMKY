package uts.kmky;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.net.Uri;
import android.util.*;

/**
 * Created by FrederikKastrup on 4/10/13.
 */
public class CallAndSMSHistory {

    Context ctx;

    public void getSMSHistory()
    {
        Uri uriSMS = Uri.parse("content://sms/");

        Cursor cursor = ctx.getContentResolver().query(uriSMS, null, null, null, null);

        String phonenumber = null;
        String type = "sms";
        long date = 0;

        //2 address
        //8 type
        //4 date
        try
        {
            while (cursor.moveToNext())
            {

            }
        }
        catch (CursorIndexOutOfBoundsException e)
        {
            android.util.Log.i("CallAndSMSHistory", "Cursor exception");
        }
        finally
        {

            cursor.close();
        }
    }

    public void getCallHistory()
    {

    }

    public void start()
    {
        //For SMS history
//        ContentResolver SMSHistory = ctx.getContentResolver();
//        SMSHistory.registerContentObserver();
    }


}
