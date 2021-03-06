package dcube.com.trust.utils;

import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import WebServicesHandler.GlobalConstants;
import WebServicesHandler.WebServices;
import dcube.com.trust.CartActivity;
import dcube.com.trust.R;
import okhttp3.OkHttpClient;

/**
 * Created by Rohit on 28/11/16.
 */


public class CartAdapter extends BaseAdapter {

    Context context;
    public static LayoutInflater inflater;

    Global global;

    ArrayList<String> al_item_name;
    ArrayList<String> al_item_desc;
    ArrayList<String> al_item_price;
    ArrayList<String> al_item_quantity;
    ArrayList<String> al_cart_id;
    ArrayList<String> al_item_type;

    String str_price ="1";
    String str_client_id;
    WebServices ws;
    int position,quantity;

    Dialog dialog;


    public CartAdapter(Context mcontext)
    {
        context = mcontext;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        global = (Global) context.getApplicationContext();

        al_item_name= new ArrayList<>();
        al_item_desc= new ArrayList<>();
        al_item_price= new ArrayList<>();
        al_item_quantity= new ArrayList<>();
        al_cart_id= new ArrayList<>();
        al_item_type= new ArrayList<>();


        str_client_id = global.getAl_src_client_details().get(global.getSelected_client()).
                get(GlobalConstants.SRC_CLIENT_ID);


        for (HashMap<String,String> hashmap: global.getAl_cart_details())
        {
            al_item_price.add(hashmap.get(GlobalConstants.GET_CART_ITEM_PRICE));
            al_item_quantity.add(hashmap.get(GlobalConstants.GET_CART_AMOUNT));
            al_cart_id.add(hashmap.get(GlobalConstants.GET_CART_ID));
            al_item_type.add(hashmap.get(GlobalConstants.GET_CART_ITEM_TYPE));

            al_item_name.add(hashmap.get(GlobalConstants.GET_CART_ITEM_NAME));

            al_item_desc.add(hashmap.get(GlobalConstants.GET_CART_ITEM_DESC));
        }


    }

    public class ViewHolder
    {
        TextView tv_name,tv_desc,tv_price;
        TextView ed_quantity;
        ImageView iv_product_image,iv_cancel;
    }

