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
 * Created by Sagar on 27/01/17.
 */
public class ViewPlanServiceAdapter extends BaseAdapter {

    public static LayoutInflater inflater;

    Global global;
    Context context;

    int quantity;

    ArrayList<String> name;
    ArrayList<String> service_id;
    ArrayList<String> al_quantity;

    ArrayList<String> updated_service_id ;
    ArrayList<String> updated_service_quantity;
    ArrayList<String> updated_service_type;

    // Array which contains the modified quantity of service
    public int[] ary_service_mod;



    public ViewPlanServiceAdapter(Context mcontext) {

        this.context = mcontext;

        global = (Global) context.getApplicationContext();

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        name = new ArrayList<>();
        service_id = new ArrayList<>();
        al_quantity = new ArrayList<>();

        updated_service_id = new ArrayList<>();
        updated_service_quantity = new ArrayList<>();
        updated_service_type = new ArrayList<>();


        for (HashMap<String, String> hashMap : global.getAl_view_plan_details())
        {
            if (hashMap.get(GlobalConstants.PLAN_ELEMENT_TYPE).equalsIgnoreCase("Service"))
            {
                name.add(hashMap.get(GlobalConstants.ORDER_ITEM_NAME));
                service_id.add(hashMap.get(GlobalConstants.PLAN_ELEMENT_ID));
                al_quantity.add(hashMap.get(GlobalConstants.PLAN_ELEMENT_QUANTITY));
            }

        }

        ary_service_mod = new int[name.size()];

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

        rowView = inflater.inflate(R.layout.view_plan_service_list, viewGroup, false);

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

                    ary_service_mod[position]++;

                }

                holder.quantity.setText(String.valueOf(quantity));

                if (quantity > 0)
                {
                    if (updated_service_id.contains(service_id.get(position)))
                    {

                        int pos = updated_service_id.indexOf(service_id.get(position));

                     //   updated_service_quantity.set(pos , holder.quantity.getText().toString());
                        updated_service_type.set(pos , "Service");
                    }
                    else
                    {

                        updated_service_id.add(service_id.get(position));
                       // updated_service_quantity.add(String.valueOf(quantity));
                        updated_service_type.add("Service");

                    }
                }
                else
                {
                    try {

                        if (updated_service_id.contains(service_id.get(position)))
                        {
                            int pos = updated_service_id.indexOf(service_id.get(position));

                            updated_service_id.remove(pos);
                         //   updated_service_quantity.remove(pos);
                            updated_service_type.remove(pos);

                        }
                    }
                    catch (Exception e)
                    {

                    }

                }

                Log.i("Service","id "+updated_service_id);
                Log.i("Service","Qty "+updated_service_quantity);
                Log.i("Service","Type "+updated_service_type);

                global.setAl_update_service_id(updated_service_id);
      //          global.setAl_update_service_qty(updated_service_quantity);
                global.setAl_update_service_type(updated_service_type);

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

                    ary_service_mod[position]--;

                    holder.quantity.setText(String.valueOf(quantity));

                    if (updated_service_id.contains(service_id.get(position)))
                    {
                        int pos = updated_service_id.indexOf(service_id.get(position));

                      //  updated_service_quantity.set(pos , holder.quantity.getText().toString());
                        updated_service_type.set(pos , "Service");

                    }
                    else
                    {
                        updated_service_id.add(service_id.get(position));
                   //     updated_service_quantity.add(String.valueOf(quantity));
                        updated_service_type.add("Service");

                    }

                    Log.i("Service","id "+updated_service_id);

                    global.setAl_update_service_id(updated_service_id);
                //    global.setAl_update_service_qty(updated_service_quantity);
                    global.setAl_update_service_type(updated_service_type);

                }
                else
                {
                    Toast.makeText(context, "Only "+max_stock+" are left", Toast.LENGTH_SHORT).show();
                }


                Log.i("Service","id "+updated_service_id);
                Log.i("Service","Qty "+updated_service_quantity);
                Log.i("Service","Type "+updated_service_type);

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
                        if (updated_service_id.contains(service_id.get(position)))
                        {
                            int pos = updated_service_id.indexOf(service_id.get(position));

                      //      updated_service_quantity.set(pos , editable.toString());
                            updated_service_type.set(pos ,"Service");

                        }
                        else
                        {
                            updated_service_id.add(service_id.get(position));
                     //       updated_service_quantity.add(String.valueOf(quantity));
                            updated_service_type.add("Service");


                        }



                        for (int i =0 ; i < name.size() ; i++)
                        {
                            Log.i("Ary",""+ary_service_mod[i]);
                        }

                        if (updated_service_quantity.size() > 0)
                        {
                            updated_service_quantity.clear();
                        }

                        for (int i =0 ; i < name.size() ; i++)
                        {
                            updated_service_quantity.add(String.valueOf(ary_service_mod[i]));
                        }



                        global.setAl_update_service_id(updated_service_id);
                        global.setAl_update_service_qty(updated_service_quantity);
                        global.setAl_update_service_type(updated_service_type);

                    }
                    else
                    {
                        Toast.makeText(context, "Only "+max_stock+" can be taken", Toast.LENGTH_SHORT).show();
                    }

                }
                catch (Exception e)
                {

                }


                Log.i("Service","id "+updated_service_id);
                Log.i("Service","Qty "+updated_service_quantity);
                Log.i("Service","Type "+updated_service_type);


            }
        });


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
