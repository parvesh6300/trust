package dcube.com.trust.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import WebServicesHandler.GlobalConstants;
import dcube.com.trust.R;

/**
 * Created by Rohit on 09/02/17.
 */
public class ClientHistoryAdapter extends BaseAdapter {


    Context context;

    public static LayoutInflater inflater;

    ArrayList<String> al_item_name= new ArrayList<>();
    ArrayList<String> al_item_visits= new ArrayList<>();
    ArrayList<String> al_date= new ArrayList<>();
    ArrayList<String> al_item_id= new ArrayList<>();
    ArrayList<String> al_item_type= new ArrayList<>();


    Calendar cl= Calendar.getInstance();
    Global global;


    public ClientHistoryAdapter(Context mcontext)
    {
        this.context= mcontext;

        this.al_date= new ArrayList<>();
        this.al_item_name= new ArrayList<>();
        this.al_item_visits= new ArrayList<>();
        al_item_type = new ArrayList<>();

        global = (Global) context.getApplicationContext();

        inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        for (HashMap<String,String> hashmap : global.getAl_client_sales_history())
        {

            al_item_name.add(hashmap.get(GlobalConstants.CLIENT_HIS_ITEM_NAME));
            al_date.add(hashmap.get(GlobalConstants.CLIENT_HIS_DATE));
            al_item_id.add(hashmap.get(GlobalConstants.CLIENT_HIS_ITEM_ID));
            al_item_visits.add(hashmap.get(GlobalConstants.CLIENT_HIS_AMOUNT));
            al_item_type.add(hashmap.get(GlobalConstants.CLIENT_HIS_ITEM_TYPE));
        }


    }


    @Override
    public int getCount() {
        return al_item_name.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }


    public class ViewHolder{

        TextView tv_date,tv_month,tv_year,tv_item_name,tv_amount,tv_item_type;
    }

    @Override
    public View getView(int pos, View convertview, ViewGroup viewGroup) {

        ViewHolder holder= new ViewHolder();

        convertview= inflater.inflate(R.layout.client_history_list,null);

        holder.tv_date= (TextView)convertview.findViewById(R.id.tv_date);
        holder.tv_month= (TextView)convertview.findViewById(R.id.tv_month);
        holder.tv_year= (TextView)convertview.findViewById(R.id.tv_year);
        holder.tv_item_name= (TextView)convertview.findViewById(R.id.tv_item_name);
        holder.tv_amount= (TextView)convertview.findViewById(R.id.tv_amount);
        holder.tv_item_type = (TextView) convertview.findViewById(R.id.tv_item_type);


        String[] date_time = al_date.get(pos).split("\\s+");

        String[] date = date_time[0].split("-");

        holder.tv_date.setText(date[2]);
        holder.tv_month.setText(date[1]+" ");
        holder.tv_year.setText("'"+date[0]);

        holder.tv_item_type.setText(al_item_type.get(pos));
        holder.tv_item_name.setText(al_item_name.get(pos));
        holder.tv_amount.setText(al_item_visits.get(pos));
//
//        if (al_item_type.get(pos).equalsIgnoreCase("product"))
//        {
//            holder.tv_item_name.setText(al_item_name.get(pos)+" ("+al_item_visits.get(pos)+")");
//            holder.tv_amount.setText("");
//        }
//        else
//        {
//            holder.tv_item_name.setText(al_item_name.get(pos));
//            holder.tv_amount.setText(al_item_visits.get(pos));
//        }



        return convertview;
    }






}
