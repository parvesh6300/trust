package dcube.com.trust.utils;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import dcube.com.trust.R;

public class ProductSelectedAdapter extends BaseAdapter {

    Context context;
    Global global;

    ArrayList<String> name = new ArrayList<>();
    ArrayList<String> category = new ArrayList<>();
    ArrayList<String> quantity = new ArrayList<>();
    ArrayList<String> price = new ArrayList<>();

    private static LayoutInflater inflater = null;

    public ProductSelectedAdapter(Activity activity) {

        context = activity.getApplicationContext();
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        name.add("Fiesta");
        category.add("Condom");
        quantity.add("x 3");
        price.add("1,500");

        name.add("Sleek /safe load");
        category.add("IUD");
        quantity.add("x 1");
        price.add("5,000");

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
        TextView quantity;
        TextView category;
        TextView price;
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

        holder.name.setText(name.get(position));
        holder.category.setText(category.get(position));
        holder.quantity.setText(quantity.get(position));
        holder.price.setText(price.get(position));

        return rowView;
    }
}