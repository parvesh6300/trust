package dcube.com.trust.utils;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

import dcube.com.trust.R;

/**
 * Created by Sagar on 17/11/16.
 */
public class MoneyBankedAdapter extends BaseAdapter {

    Context context;

    public static LayoutInflater inflater;

    ArrayList<String> al_money_detail= new ArrayList<>();
    ArrayList<String> al_money_amount= new ArrayList<>();
    ArrayList<String> al_date= new ArrayList<>();


    Calendar cl= Calendar.getInstance();

    public MoneyBankedAdapter(Context mcontext, ArrayList<String> date,
                              ArrayList<String> depositlist,ArrayList<String> depositamount)
    {

        this.context = mcontext;
        this.al_date = date;
        this.al_money_detail = depositlist;
        this.al_money_amount = depositamount;

        inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        addValues();
    }

    public class ViewHolder{

        TextView tv_date,tv_month,tv_year,tv_detail,tv_amount;
    }


    @Override
    public View getView(int pos, View convertview, ViewGroup viewGroup) {
        ViewHolder holder= new ViewHolder();

        convertview= inflater.inflate(R.layout.moneybankedlist,null);

        holder.tv_date= (TextView)convertview.findViewById(R.id.tv_date);
        holder.tv_month= (TextView)convertview.findViewById(R.id.tv_month);
        holder.tv_year= (TextView)convertview.findViewById(R.id.tv_year);
        holder.tv_detail= (TextView)convertview.findViewById(R.id.tv_detail);
        holder.tv_amount= (TextView)convertview.findViewById(R.id.tv_amount);

        holder.tv_date.setText(al_date.get(pos));
        holder.tv_detail.setText(al_money_detail.get(pos));
        holder.tv_amount.setText(al_money_amount.get(pos));


        if (pos == 2 || pos == 4)
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



    public void addValues()
    {
        al_date.add("05");
        al_date.add("10");
        al_date.add("14");
        al_date.add("16");
        al_date.add("18");

        al_money_detail.add("Rohit Ref No 328432164621384 ");
        al_money_detail.add("Anmol Ref No 420350423983992");
        al_money_detail.add("Gagan Ref No 928490270479094");
        al_money_detail.add("Rohit Ref No 290809127340913");
        al_money_detail.add("Anmol REf No 291497321471298");

        al_money_amount.add("4000");
        al_money_amount.add("2000");
        al_money_amount.add("1000");
        al_money_amount.add("5000");
        al_money_amount.add("8000");

    }


}