package dcube.com.trust.utils;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import dcube.com.trust.R;

public class CustomAdapter extends BaseAdapter {

    Context context;
    Global global;

    ArrayList<String> name = new ArrayList<>();
    ArrayList<String> branch = new ArrayList<>();
    ArrayList<String> contact = new ArrayList<>();



    private static LayoutInflater inflater = null;
    public CustomAdapter(Activity activity) {

        context = activity.getApplicationContext();
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        name.add("Khalfan");
        name.add("Gheilani");
        name.add("Hafiz");

        branch.add("Dodoma");
        branch.add("Dar Es Salaam");
        branch.add("Morogoro");

        contact.add("8879861183");
        contact.add("8879861182");
        contact.add("8879861184");

    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
         return name.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public class Holder
    {
        TextView name;
        TextView branch;
        TextView contact;
    }

    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder = new Holder();

        final View rowView;
        rowView = inflater.inflate(R.layout.client_search_item, parent , false);

        holder.name = (TextView) rowView.findViewById(R.id.name);
        holder.branch = (TextView) rowView.findViewById(R.id.branch);
        holder.contact = (TextView) rowView.findViewById(R.id.contact);

        holder.name.setText(name.get(position));
        holder.branch.setText(branch.get(position));
        holder.contact.setText(contact.get(position));

        return rowView;
    }
}