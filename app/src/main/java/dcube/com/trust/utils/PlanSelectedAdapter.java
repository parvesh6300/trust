package dcube.com.trust.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import dcube.com.trust.R;

/**
 * Created by Sagar on 24/10/16.
 */
public class PlanSelectedAdapter extends BaseAdapter {

    Context context;

    ArrayList<String> plan_name = new ArrayList<>();
    ArrayList<String> plan_productCost = new ArrayList<>();
    ArrayList<String> plan_serviceCost = new ArrayList<>();

    private static LayoutInflater inflater;

    public PlanSelectedAdapter(Context ctx,ArrayList<String> name, ArrayList<String> productCost,ArrayList<String> serviceCost)
    {
        this.context = ctx;
//        this.name = name;
//        this.productCost = productCost;
//        this.serviceCost = serviceCost;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        plan_name.add("OCP's(3 Cycles) + EC(2 Packs)");
        plan_productCost.add("15,000");
        plan_serviceCost.add("Free");

        plan_name.add("Silverline + Implants");
        plan_productCost.add("5,000");
        plan_serviceCost.add("20,000");
    }



    public class ViewHolder
    {
        TextView tv_name,tv_product_cost,tv_service_cost;
    }



    @Override
    public int getCount() {
        return plan_name.size();
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
    public View getView(int position, View view, ViewGroup viewGroup) {


        // TODO Auto-generated method stub
        final ViewHolder holder = new ViewHolder();

        final View rowView;
        rowView = inflater.inflate(R.layout.planselecteditem, null);

        holder.tv_name = (TextView) rowView.findViewById(R.id.tv_name);
        holder.tv_product_cost = (TextView) rowView.findViewById(R.id.tv_product_cost);
        holder.tv_service_cost = (TextView) rowView.findViewById(R.id.tv_service_cost);


        holder.tv_name.setText(plan_name.get(position));
        holder.tv_product_cost.setText(plan_productCost.get(position));
        holder.tv_service_cost.setText(plan_serviceCost.get(position));

        return rowView;

    }
}
