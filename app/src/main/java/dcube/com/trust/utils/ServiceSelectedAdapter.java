package dcube.com.trust.utils;

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

/**
 * Created by Sagar on 30/11/16.
 */
public class ServiceSelectedAdapter extends BaseAdapter {

    Context context;
    Global global;


    ArrayList<HashMap<String,String>> selected_service_details;

    ArrayList<String> al_service_name;
    ArrayList<String> al_service_price;

    private static LayoutInflater inflater;

    public ServiceSelectedAdapter(Context ctx) {
        this.context = ctx;
//        this.name = name;
//        this.serviceCost = serviceCost;
        global = (Global) ctx.getApplicationContext();

        selected_service_details = new ArrayList<>();
        al_service_name = new ArrayList<>();
        al_service_price = new ArrayList<>();

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        for (int i = 0 ; i < global.getAl_selected_service_id().size(); i++)
        {
            for (int j = 0 ; j < global.getAl_service_details().size(); j++)
            {
                String service_id = global.getAl_service_details().get(j).get(GlobalConstants.SERVICE_ID);

                if (global.getAl_selected_service_id().get(i).equalsIgnoreCase(service_id))
                {
                    HashMap<String,String> map = new HashMap<>();

                    map.put(GlobalConstants.SERVICE_ID , global.getAl_service_details().get(j).get(GlobalConstants.SERVICE_ID));
                    map.put(GlobalConstants.SERVICE_PRICE , global.getAl_service_details().get(j).get(GlobalConstants.SERVICE_PRICE));
                    map.put(GlobalConstants.SERVICE_NAME , global.getAl_service_details().get(j).get(GlobalConstants.SERVICE_NAME));

                    selected_service_details.add(map);
                }

            }
        }


        global.setAl_selected_service(selected_service_details);
    }


    public class ViewHolder
    {
        TextView tv_name,tv_service_cost;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        // TODO Auto-generated method stub
        final ViewHolder holder = new ViewHolder();

        final View rowView;
        rowView = inflater.inflate(R.layout.service_selected_item, null);

        holder.tv_name = (TextView) rowView.findViewById(R.id.tv_name);
        holder.tv_service_cost = (TextView) rowView.findViewById(R.id.tv_service_cost);

        holder.tv_name.setText(global.getAl_selected_service().get(position).get(GlobalConstants.SERVICE_NAME));   //service_name.get(position)

        String formatted_service_cost = global.getAl_selected_service().get(position).get(GlobalConstants.SERVICE_PRICE);

        formatted_service_cost = FormatString.getCommaInString(formatted_service_cost);

        holder.tv_service_cost.setText(formatted_service_cost);

        return rowView;
    }

    @Override
    public int getCount() {
        return selected_service_details.size();
    }  // global.getAl_selected_service()

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }


}
