package dcube.com.trust.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import WebServicesHandler.GlobalConstants;
import dcube.com.trust.R;

public class FinanceAdapter extends BaseAdapter {

    private Context context;

    Global global;

    public Integer[] mThumbIds = {

            R.drawable.moneybanked,
            R.drawable.depositmoney,
            R.drawable.stockmanagement,
            R.drawable.withdraw_money,
            R.drawable.pettycash,
            R.drawable.operatingexpenses,
            R.drawable.account_history,
            R.drawable.cash_history_logo,
            R.drawable.expense_history,
            R.drawable.revenue,  //cashin  withdraw  w
            R.drawable.salesanalytics   //cash_history
    };



//    Order of menu - bank money, operating expense,
//    stock management ,deposit money, account history, expense history, revenue, sales analytics.

    private static LayoutInflater inflater = null;

    public FinanceAdapter(Context context) {

        this.context = context;

        global = (Global) context.getApplicationContext();

//        context = context.getApplicationContext();
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
        rowView = inflater.inflate(R.layout.finance_grid_item, null);

        holder.image = (ImageView) rowView.findViewById(R.id.gridimage);
        holder.notify = (TextView) rowView.findViewById(R.id.notify);

        holder.image.setImageResource(mThumbIds[position]);

        if(position==2)
        {

            for (int i=0 ; i < global.getAl_stock_product().size() ; i++ )
            {
                int stock = Integer.parseInt(global.getAl_stock_product().get(i).get(GlobalConstants.PRODUCT_IN_STOCK));

                if (stock < 21)
                {
                    holder.notify.setVisibility(View.VISIBLE);
                    holder.notify.setText("!");
                }

            }

        }


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