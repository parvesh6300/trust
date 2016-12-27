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
 * Created by Sagar on 18/10/16.
 */
public class ExpenseAdapter extends BaseAdapter {

    Context context;

    public static LayoutInflater inflater;

    ArrayList<String> al_expense_detail= new ArrayList<>();
    ArrayList<String> al_expense_amount= new ArrayList<>();
    ArrayList<String> al_date= new ArrayList<>();
    ArrayList<String> al_expense_id= new ArrayList<>();



    Calendar cl= Calendar.getInstance();
    Global global;


    public ExpenseAdapter(Context mcontext)
    {
        this.context= mcontext;
        this.al_date= new ArrayList<>();
        this.al_expense_detail= new ArrayList<>();
        this.al_expense_amount= new ArrayList<>();

        global = (Global) context.getApplicationContext();

        inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        for (HashMap<String,String> hashmap : global.getAl_expense_details() )
        {
            al_expense_amount.add(hashmap.get(GlobalConstants.EXP_AMOUNT));
            al_expense_detail.add(hashmap.get(GlobalConstants.EXP_REASON));
            al_date.add(hashmap.get(GlobalConstants.EXP_DATE));
            al_expense_id.add(hashmap.get(GlobalConstants.EXP_ID));
        }

    }

    public class ViewHolder{

        TextView tv_date,tv_month,tv_year,tv_detail,tv_amount;
    }

    @Override
    public View getView(int pos, View convertview, ViewGroup viewGroup) {

        ViewHolder holder= new ViewHolder();

        convertview= inflater.inflate(R.layout.expenselist,null);

        holder.tv_date= (TextView)convertview.findViewById(R.id.tv_date);
        holder.tv_month= (TextView)convertview.findViewById(R.id.tv_month);
        holder.tv_year= (TextView)convertview.findViewById(R.id.tv_year);
        holder.tv_detail= (TextView)convertview.findViewById(R.id.tv_detail);
        holder.tv_amount= (TextView)convertview.findViewById(R.id.tv_amount);

        String[] date_time = al_date.get(pos).split("\\s+");

        String[] date = date_time[0].split("-");


        holder.tv_month.setText(date[1]+" ");
        holder.tv_year.setText("'"+date[0]);
        holder.tv_date.setText(date[2]);

        holder.tv_detail.setText(al_expense_detail.get(pos));
        holder.tv_amount.setText(al_expense_amount.get(pos)+" Tsh");

        return convertview;
    }

    @Override
    public int getCount() {
        return global.getAl_expense_details().size();
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
