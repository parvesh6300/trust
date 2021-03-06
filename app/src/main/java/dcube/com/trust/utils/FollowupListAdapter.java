package dcube.com.trust.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import WebServicesHandler.FormatTime;
import WebServicesHandler.GlobalConstants;
import dcube.com.trust.R;

/**
 * Created by Rohit on 18/10/16.
 */

public class FollowupListAdapter extends BaseAdapter {

    Context context;

    public static LayoutInflater inflater;

    ArrayList<String> remark;
    ArrayList<String> timing;
    ArrayList<String> al_service_name;
    ArrayList<String> al_formatted_time;

    Global global;

    String formatted_time;

    FormatTime ft;

    Calendar cl= Calendar.getInstance();

    public FollowupListAdapter(Context mcontext)
    {
        this.context= mcontext;

        ft = new FormatTime(context);

        inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        global = (Global) context.getApplicationContext();

        remark = new ArrayList<>();
        timing = new ArrayList<>();
        al_service_name = new ArrayList<>();
        al_formatted_time = new ArrayList<>();

        for (HashMap<String,String> hashmap : global.getAl_apmt_details())
        {
            remark.add(hashmap.get(GlobalConstants.APMT_REMARK));
            timing.add(hashmap.get(GlobalConstants.APMT_TIME));
            al_service_name.add(hashmap.get(GlobalConstants.APMT_SERVICE_NAME));

            try {
                formatted_time = ft.FormatTime(hashmap.get(GlobalConstants.APMT_TIME));
                al_formatted_time.add(formatted_time);

            } catch (ParseException e) {
                e.printStackTrace();
            }


        }


    }

    public class ViewHolder{

        TextView tv_date,tv_month,tv_year,tv_notes,tv_timing;
    }

    @Override
    public View getView(int pos, View convertview, ViewGroup viewGroup) {

        ViewHolder holder= new ViewHolder();

        convertview= inflater.inflate(R.layout.followuplist,null);

        holder.tv_date= (TextView)convertview.findViewById(R.id.tv_date);
        holder.tv_month= (TextView)convertview.findViewById(R.id.tv_month);
        holder.tv_year= (TextView)convertview.findViewById(R.id.tv_year);
        holder.tv_notes= (TextView)convertview.findViewById(R.id.notes);
        holder.tv_timing= (TextView)convertview.findViewById(R.id.timings);

        String[] date_time = timing.get(pos).split("\\s+");

        String[] date = date_time[0].split("-");

        holder.tv_date.setText(date[2]);
        holder.tv_month.setText(date[1]+" ");
        holder.tv_year.setText("'"+date[0]);

        holder.tv_notes.setText(al_service_name.get(pos)+" - "+remark.get(pos));
        holder.tv_timing.setText(al_formatted_time.get(pos));  //date_time[1]

        return convertview;
    }

    @Override
    public int getCount() {
        return timing.size();
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
