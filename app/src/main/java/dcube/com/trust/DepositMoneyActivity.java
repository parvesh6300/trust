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

    String branch_balance;

    GetBranchBalance get_balance;

    RelativeLayout rel_parent_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_deposit_money);

        global = (Global) getApplicationContext();


        cn = new CheckNetConnection(this);

        get_balance = new GetBranchBalance(this);

        rel_parent_layout = (RelativeLayout)  findViewById(R.id.rel_parent_layout);

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
                else if (ed_deposit_amount.getText().toString().equalsIgnoreCase("0"))
                {
                    Toast.makeText(DepositMoneyActivity.this, "Amount should be greater than 0", Toast.LENGTH_SHORT).show();
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
            new GetBranchBalanceAsyncTask().execute();
            new DepositHistoryAsyncTask().execute();
        }
        else
        {
            Toast.makeText(context, "Check Internet Connection", Toast.LENGTH_SHORT).show();
        }


        rel_parent_layout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                HideKeyboard.hideSoftKeyboard(DepositMoneyActivity.this);
               // hideSoftKeyboard(DepositMoneyActivity.this);
                return false;
            }
        });

    }


    /**
     * Custom dialog show details of transaction
     */

    public class CustomDialogClass extends Dialog {

        public Activity c;

        public TextView cancel;
        public TextView confirm,tv_deposit,tv_total_amount,tv_balance;

        float int_total_amount,int_deposit,int_balance;


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

            branch_balance = global.getStr_branch_balance();

            tv_total_amount.setText("ACCOUNT TOTAL : "+branch_balance);
            tv_deposit.setText("DEPOSIT : "+str_deposit_amount);

            if (! branch_balance.equalsIgnoreCase(null))
            {
                int_total_amount = Float.parseFloat(branch_balance);
                int_deposit = Float.parseFloat(str_deposit_amount);
                int_balance = int_total_amount + int_deposit;

                tv_balance.setText("PROJECTED BALANCE : "+String.valueOf(int_balance));

            }
            else
            {
                Toast.makeText(DepositMoneyActivity.this, "Account is very low", Toast.LENGTH_SHORT).show();
            }


            confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    dismiss();

                    if (cn.isNetConnected())
                    {
                        tv_deposit.setClickable(false);
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


    /**
     * Hit web service and get branch balance
     */


    public class GetBranchBalanceAsyncTask extends AsyncTask<String, String, String> {

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

            if (message.equalsIgnoreCase("true"))
            {
                tv_total_amount.setText(global.getStr_branch_balance()+" TZS");
            }
            else {
                Toast.makeText(context, "" + message, Toast.LENGTH_SHORT).show();
            }

        }

    }


    /**
     * Hit web service and deposit money for user
     */


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

                al_str_key.add(GlobalConstants.USER_BRANCH_ID);
                al_str_value.add(global.getAl_login_list().get(0).get(GlobalConstants.USER_BRANCH_ID));

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
                    Log.i("Key",""+ al_str_key.get(i));
                    Log.i("Value",""+ al_str_value.get(i));
                }

                message = ws.DepositMoneyService(context, al_str_key, al_str_value);

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {

            gif_loader.setVisibility(View.INVISIBLE);

            tv_deposit.setClickable(true);

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
     * Custom dialog show money deposited
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
     * Hit web service and get deposit history
     */


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

                al_str_key.add(GlobalConstants.USER_BRANCH_ID);
                al_str_value.add(global.getAl_login_list().get(0).get(GlobalConstants.USER_BRANCH_ID));

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

            gif_loader.setVisibility(View.INVISIBLE);

            if (message.equalsIgnoreCase("true"))
            {
                depositAdapter= new DepositAdapter(context);
                list_deposit.setAdapter(depositAdapter);

            }
            else {
                Toast.makeText(context, "" + message, Toast.LENGTH_SHORT).show();
            }

        }

    }



//    public static void hideSoftKeyboard(Activity activity)
//    {
//        InputMethodManager inputMethodManager = (InputMethodManager)
//                activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
//
//        inputMethodManager.hideSoftInputFromWindow(
//                activity.getCurrentFocus().getWindowToken(), 0);
//    }




}
