package dcube.com.trust.utils;

import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import WebServicesHandler.CheckNetConnection;
import WebServicesHandler.GlobalConstants;
import WebServicesHandler.WebServices;
import dcube.com.trust.R;
import dcube.com.trust.StockManagementActivity;
import okhttp3.OkHttpClient;

/**
 * Created by Rohit on 14/10/16.
 */
public class StockAdapter extends BaseAdapter {

    public static LayoutInflater inflater;
    public int quantity;
    Context mcontext;

    Global global;
    WebServices ws;
    ArrayList<String> name;
    ArrayList<String> product_id;
    ArrayList<String> SKU ;
    ArrayList<String> in_stock;
    ArrayList<String> category ;
    ArrayList<String> price ;
    ArrayList<String> category_id ;

    ArrayList<String> al_search_name;
    ArrayList<String> al_status;
    ArrayList<String> al_quantity;

    Dialog dialog;
    Dialog alertDialog;
    int pos ;

    CheckNetConnection cn;

    String str_branch;

    public StockAdapter(Context context, String search) {

        this.mcontext = context;

        global = (Global) context.getApplicationContext();

        cn = new CheckNetConnection(context);

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        name = new ArrayList<>();
        product_id = new ArrayList<>();
        SKU = new ArrayList<>();
        in_stock = new ArrayList<>();
        category = new ArrayList<>();
        price = new ArrayList<>();
        category_id = new ArrayList<>();

        al_status = new ArrayList<>();
        al_search_name = new ArrayList<>();
        al_quantity = new ArrayList<>();

        str_branch = global.getAl_login_list().get(0).get(GlobalConstants.USER_BRANCH);

        for (HashMap<String, String> hashMap : global.getAl_stock_product())
        {
            if(search.equalsIgnoreCase(""))
            {
                name.add(hashMap.get(GlobalConstants.PRODUCT_NAME));
                category.add(hashMap.get(GlobalConstants.PRODUCT_CATEGORY));
                SKU.add(hashMap.get(GlobalConstants.PRODUCT_SKU));
                price.add(hashMap.get(GlobalConstants.PRODUCT_PRICE));
                in_stock.add(hashMap.get(GlobalConstants.PRODUCT_IN_STOCK));
                product_id.add(hashMap.get(GlobalConstants.PRODUCT_ID));
                category_id.add(hashMap.get(GlobalConstants.PRODUCT_CATEGORY_ID));
                al_status.add(hashMap.get(GlobalConstants.PRODUCT_REQUEST_STATUS));
                al_quantity.add(hashMap.get(GlobalConstants.PRODUCT_QUANTITY));
            }
            else
            {
                if(hashMap.get(GlobalConstants.PRODUCT_NAME).toLowerCase().contains(search.toLowerCase()) ||
                        hashMap.get(GlobalConstants.PRODUCT_CATEGORY).toLowerCase().contains(search.toLowerCase()))
                {
                    name.add(hashMap.get(GlobalConstants.PRODUCT_NAME));
                    category.add(hashMap.get(GlobalConstants.PRODUCT_CATEGORY));
                    SKU.add(hashMap.get(GlobalConstants.PRODUCT_SKU));
                    price.add(hashMap.get(GlobalConstants.PRODUCT_PRICE));
                    in_stock.add(hashMap.get(GlobalConstants.PRODUCT_IN_STOCK));
                    product_id.add(hashMap.get(GlobalConstants.PRODUCT_ID));
                    category_id.add(hashMap.get(GlobalConstants.PRODUCT_CATEGORY_ID));
                    al_status.add(hashMap.get(GlobalConstants.PRODUCT_REQUEST_STATUS));
                    al_quantity.add(hashMap.get(GlobalConstants.PRODUCT_QUANTITY));
                }
            }


        }
    }

    public class ViewHolder {
        TextView tv_product, tv_category, tv_quantity, tv_quantity_label,tv_status;
        RelativeLayout rel_row;
    }



