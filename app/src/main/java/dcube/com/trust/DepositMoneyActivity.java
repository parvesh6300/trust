package dcube.com.trust;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
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

    ArrayList<String> al_deposit_detail= new ArrayList<>();
    ArrayList<String> al_deposit_amount= new ArrayList<>();
    ArrayList<String> al_date= new ArrayList<>();

    TextView tv_total_amount,tv_deposit;

    EditText ed_deposit_amount,ed_remark;

    GifTextView gif_loader;

    Global global;

    String str_deposit_amount,str_remark;
    WebServices ws;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_deposit_money);

        global = (Global) getApplicationContext();

        list_deposit=(ListView)findViewById(R.id.list_deposit);

        tv_total_amount=(TextView)findViewById(R.id.tv_total_amount);
        tv_deposit=(TextView)findViewById(R.id.tv_deposit);

        ed_deposit_amount=(EditText)findViewById(R.id.ed_deposit_amount);
        ed_remark = (EditText) findViewById(R.id.ed_remark);

        gif_loader = (GifTextView) findViewById(R.id.gif_loader);

        depositAdapter= new DepositAdapter(this,al_date,al_deposit_detail,al_deposit_amount);
        list_deposit.setAdapter(depositAdapter);


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

                    CustomDialogClass cdd = new CustomDialogClass(DepositMoneyActivity.this);
                    cdd.show();
                }


            }
        });


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

            tv_deposit.setText("Deposit : "+str_deposit_amount);

            int_total_amount = Integer.parseInt("1000000");
            int_deposit = Integer.parseInt(str_deposit_amount);
            int_balance = int_total_amount + int_deposit;

            tv_balance.setText("BALANCE : "+String.valueOf(int_balance));


            confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    dismiss();
                    new DepositMoneyAsyncTask().execute();

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
                startActivity(new Intent(DepositMoneyActivity.this,FinanceHomeActivity.class));
                finish();
            }
            else {
                Toast.makeText(context, "" + message, Toast.LENGTH_SHORT).show();
            }

        }

    }

}
