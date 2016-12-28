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
 * Created by Sagar on 24/10/16.
 */
public class PlanSelectedAdapter extends BaseAdapter {

    Context context;
    Global global;

    ArrayList<HashMap<String,String>> selected_plan_details;

    private static LayoutInflater inflater;

    public PlanSelectedAdapter(Context ctx)
    {
        this.context = ctx;
        global = (Global) ctx.getApplicationContext();

        selected_plan_details = new ArrayList<>();

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        for (int i = 0 ; i < global.getAl_selected_plan_id().size() ; i++ )
        {

            for (int j = 0 ; j < global.getAl_plan_details().size() ; j++)
            {
                String plan_id = global.getAl_plan_details().get(j).get(GlobalConstants.PLAN_ID);

                if (global.getAl_selected_plan_id().get(i).equalsIgnoreCase(plan_id))
                {
                    HashMap<String,String> map = new HashMap<>();

                    String str_plan_name = global.getAl_plan_details().get(j).get(GlobalConstants.PLAN_PRODUCT_NAME)+" + "+
                            global.getAl_plan_details().get(j).get(GlobalConstants.PLAN_SERVICE_NAME);

                    map.put(GlobalConstants.PLAN_NAME , str_plan_name);
                    map.put(GlobalConstants.PLAN_ID , global.getAl_plan_details().get(j).get(GlobalConstants.PLAN_ID));
                    map.put(GlobalConstants.PLAN_DURATION , global.getAl_plan_details().get(j).get(GlobalConstants.PLAN_DURATION));
                    map.put(GlobalConstants.PLAN_PRICE , global.getAl_plan_details().get(j).get(GlobalConstants.PLAN_PRICE));

                    map.put(GlobalConstants.PLAN_PRODUCT_ID , global.getAl_plan_details().get(j).get(GlobalConstants.PLAN_PRODUCT_ID));
                    map.put(GlobalConstants.PLAN_PRODUCT_SKU , global.getAl_plan_details().get(j).get(GlobalConstants.PLAN_PRODUCT_SKU));
                    map.put(GlobalConstants.PLAN_PRODUCT_NAME , global.getAl_plan_details().get(j).get(GlobalConstants.PLAN_PRODUCT_NAME));
                    map.put(GlobalConstants.PLAN_PRODUCT_CATEGORY , global.getAl_plan_details().get(j).get(GlobalConstants.PLAN_PRODUCT_CATEGORY));
                    map.put(GlobalConstants.PLAN_PRODUCT_PRICE ,global.getAl_plan_details().get(j).get(GlobalConstants.PLAN_PRODUCT_PRICE));
                    map.put(GlobalConstants.PLAN_PRODUCT_IN_STOCK ,global.getAl_plan_details().get(j).get(GlobalConstants.PLAN_PRODUCT_IN_STOCK));

                    map.put(GlobalConstants.PLAN_SERVICE_ID , global.getAl_plan_details().get(j).get(GlobalConstants.PLAN_SERVICE_ID));
                    map.put(GlobalConstants.PLAN_SERVICE_PRICE , global.getAl_plan_details().get(j).get(GlobalConstants.PLAN_SERVICE_PRICE));
                    map.put(GlobalConstants.PLAN_SERVICE_NAME , global.getAl_plan_details().get(j).get(GlobalConstants.PLAN_SERVICE_NAME));

                    selected_plan_details.add(map);
                }

            }

        }

        global.setAl_selected_plan(selected_plan_details);

    }



    public class ViewHolder
    {
        TextView tv_name,tv_product_cost,tv_service_cost;
    }



    @Override
    public int getCount() {
        return global.getAl_selected_plan().size();
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
    public View getView(int position, View view, ViewGroup viewGroup) {


        // TODO Auto-generated method stub
        final ViewHolder holder = new ViewHolder();

        final View rowView;
        rowView = inflater.inflate(R.layout.planselecteditem, null);

        holder.tv_name = (TextView) rowView.findViewById(R.id.tv_name);
        holder.tv_product_cost = (TextView) rowView.findViewById(R.id.tv_product_cost);
        holder.tv_service_cost = (TextView) rowView.findViewById(R.id.tv_service_cost);

        String str_plan_name = global.getAl_selected_plan().get(position).get(GlobalConstants.PLAN_PRODUCT_NAME)+" + "+
                global.getAl_selected_plan().get(position).get(GlobalConstants.PLAN_SERVICE_NAME);

        holder.tv_name.setText(global.getAl_selected_plan().get(position).get(GlobalConstants.PLAN_ID));   // (plan_name.get(position)
        holder.tv_product_cost.setText(global.getAl_selected_plan().get(position).get(GlobalConstants.PLAN_PRODUCT_PRICE));
        holder.tv_service_cost.setText(global.getAl_selected_plan().get(position).get(GlobalConstants.PLAN_SERVICE_PRICE));

        return rowView;

    }
}
