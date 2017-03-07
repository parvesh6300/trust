package dcube.com.trust.utils;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import WebServicesHandler.GlobalConstants;
import dcube.com.trust.R;
import dcube.com.trust.UpdatePlanActivity;

/**
 * Created by Rohit on 20/10/16.
 */
public class PlanAdapter extends BaseAdapter {


    Context context;

    public static LayoutInflater inflater;

    ArrayList<String> al_plan_name;
    ArrayList<String> al_date;
    ArrayList<String> al_plan_price;
    ArrayList<String> al_plan_id;

    Global global;

    public PlanAdapter(Context mcontext)
    {
        this.context= mcontext;

        al_plan_name = new ArrayList<>();
        al_date= new ArrayList<>();
        al_plan_price= new ArrayList<>();
        al_plan_id = new ArrayList<>();

        inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        global = (Global) context.getApplicationContext();

        for (HashMap<String,String> hashmap : global.getAl_view_plan_details() )
        {
            al_plan_name.add(hashmap.get(GlobalConstants.ORDER_ITEM_NAME));
            al_date.add(hashmap.get(GlobalConstants.PLAN_CREATED));
            al_plan_id.add(hashmap.get(GlobalConstants.PLAN_ID));

        }

    }

    public class ViewHolder{

        TextView tv_date,tv_month,tv_year, tv_plan_name;
    }



    @Override
    public int getCount() {
        return al_plan_name.size();
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
    public View getView(final int pos, View convertview, ViewGroup viewGroup) {

        ViewHolder holder= new ViewHolder();

        convertview= inflater.inflate(R.layout.planlist,null);

        holder.tv_date= (TextView)convertview.findViewById(R.id.tv_date);
        holder.tv_month= (TextView)convertview.findViewById(R.id.tv_month);
        holder.tv_year= (TextView)convertview.findViewById(R.id.tv_year);
        holder.tv_plan_name = (TextView)convertview.findViewById(R.id.tv_plan_name);


        holder.tv_plan_name.setText(al_plan_name.get(pos));

        String[] date_time = al_date.get(pos).split("\\s+");

        String[] date = date_time[0].split("-");

        holder.tv_date.setText(date[2]);
        holder.tv_month.setText(date[1]+" ");
        holder.tv_year.setText("'"+date[0]);

        convertview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                global.setStr_selected_plan_id(al_plan_id.get(pos));

                context.startActivity(new Intent(context, UpdatePlanActivity.class));
            }
        });

        return convertview;
    }

}
