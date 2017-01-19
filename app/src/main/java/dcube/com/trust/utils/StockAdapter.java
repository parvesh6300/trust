package dcube.com.trust.utils;

import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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
import okhttp3.OkHttpClient;

/**
 * Created by Sagar on 14/10/16.
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

    Dialog dialog;
    Dialog alertDialog;
    int pos ;

    CheckNetConnection cn;

    String str_branch;

    public StockAdapter(Context context) {
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

        str_branch = global.getAl_login_list().get(0).get(GlobalConstants.USER_BRANCH);

        for (HashMap<String, String> hashMap : global.getAl_stock_product()) {   //getAl_stock_product

            name.add(hashMap.get(GlobalConstants.PRODUCT_NAME));
            category.add(hashMap.get(GlobalConstants.PRODUCT_CATEGORY));
            SKU.add(hashMap.get(GlobalConstants.PRODUCT_SKU));
            price.add(hashMap.get(GlobalConstants.PRODUCT_PRICE));
            in_stock.add(hashMap.get(GlobalConstants.PRODUCT_IN_STOCK));
            product_id.add(hashMap.get(GlobalConstants.PRODUCT_ID));
        }
    }

    public class ViewHolder {
        TextView tv_product, tv_category, tv_quantity, tv_quantity_label;
        RelativeLayout rel_row;
    }



    @Override
    public View getView(final int i, View convertView, ViewGroup viewGroup) {


        final ViewHolder holder = new ViewHolder();

        convertView = inflater.inflate(R.layout.viewstock, viewGroup, false);

        holder.rel_row = (RelativeLayout) convertView.findViewById(R.id.rel_row);
        holder.tv_product = (TextView) convertView.findViewById(R.id.tv_product);
        holder.tv_category = (TextView) convertView.findViewById(R.id.tv_category);
        holder.tv_quantity = (TextView) convertView.findViewById(R.id.tv_quantity);
        holder.tv_quantity_label = (TextView) convertView.findViewById(R.id.tv_quantity_label);

        holder.tv_product.setText(name.get(i));
        holder.tv_category.setText(category.get(i));
        holder.tv_quantity.setText(in_stock.get(i));   //in_stock.get(i)

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
                TextView tv_request = (TextView) dialog.findViewById(R.id.tv_request);
                TextView tv_cancel = (TextView) dialog.findViewById(R.id.tv_cancel);

                dialog.setCancelable(false);
                dialog.show();

                Calendar calendar = Calendar.getInstance();

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy MMM dd HH:mm");

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
                        quantity = Integer.parseInt(tv_quantity1.getText().toString()) ;

                        showAlertDialog();

                    }
                });
            }
        });

        return convertView;
    }

    @Override
    public int getCount() {
        return global.getAl_stock_product().size();
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

        TextView tv_yes = (TextView) alertDialog.findViewById(R.id.tv_yes);
        TextView tv_no = (TextView) alertDialog.findViewById(R.id.tv_no);

        tv_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.cancel();

                if (cn.isNetConnected())
                {
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

        TextView tv_yes = (TextView) doneDialog.findViewById(R.id.tv_yes);
        TextView tv_no = (TextView) doneDialog.findViewById(R.id.tv_no);
        TextView tv_message = (TextView) doneDialog.findViewById(R.id.tv_message);
        TextView tv_title = (TextView) doneDialog.findViewById(R.id.tv_title);

        tv_title.setText("Confirmation Dialog");
        tv_message.setText("Stock has been requested");
        tv_yes.setText("OK");
        tv_no.setVisibility(View.GONE);

        tv_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                alertDialog.cancel();
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

            if (message.equalsIgnoreCase("true")) {
                showDoneDialog();
            } else {
                Toast.makeText(mcontext, "" + message, Toast.LENGTH_SHORT).show();
            }

        }

    }

    public class StockRequestAsyncTask extends AsyncTask<String, String, String> {

        OkHttpClient httpClient = new OkHttpClient();
        String resPonse = "";
        String message = "";
        String str_client_id;

        @Override
        protected void onPreExecute() {

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
                al_str_value.add("update_products_in_stock");

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

            if (message.equalsIgnoreCase("true")) {
                showDoneDialog();
            } else {
                Toast.makeText(mcontext, "" + message, Toast.LENGTH_SHORT).show();
            }

        }

    }


}
