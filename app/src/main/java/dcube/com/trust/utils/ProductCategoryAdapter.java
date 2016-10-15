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

public class ProductCategoryAdapter extends BaseAdapter {

    Context context;
    Global global;

    ArrayList<String> name = new ArrayList<>();
    ArrayList<String> branch = new ArrayList<>();
    ArrayList<String> contact = new ArrayList<>();

    private static LayoutInflater inflater = null;

    public ProductCategoryAdapter(Activity activity) {

        context = activity.getApplicationContext();

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        name.add("The contraceptive pill");
        name.add("The implant");
        name.add("The IUD");
        name.add("The emergency pill");
        name.add("Condoms");
        name.add("Injectables");
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
    }

    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder = new Holder();

        final View rowView;
        rowView = inflater.inflate(R.layout.product_category_item, parent , false);

        holder.name = (TextView) rowView.findViewById(R.id.name);
        holder.name.setText(name.get(position));

        return rowView;
    }
}