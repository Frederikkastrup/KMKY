package uts.kmky;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by W520 on 21-09-13.
 */
public class CustomArrayAdapter extends ArrayAdapter<Relations> {

    Context context;
    int layoutResourceID;
    Relations data[] = null;


    public CustomArrayAdapter(Context context, int layoutResourceID, Relations[] data) {
        super(context, layoutResourceID, data);
        this.layoutResourceID = layoutResourceID;
        this.context = context;
        this.data = data;
    }

    // Her overrider vi den normale getView adapter metode saa vi selv kan definere hvad vi vil smide i listen.
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row = convertView;
        RelationsHolder holder = null;

        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceID, parent, false);

            holder = new RelationsHolder();
            holder.pic1 = (ImageView)row.findViewById(R.id.pic1);
            holder.txt = (TextView)row.findViewById(R.id.textview2);
            holder.pic2 = (ImageView)row.findViewById(R.id.pic2);

            row.setTag(holder);
        }
        else
        {
            holder = (RelationsHolder)row.getTag();
        }

        Relations relation = data[position];

        holder.txt.setText(relation.name);
        holder.pic1.setImageResource(relation.icon1);
        holder.pic2.setImageResource(relation.icon2);

        return row;
    }

    static class RelationsHolder
    {
        ImageView pic1;
        ImageView pic2;
        TextView txt;
    }

}
