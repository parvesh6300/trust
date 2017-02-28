package dcube.com.trust.utils;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import WebServicesHandler.GlobalConstants;
import dcube.com.trust.R;

/**
 * Created by Sagar on 21/12/16.
 */
public class GuestProductListAdapter extends BaseAdapter {

    Context context;
    public Global global;
    int quantity;

    int[] updated_qty;

    ArrayList<String> selected_quantity = new ArrayList<>();


    ArrayList<String> name ;
    ArrayList<String> product_id;
    ArrayList<String> SKU;
    ArrayList<String> in_stock ;
    ArrayList<String> category;
    ArrayList<String> price ;


    ArrayList<String> selected_product_id = new ArrayList<>();
    ArrayList<String> selected_product_quantity = new ArrayList<>();
    ArrayList<String> selected_product_name = new ArrayList<>();
    ArrayList<String> selected_product_sku = new ArrayList<>();
    ArrayList<String> selected_product_category = new ArrayList<>();
    ArrayList<String> selected_product_price = new ArrayList<>();

    private static LayoutInflater inflater = null;

    public GuestProductListAdapter(Activity activity, String search)
    {
        context = activity.getApplicationContext();
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        name = new ArrayList<>();
        product_id = new ArrayList<>();
        SKU = new ArrayList<>();
        in_stock = new ArrayList<>();
        category = new ArrayList<>();
        price = new ArrayList<>();

        global = (Global) activity.getApplicationContext();

        try {

            if (global.getAl_product_details().size() > 0)
            {
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
                        if(hashMap.get(GlobalConstants.PRODUCT_NAME).toLowerCase().contains(search.toLowerCase()) ||
                                hashMap.get(GlobalConstants.PRODUCT_CATEGORY).toLowerCase().contains(search.toLowerCase()))
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

                updated_qty = new int[name.size()];

            }


        }catch(Exception e)
        {

        }

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

        rowView = inflater.inflate(R.layout.guest_product_list, parent , false);

        holder.quantity = (EditText) rowView.findViewById(R.id.quantity);

        holder.name = (TextView) rowView.findViewById(R.id.name);
        holder.category = (TextView) rowView.findViewById(R.id.category);
        holder.add = (TextView) rowView.findViewById(R.id.add);
        holder.minus = (TextView) rowView.findViewById(R.id.minus);

        holder.name.setText(name.get(position));
        holder.category.setText(category.get(position));

        holder.quantity.setText(String.valueOf(updated_qty[position]));


        holder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                quantity =  Integer.parseInt(holder.quantity.getText().toString());

                if(quantity > 0)
                {
                    quantity = quantity - 1;
                }

                holder.quantity.setText(String.valueOf(quantity));


                if (quantity > 0)
                {
                   // updated_qty[position] = quantity;

                    if (selected_product_id.contains(product_id.get(position)))
                    {
                        int pos = selected_product_id.indexOf(product_id.get(position));
                        selected_product_quantity.set(pos, String.valueOf(quantity));

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
                   // updated_qty[position] = 0;

                    try {


                        if (selected_product_id.contains(product_id.get(position)))
                        {
                            int pos = selected_product_id.indexOf(product_id.get(position));

                            selected_product_price.remove(pos);
                            selected_product_name.remove(pos);
                            selected_product_sku.remove(pos);
                            selected_product_category.remove(pos);
                            selected_product_id.remove(pos);
                            selected_product_quantity.remove(pos);

                        }

                    }
                    catch (Exception e)
                    {

                    }

                }

            }
        });


        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                quantity =  Integer.parseInt(holder.quantity.getText().toString());

                int max_stock = Integer.parseInt(in_stock.get(position));

                if (max_stock > quantity )
                {
                    quantity = quantity + 1;

                 //   updated_qty[position] = quantity;

                    holder.quantity.setText(String.valueOf(quantity));

                    if (selected_product_id.contains(product_id.get(position)))
                    {
                        int pos = selected_product_id.indexOf(product_id.get(position));

                        selected_product_quantity.set(pos , holder.quantity.getText().toString());

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
                    Toast.makeText(context, "Only "+max_stock+" are left", Toast.LENGTH_SHORT).show();
                }



            }
        });


        holder.quantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                try
                {
                    quantity =  Integer.parseInt(charSequence.toString());

                    int max_stock = Integer.parseInt(in_stock.get(position));

                    if (quantity > max_stock)
                    {
                        String qt = charSequence.toString();

                        String[] q = new String[qt.length()];

                        q[0] =  qt.substring(0,qt.length()-1);

                        holder.quantity.setText(q[0]);

                    }
                }
                catch (Exception e)
                {

                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

                try {

                    quantity =  Integer.parseInt(editable.toString());

                    int max_stock = Integer.parseInt(in_stock.get(position));

                    if (quantity > 0)
                    {
                        updated_qty[position] = quantity;

                        if (max_stock > quantity)
                        {

                            if (selected_product_id.contains(product_id.get(position)))
                            {
                                int pos = selected_product_id.indexOf(product_id.get(position));

                                selected_product_quantity.set(pos , editable.toString());
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

                            setArrayListInGlobalList();

                        }
                        else
                        {
                            Toast.makeText(context, "Only "+max_stock+" are left", Toast.LENGTH_SHORT).show();
                        }

                    }
                    else
                    {

                        updated_qty[position] = 0;

                        if (selected_product_id.contains(product_id.get(position)))
                        {
                            int pos = selected_product_id.indexOf(product_id.get(position));

                            selected_product_price.remove(pos);
                            selected_product_name.remove(pos);
                            selected_product_sku.remove(pos);
                            selected_product_category.remove(pos);
                            selected_product_id.remove(pos);
                            selected_product_quantity.remove(pos);

                        }

                        setArrayListInGlobalList();

                        Toast.makeText(context, "Quantity should be greater than 0", Toast.LENGTH_SHORT).show();
                    }


                    Log.i("Product","id "+selected_product_id);
                    Log.i("Product","Qty "+selected_product_quantity);

                    Log.i("Array","Qty "+updated_qty[position]);


                }
                catch (Exception e)
                {

                }

            }
        });


        return rowView;
    }



    public void setArrayListInGlobalList()
    {
        global.setAl_select_product(selected_product_id);
        global.setAl_selected_product_quantity(selected_product_quantity);
        global.setAl_selected_product_category(selected_product_category);
        global.setAl_selected_product_price(selected_product_price);
        global.setAl_selected_product_sku(selected_product_sku);
        global.setAl_selected_product_name(selected_product_name);
    }



}
