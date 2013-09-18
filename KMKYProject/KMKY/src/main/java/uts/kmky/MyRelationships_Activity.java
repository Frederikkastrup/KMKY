package uts.kmky;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class MyRelationships_Activity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myrelationships_layout);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my_relationships_, menu);
        return true;
    }
    
}
