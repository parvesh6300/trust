package dcube.com.trust.utils;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import WebServicesHandler.GlobalConstants;
import dcube.com.trust.R;

public class ProductListAdapter extends BaseAdapter {

    Context context;
    Global global;
    static int quantity;

    ArrayList<String> name = new ArrayList<>();
    ArrayList<String> product_id = new ArrayList<>();
    ArrayList<String> SKU = new ArrayList<>();
    ArrayList<String> in_stock = new ArrayList<>();
    ArrayList<String> category = new ArrayList<>();
    ArrayList<String> price = new ArrayList<>();
    ArrayList<String> selected_product = new ArrayList<>();
    ArrayList<String> selected_product_quantity = new ArrayList<>();

//    ArrayList<String> al_selected_product;
//    ArrayList<String> al_selected_product_quantity;

    private static LayoutInflater inflater = null;

    public ProductListAdapter(Activity activity, String search) {

        context = activity.getApplicationContext();
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        global = (Global) activity.getApplicationContext();

//        al_selected_product = new ArrayList<>();
//        al_selected_product_quantity = new ArrayList<>();

        try {

            for (HashMap<String, String> hashMap : global.getAl_product_details())
            {

                if(search.equalsIgnoreCase(""))
                {
                    name.add(hashMap.get(GlobalConstants.PRODUCT_NAME));
                    category.add(GlobalConstants.PRODUCT_CATEGORY);
                    SKU.add(hashMap.get(GlobalConstants.PRODUCT_SKU));
                    price.add(GlobalConstants.PRODUCT_PRICE);
                    in_stock.add(GlobalConstants.PRODUCT_IN_STOCK);
                    product_id.add(GlobalConstants.PRODUCT_ID);
                    selected_product.add("0");
                }
                else
                {
                    if(hashMap.get(GlobalConstants.PRODUCT_NAME).contains(search))
                    {
                        name.add(hashMap.get(GlobalConstants.PRODUCT_NAME));
                        category.add(GlobalConstants.PRODUCT_CATEGORY);
                        SKU.add(hashMap.get(GlobalConstants.PRODUCT_SKU));
                        price.add(GlobalConstants.PRODUCT_PRICE);
                        in_stock.add(GlobalConstants.PRODUCT_IN_STOCK);
                        product_id.add(GlobalConstants.PRODUCT_ID);
                        selected_product.add("0");
                    }

                }
            }

        }catch(Exception e)
        {

        }

    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
         return name.size();
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
        EditText quantity;
        TextView category;
        TextView add;
        TextView minus;
    }

    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {

        // TODO Auto-generated method stub
        final Holder holder = new Holder();

        final View rowView;
        rowView = inflater.inflate(R.layout.product_category_item, parent , false);

        holder.name = (TextView) rowView.findViewById(R.id.name);
        holder.quantity = (EditText) rowView.findViewById(R.id.quantity);
        holder.category = (TextView) rowView.findViewById(R.id.category);
        holder.add = (TextView) rowView.findViewById(R.id.add);
        holder.minus = (TextView) rowView.findViewById(R.id.minus);

        holder.name.setText(name.get(position));
        holder.category.setText(name.get(position));


        holder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                quantity =  Integer.parseInt(holder.quantity.getText().toString());

                if(Integer.parseInt(holder.quantity.getText().toString()) > 0)
                {
                    quantity = quantity - 1;
                }

                holder.quantity.setText(String.valueOf(quantity));

                selected_product.add(position, product_id.get(position));
                selected_product_quantity.add(position ,String.valueOf(quantity));

                global.setAl_select_product(selected_product);
                global.setAl_selected_product_quantity(selected_product_quantity);
            }
        });


        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                quantity =  Integer.parseInt(holder.quantity.getText().toString());

                quantity = quantity + 1;

                holder.quantity.setText(String.valueOf(quantity));

                selected_product.add(position, product_id.get(position));
                selected_product_quantity.add(position ,String.valueOf(quantity));

                global.setAl_select_product(selected_product);
                global.setAl_selected_product_quantity(selected_product_quantity);

            }
        });

        return rowView;
    }


}