package dcube.com.trust.utils;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import WebServicesHandler.GlobalConstants;
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

        global = (Global) activity.getApplicationContext();

    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
         return global.getAl_src_client_details().size();
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

        holder.name.setText(global.getAl_src_client_details().get(position).get(GlobalConstants.SRC_CLIENT_NAME));   //name.get(position)
        holder.branch.setText(global.getAl_src_client_details().get(position).get(GlobalConstants.SRC_CLIENT_BRANCH));
        holder.contact.setText(global.getAl_src_client_details().get(position).get(GlobalConstants.SRC_CLIENT_CONTACT));

        return rowView;
    }
}