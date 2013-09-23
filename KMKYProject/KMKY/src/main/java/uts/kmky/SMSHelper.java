package uts.kmky;

import android.content.BroadcastReceiver;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

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
                        String phoneNumber = currentMessage.getDisplayOriginatingAddress();

                        //Sorting away +61
                        String senderNum = phoneNumber.substring(3, phoneNumber.length());

                        Date now = new Date();
                        String CurrentTime = new SimpleDateFormat("yyyy-MM-dd").format(now);


                        //Log message
                        Log.i("SmsReceiver", "senderNum: " + senderNum);

                        // Detects incoming SMS
                        Toast.makeText(ctx, "Incoming SMS From: "+ senderNum, Toast.LENGTH_LONG).show();

                        //Detects data of incoming sms
                        Toast.makeText(ctx, "Time: " + CurrentTime, Toast.LENGTH_LONG).show();


                    } // end for loop
                } // bundle is null

            } catch (Exception e) {
                Log.e("SmsReceiver", "Exception smsReceiver" +e);

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

            cur.moveToNext();

            String phonenumber = cur.getString(cur.getColumnIndex("address"));

            //Sorting away +61
            phonenumber = phonenumber.substring(3, phonenumber.length());

            Date now = new Date();
            String CurrentTime = new SimpleDateFormat("yyyy-MM-dd").format(now);




            //Getting the number
            Log.i("OutSMS", phonenumber + " " + CurrentTime);



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

}
