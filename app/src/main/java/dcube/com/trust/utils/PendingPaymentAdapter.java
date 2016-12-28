package dcube.com.trust.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import WebServicesHandler.GlobalConstants;
import dcube.com.trust.R;

/**
 * Created by Sagar on 20/10/16.
 */
public class PendingPaymentAdapter extends BaseAdapter {


    Context context;

    public static LayoutInflater inflater;

    ArrayList<String> al_product_name= new ArrayList<>();
    ArrayList<String> al_product_cost= new ArrayList<>();
    ArrayList<String> al_date= new ArrayList<>();

    Global global;


    public PendingPaymentAdapter(Context mcontext, ArrayList<String> date,ArrayList<String> product_name,ArrayList<String> product_cost)
    {
        this.context= mcontext;
        this.al_date=date;
        this.al_product_name= product_name;
        this.al_product_cost= product_cost;

        global = (Global) context.getApplicationContext();

        inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        for (HashMap<String,String> hashmap : global.getAl_pend_pmt_details())
        {

            al_product_name.add(hashmap.get(GlobalConstants.PEND_ITEM_NAME));
            al_product_cost.add(hashmap.get(GlobalConstants.PEND_ITEM_PRICE));
            al_date.add(hashmap.get(GlobalConstants.PEND_DATE));

        }


    }

    public class ViewHolder{

        TextView tv_date,tv_month,tv_year,tv_product_name,tv_cost;
    }


    @Override
    public View getView(int pos, View convertview, ViewGroup viewGroup) {



        ViewHolder holder= new ViewHolder();

        convertview= inflater.inflate(R.layout.pending_payment_list,null);

        holder.tv_date= (TextView)convertview.findViewById(R.id.tv_date);
        holder.tv_month= (TextView)convertview.findViewById(R.id.tv_month);
        holder.tv_year= (TextView)convertview.findViewById(R.id.tv_year);
        holder.tv_product_name= (TextView)convertview.findViewById(R.id.tv_product_name);
        holder.tv_cost= (TextView)convertview.findViewById(R.id.tv_cost);

        String[] date_time = al_date.get(pos).split("\\s+");

        String[] date = date_time[0].split("-");

        holder.tv_date.setText(date[2]);
        holder.tv_month.setText(date[1]+" ");
        holder.tv_year.setText("'"+date[0]);

        holder.tv_product_name.setText(al_product_name.get(pos));
        holder.tv_cost.setText(al_product_cost.get(pos)+" Tsh");

        return convertview;
    }


    @Override
    public int getCount() {
        return al_product_cost.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }



}
