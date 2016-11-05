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
public class PendingPaymentAdapter extends BaseAdapter {


    Context context;

    public static LayoutInflater inflater;

    ArrayList<String> al_product_name= new ArrayList<>();
    ArrayList<String> al_product_cost= new ArrayList<>();
    ArrayList<String> al_date= new ArrayList<>();


    public PendingPaymentAdapter(Context mcontext, ArrayList<String> date,ArrayList<String> product_name,ArrayList<String> product_cost)
    {
        this.context= mcontext;
        this.al_date=date;
        this.al_product_name= product_name;
        this.al_product_cost= product_cost;

        inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        addValues();
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

        holder.tv_date.setText(al_date.get(pos));
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


    public void addValues()
    {
        al_date.add("05");
        al_date.add("10");
        al_date.add("14");
        al_date.add("16");
        al_date.add("18");

        al_product_name.add("Bull Condom");
        al_product_name.add("Fiesta");
        al_product_name.add("Implanon Implants");
        al_product_name.add("Safe Load");
        al_product_name.add("Jadelle");

        al_product_cost.add("4000");
        al_product_cost.add("2000");
        al_product_cost.add("1000");
        al_product_cost.add("5000");
        al_product_cost.add("8000");

    }






}
