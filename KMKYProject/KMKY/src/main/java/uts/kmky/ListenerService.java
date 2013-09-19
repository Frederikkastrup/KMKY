package uts.kmky;

/**
 * Created by FrederikKastrup on 18/09/13.
 */


        import android.app.Service;
        import android.content.Intent;
        import android.os.IBinder;
        import android.widget.Toast;


/**
 * Created by FrederikKastrup on 18/09/13.
 */
public class ListenerService extends Service{

    public static final String TAG = "ListnerService";
    private CallHelper CallHelper;
    public void onCreate()
    {
        super.onCreate();


    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        Toast.makeText(this, "Listen for calls", Toast.LENGTH_LONG).show();

        CallHelper = new CallHelper(this);

        int res = super.onStartCommand(intent, flags, startId);
        CallHelper.start();


        return START_STICKY;


    }


    @Override
    public IBinder onBind(Intent intent)
    {
        //TODO Replace with service binding implementation.
        return null;
    }
}