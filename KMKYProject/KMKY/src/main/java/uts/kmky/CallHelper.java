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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by FrederikKastrup on 18/09/13.
 */
public class CallHelper {

    private Context ctx;
    private TelephonyManager tm;
    private IncomingCalls callStateListener;
    private outGoingCalls outgoingReceiver;

    public CallHelper(Context ctx) {
        this.ctx = ctx;

        callStateListener = new IncomingCalls();
        outgoingReceiver = new outGoingCalls();
    }


    //Listens for incoming calls
    private class IncomingCalls extends PhoneStateListener
    {
        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            switch (state) {
                case TelephonyManager.CALL_STATE_RINGING:
                    // called when someone is calling this phone


                    String subString = incomingNumber.substring(0, 3);

                    Log.i("CallHelper", "Calling: " + subString);

                    if (subString.equals("+61"))
                    {
                        incomingNumber = incomingNumber.substring(3, incomingNumber.length());
                    }

                    Log.i ("CallHelper", "Caller: " + incomingNumber);

                    long timeInMilliseconds = getTime();
                    
                    

                    DataModel.getInstance().addLog(incomingNumber, "call", timeInMilliseconds, 1, 0);

                    break;
            }
        }
    }

    //Listens for outgoing calls
    public class outGoingCalls extends BroadcastReceiver
    {
        public outGoingCalls(){}

    @Override
    public void onReceive(Context context, Intent intent) {
        String outgoingNumber = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);

        //Getting the time
        long timeMilliseconds = getTime();



      //Sorting away 0
      String subString = outgoingNumber.substring(0, 3);

      Log.i("CallHelper", "Calling: " + subString);

      if (subString.equals("+61"))
      {
          outgoingNumber = outgoingNumber.substring(3, outgoingNumber.length());
      }

      Log.i("CallHelper", "Calling: " + outgoingNumber);

        DataModel.getInstance().addLog(outgoingNumber, "call", timeMilliseconds, 0, 1);

    }

}

    //Sets the BroadcastReciever and Telephonymanager
    public void start() {
        tm = (TelephonyManager) ctx.getSystemService(Context.TELEPHONY_SERVICE);
        tm.listen(callStateListener, PhoneStateListener.LISTEN_CALL_STATE);

        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_NEW_OUTGOING_CALL);
        ctx.registerReceiver(outgoingReceiver, intentFilter);
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
