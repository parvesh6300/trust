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
 * Created by Rohit on 30/11/16.
 */
public class ServiceAdapter extends BaseAdapter {

    Context context;

    public static LayoutInflater inflater;

    ArrayList<String> al_service_name;
    ArrayList<String> al_date;
    ArrayList<String> al_service_price;

    Global global;

    public ServiceAdapter(Context mcontext)
    {
        this.context= mcontext;

        al_service_name = new ArrayList<>();
        al_date= new ArrayList<>();
        al_service_price = new ArrayList<>();

        inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        global = (Global) context.getApplicationContext();

        for (HashMap<String,String> hashmap : global.getAl_view_service_details() )
        {
            al_service_name.add(hashmap.get(GlobalConstants.ORDER_ITEM_NAME));
            al_date.add(hashmap.get(GlobalConstants.ORDER_CREATED));
            al_service_price.add(hashmap.get(GlobalConstants.ORDER_ITEM_PRICE));
        }
    }

    public class ViewHolder{

        TextView tv_date,tv_month,tv_year, tv_service_name;
    }


    @Override
    public View getView(int pos, View convertview, ViewGroup viewGroup) {

        ViewHolder holder= new ViewHolder();

        convertview= inflater.inflate(R.layout.service_list,null);

        holder.tv_date= (TextView)convertview.findViewById(R.id.tv_date);
        holder.tv_month= (TextView)convertview.findViewById(R.id.tv_month);
        holder.tv_year= (TextView)convertview.findViewById(R.id.tv_year);
        holder.tv_service_name = (TextView)convertview.findViewById(R.id.tv_service_name);

        holder.tv_service_name.setText(al_service_name.get(pos));

        String[] date_time = al_date.get(pos).split("\\s+");

        String[] date = date_time[0].split("-");

        holder.tv_date.setText(date[2]);
        holder.tv_month.setText(date[1]+" ");
        holder.tv_year.setText("'"+date[0]);

        return convertview;
    }

    @Override
    public int getCount() {
        return al_service_name.size();
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
