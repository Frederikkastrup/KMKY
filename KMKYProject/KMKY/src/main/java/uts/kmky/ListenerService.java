package uts.kmky;

/**
 * Created by FrederikKastrup on 18/09/13.
 */
        import android.app.Service;
        import android.content.ContentResolver;
        import android.content.Context;
        import android.content.Intent;
        import android.net.Uri;
        import android.os.IBinder;
        import android.widget.Toast;

        import java.util.Date;

/**
 * Created by FrederikKastrup on 18/09/13.
 */
public class ListenerService extends Service{

    private CallHelper CallHelper;
    private SMSHelper SMSHelper;

    public void onCreate()
    {
        super.onCreate();
        DataModel dataModel = DataModel.getInstance();
    }

    //Invokes the CallHelper.class and SMSHelper.class. Restarts them if shut down.
    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        Toast.makeText(this, "Listen for calls", Toast.LENGTH_LONG).show();

        SMSHelper = new SMSHelper(this);
        CallHelper = new CallHelper(this);

        int res = super.onStartCommand(intent, flags, startId);
        CallHelper.start();
        SMSHelper.start();

        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent)
    {
        //TODO Replace with service binding implementation.
        return null;
    }
}