package uts.kmky;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.Toast;

public class MyRelationships_Activity extends Activity {

    public static Context appContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myrelationships_layout);

        //Starting the listnerservice when app starts
        startService(new Intent(getBaseContext(), ListenerService.class));

        MySQLiteHelper helper = new MySQLiteHelper(appContext);


        // Initiate Actionbar
        ActionBar actionbar = getActionBar();

        // Set ActionBar to use tabs
        actionbar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Initiate three tabs and set text to them
        ActionBar.Tab MyRelationshipTab = actionbar.newTab().setText("Relationships");
        ActionBar.Tab FavoritesTab = actionbar.newTab().setText("Favorites");
        ActionBar.Tab FindTab = actionbar.newTab().setText("Find");

        // Create the three fragments that we want to use for displaying content
        Fragment MyRelationships = new MyRelationshipsFragment();
        Fragment Favorites = new FavoritesFragment();
        Fragment Find = new FindFragment();

        // Set tab listener so we are able to listen for clicks
        MyRelationshipTab.setTabListener(new MyTabsListener (MyRelationships));
        FavoritesTab.setTabListener(new MyTabsListener(Favorites));
        FindTab.setTabListener(new MyTabsListener(Find));

        // Add tabs to the actionbar
        actionbar.addTab(MyRelationshipTab);
        actionbar.addTab(FavoritesTab);
        actionbar.addTab(FindTab);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my_relationships_, menu);
        return true;
    }




    public class MyTabsListener implements ActionBar.TabListener{

        public Fragment fragment;

        public MyTabsListener(Fragment fragment)
        {
            this.fragment = fragment;
        }

        @Override
        public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

           // Toast.makeText(MyRelationships_Activity.appContext, "Reselected!", Toast.LENGTH_LONG).show();
        }


        @Override
        public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
            ft.replace(R.id.fragment_container, fragment);
        }

        @Override
        public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
            ft.remove(fragment);
        }
    
}
}