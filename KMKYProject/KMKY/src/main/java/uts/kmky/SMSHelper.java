package uts.kmky;

import android.content.BroadcastReceiver;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import android.database.ContentObserver;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by FrederikKastrup on 19/09/13.
 */
public class SMSHelper {

    private Context ctx;
    private IncomingSMS incomingSMS;
    private ContentObserver outgoingSms;
    private Handler handle;

    public SMSHelper(Context ctx)
    {
        this.ctx = ctx;

        incomingSMS = new IncomingSMS();
        outgoingSms = new OutgoingSMS(handle);
    }

    private class IncomingSMS extends BroadcastReceiver
    {
        //Get object of Smsmanager
        final SmsManager sms = SmsManager.getDefault();

        @Override
        public void onReceive(Context context, Intent intent) {
            // Retrieves a map of extended data from the intent.
            final Bundle bundle = intent.getExtras();
            try {
                    if (bundle != null) {
                    final Object[] pdusObj = (Object[]) bundle.get("pdus");

                    for (int i = 0; i < pdusObj.length; i++) {

                        SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
                        String incomingSMS = currentMessage.getDisplayOriginatingAddress();

                        String subString = incomingSMS.substring(0, 3);

                        Log.i("SMSHelper", "incoming SMS substring: " + subString);

                        if (subString.equals("+61"))
                        {
                            incomingSMS = incomingSMS.substring(3, incomingSMS.length());
                        }

                        long timeInMillisecond = getTime();

                        DataModel.getInstance().addLog(incomingSMS, "sms", timeInMillisecond, 1, 0);

                       //Log message
                        Log.i("SMSHelper", "incoming SMS from " + incomingSMS);

                    } // end for loop
                } // bundle is null

            } catch (Exception e) {
                Log.e("SMS", "Exception smsReceiver" +e);

            }

        }
    }

    private class OutgoingSMS extends ContentObserver
    {
        public OutgoingSMS(Handler handler)
        {
            super(handler);
        }

        @Override
        public boolean deliverSelfNotifications()
        {
            return true;
        }

        @Override
        public void onChange(boolean selfChange)
        {
            super.onChange(selfChange);

            Uri uri =Uri.parse("content://sms/outbox");

            Cursor cur = ctx.getContentResolver().query(uri, null, null, null, null);

            String outgoingSMS = null;

            try
            {
                //Moves cursor to address
                cur.moveToFirst();

                outgoingSMS = cur.getString(cur.getColumnIndex("address"));

                String subString = outgoingSMS.substring(0, 3);

                Log.i("SMSHelper", "Outgoing SMS substring: " + subString);

                if (subString.equals("+61"))
                {
                    outgoingSMS = outgoingSMS.substring(3, outgoingSMS.length());
                }



            }
            catch (CursorIndexOutOfBoundsException e) {Log.i("SMSHelper", "Outgoing Exception");}

            finally {

                cur.close();
            }

            if (outgoingSMS != null)
            {
            //Getting the number
                long timeInMillisecond = getTime();

                Log.i("SMSHelper", "Outgoing SMS to" + outgoingSMS);

                DataModel.getInstance().addLog(outgoingSMS, "sms", timeInMillisecond, 0, 1);
            }
        }

    }

    public void start()
    {
        //Setting the BroadcastReciever for Incoming SMS
        String ACTION_SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";
        IntentFilter IntentFilter = new IntentFilter();
        IntentFilter.addAction(ACTION_SMS_RECEIVED);
        ctx.registerReceiver(incomingSMS,IntentFilter);


        //Outgoing SMS
        ContentResolver contentResolver = ctx.getContentResolver();
        contentResolver.registerContentObserver(Uri.parse("content://sms"), true, outgoingSms);


    }

    public long getTime()
    {
        Date now = new Date();
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd").format(now);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        Date date = null;

        try {
            date = dateFormat.parse(timeStamp);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        long timeMilliseconds = date.getTime();
        Log.i("CallHelper", String.valueOf(timeMilliseconds));


        return timeMilliseconds;

    }

}