    @Override
    public View getView(final int pos, View convertView, ViewGroup viewGroup) {

        final ViewHolder holder= new ViewHolder();

        convertView = inflater.inflate(R.layout.cart_list,viewGroup,false);

        holder.tv_name= (TextView)convertView.findViewById(R.id.tv_name);
        holder.tv_desc= (TextView)convertView.findViewById(R.id.tv_desc);
        holder.tv_price=(TextView)convertView.findViewById(R.id.tv_price);
        holder.ed_quantity = (TextView)convertView.findViewById(R.id.ed_quantity);

        holder.iv_product_image = (ImageView) convertView.findViewById(R.id.iv_product_image);
        holder.iv_cancel = (ImageView) convertView.findViewById(R.id.iv_cancel);

        holder.tv_name.setText(al_item_name.get(pos));
        holder.tv_desc.setText(al_item_type.get(pos));

        str_price = al_item_price.get(pos);

        String str_item_type = al_item_type.get(pos);

        if (str_item_type.equalsIgnoreCase("product"))
        {
            String formatted_qnty = al_item_quantity.get(pos);

            formatted_qnty = FormatString.getCommaInString(formatted_qnty);

            //holder.ed_quantity.setText(al_item_quantity.get(pos));

            holder.ed_quantity.setText(formatted_qnty);

            holder.ed_quantity.setFocusable(true);
            holder.ed_quantity.setClickable(true);
            holder.ed_quantity.setInputType(InputType.TYPE_CLASS_NUMBER);

            int each_price = Integer.parseInt(str_price);
            int qt = Integer.parseInt(al_item_quantity.get(pos));        //holder.ed_quantity.getText().toString()

            String formatted_item_cost = String.valueOf(each_price * qt);

            formatted_item_cost = FormatString.getCommaInString(formatted_item_cost);

            holder.tv_price.setText(formatted_item_cost);      //String.valueOf(each_price * qt)

        }
        else
        {
            holder.ed_quantity.setText("NA");
            holder.ed_quantity.setFocusable(false);
            holder.ed_quantity.setClickable(false);

            String formatted_price = str_price;

            formatted_price = FormatString.getCommaInString(formatted_price);

            holder.tv_price.setText(formatted_price);     //str_price

        }


        holder.ed_quantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (al_item_type.get(pos).equalsIgnoreCase("product"))
                {
                    position = pos;
                    showDialog();
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


    public void showDialog()
    {
        dialog = new Dialog(context);

        dialog.setContentView(R.layout.cart_update_dialog);

        final EditText tv_quantity1 = (EditText) dialog.findViewById(R.id.tv_quantity);
        TextView tv_plus = (TextView) dialog.findViewById(R.id.tv_plus);
        TextView tv_minus = (TextView) dialog.findViewById(R.id.tv_minus);
        TextView tv_product_name = (TextView) dialog.findViewById(R.id.tv_product_name);
        TextView tv_product_cat = (TextView) dialog.findViewById(R.id.tv_product_cat);
        Button tv_request = (Button) dialog.findViewById(R.id.tv_request);
        Button tv_cancel = (Button) dialog.findViewById(R.id.tv_cancel);

        dialog.setCancelable(false);
        dialog.show();

        tv_product_name.setText(al_item_name.get(position));
        tv_product_cat.setText(al_item_type.get(position));


        String formatted_qnty = al_item_quantity.get(position);

        formatted_qnty = FormatString.getCommaInString(formatted_qnty);

        tv_quantity1.setText(formatted_qnty);     //al_item_quantity.get(position)


        tv_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String s = tv_quantity1.getText().toString().replace(",", "");

                quantity =  Integer.parseInt(s);     //holder.quantity.getText().toString()

                //quantity = Integer.parseInt(tv_quantity1.getText().toString());

                int max_stock = Integer.parseInt(global.getAl_cart_details().get(position).get(GlobalConstants.GET_CART_MAX_STOCK));

                if (quantity > max_stock)
                {
                    Toast.makeText(context, "Only "+max_stock+" is left", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    quantity++;

                    String formatted_qnty = String.valueOf(quantity);

                    formatted_qnty = FormatString.getCommaInString(formatted_qnty);

                    tv_quantity1.setText(formatted_qnty);        //String.valueOf(quantity)
                }

            }
        });


        tv_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String s = tv_quantity1.getText().toString().replace(",", "");

                quantity =  Integer.parseInt(s);

                if (quantity > 0) {
                    quantity--;
                }

                String formatted_qnty = String.valueOf(quantity);

                formatted_qnty = FormatString.getCommaInString(formatted_qnty);

              //  tv_quantity1.setText(String.valueOf(quantity));

                tv_quantity1.setText(formatted_qnty);

            }
        });


        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.cancel();
            }
        });


        tv_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String s = tv_quantity1.getText().toString().replace(",", "");

                quantity =  Integer.parseInt(s);

              //  quantity = Integer.parseInt(tv_quantity1.getText().toString());

                new UpdateCartItemAsyncTask().execute();

            }
        });


        tv_quantity1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {

                try {

                    final String s = charSequence.toString().replace(",", "");

                    quantity = Integer.parseInt(s);       //Integer.parseInt(charSequence.toString())


                  //  quantity =  Integer.parseInt(s.toString());

                    int max_stock = Integer.parseInt(global.getAl_cart_details().get(position).get(GlobalConstants.GET_CART_MAX_STOCK));

                    if (quantity > max_stock)
                    {
                        String qt = charSequence.toString();

                        String[] q = new String[qt.length()];

                        q[0] =  qt.substring(0,qt.length()-1);

                        String formatted_qnty = q[0];

                        formatted_qnty = FormatString.getCommaInString(formatted_qnty);

                        tv_quantity1.setText(formatted_qnty);  //q[0]


                       // tv_quantity1.setText(q[0]);

                    }
                }
                catch (Exception e)
                {

                }



            }

            @Override
            public void afterTextChanged(Editable editable) {

                try {

                    final String s = editable.toString().replace(",", "");

                    quantity =  Integer.parseInt(s);     //editable.toString()


                  //  quantity =  Integer.parseInt(editable.toString());

                    int max_stock = Integer.parseInt(global.getAl_cart_details().get(position).get(GlobalConstants.GET_CART_MAX_STOCK));

                    if (quantity > 0)
                    {
                        if (max_stock > quantity)
                        {
                            quantity = Integer.parseInt(s);      //tv_quantity1.getText().toString()
                        }
                    }
                    else
                    {
                        Toast.makeText(context, "Quantity should be greater than 0", Toast.LENGTH_SHORT).show();
                    }



                }
                catch (Exception e)
                {
                    Log.e("Exception","in cart "+e.getMessage());
                }



            }
        });




    }


    public class DeleteCartItemAsyncTask extends AsyncTask<String, String, String> {

        OkHttpClient httpClient = new OkHttpClient();
        String resPonse = "";
        String message = "";

        @Override
        protected void onPreExecute() {

            ((CartActivity)context).loader(context,true);

        }

        @Override
        protected String doInBackground(String... params) {
            try {

                ArrayList<String> al_str_key = new ArrayList<>();
                ArrayList<String> al_str_value = new ArrayList<>();

                al_str_key.add(GlobalConstants.USER_BRANCH_ID);
                al_str_value.add(global.getAl_login_list().get(0).get(GlobalConstants.USER_BRANCH_ID));

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

            ((CartActivity)context).loader(context,false);

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

            ((CartActivity)context).loader(context,true);

        }

        @Override
        protected String doInBackground(String... params) {
            try {

                ArrayList<String> al_str_key = new ArrayList<>();
                ArrayList<String> al_str_value = new ArrayList<>();

                al_str_key.add(GlobalConstants.USER_BRANCH_ID);
                al_str_value.add(global.getAl_login_list().get(0).get(GlobalConstants.USER_BRANCH_ID));

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

            ((CartActivity)context).loader(context,false);

            if (message.equalsIgnoreCase("true"))
            {
//                new CartAdapter(context);
//                notifyDataSetChanged();

                ((CartActivity)context).updateList(context);
            }
            else {
                Toast.makeText(context, "" + message, Toast.LENGTH_SHORT).show();
            }

        }

    }


    public class UpdateCartItemAsyncTask extends AsyncTask<String, String, String> {

        OkHttpClient httpClient = new OkHttpClient();
        String resPonse = "";
        String message = "";

        @Override
        protected void onPreExecute() {

            dialog.dismiss();

            ((CartActivity)context).loader(context,true);

        }

        @Override
        protected String doInBackground(String... params) {
            try {

                ArrayList<String> al_str_key = new ArrayList<>();
                ArrayList<String> al_str_value = new ArrayList<>();

                al_str_key.add(GlobalConstants.USER_BRANCH_ID);
                al_str_value.add(global.getAl_login_list().get(0).get(GlobalConstants.USER_BRANCH_ID));

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

            ((CartActivity)context).loader(context,false);

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
