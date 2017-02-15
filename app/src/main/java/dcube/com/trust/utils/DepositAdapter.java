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
 * Created by Sagar on 18/10/16.
 */
public class DepositAdapter extends BaseAdapter {

    Context context;

    public static LayoutInflater inflater;

    ArrayList<String> al_deposit_detail= new ArrayList<>();
    ArrayList<String> al_deposit_amount= new ArrayList<>();
    ArrayList<String> al_date= new ArrayList<>();

    Global global;


    public DepositAdapter(Context mcontext)
    {
        this.context= mcontext;
        al_date = new ArrayList<>();
        al_deposit_detail = new ArrayList<>();
        al_deposit_amount = new ArrayList<>();

        global = (Global) context.getApplicationContext();

        inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        for (HashMap<String,String> hashmap  : global.getAl_deposit_details() )
        {
            al_date.add(hashmap.get(GlobalConstants.DEPOSIT_DATE));
            al_deposit_amount.add(hashmap.get(GlobalConstants.DEPOSIT_AMOUNT));
            al_deposit_detail.add(hashmap.get(GlobalConstants.DEPOSIT_REMARKS));
        }

    }

    public class ViewHolder{

        TextView tv_date,tv_month,tv_year,tv_detail,tv_amount;
    }

    @Override
    public View getView(int pos, View convertview, ViewGroup viewGroup) {

        ViewHolder holder= new ViewHolder();

        convertview= inflater.inflate(R.layout.depositlist,null);

        holder.tv_date= (TextView)convertview.findViewById(R.id.tv_date);
        holder.tv_month= (TextView)convertview.findViewById(R.id.tv_month);
        holder.tv_year= (TextView)convertview.findViewById(R.id.tv_year);
        holder.tv_detail= (TextView)convertview.findViewById(R.id.tv_detail);
        holder.tv_amount= (TextView)convertview.findViewById(R.id.tv_amount);

        String[] date_time = al_date.get(pos).split("\\s+");

        String[] date = date_time[0].split("-");

        holder.tv_date.setText(date[2]);
        holder.tv_month.setText(date[1]+" ");
        holder.tv_year.setText("'"+date[0]);

        holder.tv_detail.setText(al_deposit_detail.get(pos));
        holder.tv_amount.setText(al_deposit_amount.get(pos)+" TZS");

        return convertview;
    }

    @Override
    public int getCount() {
        return al_deposit_amount.size();
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
