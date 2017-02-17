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
 * Created by Sagar on 15/02/17.
 */
public class PettyCashAdapter extends BaseAdapter {

    public static LayoutInflater inflater;
    Context context;
    ArrayList<String> al_petty_detail = new ArrayList<>();
    ArrayList<String> al_petty_amount = new ArrayList<>();
    ArrayList<String> al_date = new ArrayList<>();

    Global global;


    public PettyCashAdapter(Context mcontext) {
        this.context = mcontext;
        al_date = new ArrayList<>();
        al_petty_detail = new ArrayList<>();
        al_petty_amount = new ArrayList<>();

        global = (Global) context.getApplicationContext();

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        for (HashMap<String, String> hashmap : global.getAl_petty_details())
        {
            al_date.add(hashmap.get(GlobalConstants.PT_CREATED));
            al_petty_amount.add(hashmap.get(GlobalConstants.PT_CASH));
            al_petty_detail.add(hashmap.get(GlobalConstants.PT_REASON));
        }
    }


    @Override
    public int getCount() {
        return al_petty_detail.size();
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
    public View getView(int pos, View convertView, ViewGroup viewGroup) {

        View vi = convertView;
        ViewHolder holder;

        if (convertView == null) {

            /****** Inflate tabitem.xml file for each row ( Defined below ) *******/
            vi = inflater.inflate(R.layout.petty_cash_list, null);

            /****** View Holder Object to contain tabitem.xml file elements ******/

            holder = new ViewHolder();

            holder.tv_date= (TextView)vi.findViewById(R.id.tv_date);
            holder.tv_month= (TextView)vi.findViewById(R.id.tv_month);
            holder.tv_year= (TextView)vi.findViewById(R.id.tv_year);
            holder.tv_detail= (TextView)vi.findViewById(R.id.tv_detail);
            holder.tv_amount= (TextView)vi.findViewById(R.id.tv_amount);


            /************  Set holder with LayoutInflater ************/
            vi.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) vi.getTag();

        }

        String[] date_time = al_date.get(pos).split("\\s+");

        String[] date = date_time[0].split("-");

        holder.tv_date.setText(date[2]);
        holder.tv_month.setText(date[1]+" ");
        holder.tv_year.setText("'"+date[0]);

        holder.tv_detail.setText(al_petty_detail.get(pos));
        holder.tv_amount.setText(al_petty_amount.get(pos)+" TZS");


        return vi;
    }

    public static class ViewHolder {

        public TextView tv_detail;
        public TextView tv_amount;
        public TextView tv_date,tv_month,tv_year;


    }
}
