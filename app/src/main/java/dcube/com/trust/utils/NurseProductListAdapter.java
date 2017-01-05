package dcube.com.trust.utils;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
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
public class NurseProductListAdapter extends BaseAdapter {

    Context context;
    Global global;
    static int quantity;

    ArrayList<String> name = new ArrayList<>();
    ArrayList<String> product_id = new ArrayList<>();
    ArrayList<String> SKU = new ArrayList<>();
    ArrayList<String> in_stock = new ArrayList<>();
    ArrayList<String> category = new ArrayList<>();
    ArrayList<String> price = new ArrayList<>();

    ArrayList<String> selected_product_id = new ArrayList<>();
    ArrayList<String> selected_product_quantity = new ArrayList<>();
    ArrayList<String> selected_product_name = new ArrayList<>();
    ArrayList<String> selected_product_sku = new ArrayList<>();
    ArrayList<String> selected_product_category = new ArrayList<>();
    ArrayList<String> selected_product_price = new ArrayList<>();


    private static LayoutInflater inflater = null;

    public NurseProductListAdapter(Activity activity, String search)
    {
        context = activity.getApplicationContext();
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        global = (Global) activity.getApplicationContext();

        try {

            for (HashMap<String, String> hashMap : global.getAl_product_details())
            {

                if(search.equalsIgnoreCase(""))
                {
                    name.add(hashMap.get(GlobalConstants.PRODUCT_NAME));
                    category.add(hashMap.get(GlobalConstants.PRODUCT_CATEGORY));
                    SKU.add(hashMap.get(GlobalConstants.PRODUCT_SKU));
                    price.add(hashMap.get(GlobalConstants.PRODUCT_PRICE));
                    in_stock.add(hashMap.get(GlobalConstants.PRODUCT_IN_STOCK));
                    product_id.add(hashMap.get(GlobalConstants.PRODUCT_ID));

                }
                else
                {
                    if(hashMap.get(GlobalConstants.PRODUCT_NAME).contains(search) || hashMap.get(GlobalConstants.PRODUCT_CATEGORY).contains(search))
                    {
                        name.add(hashMap.get(GlobalConstants.PRODUCT_NAME));
                        category.add(hashMap.get(GlobalConstants.PRODUCT_CATEGORY));
                        SKU.add(hashMap.get(GlobalConstants.PRODUCT_SKU));
                        price.add(hashMap.get(GlobalConstants.PRODUCT_PRICE));
                        in_stock.add(hashMap.get(GlobalConstants.PRODUCT_IN_STOCK));
                        product_id.add(hashMap.get(GlobalConstants.PRODUCT_ID));
                    }
                }
            }

        }catch(Exception e)
        {

        }

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

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        final Holder holder = new Holder();

        final View rowView;
        rowView = inflater.inflate(R.layout.product_category_item, parent , false);

        holder.name = (TextView) rowView.findViewById(R.id.name);
        holder.quantity = (TextView) rowView.findViewById(R.id.quantity);
        holder.category = (TextView) rowView.findViewById(R.id.category);
        holder.add = (TextView) rowView.findViewById(R.id.add);
        holder.minus = (TextView) rowView.findViewById(R.id.minus);

        holder.name.setText(name.get(position));
        holder.category.setText(category.get(position));

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
                    if (selected_product_id.contains(product_id.get(position)))
                    {
                        int pos = selected_product_id.indexOf(product_id.get(position));
                        selected_product_quantity.add(pos, String.valueOf(quantity));
                    }
                    else
                    {

                        selected_product_id.add(product_id.get(position));
                        selected_product_quantity.add(String.valueOf(quantity));
                        selected_product_category.add(category.get(position));
                        selected_product_sku.add(SKU.get(position));
                        selected_product_name.add(name.get(position));
                        selected_product_price.add(price.get(position));

                    }
                }
                else
                {
                    try {
                        selected_product_price.remove(position);
                        selected_product_name.remove(position);
                        selected_product_sku.remove(position);
                        selected_product_category.remove(position);
                        selected_product_id.remove(position);
                        selected_product_quantity.remove(position);
                    }
                    catch (Exception e)
                    {

                    }

                }

                global.setAl_select_product(selected_product_id);
                global.setAl_selected_product_quantity(selected_product_quantity);
                global.setAl_selected_product_category(selected_product_category);
                global.setAl_selected_product_price(selected_product_price);
                global.setAl_selected_product_sku(selected_product_sku);
                global.setAl_selected_product_name(selected_product_name);

            }
        });

        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                quantity =  Integer.parseInt(holder.quantity.getText().toString());

                quantity = quantity + 1;

                holder.quantity.setText(String.valueOf(quantity));

                if (selected_product_id.contains(product_id.get(position)))
                {
                    int pos = selected_product_id.indexOf(product_id.get(position));

                    selected_product_quantity.add(pos , String.valueOf(quantity));
               }
                else
                {
                    selected_product_id.add(product_id.get(position));
                    selected_product_quantity.add(String.valueOf(quantity));
                    selected_product_category.add(category.get(position));
                    selected_product_sku.add(SKU.get(position));
                    selected_product_name.add(name.get(position));
                    selected_product_price.add(price.get(position));

                }

                global.setAl_select_product(selected_product_id);
                global.setAl_selected_product_quantity(selected_product_quantity);
                global.setAl_selected_product_category(selected_product_category);
                global.setAl_selected_product_price(selected_product_price);
                global.setAl_selected_product_sku(selected_product_sku);
                global.setAl_selected_product_name(selected_product_name);

            }
        });


        holder.quantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if (selected_product_id.contains(product_id.get(position)))
                {
                    int pos = selected_product_id.indexOf(product_id.get(position));

                    selected_product_quantity.add(pos , editable.toString());
                }
                else
                {
                    selected_product_id.add(product_id.get(position));
                    selected_product_quantity.add(String.valueOf(quantity));
                    selected_product_category.add(category.get(position));
                    selected_product_sku.add(SKU.get(position));
                    selected_product_name.add(name.get(position));
                    selected_product_price.add(price.get(position));

                }

                global.setAl_select_product(selected_product_id);
                global.setAl_selected_product_quantity(selected_product_quantity);
                global.setAl_selected_product_category(selected_product_category);
                global.setAl_selected_product_price(selected_product_price);
                global.setAl_selected_product_sku(selected_product_sku);
                global.setAl_selected_product_name(selected_product_name);}
        });


        return rowView;
    }

    public class Holder
    {
        TextView name;
        TextView quantity;
        TextView category;
        TextView add;
        TextView minus;
    }


}