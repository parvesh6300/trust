package dcube.com.trust.utils;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import dcube.com.trust.R;

/**
 * Created by Rohit on 21/12/16.
 */
public class GuestProductSelectedAdapter extends BaseAdapter {

    Context context;
    Global global;

    private static LayoutInflater inflater = null;

    public GuestProductSelectedAdapter(Activity activity)
    {
        context = activity.getApplicationContext();
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        global = (Global) activity.getApplicationContext();

    }

    @Override
    public int getCount() {
        return global.getAl_select_product().size();
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


        holder.name.setText(global.getAl_selected_product_name().get(position));

        String formatted_prdct_cost = global.getAl_selected_product_price().get(position);

        formatted_prdct_cost = FormatString.getCommaInString(formatted_prdct_cost);

        holder.product_cost.setText(formatted_prdct_cost);  //global.getAl_selected_product_price().get(position)

        int int_quantity = Integer.parseInt(global.getAl_selected_product_quantity().get(position));
        float int_product_cost = Float.parseFloat(global.getAl_selected_product_price().get(position));
        float total_cost = int_quantity * int_product_cost;


        String formatted_total_cost = String.valueOf(total_cost);

        formatted_total_cost = FormatString.getCommaInString(formatted_total_cost);

        holder.price.setText(formatted_total_cost);   //String.valueOf(total_cost)


        String formatted_qnty = global.getAl_selected_product_quantity().get(position);

        formatted_qnty = FormatString.getCommaInString(formatted_qnty);

        holder.quantity.setText(" x "+formatted_qnty); //global.getAl_selected_product_quantity().get(position)

        holder.category.setText(global.getAl_selected_product_category().get(position));

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
