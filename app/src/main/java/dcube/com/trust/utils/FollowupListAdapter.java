package dcube.com.trust.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

import dcube.com.trust.R;

/**
 * Created by Sagar on 18/10/16.
 */

public class FollowupListAdapter extends BaseAdapter {

    Context context;

    public static LayoutInflater inflater;

    ArrayList<String> notes= new ArrayList<>();
    ArrayList<String> timing = new ArrayList<>();
    ArrayList<String> al_date= new ArrayList<>();

    Calendar cl= Calendar.getInstance();

    public FollowupListAdapter(Context mcontext)
    {
        this.context= mcontext;

        inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        addValues();
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

        holder.tv_date.setText(al_date.get(pos));
        holder.tv_notes.setText(notes.get(pos));
        holder.tv_timing.setText(timing.get(pos));

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


    public void addValues()
    {
        al_date.add("21");
        al_date.add("25");
        al_date.add("30");

        notes.add("Early ultrasound screening");
        notes.add("Triple screen test");
        notes.add("Review of fetal growth");

        timing.add("10:00 am");
        timing.add("01:00 pm");
        timing.add("4:00 pm");
    }
}
