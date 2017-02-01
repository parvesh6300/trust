package dcube.com.trust.utils;

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
 * Created by Sagar on 25/01/17.
 */
public class ViewPlanProductAdapter extends BaseAdapter {

    public static LayoutInflater inflater;

    Global global;
    Context context;

    int quantity;

    ArrayList<String> name;
    ArrayList<String> product_id;
    ArrayList<String> al_quantity;

    ArrayList<String> updated_product_id ;
    ArrayList<String> updated_product_quantity;
    ArrayList<String> updated_product_type;


    ArrayList<String> al_with_zero;

    // Array which contains the modified quantity of products
    public int[] ary_product_mod;


    public ViewPlanProductAdapter(Context mcontext)
    {

        this.context = mcontext;

        global = (Global) context.getApplicationContext();

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        name = new ArrayList<>();
        product_id = new ArrayList<>();
        al_quantity = new ArrayList<>();

        updated_product_id = new ArrayList<>();
        updated_product_quantity = new ArrayList<>();
        updated_product_type = new ArrayList<>();

        al_with_zero = new ArrayList<>();


        for (HashMap<String, String> hashMap : global.getAl_view_plan_details())
        {
            if (hashMap.get(GlobalConstants.PLAN_ELEMENT_TYPE).equalsIgnoreCase("Product"))
            {
                name.add(hashMap.get(GlobalConstants.ORDER_ITEM_NAME));
                product_id.add(hashMap.get(GlobalConstants.PLAN_ELEMENT_ID));
                al_quantity.add(hashMap.get(GlobalConstants.PLAN_ELEMENT_QUANTITY));

                al_with_zero.add("0");
            }

        }

        ary_product_mod = new int[name.size()] ;
        global.setAl_update_product_qty(al_with_zero);

    }


    public class ViewHolder {
        TextView name;
        EditText quantity;
        TextView category;
        TextView add;
        TextView minus;
    }


    @Override
    public View getView(final int position, View rowView, ViewGroup viewGroup) {

        final ViewHolder holder = new ViewHolder();

        rowView = inflater.inflate(R.layout.view_plan_product_list, viewGroup, false);

        holder.quantity = (EditText) rowView.findViewById(R.id.quantity);

        holder.name = (TextView) rowView.findViewById(R.id.name);
        holder.category = (TextView) rowView.findViewById(R.id.category);
        holder.add = (TextView) rowView.findViewById(R.id.add);
        holder.minus = (TextView) rowView.findViewById(R.id.minus);

        holder.name.setText(name.get(position));
        holder.quantity.setText(al_quantity.get(position));


        holder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                quantity =  Integer.parseInt(holder.quantity.getText().toString());

                if(Integer.parseInt(holder.quantity.getText().toString()) > 0)
                {
                    quantity = quantity - 1;

                    ary_product_mod[position] = ary_product_mod[position]+1;
                }

                holder.quantity.setText(String.valueOf(quantity));

                if (quantity > 0)
                {

                    if (updated_product_id.contains(product_id.get(position)))
                    {

                        int pos = updated_product_id.indexOf(product_id.get(position));

                    //    updated_product_quantity.set(pos , holder.quantity.getText().toString());
                        updated_product_type.set(pos , "Product");

                   //     ary_product_mod[position] = ary_product_mod[position]+1;

                    }
                    else
                    {

                        updated_product_id.add(product_id.get(position));
                    //    updated_product_quantity.add(String.valueOf(quantity));
                        updated_product_type.add("Product");

                   //     ary_product_mod[position] = ary_product_mod[position]+1;
                    }
                }
                else
                {
                    try {
                        if (updated_product_id.contains(product_id.get(position)))
                        {
                            int pos = updated_product_id.indexOf(product_id.get(position));

                            updated_product_id.remove(pos);
                       //     updated_product_quantity.remove(pos);
                            updated_product_type.remove(pos);

                        }
                    }
                    catch (Exception e)
                    {

                    }

                }

                global.setAl_update_product_id(updated_product_id);
             //   global.setAl_update_product_qty(updated_product_quantity);
                global.setAl_update_product_type(updated_product_type);

            }
        });




        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                quantity =  Integer.parseInt(holder.quantity.getText().toString());

                int max_stock = Integer.parseInt(al_quantity.get(position));

                if (max_stock > quantity )
                {
                    quantity = quantity + 1;

                    ary_product_mod[position] = ary_product_mod[position]-1;

                    holder.quantity.setText(String.valueOf(quantity));

                    if (updated_product_id.contains(product_id.get(position)))
                    {
                        int pos = updated_product_id.indexOf(product_id.get(position));

                    //    updated_product_quantity.set(pos , holder.quantity.getText().toString());
                        updated_product_type.set(pos , "Product");

                      //  ary_product_mod[position] = ary_product_mod[position]-1;
                    }
                    else
                    {
                        updated_product_id.add(product_id.get(position));
                    //    updated_product_quantity.add(String.valueOf(quantity));
                        updated_product_type.add("Product");

                     //   ary_product_mod[position] = ary_product_mod[position]-1;

                    }

                  //  Log.i("Product","id "+updated_product_id);

                    global.setAl_update_product_id(updated_product_id);
                  //  global.setAl_update_product_qty(updated_product_quantity);
                    global.setAl_update_product_type(updated_product_type);

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

                try {

                    quantity =  Integer.parseInt(charSequence.toString());

                    int max_stock = Integer.parseInt(al_quantity.get(position));

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

                    int max_stock = Integer.parseInt(al_quantity.get(position));

                    if (max_stock > quantity)
                    {
                        ary_product_mod[position] = Integer.parseInt(al_quantity.get(position)) - quantity;

                        if (updated_product_id.contains(product_id.get(position)))
                        {
                            int pos = updated_product_id.indexOf(product_id.get(position));

                        //    updated_product_quantity.set(pos , editable.toString());
                            updated_product_type.set(pos , "Product");

                          //  ary_product_mod[position] = Integer.parseInt(al_quantity.get(position)) - quantity;


                        }
                        else
                        {
                            updated_product_id.add(product_id.get(position));
                        //    updated_product_quantity.add(String.valueOf(quantity));
                            updated_product_type.add("Product");

                         //   ary_product_mod[position] = Integer.parseInt(al_quantity.get(position)) - quantity;

                        }

                        for (int i =0 ; i < name.size() ; i++)
                        {
                            Log.i("Ary",""+ary_product_mod[i]);
                        }

                        if (updated_product_quantity.size() > 0)
                        {
                            updated_product_quantity.clear();
                        }

                        for (int i =0 ; i < name.size() ; i++)
                        {
                            updated_product_quantity.add(String.valueOf(ary_product_mod[i]));
                        }


                        Log.i("Product","id "+updated_product_id);
                        Log.i("Product","Qty "+updated_product_quantity);
                        Log.i("PRoduct","type "+updated_product_type);


                        global.setAl_select_product(updated_product_id);
                        global.setAl_update_product_qty(updated_product_quantity);
                        global.setAl_update_product_type(updated_product_type);


                    }
                    else
                    {
                        Toast.makeText(context, "Only "+max_stock+" are left", Toast.LENGTH_SHORT).show();
                    }

                }
                catch (Exception e)
                {

                }

            }
        });



         return rowView;
    }

    @Override
    public int getCount()
    {
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
