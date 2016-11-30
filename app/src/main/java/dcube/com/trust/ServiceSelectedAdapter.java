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
public class ServiceSelectedAdapter extends BaseAdapter {

    Context context;

    ArrayList<String> service_name = new ArrayList<>();
    ArrayList<String> service_Cost = new ArrayList<>();

    private static LayoutInflater inflater;

    public ServiceSelectedAdapter(Context ctx,ArrayList<String> name,ArrayList<String> serviceCost)
    {
        this.context = ctx;
//        this.name = name;
//        this.serviceCost = serviceCost;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        service_name.add("CPAC + IUCD");
        service_Cost.add("150,000");

        service_name.add("Gynaecologist");
        service_Cost.add("50,000");
    }


    public class ViewHolder
    {
        TextView tv_name,tv_service_cost;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        // TODO Auto-generated method stub
        final ViewHolder holder = new ViewHolder();

        final View rowView;
        rowView = inflater.inflate(R.layout.service_selected_item, null);

        holder.tv_name = (TextView) rowView.findViewById(R.id.tv_name);
        holder.tv_service_cost = (TextView) rowView.findViewById(R.id.tv_service_cost);


        holder.tv_name.setText(service_name.get(position));
        holder.tv_service_cost.setText(service_Cost.get(position));

        return rowView;
    }

    @Override
    public int getCount() {
        return service_name.size();
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
