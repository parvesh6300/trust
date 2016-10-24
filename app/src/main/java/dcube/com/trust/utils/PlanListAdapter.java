package dcube.com.trust.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import dcube.com.trust.R;

public class PlanListAdapter extends BaseAdapter {

    Context context;
    Global global;
    static int quantity;

    int selectedIndex = -1;

    boolean isSelected = false;

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
        ImageView iv;
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
        holder.iv = (ImageView) rowView.findViewById(R.id.iv);

        holder.name.setText(name.get(position));
        holder.product_cost.setText(productCost.get(position));
        holder.service_cost.setText(serviceCost.get(position));



        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(holder.iv.getVisibility() == View.INVISIBLE)
                {
                    holder.iv.setVisibility(View.VISIBLE);
                    rowView.setBackgroundColor(Color.parseColor("#603370"));    // Purple
                    holder.name.setTextColor(Color.parseColor("#FFFFFF"));      // white
                    holder.product_cost.setTextColor(Color.parseColor("#FFFFFF"));      // white
                    holder.service_cost.setTextColor(Color.parseColor("#FFFFFF"));      // white
                }
                else
                {
                    holder.iv.setVisibility(View.VISIBLE);
                    rowView.setBackgroundColor(Color.parseColor("#FFFFFF"));    // white
                    holder.name.setTextColor(Color.parseColor("#45265f"));      // text color
                    holder.product_cost.setTextColor(Color.parseColor("#45265f"));      // text color
                    holder.service_cost.setTextColor(Color.parseColor("#45265f"));      // text color
                }


            }
        });


        return rowView;
    }

    public void setSelectedIndex(int index){
        selectedIndex = index;
    }

}