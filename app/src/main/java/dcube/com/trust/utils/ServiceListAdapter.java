package dcube.com.trust.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import WebServicesHandler.GlobalConstants;
import dcube.com.trust.R;

/**
 * Created by Sagar on 30/11/16.
 */
public class ServiceListAdapter extends BaseAdapter{


    Context context;
    Global global;
    static int quantity;

    int selectedIndex = -1;

    boolean isSelected = false;

    ArrayList<String> name ;
    ArrayList<String> serviceCost;
    ArrayList<String> service_id ;
    ArrayList<String> al_selected_service;

    private static LayoutInflater inflater = null;

    public ServiceListAdapter(Activity activity,String search)
    {
        context = activity.getApplicationContext();

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        global = (Global) activity.getApplicationContext();

        al_selected_service = new ArrayList<>();
        name = new ArrayList<>();
        serviceCost = new ArrayList<>();
        service_id = new ArrayList<>();


        try {

            for (HashMap<String,String> hashmap : global.getAl_service_details())
            {
                if (search.equalsIgnoreCase(""))
                {
                    name.add(hashmap.get(GlobalConstants.SERVICE_NAME));
                    serviceCost.add(hashmap.get(GlobalConstants.SERVICE_PRICE));
                    service_id.add(hashmap.get(GlobalConstants.SERVICE_ID));
                }
                else
                {
                    if (hashmap.get(GlobalConstants.SERVICE_NAME).contains(search) )
                    {
                        name.add(hashmap.get(GlobalConstants.SERVICE_NAME));
                        serviceCost.add(hashmap.get(GlobalConstants.SERVICE_PRICE));
                        service_id.add(hashmap.get(GlobalConstants.SERVICE_ID));
                    }
                }

            }
        }
        catch (Exception e)
        {

        }



    }

    public class Holder {
        TextView name;
        TextView service_cost;
        ImageView iv;
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup)
    {
        // TODO Auto-generated method stub
        final Holder holder = new Holder();

        final View rowView;

        rowView = inflater.inflate(R.layout.buy_service_item,viewGroup, false);

        holder.name = (TextView) rowView.findViewById(R.id.name);
        holder.service_cost = (TextView) rowView.findViewById(R.id.service_cost);
        holder.iv = (ImageView) rowView.findViewById(R.id.iv);

        holder.name.setText(name.get(position));
        holder.service_cost.setText(serviceCost.get(position));


        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(holder.iv.getVisibility() == View.INVISIBLE)
                {
                    holder.iv.setVisibility(View.VISIBLE);
                    rowView.setBackgroundColor(Color.parseColor("#603370"));    // Purple
                    holder.name.setTextColor(Color.parseColor("#FFFFFF"));      // white
                    holder.service_cost.setTextColor(Color.parseColor("#FFFFFF"));      // white

                    al_selected_service.add(service_id.get(position));

                }
                else
                {
                    holder.iv.setVisibility(View.INVISIBLE);
                    rowView.setBackgroundColor(Color.parseColor("#FFFFFF"));    // white
                    holder.name.setTextColor(Color.parseColor("#45265f"));      // text color
                    holder.service_cost.setTextColor(Color.parseColor("#45265f"));      // text color

                    al_selected_service.remove(service_id.get(position));

                }

                global.setAl_selected_service_id(al_selected_service);

            }
        });


        return rowView;
    }


    @Override
    public int getCount() {
        return name.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    public void setSelectedIndex(int index){
        selectedIndex = index;
    }


}