    @Override
    public View getView(final int i,View rowView, ViewGroup viewGroup) {


        final ViewHolder holder = new ViewHolder();
        final View convertView;

        convertView = inflater.inflate(R.layout.viewstock, viewGroup, false);

        holder.rel_row = (RelativeLayout) convertView.findViewById(R.id.rel_row);
        holder.tv_product = (TextView) convertView.findViewById(R.id.tv_product);
        holder.tv_category = (TextView) convertView.findViewById(R.id.tv_category);
        holder.tv_quantity = (TextView) convertView.findViewById(R.id.tv_quantity);
        holder.tv_status = (TextView) convertView.findViewById(R.id.tv_status);
        holder.tv_quantity_label = (TextView) convertView.findViewById(R.id.tv_quantity_label);

        holder.tv_product.setText(name.get(i));
        holder.tv_category.setText(category.get(i));

        int stock = Integer.parseInt(in_stock.get(i));

        if (stock < 21)
        {
            holder.tv_quantity.setBackgroundResource(R.drawable.red_circle);
        }


        String amount = in_stock.get(i);

        amount = FormatString.getCommaInString(amount);

        holder.tv_quantity.setText(amount);       //in_stock.get(i)




        if (al_status.get(i).equalsIgnoreCase("0"))
        {
            //holder.tv_status.setBackgroundResource(R.drawable.red_circle);
            holder.tv_status.setTextColor(mcontext.getResources().getColor(R.color.yellow));
            holder.tv_status.setText("Requested");
           // holder.tv_status.setText(al_quantity.get(i));
        }
        else if (al_status.get(i).equalsIgnoreCase("1"))
        {
           // holder.tv_status.setBackgroundResource(R.drawable.green_circle);
            holder.tv_status.setTextColor(mcontext.getResources().getColor(R.color.green));
            holder.tv_status.setText("Approved");
          //  holder.tv_status.setText(al_quantity.get(i));
        }
        else
        {
            holder.tv_status.setVisibility(View.INVISIBLE);
        }


        holder.rel_row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog = new Dialog(mcontext);

                dialog.setContentView(R.layout.quantitydialog);

                final EditText tv_quantity1 = (EditText) dialog.findViewById(R.id.tv_quantity);
                TextView tv_plus = (TextView) dialog.findViewById(R.id.tv_plus);
                TextView tv_minus = (TextView) dialog.findViewById(R.id.tv_minus);
                TextView tv_current_time = (TextView) dialog.findViewById(R.id.tv_current_time);
                TextView tv_product_name = (TextView) dialog.findViewById(R.id.tv_product_name);
                TextView tv_product_cat = (TextView) dialog.findViewById(R.id.tv_product_cat);
                Button tv_request = (Button) dialog.findViewById(R.id.tv_request);
                Button tv_cancel = (Button) dialog.findViewById(R.id.tv_cancel);

                dialog.setCancelable(false);
                dialog.show();

                Calendar calendar = Calendar.getInstance();

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MMM-dd hh:mm a");

                String date = sdf.format(calendar.getTime());

                tv_product_name.setText(name.get(i));
                tv_product_cat.setText(category.get(i));
                tv_current_time.setText(date);
          //      tv_quantity1.setText(global.getAl_stock_product().get(i).get(GlobalConstants.PRODUCT_IN_STOCK));

                tv_plus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        quantity++;
                        tv_quantity1.setText(String.valueOf(quantity));

                    }
                });


                tv_minus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (quantity > 0) {
                            quantity--;
                        }

                        tv_quantity1.setText(String.valueOf(quantity));
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

                        pos = i;

                        if (tv_quantity1.getText().toString().matches("") )
                        {
                            Toast.makeText(mcontext, "Enter Quantity", Toast.LENGTH_SHORT).show();
                        }
                        else if (tv_quantity1.getText().toString().equalsIgnoreCase("0"))
                        {
                            Toast.makeText(mcontext, "Requested Quantity is 0", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            quantity = Integer.parseInt(tv_quantity1.getText().toString()) ;

                            showAlertDialog();
                        }



                    }
                });
            }
        });

        return convertView;
    }

    @Override
    public int getCount() {
        return name.size();
    }


    @Override
    public Object getItem(int i) {
        return i;
    }


    @Override
    public long getItemId(int i) {
        return i;
    }



    public void showAlertDialog()
    {

        alertDialog = new Dialog(mcontext);

        alertDialog.setContentView(R.layout.stockalertdialog);
        //alertDialog.create();
        alertDialog.show();

        Button tv_yes = (Button) alertDialog.findViewById(R.id.tv_yes);
        Button tv_no = (Button) alertDialog.findViewById(R.id.tv_no);

        tv_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.cancel();

                if (cn.isNetConnected())
                {
                    alertDialog.dismiss();

                    //new StockRequestAsyncTask().execute();


                    new AdminStockRequestAsyncTask().execute();
                }
                else
                {
                    Toast.makeText(mcontext, "Check Internet Connection", Toast.LENGTH_SHORT).show();
                }

            }
        });

        tv_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                alertDialog.cancel();
            }
        });
    }


    public void showDoneDialog()
    {

        final Dialog doneDialog = new Dialog(mcontext);

        doneDialog.setContentView(R.layout.stockalertdialog);

        //doneDialog.create();
        doneDialog.show();

        Button tv_yes = (Button) doneDialog.findViewById(R.id.tv_yes);
        Button tv_no = (Button) doneDialog.findViewById(R.id.tv_no);
        TextView tv_message = (TextView) doneDialog.findViewById(R.id.tv_message);
        TextView tv_title = (TextView) doneDialog.findViewById(R.id.tv_title);

        tv_title.setText("Confirmation Dialog");
        tv_message.setText("Stock has been requested");
        tv_yes.setText("OK");
        tv_no.setVisibility(View.GONE);

        tv_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                doneDialog.cancel();
            }
        });
    }


    public class AdminStockRequestAsyncTask extends AsyncTask<String, String, String> {

        OkHttpClient httpClient = new OkHttpClient();
        String resPonse = "";
        String message = "";
        String str_client_id;

        @Override
        protected void onPreExecute() {

            ((StockManagementActivity)mcontext).startLoader(mcontext);
        }

        @Override
        protected String doInBackground(String... params) {

            try {

                ArrayList<String> al_str_key = new ArrayList<>();
                ArrayList<String> al_str_value = new ArrayList<>();

                al_str_key.add(GlobalConstants.USER_BRANCH_ID);
                al_str_value.add(global.getAl_login_list().get(0).get(GlobalConstants.USER_BRANCH_ID));

                al_str_key.add(GlobalConstants.STOCK_ITEM_ID);
                al_str_value.add(global.getAl_stock_product().get(pos).get(GlobalConstants.PRODUCT_ID));

                al_str_key.add(GlobalConstants.STOCK_ITEM_QTY);
                al_str_value.add(String.valueOf(quantity));

                al_str_key.add(GlobalConstants.BRANCH);
                al_str_value.add(str_branch);

                al_str_key.add(GlobalConstants.ACTION);
                al_str_value.add("request_stock");   //request_admin_for_products

                Log.i("Key",""+al_str_key);
                Log.i("Value",""+al_str_value);

                message = ws.UpdateStockService(mcontext, al_str_key, al_str_value);

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {

            ((StockManagementActivity)mcontext).stopLoader(mcontext);

            if (message.equalsIgnoreCase("true")) {

                Toast.makeText(mcontext, "Stock Requested", Toast.LENGTH_SHORT).show();

                ((StockManagementActivity)mcontext).updateList(mcontext);
               // new GetStockProductAsyncTask().execute();
               // showDoneDialog();
            } else {
                Toast.makeText(mcontext, "" + message, Toast.LENGTH_SHORT).show();
            }

        }

    }


    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }
}
