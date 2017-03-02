package dcube.com.trust.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import WebServicesHandler.GlobalConstants;
import dcube.com.trust.R;

/**
 * Created by Rohit on 14/10/16.
 */
public class SoldProductAdapter extends BaseAdapter {


    Context mcontext;

    Global global;

    ArrayList<String> al_quantity= new ArrayList<>();
    ArrayList<String> al_product_name= new ArrayList<>();
    ArrayList<String> al_category= new ArrayList<>();
    ArrayList<String> al_product_id= new ArrayList<>();

    ViewHolder holder;

    public LayoutInflater inflater;


    public SoldProductAdapter(Context context,String search)
    {

        this.mcontext=context;
        global = (Global) context.getApplicationContext();

        inflater= (LayoutInflater) mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        try
        {

            if (global.getAl_product_sold().size() > 0)
            {
                for (HashMap<String,String> hashmap : global.getAl_product_sold())
                {
                    if(search.equalsIgnoreCase(""))
                    {
                        al_quantity.add(hashmap.get(GlobalConstants.PRODUCT_QUANTITY));
                        al_product_name.add(hashmap.get(GlobalConstants.PRODUCT_NAME));
                        al_product_id.add(hashmap.get(GlobalConstants.PRODUCT_ID));
                        al_category.add(hashmap.get(GlobalConstants.PRODUCT_CATEGORY));

                    }
                    else
                    {
                        if(hashmap.get(GlobalConstants.PRODUCT_NAME).toLowerCase().contains(search.toLowerCase()) ||
                                hashmap.get(GlobalConstants.PRODUCT_CATEGORY).toLowerCase().contains(search.toLowerCase()))
                        {
                            al_quantity.add(hashmap.get(GlobalConstants.PRODUCT_QUANTITY));
                            al_product_name.add(hashmap.get(GlobalConstants.PRODUCT_NAME));
                            al_product_id.add(hashmap.get(GlobalConstants.PRODUCT_ID));
                            al_category.add(hashmap.get(GlobalConstants.PRODUCT_CATEGORY));

                        }
                    }
                }

            }

        }
        catch (Exception e)
        {

        }


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
