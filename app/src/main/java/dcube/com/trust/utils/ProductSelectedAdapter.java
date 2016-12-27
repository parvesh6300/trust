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

import dcube.com.trust.R;

public class ProductSelectedAdapter extends BaseAdapter {

    Context context;
    Global global;

    ArrayList<String> name = new ArrayList<>();
    ArrayList<String> category = new ArrayList<>();
    ArrayList<String> quantity = new ArrayList<>();
    ArrayList<String> price = new ArrayList<>();

    ArrayList<HashMap<String,String>> selected_product_details;

    private static LayoutInflater inflater = null;

    public ProductSelectedAdapter(Activity activity) {

        context = activity.getApplicationContext();
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        selected_product_details = new ArrayList<>();

        global = (Global) activity.getApplicationContext();

        name.add("Fiesta");
        category.add("Condom");
        quantity.add("x 3");
        price.add("1,500");

        name.add("Sleek /safe load");
        category.add("IUD");
        quantity.add("x 1");
        price.add("5,000");

        for (int i = 0 ; i < global.getAl_select_product().size() ; i++)
        {
            name.add(global.getAl_select_product().get(i));
        }


//        for (int i = 0; i < global.getAl_selected_product_id().size(); i++)
//        {
//
//            for (int j = 0; j < global.getAl_product_details().size(); j++)
//            {
//                String product_id = global.getAl_product_details().get(j).get(GlobalConstants.PRODUCT_ID);
//
//                if (global.getAl_selected_product_id().get(i).equalsIgnoreCase(product_id))
//                {
//                    HashMap<String,String> map = new HashMap<>();
//
//                    map.put(GlobalConstants.PRODUCT_ID , global.getAl_product_details().get(j).get(GlobalConstants.PRODUCT_ID));
//                    map.put(GlobalConstants.PRODUCT_SKU ,global.getAl_product_details().get(j).get(GlobalConstants.PRODUCT_SKU));
//                    map.put(GlobalConstants.PRODUCT_NAME , global.getAl_product_details().get(j).get(GlobalConstants.PRODUCT_NAME));
//                    map.put(GlobalConstants.PRODUCT_CATEGORY , global.getAl_product_details().get(j).get(GlobalConstants.PRODUCT_CATEGORY));
//                    map.put(GlobalConstants.PRODUCT_PRICE , global.getAl_product_details().get(j).get(GlobalConstants.PRODUCT_PRICE));
//                    map.put(GlobalConstants.PRODUCT_IN_STOCK , global.getAl_product_details().get(j).get(GlobalConstants.PRODUCT_IN_STOCK));
//                    map.put(GlobalConstants.PRODUCT_QUANTITY , global.getAl_selected_product_quantity().get(i));
//
//                    selected_product_details.add(map);
//                }
//
//            }
//        }
//        global.setAl_selected_product(selected_product_details);

    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
         return global.getAl_select_product().size();
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
        TextView quantity;
        TextView category;
        TextView price,product_cost;
    }

    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {

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

        int int_quantity = Integer.parseInt(global.getAl_selected_product_quantity().get(position));
//        int int_product_cost = Integer.parseInt(global.getAl_selected_product().get(position).get(GlobalConstants.PRODUCT_PRICE));
//        int total_cost = int_quantity * int_product_cost;

//        holder.price.setText(String.valueOf(total_cost));

        return rowView;
    }
}