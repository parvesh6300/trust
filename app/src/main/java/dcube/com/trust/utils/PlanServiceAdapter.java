package dcube.com.trust.utils;

import android.content.Context;
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
 * Created by Sagar on 26/01/17.
 */
public class PlanServiceAdapter extends BaseAdapter {


    Context context;
    Global global;
    int quantity;

    int selectedIndex = -1;

    boolean isSelected = false;

    ArrayList<String> name ;
    ArrayList<String> serviceCost;
    ArrayList<String> service_id ;
    ArrayList<String> service_quantity;

    private static LayoutInflater inflater = null;

    public PlanServiceAdapter(Context context) {

        context = context.getApplicationContext();
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        global = (Global) context.getApplicationContext();
        name = new ArrayList<>();
        service_quantity = new ArrayList<>();
        service_id = new ArrayList<>();

        int pos = global.getServiceAppointmentPos();

        for (HashMap<String,String> hashmap : global.getAl_element_plan())
        {
            if (hashmap.get(GlobalConstants.PLAN_ELEMENT_TYPE).equalsIgnoreCase("Service"))
            {
                name.add(hashmap.get(GlobalConstants.PLAN_ELEMENT_NAME));
                service_quantity.add(hashmap.get(GlobalConstants.PLAN_ELEMENT_QUANTITY));
                service_id.add(hashmap.get(GlobalConstants.PLAN_ELEMENT_ID));

            }
        }

//        String str_plan_id = global.getAl_plan_details().get(pos).get(GlobalConstants.PLAN_ID);
//
//        for ( int i = 0 ; i < global.getAl_element_plan().size() ; i++)
//        {
//            if(global.getAl_element_plan().get(i).get(GlobalConstants.PLAN_ID).equalsIgnoreCase(str_plan_id))
//            {
//                if (global.getAl_element_plan().get(i).get(GlobalConstants.PLAN_ELEMENT_TYPE).equalsIgnoreCase("Service"))
//                {
//                    name.add(global.getAl_element_plan().get(i).get(GlobalConstants.PLAN_ELEMENT_NAME));
//                    service_quantity.add(global.getAl_element_plan().get(i).get(GlobalConstants.PLAN_ELEMENT_QUANTITY));
//                }
//
//            }
//        }



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


    public class Holder {
        TextView name;
        TextView service_quantity;
        ImageView iv;
    }

    @Override
    public View getView(int pos, View view, ViewGroup viewGroup) {


        final Holder holder = new Holder();

        final View rowView;
        rowView = inflater.inflate(R.layout.buy_service_item, null);

        holder.name = (TextView) rowView.findViewById(R.id.name);
        holder.service_quantity = (TextView) rowView.findViewById(R.id.service_cost);

        holder.name.setText(name.get(pos));
        holder.service_quantity.setText(service_quantity.get(pos));

        return rowView;
    }
}
