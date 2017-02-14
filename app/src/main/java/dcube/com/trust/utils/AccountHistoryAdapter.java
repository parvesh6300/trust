package dcube.com.trust.utils;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import WebServicesHandler.GlobalConstants;
import dcube.com.trust.R;
import dcube.com.trust.utils.Global;

/**
 * Created by Sagar on 24/01/17.
 */
public class AccountHistoryAdapter extends BaseAdapter {


    Context context;

    public static LayoutInflater inflater;

    ArrayList<String> al_money_detail;
    ArrayList<String> al_money_amount;
    ArrayList<String> al_date;

    Global global;

    public AccountHistoryAdapter(Context mcontext)
    {

        this.context = mcontext;

        global = (Global) context.getApplicationContext();

        al_money_detail= new ArrayList<>();
        al_money_amount= new ArrayList<>();
        al_date= new ArrayList<>();

        inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // addValues();

        for (HashMap<String, String> hashmap : global.getAl_money_bank_history())
        {
            al_money_detail.add(hashmap.get(GlobalConstants.MONEY_BANKED_REASON));
            al_date.add(hashmap.get(GlobalConstants.MONEY_BANKED_DATE));
            al_money_amount.add(hashmap.get(GlobalConstants.MONEY_BANKED_HIS_AMOUNT));

        }



    }


    public class ViewHolder{

        TextView tv_date,tv_month,tv_year,tv_detail,tv_amount;
    }


    @Override
    public View getView(int pos, View convertview, ViewGroup viewGroup) {

        ViewHolder holder= new ViewHolder();

        convertview= inflater.inflate(R.layout.account_history_list,null);

        holder.tv_date= (TextView)convertview.findViewById(R.id.tv_date);
        holder.tv_month= (TextView)convertview.findViewById(R.id.tv_month);
        holder.tv_year= (TextView)convertview.findViewById(R.id.tv_year);
        holder.tv_detail= (TextView)convertview.findViewById(R.id.tv_detail);
        holder.tv_amount= (TextView)convertview.findViewById(R.id.tv_amount);

        // "date": "2017-01-19",

        String[] date = al_date.get(pos).split("-");

        holder.tv_date.setText(date[2]);
        holder.tv_month.setText(date[1]+" '");
        holder.tv_year.setText(date[0]);

        holder.tv_detail.setText(al_money_detail.get(pos));
        holder.tv_amount.setText(al_money_amount.get(pos));


        if (al_money_detail.get(pos).equalsIgnoreCase("moneybanked") || al_money_detail.get(pos).equalsIgnoreCase("expense")  )
        {
            holder.tv_amount.setTextColor(Color.parseColor("#E74C3C")); // RED
        }
        else
        {
            holder.tv_amount.setTextColor(Color.parseColor("#FF368C28"));  // Green
        }


        return convertview;

    }


    @Override
    public int getCount() {
        return al_money_detail.size();
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