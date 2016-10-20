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
 * Created by Sagar on 20/10/16.
 */
public class PlanAdapter extends BaseAdapter {


    Context context;

    public static LayoutInflater inflater;

    ArrayList<String> al_plan_name = new ArrayList<>();
    ArrayList<String> al_date= new ArrayList<>();


    public PlanAdapter(Context mcontext, ArrayList<String> date,ArrayList<String> product_name)
    {
        this.context= mcontext;
        this.al_date=date;
        this.al_plan_name = product_name;

         inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        addValues();
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
    public View getView(int pos, View convertview, ViewGroup viewGroup) {
        ViewHolder holder= new ViewHolder();

        convertview= inflater.inflate(R.layout.planlist,null);

        holder.tv_date= (TextView)convertview.findViewById(R.id.tv_date);
        holder.tv_month= (TextView)convertview.findViewById(R.id.tv_month);
        holder.tv_year= (TextView)convertview.findViewById(R.id.tv_year);
        holder.tv_plan_name = (TextView)convertview.findViewById(R.id.tv_plan_name);

        holder.tv_date.setText(al_date.get(pos));
        holder.tv_plan_name.setText(al_plan_name.get(pos));

        return convertview;
    }



    public void addValues()
    {
        al_date.add("05");
        al_date.add("10");
        al_date.add("14");
        al_date.add("16");
        al_date.add("18");

        al_plan_name.add("Product");
        al_plan_name.add("Service");
        al_plan_name.add("Product");
        al_plan_name.add("Service");
        al_plan_name.add("Product");

    }


}
