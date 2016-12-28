package dcube.com.trust.utils;

import android.content.Context;
import android.os.AsyncTask;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import WebServicesHandler.GlobalConstants;
import WebServicesHandler.WebServices;
import dcube.com.trust.R;
import okhttp3.OkHttpClient;

/**
 * Created by Sagar on 28/11/16.
 */
public class CartAdapter extends BaseAdapter {

    Context context;
    public static LayoutInflater inflater;

    Global global;

    ArrayList<String> al_item_name= new ArrayList<>();
    ArrayList<String> al_item_desc= new ArrayList<>();
    ArrayList<String> al_item_price= new ArrayList<>();
    ArrayList<String> al_item_quantity= new ArrayList<>();
    ArrayList<String> al_cart_id= new ArrayList<>();
    ArrayList<String> al_item_type= new ArrayList<>();


    String str_price ="1";
    String str_client_id;
    WebServices ws;
    int position,quantity;

    public CartAdapter(Context mcontext)
    {
        context = mcontext;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        global = (Global) context.getApplicationContext();

        str_client_id = global.getAl_src_client_details().get(global.getSelected_client()).
                get(GlobalConstants.SRC_CLIENT_ID);


        for (HashMap<String,String> hashmap: global.getAl_cart_details())
        {

            al_item_price.add(hashmap.get(GlobalConstants.GET_CART_ITEM_PRICE));
            al_item_quantity.add(hashmap.get(GlobalConstants.GET_CART_AMOUNT));
            al_cart_id.add(hashmap.get(GlobalConstants.GET_CART_ID));
            al_item_type.add(hashmap.get(GlobalConstants.GET_CART_ITEM_TYPE));

            if (hashmap.get(GlobalConstants.GET_CART_ITEM_TYPE).equalsIgnoreCase("plan"))
            {
                al_item_name.add(hashmap.get(GlobalConstants.GET_CART_ITEM_ID));
            }
            else
            {
                al_item_name.add(hashmap.get(GlobalConstants.GET_CART_ITEM_NAME));
            }

            al_item_desc.add(hashmap.get(GlobalConstants.GET_CART_ITEM_DESC));
        }


    }

    public class ViewHolder
    {
        TextView tv_name,tv_desc,tv_price;
        EditText ed_quantity;
        ImageView iv_product_image,iv_cancel;
    }

