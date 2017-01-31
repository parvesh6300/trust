package dcube.com.trust.utils;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import WebServicesHandler.GlobalConstants;
import dcube.com.trust.R;

public class PlanListAdapter extends BaseAdapter {

    static int quantity;
    private static LayoutInflater inflater = null;
    Context context;
    Global global;
    int selectedIndex = -1;
    boolean isSelected = false;

    ArrayList<String> al_selected_plan;
    ArrayList<String> al_plan_name;
//    ArrayList<String> al_product_name;
//    ArrayList<String> al_service_name;
    ArrayList<String> al_plan_id;
    ArrayList<String> al_plan_cost;

    public PlanListAdapter(Activity activity, String search) {

        context = activity.getApplicationContext();
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        global = (Global) activity.getApplicationContext();

        al_selected_plan = new ArrayList<>();
        al_plan_name = new ArrayList<>();
        al_plan_id = new ArrayList<>();
        al_plan_cost = new ArrayList<>();


        try {

            for (HashMap<String, String> hashmap : global.getAl_plan_details())
            {

                if (search.equalsIgnoreCase(""))
                {
                    al_plan_name.add(hashmap.get(GlobalConstants.PLAN_NAME));
                    al_plan_cost.add(hashmap.get(GlobalConstants.PLAN_PRICE));
                    al_plan_id.add(hashmap.get(GlobalConstants.PLAN_ID));
                }
                else
                {
//hashmap.get(GlobalConstants.PLAN_ID).toLowerCase().contains(search.toLowerCase()) ||

                    if (hashmap.get(GlobalConstants.PLAN_NAME).toLowerCase().contains(search.toLowerCase()))
                    {
                        al_plan_name.add(hashmap.get(GlobalConstants.PLAN_NAME));
                        al_plan_cost.add(hashmap.get(GlobalConstants.PLAN_PRICE));
                        al_plan_id.add(hashmap.get(GlobalConstants.PLAN_ID));
                    }
                }
            }



        } catch (Exception e) {

        }


    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return al_plan_name.size();
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

    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {

        // TODO Auto-generated method stub
        final Holder holder = new Holder();

        final View rowView;
        rowView = inflater.inflate(R.layout.buy_plan_item, parent, false);

        holder.name = (TextView) rowView.findViewById(R.id.name);
    //    holder.product_cost = (TextView) rowView.findViewById(R.id.product_cost);
        holder.plan_cost = (TextView) rowView.findViewById(R.id.plan_cost);
        holder.iv = (ImageView) rowView.findViewById(R.id.iv);

        holder.name.setText(al_plan_name.get(position));
     //   holder.product_cost.setText(global.getAl_plan_details().get(position).get(GlobalConstants.PLAN_ITEM_NAME));
        holder.plan_cost.setText(al_plan_cost.get(position));

/*
        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (holder.iv.getVisibility() == View.INVISIBLE) {
                    holder.iv.setVisibility(View.VISIBLE);
                    rowView.setBackgroundColor(Color.parseColor("#603370"));    // Purple
                    holder.name.setTextColor(Color.parseColor("#FFFFFF"));      // white
//                    holder.product_cost.setTextColor(Color.parseColor("#FFFFFF"));      // white
                    holder.plan_cost.setTextColor(Color.parseColor("#FFFFFF"));      // white

                    al_selected_plan.add(al_plan_id.get(position));

                } else {
                    holder.iv.setVisibility(View.INVISIBLE);
                    rowView.setBackgroundColor(Color.parseColor("#FFFFFF"));    // white
                    holder.name.setTextColor(Color.parseColor("#45265f"));      // text color
              //      holder.product_cost.setTextColor(Color.parseColor("#45265f"));      // text color
                    holder.plan_cost.setTextColor(Color.parseColor("#45265f"));      // text color

                    al_selected_plan.remove(al_plan_id.get(position));

                }

                global.setAl_selected_plan_id(al_selected_plan);
                Log.e("SelectedPlan", "Size " + al_selected_plan.size());

            }
        });
*/

        return rowView;
    }

    public void setSelectedIndex(int index) {
        selectedIndex = index;
    }

    public class Holder {
        TextView name;
      //  TextView product_cost;
        TextView plan_cost;
        ImageView iv;
    }

}