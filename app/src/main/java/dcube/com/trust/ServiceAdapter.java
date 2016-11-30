package dcube.com.trust;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Sagar on 30/11/16.
 */
public class ServiceAdapter extends BaseAdapter {

    Context context;

    public static LayoutInflater inflater;

    ArrayList<String> al_service_name = new ArrayList<>();
    ArrayList<String> al_date= new ArrayList<>();

    public ServiceAdapter(Context mcontext, ArrayList<String> date,ArrayList<String> product_name)
    {
        this.context= mcontext;
        this.al_date=date;
        this.al_service_name = product_name;

        inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        addValues();
    }

    public class ViewHolder{

        TextView tv_date,tv_month,tv_year, tv_plan_name;
    }


    @Override
    public View getView(int pos, View convertview, ViewGroup viewGroup) {

        ViewHolder holder= new ViewHolder();

        convertview= inflater.inflate(R.layout.service_list,null);

        holder.tv_date= (TextView)convertview.findViewById(R.id.tv_date);
        holder.tv_month= (TextView)convertview.findViewById(R.id.tv_month);
        holder.tv_year= (TextView)convertview.findViewById(R.id.tv_year);
        holder.tv_plan_name = (TextView)convertview.findViewById(R.id.tv_plan_name);

        holder.tv_date.setText(al_date.get(pos));
        holder.tv_plan_name.setText(al_service_name.get(pos));

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


    public void addValues()
    {
        al_date.add("05");
        al_date.add("10");
        al_date.add("14");
        al_date.add("16");
        al_date.add("18");

        al_service_name.add("Provider Counsultation");
        al_service_name.add("CPAC + IUCD");
        al_service_name.add("Gynaecologist");
        al_service_name.add("Pap Smear");
        al_service_name.add("Ultrasound");
    }
}
