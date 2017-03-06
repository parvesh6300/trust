package dcube.com.trust.utils;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import WebServicesHandler.GlobalConstants;
import dcube.com.trust.R;

public class CustomAdapter extends BaseAdapter {

    Context context;
    Global global;

    ArrayList<String> name = new ArrayList<>();
    ArrayList<String> branch = new ArrayList<>();
    ArrayList<String> contact = new ArrayList<>();

    private static LayoutInflater inflater = null;

    public CustomAdapter(Activity activity,String keyword) {

        context = activity.getApplicationContext();
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        name = new ArrayList<>();
        branch = new ArrayList<>();
        contact = new ArrayList<>();

        global = (Global) activity.getApplicationContext();

        for (HashMap<String,String> hashMap :global.getAl_src_client_details() )
        {
            if(keyword.equalsIgnoreCase(""))
            {
                name.add(hashMap.get(GlobalConstants.SRC_CLIENT_NAME));
                branch.add(hashMap.get(GlobalConstants.SRC_CLIENT_AREA));
                contact.add(hashMap.get(GlobalConstants.SRC_CLIENT_CONTACT));
            }
            else
            {
                if (hashMap.get(GlobalConstants.SRC_CLIENT_NAME).toLowerCase().contains(keyword.toLowerCase()) ||
                        hashMap.get(GlobalConstants.SRC_CLIENT_CONTACT).toLowerCase().contains(keyword.toLowerCase()))
                {
                    name.add(hashMap.get(GlobalConstants.SRC_CLIENT_NAME));
                    branch.add(hashMap.get(GlobalConstants.SRC_CLIENT_AREA));
                    contact.add(hashMap.get(GlobalConstants.SRC_CLIENT_CONTACT));
                }

            }


        }

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


        //global.getAl_src_client_details().get(position).get(GlobalConstants.SRC_CLIENT_NAME)
        holder.name.setText(name.get(position));

        //global.getAl_src_client_details().get(position).get(GlobalConstants.SRC_CLIENT_AREA)
        holder.branch.setText(branch.get(position));

        //global.getAl_src_client_details().get(position).get(GlobalConstants.SRC_CLIENT_CONTACT)
        holder.contact.setText(contact.get(position));

        return rowView;
    }
}