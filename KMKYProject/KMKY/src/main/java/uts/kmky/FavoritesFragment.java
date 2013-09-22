package uts.kmky;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by W520 on 18-09-13.
 */
public class FavoritesFragment extends ListFragment {

    @Override
    public void onActivityCreated (Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        String[] values = new String[] { "Mikkel", "Frederik", "Tuck",
                "Treffyn", "Jeannette", "Tara", "Camilla", "Peter",
                "Stephan", "Umachanger" };

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, values);
        setListAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.favoritesfragment, container, false);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        // Do something with the data

    }
}
