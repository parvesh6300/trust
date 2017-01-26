package dcube.com.trust.utils;

import android.content.Context;
import android.util.Log;
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

/**
 * Created by Sagar on 25/01/17.
 */
public class UpdatePlanAdapter extends BaseAdapter {

    public static LayoutInflater inflater;

    Global global;
    Context context;

    ArrayList<String> name;
    ArrayList<String> product_id;
    ArrayList<String> SKU;
    ArrayList<String> in_stock;
    ArrayList<String> category;
    ArrayList<String> price;

    public UpdatePlanAdapter(Context mcontext) {

        this.context = mcontext;

        global = (Global) context.getApplicationContext();

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        name = new ArrayList<>();
        product_id = new ArrayList<>();
        SKU = new ArrayList<>();
        in_stock = new ArrayList<>();
        category = new ArrayList<>();
        price = new ArrayList<>();
    //    addValues();

        Log.e("Size",""+global.getAl_product_details().size());

        for (HashMap<String, String> hashMap : global.getAl_product_details())
        {
            name.add(hashMap.get(GlobalConstants.PRODUCT_NAME));
            category.add(hashMap.get(GlobalConstants.PRODUCT_CATEGORY));
            SKU.add(hashMap.get(GlobalConstants.PRODUCT_SKU));
            price.add(hashMap.get(GlobalConstants.PRODUCT_PRICE));
            in_stock.add(hashMap.get(GlobalConstants.PRODUCT_IN_STOCK));
            product_id.add(hashMap.get(GlobalConstants.PRODUCT_ID));
        }


    }

    @Override
    public View getView(int position, View rowView, ViewGroup viewGroup) {

        final ViewHolder holder = new ViewHolder();

        rowView = inflater.inflate(R.layout.update_plan_list, viewGroup, false);

        holder.quantity = (EditText) rowView.findViewById(R.id.quantity);

        holder.name = (TextView) rowView.findViewById(R.id.name);
        holder.category = (TextView) rowView.findViewById(R.id.category);
        holder.add = (TextView) rowView.findViewById(R.id.add);
        holder.minus = (TextView) rowView.findViewById(R.id.minus);

        holder.name.setText(name.get(position));
        holder.category.setText(category.get(position));

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

    public class ViewHolder {
        TextView name;
        EditText quantity;
        TextView category;
        TextView add;
        TextView minus;
    }



//    public void addValues()
//    {
//        name.add("PL123");
//        name.add("PL124");
//        name.add("PL125");
//        name.add("PL126");
//        name.add("PL127");
//
//        category.add();
//
//
//    }
//


}
