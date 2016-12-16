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

    private static LayoutInflater inflater = null;

    public ProductListAdapter(Activity activity) {

        context = activity.getApplicationContext();
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        global = (Global) activity.getApplicationContext();

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

                if(Integer.parseInt(holder.quantity.getText().toString()) > 0) {
                    quantity = quantity - 1;
                }
                   holder.quantity.setText(String.valueOf(quantity));
            }
        });

        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                quantity =  Integer.parseInt(holder.quantity.getText().toString());

                quantity = quantity + 1;
                holder.quantity.setText(String.valueOf(quantity));
            }
        });

        return rowView;
    }



}