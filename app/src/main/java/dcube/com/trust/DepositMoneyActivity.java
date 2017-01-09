package dcube.com.trust;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import WebServicesHandler.CheckNetConnection;
import WebServicesHandler.GlobalConstants;
import WebServicesHandler.WebServices;
import dcube.com.trust.utils.DepositAdapter;
import dcube.com.trust.utils.Global;
import okhttp3.OkHttpClient;
import pl.droidsonroids.gif.GifTextView;

public class DepositMoneyActivity extends Activity {

    Context context = this;
    ListView list_deposit;

    DepositAdapter depositAdapter;

   TextView tv_total_amount,tv_deposit;

    EditText ed_deposit_amount,ed_remark;

    GifTextView gif_loader;

    Global global;

    String str_deposit_amount,str_remark,str_branch;
    WebServices ws;

    CustomDialogClass cdd;

    CheckNetConnection cn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_deposit_money);

        global = (Global) getApplicationContext();

        cn = new CheckNetConnection(this);

        list_deposit=(ListView)findViewById(R.id.list_deposit);

        tv_total_amount=(TextView)findViewById(R.id.tv_total_amount);
        tv_deposit=(TextView)findViewById(R.id.tv_deposit);

        ed_deposit_amount=(EditText)findViewById(R.id.ed_deposit_amount);
        ed_remark = (EditText) findViewById(R.id.ed_remark);

        gif_loader = (GifTextView) findViewById(R.id.gif_loader);

        str_branch = global.getAl_login_list().get(0).get(GlobalConstants.USER_BRANCH);


        tv_deposit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ed_deposit_amount.getText().toString().matches(""))
                {
                    Toast.makeText(DepositMoneyActivity.this, "Enter Amount", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    str_deposit_amount = ed_deposit_amount.getText().toString();

                    if (ed_remark.getText().toString().matches(""))
                    {
                        str_remark = "";
                    }
                    else
                    {
                        str_remark = ed_remark.getText().toString();
                    }

                    cdd = new CustomDialogClass(DepositMoneyActivity.this);
                    cdd.show();
                }


            }
        });


        if (cn.isNetConnected())
        {
            new DepositHistoryAsyncTask().execute();
        }
        else
        {
            Toast.makeText(context, "Check Internet Connection", Toast.LENGTH_SHORT).show();
        }


    }



    public class CustomDialogClass extends Dialog {

        public Activity c;

        public TextView cancel;
        public TextView confirm,tv_deposit,tv_total_amount,tv_balance;

        int int_total_amount,int_deposit,int_balance;


        public CustomDialogClass(Activity a) {
            super(a);
            // TODO Auto-generated constructor stub
            this.c = a;
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.deposit_dialog);

            confirm = (TextView) findViewById(R.id.confirm);
            cancel = (TextView) findViewById(R.id.cancel);
            tv_deposit = (TextView) findViewById(R.id.tv_deposit);
            tv_balance = (TextView) findViewById(R.id.tv_balance);
            tv_total_amount = (TextView) findViewById(R.id.tv_total_amount);

            tv_total_amount.setText("ACCOUNT TOTAL : "+String.valueOf(global.getInt_ac_balance()));
            tv_deposit.setText("Deposit : "+str_deposit_amount);

            int_total_amount = global.getInt_ac_balance();
            int_deposit = Integer.parseInt(str_deposit_amount);
            int_balance = int_total_amount + int_deposit;

            tv_balance.setText("BALANCE : "+String.valueOf(int_balance));


            confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    dismiss();

                    if (cn.isNetConnected())
                    {
                        new DepositMoneyAsyncTask().execute();
                    }
                    else
                    {
                        Toast.makeText(context, "Check Internet Connection", Toast.LENGTH_SHORT).show();
                    }

                }
            });

            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    dismiss();
                }
            });


        }
    }



    public class DepositMoneyAsyncTask extends AsyncTask<String, String, String> {

        OkHttpClient httpClient = new OkHttpClient();
        String resPonse = "";
        String message = "";

        @Override
        protected void onPreExecute() {

            gif_loader.setVisibility(View.VISIBLE);

        }

        @Override
        protected String doInBackground(String... params) {

            try {

                ArrayList<String> al_str_key = new ArrayList<>();
                ArrayList<String> al_str_value = new ArrayList<>();

                al_str_key.add(GlobalConstants.DEPOSIT_AMOUNT);
                al_str_value.add(str_deposit_amount);

                al_str_key.add(GlobalConstants.DEPOSIT_REMARKS);
                al_str_value.add(str_remark);

                al_str_key.add(GlobalConstants.BRANCH);
                al_str_value.add(str_branch);

                al_str_key.add(GlobalConstants.ACTION);
                al_str_value.add("deposits");

                for (int i =0 ; i < al_str_key.size() ; i++)
                {
                    Log.e("Key",""+ al_str_key.get(i));
                    Log.e("Value",""+ al_str_value.get(i));
                }

                message = ws.DepositMoneyService(context, al_str_key, al_str_value);

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {

            gif_loader.setVisibility(View.GONE);

            if (message.equalsIgnoreCase("true"))
            {
                showDoneDialog();

            }
            else {
                Toast.makeText(context, "" + message, Toast.LENGTH_SHORT).show();
            }

        }

    }


    public void showDoneDialog() {

        final Dialog doneDialog = new Dialog(context);

        doneDialog.setContentView(R.layout.stockalertdialog);

        //doneDialog.create();
        doneDialog.show();

        TextView tv_yes = (TextView) doneDialog.findViewById(R.id.tv_yes);
        TextView tv_no = (TextView) doneDialog.findViewById(R.id.tv_no);
        TextView tv_message = (TextView) doneDialog.findViewById(R.id.tv_message);
        TextView tv_title = (TextView) doneDialog.findViewById(R.id.tv_title);

        tv_title.setText("Confirmation Dialog");
        tv_message.setText("Money Deposited");
        tv_yes.setText("OK");
        tv_no.setVisibility(View.GONE);

        tv_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                cdd.dismiss();
                doneDialog.cancel();
                finish();

            }
        });
    }


    public class DepositHistoryAsyncTask extends AsyncTask<String, String, String> {

        OkHttpClient httpClient = new OkHttpClient();
        String resPonse = "";
        String message = "";

        @Override
        protected void onPreExecute() {

            gif_loader.setVisibility(View.VISIBLE);

        }

        @Override
        protected String doInBackground(String... params) {

            try {

                ArrayList<String> al_str_key = new ArrayList<>();
                ArrayList<String> al_str_value = new ArrayList<>();


                al_str_key.add(GlobalConstants.ACTION);
                al_str_value.add("deposits_history");

                message = ws.DepositHistoryService(context, al_str_key, al_str_value);

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {

            gif_loader.setVisibility(View.GONE);

            if (message.equalsIgnoreCase("true"))
            {
                tv_total_amount.setText(" "+String.valueOf(global.getInt_ac_balance() +" Tsh"));

                depositAdapter= new DepositAdapter(context);
                list_deposit.setAdapter(depositAdapter);

            }
            else {
                Toast.makeText(context, "" + message, Toast.LENGTH_SHORT).show();
            }

        }

    }



    protected boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnected()) {
            return true;
        } else {
            return false;
        }
    }


}
