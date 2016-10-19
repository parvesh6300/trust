package dcube.com.trust.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import dcube.com.trust.R;

public class NurseAdapter extends BaseAdapter {

    private Context context;

    public Integer[] mThumbIds = {
            R.drawable.addnewclient,
            R.drawable.searchclient,
            R.drawable.calender,
            R.drawable.products,
    };

    private static LayoutInflater inflater = null;

    public NurseAdapter(Context context) {

        this.context = context;

        context = context.getApplicationContext();
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mThumbIds.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public class Holder
    {
        ImageView image;
        TextView notify;

    }

    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder = new Holder();

        final View rowView;
        rowView = inflater.inflate(R.layout.nurse_grid_item, null);

        holder.image = (ImageView) rowView.findViewById(R.id.gridimage);
        holder.notify = (TextView) rowView.findViewById(R.id.notify);

        holder.image.setImageResource(mThumbIds[position]);

        /*rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                activity.startActivity(new Intent(activity, SalonActivity.class));
                activity.overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
            }
        });*/

        return rowView;
    }
}
