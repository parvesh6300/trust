package dcube.com.trust.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;

import WebServicesHandler.FormatTime;
import WebServicesHandler.GlobalConstants;
import dcube.com.trust.R;

/**
 * Created by Sagar on 13/10/16.
 */
public class CalendarListAdapter extends BaseAdapter {

    public Context context;
    ArrayList<String> al_name = new ArrayList<>();
    ArrayList<String> al_time = new ArrayList<>();

    public static LayoutInflater inflater;
    Global global;

    String formatted_time;

    FormatTime ft;

    public CalendarListAdapter(Context context) throws ParseException {
        this.context= context;

        ft = new FormatTime(context);

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        global = (Global) context.getApplicationContext() ;

        for (HashMap<String,String> hashmap : global.getAl_apmt_details())
        {
            al_name.add(hashmap.get(GlobalConstants.APMT_CLIENT_ID));

            formatted_time = ft.FormatTime(hashmap.get(GlobalConstants.APMT_TIME));

            al_time.add(formatted_time);
        }


    }

    @Override
    public int getCount() {
        return al_name.size();
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

        TextView tv_name,tv_time;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {

        ViewHolder holder= new ViewHolder();

        convertView = inflater.inflate(R.layout.calendar_list,viewGroup,false);

        holder.tv_name= (TextView)convertView.findViewById(R.id.tv_name);
        holder.tv_time= (TextView)convertView.findViewById(R.id.tv_time);

        holder.tv_name.setText(al_name.get(i));
        holder.tv_time.setText(al_time.get(i));

        return convertView;
    }


}
