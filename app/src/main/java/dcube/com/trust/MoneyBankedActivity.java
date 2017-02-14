package dcube.com.trust;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import WebServicesHandler.CheckNetConnection;
import WebServicesHandler.GlobalConstants;
import WebServicesHandler.HideKeyboard;
import WebServicesHandler.WebServices;
import dcube.com.trust.utils.Global;
import dcube.com.trust.utils.MoneyBankedAdapter;
import okhttp3.OkHttpClient;
import pl.droidsonroids.gif.GifTextView;

public class MoneyBankedActivity extends Activity {


    ListView lv_money_banked;

    MoneyBankedAdapter moneyBankedAdapter;

    TextView tv_total_amount,tv_deposit;

    EditText ed_deposit_amount;
    GifTextView gif_loader;

    Global global;
    WebServices ws;

    CustomDialogClass cdd;
    String str_deposit_amount;

    RelativeLayout rel_parent_layout;

    CheckNetConnection cn;

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_money_banked);

        context = this;

        cn = new CheckNetConnection(context);

        rel_parent_layout = (RelativeLayout) findViewById(R.id.rel_parent_layout);

        global = (Global) context.getApplicationContext();

        gif_loader = (GifTextView) findViewById(R.id.gif_loader);

        lv_money_banked=(ListView)findViewById(R.id.lv_money_banked);
        ed_deposit_amount=(EditText)findViewById(R.id.ed_deposit_amount);
        tv_total_amount=(TextView)findViewById(R.id.tv_total_amount);
        tv_deposit=(TextView)findViewById(R.id.tv_deposit);

