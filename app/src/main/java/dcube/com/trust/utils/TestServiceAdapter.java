package dcube.com.trust.utils;

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
 * Created by Rohit on 17/02/17.
 */
public class TestServiceAdapter extends BaseAdapter {

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


    public TestServiceAdapter(Context context,String search)
    {
        context = context.getApplicationContext();

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        global = (Global) context.getApplicationContext();

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
                    if (hashmap.get(GlobalConstants.SERVICE_NAME).toLowerCase().contains(search.toLowerCase()) )
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


    public class ViewHolder {

        TextView name;
        TextView service_cost;
        ImageView iv;


        public ViewHolder(View rowView) {

            name = (TextView) rowView.findViewById(R.id.name);
            service_cost = (TextView) rowView.findViewById(R.id.service_cost);
            iv = (ImageView) rowView.findViewById(R.id.iv);
        }

    }


    @Override
    public int getCount() {
        return name.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        // TODO Auto-generated method stub
        final ViewHolder viewHolder;

        if (convertView == null)
        {
            convertView = inflater.inflate(R.layout.buy_service_item, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.name.setText(name.get(position));
        viewHolder.service_cost.setText(serviceCost.get(position));

        final View finalConvertView = convertView;

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(viewHolder.iv.getVisibility() == View.INVISIBLE)
                {
                    viewHolder.iv.setVisibility(View.VISIBLE);
                    finalConvertView.setBackgroundColor(Color.parseColor("#603370"));    // Purple
                    viewHolder.name.setTextColor(Color.parseColor("#FFFFFF"));      // white
                    viewHolder.service_cost.setTextColor(Color.parseColor("#FFFFFF"));      // white

                    al_selected_service.add(service_id.get(position));

                }
                else
                {
                    viewHolder.iv.setVisibility(View.INVISIBLE);
                    finalConvertView.setBackgroundColor(Color.parseColor("#FFFFFF"));    // white
                    viewHolder.name.setTextColor(Color.parseColor("#45265f"));      // text color
                    viewHolder.service_cost.setTextColor(Color.parseColor("#45265f"));      // text color

                    al_selected_service.remove(service_id.get(position));

                }

                global.setAl_selected_service_id(al_selected_service);

            }
        });



        return convertView;
    }

}
