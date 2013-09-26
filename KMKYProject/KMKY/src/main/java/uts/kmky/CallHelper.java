package uts.kmky;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.sqlite.SQLiteDatabase;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by FrederikKastrup on 18/09/13.
 */
public class CallHelper {

    private Context ctx;
    private TelephonyManager tm;
    private IncomingCalls callStateListener;
    private OutgoingCalls outgoingReceiver;

    public CallHelper(Context ctx) {
        this.ctx = ctx;

        callStateListener = new IncomingCalls();
        outgoingReceiver = new OutgoingCalls();
    }


    //Listens for incoming calls
    private class IncomingCalls extends PhoneStateListener
    {
        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            switch (state) {
                case TelephonyManager.CALL_STATE_RINGING:
                    // called when someone is calling this phone

                    Date now = new Date();
                    String CurrentTime = new SimpleDateFormat("yyyy-MM-dd").format(now);
                    long timeMilliseconds = now.getTime();


                    //Sorting away 0
                    incomingNumber = incomingNumber.substring(1, incomingNumber.length());

                    Log.i ("CallListner", "Caller: " + incomingNumber);

                    //Toast message showing the phone number calling you
                    Toast.makeText(ctx,"Incoming Call From: "+incomingNumber,Toast.LENGTH_LONG).show();

                    //Toast message showing the date of the incoming call
                    Toast.makeText(ctx, "Time: " + CurrentTime, Toast.LENGTH_LONG).show();

                    MySQLiteHelper.getInstance(ctx).addLog(incomingNumber, "call", timeMilliseconds, 1, 0);



                    break;


            }
        }
    }

    //Listens for outgoing calls
    public class OutgoingCalls extends BroadcastReceiver
    {
        public OutgoingCalls(){}

    @Override
    public void onReceive(Context context, Intent intent) {
        String OutGoingnumber = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);

        Date now = new Date();
        String CurrentTime = new SimpleDateFormat("yyyy-MM-dd").format(now);

        //Sorting away 0
        OutGoingnumber = OutGoingnumber.substring(1, OutGoingnumber.length());


        Log.i ("CallBroadcast", "Calling: " + OutGoingnumber);

        //Toast message showing the phone number you are calling
        Toast.makeText(ctx, "Outgoing: " + OutGoingnumber, Toast.LENGTH_LONG).show();

        //Toast message showing the date of the outgoing call
        Toast.makeText(ctx, "Time: " + CurrentTime, Toast.LENGTH_LONG).show();


    }



}



    //Sets the BroadcastReciever and Telephonymanager
    public void start() {
        tm = (TelephonyManager) ctx.getSystemService(Context.TELEPHONY_SERVICE);
        tm.listen(callStateListener, PhoneStateListener.LISTEN_CALL_STATE);

        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_NEW_OUTGOING_CALL);
        ctx.registerReceiver(outgoingReceiver, intentFilter);
    }

}
