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

public class PlanListAdapter extends BaseAdapter {

    Context context;
    Global global;
    static int quantity;

    ArrayList<String> name = new ArrayList<>();
    ArrayList<String> productCost = new ArrayList<>();
    ArrayList<String> serviceCost = new ArrayList<>();

    private static LayoutInflater inflater = null;

    public PlanListAdapter(Activity activity,ArrayList<String> name,ArrayList<String> productCost,ArrayList<String> serviceCost) {

        context = activity.getApplicationContext();
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        this.name = name;
        this.productCost = productCost;
        this.serviceCost = serviceCost;
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

    public class Holder {
        TextView name;
        TextView product_cost;
        TextView service_cost;
    }

    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {

        // TODO Auto-generated method stub
        final Holder holder = new Holder();

        final View rowView;
        rowView = inflater.inflate(R.layout.buy_plan_item, parent , false);

        holder.name = (TextView) rowView.findViewById(R.id.name);
        holder.product_cost = (TextView) rowView.findViewById(R.id.product_cost);
        holder.service_cost = (TextView) rowView.findViewById(R.id.service_cost);

        holder.name.setText(name.get(position));
        holder.service_cost.setText(serviceCost.get(position));
        holder.service_cost.setText(serviceCost.get(position));

        return rowView;
    }
}