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

/**
 * Created by Sagar on 21/12/16.
 */
public class NurseProductSelectedAdapter extends BaseAdapter {

    Context context;
    Global global;

    ArrayList<String> name = new ArrayList<>();
    ArrayList<String> category = new ArrayList<>();
    ArrayList<String> quantity = new ArrayList<>();
    ArrayList<String> price = new ArrayList<>();

    ArrayList<HashMap<String,String>> selected_product_details;

    private static LayoutInflater inflater = null;

    public NurseProductSelectedAdapter(Activity activity)
    {
        context = activity.getApplicationContext();
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        selected_product_details = new ArrayList<>();

        global = (Global) activity.getApplicationContext();

    }

    @Override
    public int getCount() {
        return 0;
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
    public View getView(int position, View view, ViewGroup parent) {
        // TODO Auto-generated method stub
        final Holder holder = new Holder();

        final View rowView;
        rowView = inflater.inflate(R.layout.product_selected_item, parent , false);

        holder.name = (TextView) rowView.findViewById(R.id.name);
        holder.quantity = (TextView) rowView.findViewById(R.id.quantity);
        holder.category = (TextView) rowView.findViewById(R.id.category);
        holder.price = (TextView) rowView.findViewById(R.id.price);
        holder.product_cost = (TextView) rowView.findViewById(R.id.product_cost);

//        holder.name.setText(global.getAl_selected_product().get(position).get(GlobalConstants.PRODUCT_NAME));  //name.get(position)
//        holder.category.setText(global.getAl_selected_product().get(position).get(GlobalConstants.PRODUCT_CATEGORY));
//        holder.quantity.setText(" x "+global.getAl_selected_product().get(position).get(GlobalConstants.PRODUCT_QUANTITY));
//        holder.product_cost.setText(global.getAl_selected_product().get(position).get(GlobalConstants.PRODUCT_PRICE));

        holder.name.setText(global.getAl_select_product().get(position));
//        holder.category.setText();

        int int_quantity = Integer.parseInt(global.getAl_selected_product().get(position).get(GlobalConstants.PRODUCT_QUANTITY));
        int int_product_cost = Integer.parseInt(global.getAl_selected_product().get(position).get(GlobalConstants.PRODUCT_PRICE));
        int total_cost = int_quantity * int_product_cost;

        holder.price.setText(String.valueOf(total_cost));

        return rowView;

    }


    public class Holder
    {
        TextView name;
        TextView quantity;
        TextView category;
        TextView price,product_cost;
    }


}
