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
 * Created by Sagar on 14/10/16.
 */
public class SoldProductAdapter extends BaseAdapter {


    Context mcontext;

    ArrayList<String> al_quantity= new ArrayList<>();
    ArrayList<String> al_product_name= new ArrayList<>();
    ArrayList<String> al_category= new ArrayList<>();

    ViewHolder holder;

    public LayoutInflater inflater;


    public SoldProductAdapter(Context context, ArrayList<String> productname,ArrayList<String> quantity,ArrayList<String> category)
    {
        this.al_product_name= productname;
        this.al_quantity= quantity;
        this.al_category= category;

        this.mcontext=context;

        inflater= (LayoutInflater) mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    public class ViewHolder{
        TextView tv_quantity,tv_product_name,tv_product_category;
    }

    @Override
    public View getView(int position, View convertview, ViewGroup viewGroup) {


            convertview= inflater.inflate(R.layout.salesanalyticslist, viewGroup, false );
            holder= new ViewHolder();


        holder.tv_product_name= (TextView)convertview.findViewById(R.id.tv_product_name);
        holder.tv_quantity= (TextView)convertview.findViewById(R.id.tv_quantity);
        holder.tv_product_category= (TextView)convertview.findViewById(R.id.tv_product_category);



        holder.tv_product_name.setText(al_product_name.get(position));

        Log.e("Product","Product "+al_product_name.get(position));

        holder.tv_quantity.setText(al_quantity.get(position));

        holder.tv_product_category.setText(al_category.get(position));

        return convertview;
    }


    @Override
    public int getCount() {
        return al_product_name.size();
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
