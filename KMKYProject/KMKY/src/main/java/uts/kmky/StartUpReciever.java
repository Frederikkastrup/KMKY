package uts.kmky;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by FrederikKastrup on 18/09/13.
 */
public class StartUpReciever extends BroadcastReceiver {

    //Starts the ListenerService at Boot
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent startServiceIntent = new Intent(context, ListenerService.class);
        context.startService(startServiceIntent);
    }
}