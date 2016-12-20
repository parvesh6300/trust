package dcube.com.trust.utils;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import WebServicesHandler.GlobalConstants;
import dcube.com.trust.R;

public class ProductListAdapter extends BaseAdapter {

    Context context;
    Global global;
    static int quantity;

    ArrayList<String> name = new ArrayList<>();
    ArrayList<String> category = new ArrayList<>();
    //ArrayList<String> quantity = new ArrayList<>();

    ArrayList<String> al_selected_product;
    ArrayList<String> al_selected_product_quantity;

    private static LayoutInflater inflater = null;

    public ProductListAdapter(Activity activity) {

        context = activity.getApplicationContext();
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        global = (Global) activity.getApplicationContext();

        al_selected_product = new ArrayList<>();
        al_selected_product_quantity = new ArrayList<>();

    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
         return global.getAl_product_details().size();
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
        holder.quantity = (TextView) rowView.findViewById(R.id.quantity);
        holder.category = (TextView) rowView.findViewById(R.id.category);
        holder.add = (TextView) rowView.findViewById(R.id.add);
        holder.minus = (TextView) rowView.findViewById(R.id.minus);

        holder.name.setText(global.getAl_product_details().get(position).get(GlobalConstants.PRODUCT_NAME));   // name.get(position)
        holder.category.setText(global.getAl_product_details().get(position).get(GlobalConstants.PRODUCT_CATEGORY));

        holder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                quantity =  Integer.parseInt(holder.quantity.getText().toString());

                if(Integer.parseInt(holder.quantity.getText().toString()) > 0)
                {
                    quantity = quantity - 1;
                }

                holder.quantity.setText(String.valueOf(quantity));

                if (quantity > 0)
                {
                    for (int j = 0 ; j < al_selected_product.size() ; j++)
                    {
                        for (int i = 0 ; i < global.getAl_selected_product_id().size() ; i++)
                        {
                            if (al_selected_product.contains(global.getAl_selected_product_id().get(i)))
                            {
                                al_selected_product.remove(position);
                                al_selected_product_quantity.remove(position);
                            }
                            else
                            {
                                al_selected_product.add(global.al_product_details.get(position).get(GlobalConstants.PRODUCT_ID));
                                al_selected_product_quantity.add(String.valueOf(quantity));
                            }
                        }
                    }

                    al_selected_product.add(global.al_product_details.get(position).get(GlobalConstants.PRODUCT_ID));
                    al_selected_product_quantity.add(String.valueOf(quantity));

                }

                global.setAl_selected_product_id(al_selected_product);
                global.setAl_selected_product_quantity(al_selected_product_quantity);

            }
        });

        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                quantity =  Integer.parseInt(holder.quantity.getText().toString());

                quantity = quantity + 1;
                holder.quantity.setText(String.valueOf(quantity));

                if (quantity > 0)
                {
                    for (int j = 0 ; j < al_selected_product.size() ; j++)
                    {
                        for (int i = 0 ; i < global.getAl_selected_product_id().size() ; i++)
                        {
                            if (al_selected_product.contains(global.getAl_selected_product_id().get(i)))
                            {
                                al_selected_product.remove(i);
                                al_selected_product_quantity.remove(i);
                            }
                            else
                            {
                                al_selected_product.add(global.al_product_details.get(position).get(GlobalConstants.PRODUCT_ID));
                                al_selected_product_quantity.add(String.valueOf(quantity));
                            }
                        }
                    }

                    al_selected_product.add(global.al_product_details.get(position).get(GlobalConstants.PRODUCT_ID));
                    al_selected_product_quantity.add(String.valueOf(quantity));

                }

                global.setAl_selected_product_id(al_selected_product);
                global.setAl_selected_product_quantity(al_selected_product_quantity);

            }
        });

        return rowView;
    }

    /*
    // Filter Class
    public void filter(String charText)
    {

        charText = charText.toLowerCase(Locale.getDefault());
        global.al_product_details.clear();

        if (charText.length() == 0) {
            global.al_product_details.addAll(al_product_details);
        }
        else
        {
            for (int j =0 ; j < global.al_product_details.size(); j++)
            {
                if (global.al_product_details.get(j).get(GlobalConstants.PRODUCT_NAME).contains(charText))
                {
                    HashMap<String,String> map = new HashMap<>();

                    map.put(GlobalConstants.PRODUCT_ID , global.getAl_product_details().get(j).get(GlobalConstants.PRODUCT_ID));
                    map.put(GlobalConstants.PRODUCT_SKU ,global.getAl_product_details().get(j).get(GlobalConstants.PRODUCT_SKU));
                    map.put(GlobalConstants.PRODUCT_NAME , global.getAl_product_details().get(j).get(GlobalConstants.PRODUCT_NAME));
                    map.put(GlobalConstants.PRODUCT_CATEGORY , global.getAl_product_details().get(j).get(GlobalConstants.PRODUCT_CATEGORY));
                    map.put(GlobalConstants.PRODUCT_PRICE , global.getAl_product_details().get(j).get(GlobalConstants.PRODUCT_PRICE));
                    map.put(GlobalConstants.PRODUCT_IN_STOCK , global.getAl_product_details().get(j).get(GlobalConstants.PRODUCT_IN_STOCK));

                    al_product_details.add(map);
                }

            }

            global.setAl_product_details(al_product_details);
        }


        notifyDataSetChanged();
    }

    */

}