//
//        View header = getLayoutInflater().inflate(R.layout.moneybankedlistheader, lv_money_banked, false);
//        lv_money_banked.addHeaderView(header, null, false);

        tv_deposit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ed_deposit_amount.getText().toString().matches(""))
                {
                    Toast.makeText(MoneyBankedActivity.this, "Enter Amount", Toast.LENGTH_SHORT).show();
                }
                else if ((ed_deposit_amount.getText().toString().equalsIgnoreCase("0")))
                {
                    Toast.makeText(MoneyBankedActivity.this, "Amount should be greater than 0", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    str_deposit_amount = ed_deposit_amount.getText().toString();

                    float account_total = Float.parseFloat(global.getStr_branch_balance());
                    float deposit_amount = Float.parseFloat(str_deposit_amount);

                    if (account_total > deposit_amount)
                    {
                        cdd = new CustomDialogClass(MoneyBankedActivity.this);
                        cdd.show();
                    }
                    else
                    {
                        insufficientDialog();
                      //  Toast.makeText(MoneyBankedActivity.this, "Money to be Banked, is less than Account Balance", Toast.LENGTH_SHORT).show();
                    }


                }

            }
        });



        rel_parent_layout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                HideKeyboard.hideSoftKeyboard(MoneyBankedActivity.this);

                return false;
            }
        });




        if (cn.isNetConnected())
        {
            new GetBranchBalanceAsyncTask().execute();
            new MoneyBankHistoryAsyncTask().execute();
        }
        else
        {
            Toast.makeText(MoneyBankedActivity.this, "Check Internet Connection", Toast.LENGTH_SHORT).show();
        }


    }


    /**
     * Transaction custom dialog
     */

    public class CustomDialogClass extends Dialog {

        public Activity c;

        public TextView cancel,tv_account_total,tv_deposit_money;
        public TextView confirm,tv_balance;

        public CustomDialogClass(Activity a) {
            super(a);
            // TODO Auto-generated constructor stub
            this.c = a;
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            requestWindowFeature(Window.FEATURE_NO_TITLE);

            setContentView(R.layout.money_banked_dialog);

            confirm = (TextView) findViewById(R.id.confirm);
            cancel = (TextView) findViewById(R.id.cancel);
            tv_balance = (TextView) findViewById(R.id.tv_balance);
            tv_account_total = (TextView) findViewById(R.id.tv_account_total);
            tv_deposit_money = (TextView) findViewById(R.id.tv_deposit_money);

            float account_total = Float.parseFloat(global.getStr_branch_balance());

            tv_account_total.setText("Account Total : "+global.getStr_branch_balance()+" TZS");

            tv_deposit_money.setText("Money to Bank : "+str_deposit_amount+" TZS");

            float deposit_amount = Float.parseFloat(str_deposit_amount);

            float balance = account_total - deposit_amount;

            tv_balance.setText("Projected Balance : "+String.valueOf(balance)+" TZS");

            confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    dismiss();

                    new MoneyBankAsyncTask().execute();

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


    /**
     * hit the service and bank the money
     */


    public class MoneyBankAsyncTask extends AsyncTask<String, String, String> {

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

                al_str_key.add(GlobalConstants.USER_BRANCH_ID);
                al_str_value.add(global.getAl_login_list().get(0).get(GlobalConstants.USER_BRANCH_ID));

                al_str_key.add(GlobalConstants.USER_ID);
                al_str_value.add(global.getAl_login_list().get(0).get(GlobalConstants.USER_ID));

                al_str_key.add(GlobalConstants.MONEY_BANKED_AMOUNT);
                al_str_value.add(str_deposit_amount);

                al_str_key.add(GlobalConstants.ACTION);
                al_str_value.add("bank_money");

                for (int i =0 ; i < al_str_key.size() ; i++)
                {
                    Log.i("Key",""+ al_str_key.get(i));
                    Log.i("Value",""+ al_str_value.get(i));
                }

                message = ws.MoneyBankedService(context, al_str_key, al_str_value);

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {

            gif_loader.setVisibility(View.INVISIBLE);

            if (message.equalsIgnoreCase("true"))
            {
                showDoneDialog();

            }
            else {
                Toast.makeText(context, "" + message, Toast.LENGTH_SHORT).show();
            }

        }

    }

    /**
     * Hit the service and get the money bank history
     */


    public class MoneyBankHistoryAsyncTask extends AsyncTask<String, String, String> {

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

                al_str_key.add(GlobalConstants.USER_BRANCH_ID);
                al_str_value.add(global.getAl_login_list().get(0).get(GlobalConstants.USER_BRANCH_ID));

                al_str_key.add(GlobalConstants.ACTION);
                al_str_value.add("money_banked");

                for (int i =0 ; i < al_str_key.size() ; i++)
                {
                    Log.e("Key",""+ al_str_key.get(i));
                    Log.e("Value",""+ al_str_value.get(i));
                }

                message = ws.MoneyBankHistoryService(context, al_str_key, al_str_value);

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {

            gif_loader.setVisibility(View.INVISIBLE);

            if (message.equalsIgnoreCase("true"))
            {
                moneyBankedAdapter= new MoneyBankedAdapter(MoneyBankedActivity.this);
                lv_money_banked.setAdapter(moneyBankedAdapter);
            }
            else {
                Toast.makeText(context, "" + message, Toast.LENGTH_SHORT).show();
            }

        }

    }


    /**
     * Hit the service and get the branch balance
     */

    public class GetBranchBalanceAsyncTask extends AsyncTask<String, String, String> {

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

                al_str_key.add(GlobalConstants.USER_BRANCH_ID);
                al_str_value.add(global.getAl_login_list().get(0).get(GlobalConstants.USER_BRANCH_ID));

                al_str_key.add(GlobalConstants.ACTION);
                al_str_value.add("get_branch_balance");

                for (int i =0 ; i < al_str_key.size() ; i++)
                {
                    Log.i("Key",""+ al_str_key.get(i));
                    Log.i("Value",""+ al_str_value.get(i));
                }

                message = ws.GetBranchBalanceService(context, al_str_key, al_str_value);

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {

            gif_loader.setVisibility(View.INVISIBLE);

            if (message.equalsIgnoreCase("true"))
            {
                tv_total_amount.setText(global.getStr_branch_balance());
            }
            else {
                Toast.makeText(context, "" + message, Toast.LENGTH_SHORT).show();
            }

        }

    }


    /**
     * SHow confirmation dialog
     */

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


    /**
     * Insufficient amount dialog
     */

    public void insufficientDialog() {

        final Dialog doneDialog = new Dialog(context);

        doneDialog.setContentView(R.layout.insufficient_amount_dialog);

        //doneDialog.create();
        doneDialog.show();

        TextView tv_ok = (TextView) doneDialog.findViewById(R.id.tv_ok);
        TextView tv_account_total = (TextView) doneDialog.findViewById(R.id.tv_account_total);
        TextView tv_wd_amount = (TextView) doneDialog.findViewById(R.id.tv_wd_amount);

        tv_account_total.setText("ACCOUNT TOTAL : "+global.getStr_branch_balance()+" TZS");
        tv_wd_amount.setText("MONEY TO BANK : "+str_deposit_amount+" TZS");

        tv_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                doneDialog.dismiss();
            }
        });
    }


}


