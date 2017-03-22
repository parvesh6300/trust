package dcube.com.trust.utils;

import android.content.Context;
import android.util.Log;
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
 * Created by Rohit on 26/01/17.
 */
public class PlanProductAdapter extends BaseAdapter
{
    public static LayoutInflater inflater;

    Global global;
    Context context;

    ArrayList<String> name;
    ArrayList<String> quantity;
    ArrayList<String> product_id;


    public PlanProductAdapter(Context mcontext)
    {
        this.context = mcontext;

        global = (Global) context.getApplicationContext();

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        name = new ArrayList<>();
        product_id = new ArrayList<>();
        quantity = new ArrayList<>();


        int pos = global.getPlan_selected_pos();

        String str_plan_id = global.getAl_element_plan().get(pos).get(GlobalConstants.PLAN_ID);

        Log.e("Plan","id "+str_plan_id);


        for (HashMap<String,String> hashmap : global.getAl_element_plan())
        {
            if (hashmap.get(GlobalConstants.PLAN_ELEMENT_TYPE).equalsIgnoreCase("Product"))
            {
                name.add(hashmap.get(GlobalConstants.PLAN_ELEMENT_NAME));
                quantity.add(hashmap.get(GlobalConstants.PLAN_ELEMENT_QUANTITY));
                product_id.add(hashmap.get(GlobalConstants.PLAN_ELEMENT_ID));

            }
        }


    }

    public class Holder {
        TextView name;
        TextView tv_quantity;
    }

    @Override
    public View getView(int pos, View rowView, ViewGroup parent) {

        final Holder holder = new Holder();

        rowView = inflater.inflate(R.layout.plan_product_list, parent, false);

        holder.name = (TextView) rowView.findViewById(R.id.name);
        holder.tv_quantity = (TextView) rowView.findViewById(R.id.tv_quantity);

        holder.name.setText(name.get(pos));
        holder.tv_quantity.setText(quantity.get(pos));

        return rowView;
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


}