    @Override
    public View getView(final int pos, View convertView, ViewGroup viewGroup) {

        final ViewHolder holder= new ViewHolder();

        convertView = inflater.inflate(R.layout.cart_list,viewGroup,false);

        holder.tv_name= (TextView)convertView.findViewById(R.id.tv_name);
        holder.tv_desc= (TextView)convertView.findViewById(R.id.tv_desc);
        holder.tv_price=(TextView)convertView.findViewById(R.id.tv_price);

        holder.ed_quantity = (EditText)convertView.findViewById(R.id.ed_quantity);

        holder.iv_product_image = (ImageView) convertView.findViewById(R.id.iv_product_image);
        holder.iv_cancel = (ImageView) convertView.findViewById(R.id.iv_cancel);

        holder.tv_name.setText(al_item_name.get(pos));   //al_product_name.get(pos)
        holder.tv_desc.setText(al_item_type.get(pos));

        str_price = al_item_price.get(pos);

        holder.tv_price.setText(str_price);

        String str_item_type = al_item_type.get(pos);

        if (str_item_type.equalsIgnoreCase("service") || str_item_type.equalsIgnoreCase("plan"))
        {
            holder.ed_quantity.setText("NA");
            holder.ed_quantity.setFocusable(false);
            holder.ed_quantity.setClickable(false);
        }
        else
        {
            holder.ed_quantity.setText(al_item_quantity.get(pos));
        }

        holder.ed_quantity.setInputType(InputType.TYPE_CLASS_NUMBER);

        holder.ed_quantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                try {

                    int qty = Integer.parseInt(editable.toString());
                    int max_stock = Integer.parseInt(global.getAl_cart_details().get(pos).get(GlobalConstants.GET_CART_MAX_STOCK));

                    if (qty > max_stock)
                    {
                        Toast.makeText(context, "Only "+max_stock+" is left", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        String price = String.valueOf(qty* Integer.parseInt(str_price));   //al_product_price.get(pos)

                        holder.tv_price.setText(price);
                        holder.ed_quantity.setText(String.valueOf(qty));

                        quantity = qty;

                        position = pos;

                        new UpdateCartItemAsyncTask().execute();
                    }

                }
                catch (Exception e)
                {

                }

            }
        });


        holder.iv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                position = pos;
                al_item_name.remove(pos);
                al_item_desc.remove(pos);
                al_item_type.remove(pos);
                al_item_price.remove(pos);
                al_item_quantity.remove(pos);
                al_cart_id.remove(pos);

                notifyDataSetChanged();
                new DeleteCartItemAsyncTask().execute();

            }
        });


        return convertView;
    }

    @Override
    public int getCount() {
        return al_item_name.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }


    public class DeleteCartItemAsyncTask extends AsyncTask<String, String, String> {

        OkHttpClient httpClient = new OkHttpClient();
        String resPonse = "";
        String message = "";

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected String doInBackground(String... params) {
            try {

                ArrayList<String> al_str_key = new ArrayList<>();
                ArrayList<String> al_str_value = new ArrayList<>();

                al_str_key.add(GlobalConstants.GET_CART_ID);
                al_str_value.add(global.al_cart_details.get(position).get(GlobalConstants.GET_CART_ID));

                al_str_key.add(GlobalConstants.ACTION);
                al_str_value.add("delete_in_cart");

                message = ws.DeleteItemCartService(context, al_str_key, al_str_value);

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {

            if (message.equalsIgnoreCase("true"))
            {
                new GetCartItemsAsyncTask().execute();}
            else
            {
                Toast.makeText(context, "" + message, Toast.LENGTH_SHORT).show();
            }

        }

    }


    public class GetCartItemsAsyncTask extends AsyncTask<String, String, String> {

        OkHttpClient httpClient = new OkHttpClient();
        String resPonse = "";
        String message = "";


        @Override
        protected void onPreExecute() {


        }

        @Override
        protected String doInBackground(String... params) {
            try {

                ArrayList<String> al_str_key = new ArrayList<>();
                ArrayList<String> al_str_value = new ArrayList<>();

                al_str_key.add(GlobalConstants.CART_CLIENT_ID);
                al_str_value.add(str_client_id);

                al_str_key.add(GlobalConstants.ACTION);
                al_str_value.add("get_cart_by_client_id");

                message = ws.GetCartService(context, al_str_key, al_str_value);

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {

            if (!message.equalsIgnoreCase("true"))
            {
//                Toast.makeText(context, "" + message, Toast.LENGTH_SHORT).show();
            }
            else {
                notifyDataSetChanged();
            }

        }

    }


    public class UpdateCartItemAsyncTask extends AsyncTask<String, String, String> {

        OkHttpClient httpClient = new OkHttpClient();
        String resPonse = "";
        String message = "";

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected String doInBackground(String... params) {
            try {

                ArrayList<String> al_str_key = new ArrayList<>();
                ArrayList<String> al_str_value = new ArrayList<>();

                al_str_key.add(GlobalConstants.GET_CART_ID);
                al_str_value.add(global.al_cart_details.get(position).get(GlobalConstants.GET_CART_ID));

                al_str_key.add(GlobalConstants.CART_AMOUNT);
                al_str_value.add(String.valueOf(quantity));

                al_str_key.add(GlobalConstants.ACTION);
                al_str_value.add("update_in_cart");

                message = ws.UpdateCartItemService(context, al_str_key, al_str_value);

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {

            if (message.equalsIgnoreCase("true"))
            {
                new GetCartItemsAsyncTask().execute();
            }
            else
            {
                Toast.makeText(context, "" + message, Toast.LENGTH_SHORT).show();
            }

        }

    }



    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }
}
