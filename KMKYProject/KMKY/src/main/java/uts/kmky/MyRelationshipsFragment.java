package uts.kmky;

import android.app.ListFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by W520 on 18-09-13.
 */
public class MyRelationshipsFragment extends ListFragment {

    @Override
    public void onActivityCreated (Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);



        Relations relations_data[] = new Relations[]
                {
                        new Relations(R.drawable.heart_32x32, "Mikkel", R.drawable.heart_32x32),
                        new Relations(R.drawable.heart_32x32, "Frederik", R.drawable.heart_32x32),
                        new Relations(R.drawable.heart_32x32, "Tuck", R.drawable.heart_32x32),
                        new Relations(R.drawable.heart_32x32, "Treffyn", R.drawable.heart_32x32),
                        new Relations(R.drawable.heart_32x32, "Jeannette", R.drawable.heart_32x32),
                        new Relations(R.drawable.heart_32x32, "Camilla", R.drawable.heart_32x32),
                        new Relations(R.drawable.heart_32x32, "Stephan", R.drawable.heart_32x32)
                };


        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService( Context.LAYOUT_INFLATER_SERVICE );

        CustomArrayAdapter adapter = new CustomArrayAdapter(getActivity(), R.layout.listview_row, relations_data);

        View header = (View)inflater.inflate(R.layout.listview_header, null);
        setListAdapter(null); // Saettes saa headeren ikke gentagende bliver tilfoejet efter setListAdapter er aktiveret - For det bliver den hver gang fragmentet bliver inflated.
        getListView().addHeaderView(header);

        setListAdapter(adapter);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.myrelationshipsfragment, container, false);

    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        // Do something with the data

    }
}
