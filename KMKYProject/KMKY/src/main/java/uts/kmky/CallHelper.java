package uts.kmky;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by FrederikKastrup on 18/09/13.
 */
public class CallHelper {


    //Listens for incoming calls
    private class IncomingCalls extends PhoneStateListener
    {
        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            switch (state) {
                case TelephonyManager.CALL_STATE_RINGING:
                    // called when someone is ringing to this phone

                    Toast.makeText(ctx,"Incoming: "+incomingNumber,Toast.LENGTH_LONG).show();
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
        String number = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);

        Toast.makeText(ctx, "Outgoing: "+number, Toast.LENGTH_LONG).show();

    }



}

    private Context ctx;
    private TelephonyManager tm;
    private IncomingCalls callStateListener;


    private OutgoingCalls outgoingReceiver;

    public CallHelper(Context ctx) {
        this.ctx = ctx;

        callStateListener = new IncomingCalls();
        outgoingReceiver = new OutgoingCalls();
    }

    public void start() {
        tm = (TelephonyManager) ctx.getSystemService(Context.TELEPHONY_SERVICE);
        tm.listen(callStateListener, PhoneStateListener.LISTEN_CALL_STATE);

        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_NEW_OUTGOING_CALL);
        ctx.registerReceiver(outgoingReceiver, intentFilter);
    }

}
