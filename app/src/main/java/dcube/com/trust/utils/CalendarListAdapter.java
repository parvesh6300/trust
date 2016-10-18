package dcube.com.trust.utils;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import dcube.com.trust.R;

/**
 * Created by Sagar on 13/10/16.
 */
public class CalendarListAdapter extends BaseAdapter {

    public Context context;
    ArrayList<String> al_name = new ArrayList<>();
    ArrayList<String> al_time = new ArrayList<>();
    public static LayoutInflater inflater;


    public CalendarListAdapter(Context context)
    {
        this.context= context;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        Log.e("Adapter","Adapter Starts");

        fillNameInList();
        fillTImeInList();
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

    public void fillNameInList()
    {

        al_name.add("Evangle Omondi");
        al_name.add("Sausanfatimah");
        al_name.add("Anyelwiswe");
        al_name.add("William");
        al_name.add("Robert");
        al_name.add("Mcculum");
        al_name.add("De Villiers");
        al_name.add("Peitersen");

    }

    public void fillTImeInList()
    {
        al_time.add("10:00 am");
        al_time.add("11:00 am");
        al_time.add("12:30 pm");
        al_time.add("1:30 pm");
        al_time.add("2:00 pm");
        al_time.add("3:30 pm");
        al_time.add("4:15 pm");
        al_time.add("5:30 pm");

    }
}
