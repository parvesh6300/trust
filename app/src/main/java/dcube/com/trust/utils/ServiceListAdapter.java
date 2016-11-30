package dcube.com.trust.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import dcube.com.trust.R;

/**
 * Created by Sagar on 30/11/16.
 */
public class ServiceListAdapter extends BaseAdapter{


    Context context;
    Global global;
    static int quantity;

    int selectedIndex = -1;

    boolean isSelected = false;

    ArrayList<String> name = new ArrayList<>();
    ArrayList<String> serviceCost = new ArrayList<>();

    private static LayoutInflater inflater = null;

    public ServiceListAdapter(Activity activity, ArrayList<String> name, ArrayList<String> serviceCost)
    {
        context = activity.getApplicationContext();
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        this.name = name;
        this.serviceCost = serviceCost;
    }

    public class Holder {
        TextView name;
        TextView service_cost;
        ImageView iv;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        // TODO Auto-generated method stub
        final Holder holder = new Holder();

        final View rowView;
        rowView = inflater.inflate(R.layout.buy_service_item, null);

        holder.name = (TextView) rowView.findViewById(R.id.name);
        holder.service_cost = (TextView) rowView.findViewById(R.id.service_cost);
        holder.iv = (ImageView) rowView.findViewById(R.id.iv);

        holder.name.setText(name.get(position));
        holder.service_cost.setText(serviceCost.get(position));



        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(holder.iv.getVisibility() == View.INVISIBLE)
                {
                    holder.iv.setVisibility(View.VISIBLE);
                    rowView.setBackgroundColor(Color.parseColor("#603370"));    // Purple
                    holder.name.setTextColor(Color.parseColor("#FFFFFF"));      // white
                    holder.service_cost.setTextColor(Color.parseColor("#FFFFFF"));      // white
                }
                else
                {
                    holder.iv.setVisibility(View.VISIBLE);
                    rowView.setBackgroundColor(Color.parseColor("#FFFFFF"));    // white
                    holder.name.setTextColor(Color.parseColor("#45265f"));      // text color
                    holder.service_cost.setTextColor(Color.parseColor("#45265f"));      // text color
                }


            }
        });


        return rowView;
    }


    @Override
    public int getCount() {
        return name.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    public void setSelectedIndex(int index){
        selectedIndex = index;
    }


}
