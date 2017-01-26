package dcube.com.trust.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import WebServicesHandler.GlobalConstants;
import dcube.com.trust.R;

/**
 * Created by Sagar on 26/01/17.
 */
public class PlanProductAdapter extends BaseAdapter
{
    public static LayoutInflater inflater;

    Global global;
    Context context;

    ArrayList<String> name;
    ArrayList<String> product_id;
    ArrayList<String> SKU;
    ArrayList<String> quantity;
    ArrayList<String> category;
    ArrayList<String> price;

    public PlanProductAdapter(Context mcontext)
    {
        this.context = mcontext;

        global = (Global) context.getApplicationContext();

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        name = new ArrayList<>();
        product_id = new ArrayList<>();
        SKU = new ArrayList<>();
        quantity = new ArrayList<>();
        category = new ArrayList<>();
        price = new ArrayList<>();


        int pos = global.getServiceAppointmentPos();

        String str_plan_id = global.getAl_plan_details().get(pos).get(GlobalConstants.PLAN_ID);

        for ( int i = 0 ; i < global.getAl_element_plan().size() ; i++)
        {
            if(global.getAl_element_plan().get(i).get(GlobalConstants.PLAN_ID).equalsIgnoreCase(str_plan_id))
            {
                if (global.getAl_element_plan().get(i).get(GlobalConstants.PLAN_ELEMENT_TYPE).equalsIgnoreCase("Product"))
                {
                    name.add(global.getAl_element_plan().get(i).get(GlobalConstants.PLAN_ELEMENT_NAME));
                    quantity.add(global.getAl_element_plan().get(i).get(GlobalConstants.PLAN_ELEMENT_QUANTITY));
                }

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
