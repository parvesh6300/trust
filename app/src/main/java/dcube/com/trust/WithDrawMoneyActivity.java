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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import WebServicesHandler.CheckNetConnection;
import WebServicesHandler.GlobalConstants;
import WebServicesHandler.HideKeyboard;
import WebServicesHandler.WebServices;
import dcube.com.trust.utils.Global;
import okhttp3.OkHttpClient;
import pl.droidsonroids.gif.GifTextView;


public class WithDrawMoneyActivity extends Activity implements View.OnClickListener{


    EditText ed_petty_amount,ed_exp_amount,ed_wd_amount;

    TextView tv_assign_petty,tv_assign_exp,tv_wd_amount,tv_total_amount,tv_bank_balance,tv_submit;

    Context context = this;

    RelativeLayout rel_parent_layout;

    GifTextView gif_loader;

    CheckNetConnection cn;

    Global global;

    WebServices ws;

    String str_rsn,str_amount;

    CustomDialogClass cd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_with_draw_money);

        global = (Global) getApplicationContext();

        cn = new CheckNetConnection(this);

        gif_loader = (GifTextView) findViewById(R.id.gif_loader);

        rel_parent_layout = (RelativeLayout) findViewById(R.id.rel_parent_layout);

        tv_assign_petty = (TextView) findViewById(R.id.tv_assign_petty);
        tv_assign_exp = (TextView) findViewById(R.id.tv_assign_exp);
        tv_wd_amount = (TextView) findViewById(R.id.tv_wd_amount);
        tv_total_amount = (TextView) findViewById(R.id.tv_total_amount);
        tv_bank_balance = (TextView) findViewById(R.id.tv_bank_balance);
        tv_submit = (TextView) findViewById(R.id.tv_submit);

        ed_petty_amount = (EditText)findViewById(R.id.ed_petty_amount);
        ed_exp_amount = (EditText)findViewById(R.id.ed_exp_amount);
        ed_wd_amount = (EditText)findViewById(R.id.ed_wd_amount);

        tv_assign_exp.setOnClickListener(this);
        tv_wd_amount.setOnClickListener(this);
        tv_assign_petty.setOnClickListener(this);
        tv_submit.setOnClickListener(this);


        rel_parent_layout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                HideKeyboard.hideSoftKeyboard(WithDrawMoneyActivity.this);

                return false;
            }
        });


        if (cn.isNetConnected())
        {
            new GetBankBalanceAsyncTask().execute();
            new GetCashInHandBalanceAsyncTask().execute();
        }
        else
        {
            Toast.makeText(WithDrawMoneyActivity.this, "Check Net Connection", Toast.LENGTH_SHORT).show();
        }

    }


    @Override
    public void onClick(View view)
    {

        if (view == tv_assign_exp)
        {
            if (validate(ed_exp_amount))
            {
                Log.i("tv_assign_exp","Done");

                str_rsn = "EXPENSE";
                str_amount = ed_exp_amount.getText().toString();

                validateAmount(global.getStr_cash_in_hand_balance(),false);

            }

        }

        if (view == tv_assign_petty)
        {
            if (validate(ed_petty_amount))
            {
                Log.i("tv_assign_petty","Done");

                str_rsn = "PETTY CASH";
                str_amount = ed_petty_amount.getText().toString();

                validateAmount(global.getStr_cash_in_hand_balance(),false);
            }

        }


        if (view == tv_wd_amount)
        {
            if (validate(ed_wd_amount))
            {
                Log.i("tv_wd_amount","Done");

                str_rsn = "WITHDRAW";
                str_amount = ed_wd_amount.getText().toString();

                validateAmount(global.getStr_bank_bra_bal(),true);

            }

        }

        if (view == tv_submit)
        {
            finish();
        }


    }


    /**
     * Validate edittext is empty or filled
     * @param ed edittext
     * @return boolean
     */

    public boolean validate(EditText ed)
    {
        if (ed.getText().toString().matches(""))
        {
            Toast.makeText(context, "Enter Amount", Toast.LENGTH_SHORT).show();
        }
        else
        {
            return true;
        }

        return false;
    }


    /**
     * Hit Service and assign money
     */

    public class AssignPettyCashAsyncTask extends AsyncTask<String, String, String>
    {

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

                al_str_key.add(GlobalConstants.HAND_REASON);
                al_str_value.add(str_rsn);

                al_str_key.add(GlobalConstants.HAND_AMOUNT);
                al_str_value.add(str_amount);

                al_str_key.add(GlobalConstants.ACTION);
                al_str_value.add("add_petty_cash");

                for (int i =0 ; i < al_str_key.size() ; i++)
                {
                    Log.i("Key",""+ al_str_key.get(i));
                    Log.i("Value",""+ al_str_value.get(i));
                }

                message = ws.AssignCashInHandService(context, al_str_key, al_str_value);

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
                finish();
            }
            else {
                Toast.makeText(context, "" + message, Toast.LENGTH_SHORT).show();
            }

        }

    }




    /**
     * Hit Service and assign money
     */

    public class AssignCashInHandAsyncTask extends AsyncTask<String, String, String>
    {

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

                al_str_key.add(GlobalConstants.HAND_REASON);
                al_str_value.add(str_rsn);

                al_str_key.add(GlobalConstants.HAND_AMOUNT);
                al_str_value.add(str_amount);

                al_str_key.add(GlobalConstants.ACTION);
                al_str_value.add("assign_cash_in_hand");

                for (int i =0 ; i < al_str_key.size() ; i++)
                {
                    Log.i("Key",""+ al_str_key.get(i));
                    Log.i("Value",""+ al_str_value.get(i));
                }

                message = ws.AssignCashInHandService(context, al_str_key, al_str_value);

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
                finish();
            }
            else {
                Toast.makeText(context, "" + message, Toast.LENGTH_SHORT).show();
            }

        }

    }



    /**
     * Show the details of transaction
     */

    public class CustomDialogClass extends Dialog
    {

        public Activity c;

        public TextView cancel,tv_account_total,tv_wd_amount;
        public TextView confirm,tv_balance,tv_rsn;

        String str_total;

        boolean is_branch_bal;

        public CustomDialogClass(Activity a,String total,boolean is_bra_bal) {
            super(a);
            // TODO Auto-generated constructor stub
            this.c = a;

            str_total = total;

            is_branch_bal = is_bra_bal;

        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            requestWindowFeature(Window.FEATURE_NO_TITLE);

            setContentView(R.layout.cash_in_hand_dialog);

            confirm = (TextView) findViewById(R.id.confirm);
            cancel = (TextView) findViewById(R.id.cancel);

            tv_account_total = (TextView) findViewById(R.id.tv_account_total);
            tv_wd_amount = (TextView) findViewById(R.id.tv_wd_amount);
            tv_balance = (TextView) findViewById(R.id.tv_balance);
            tv_rsn = (TextView) findViewById(R.id.tv_rsn);

            float account_total = Float.parseFloat(str_total);  //global.getStr_cash_in_hand_balance()
            float wd_amount = Float.parseFloat(str_amount);
            float balance = account_total - wd_amount ;

            tv_account_total.setText("ACCOUNT TOTAL : "+str_total+" TZS");  //global.getStr_cash_in_hand_balance()
            tv_wd_amount.setText("AMOUNT : "+str_amount+" TZS");
            tv_balance.setText("PROJECTED BALANCE : "+String.valueOf(balance)+" TZS");
            tv_rsn.setText("REASON : "+str_rsn);


            confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (cn.isNetConnected())
                    {
                        dismiss();

                        if (is_branch_bal)
                        {
                            new AssignCashInHandAsyncTask().execute();
                        }
                        else
                        {
                            if(str_rsn.equalsIgnoreCase("EXPENSE"))
                            {
                                new AssignExpenseBalanceAsyncTask().execute();
                            }
                            else if (str_rsn.equalsIgnoreCase("PETTY CASH"))
                            {
                                new AssignPettyCashAsyncTask().execute();
                            }
                            else
                            {
                                Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
                            }

                        }

                    }
                    else {
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
     * Check filled amount is sufficient or not
     */

    public void validateAmount(String total_bal,boolean is_branch_bal)
    {
        float total_balance = Float.parseFloat(total_bal);  //global.getStr_cash_in_hand_balance()
        float wd_amount = Float.parseFloat(str_amount);

        if (total_balance > wd_amount)
        {
            cd = new CustomDialogClass(this,total_bal,is_branch_bal);
            cd.show();

        }
        else
        {
            insufficientDialog(total_bal);
        }

    }


    /**
     * Amount not sufficient dialog
     */

    public void insufficientDialog(String total_bal)
    {

        final Dialog doneDialog = new Dialog(context);

        doneDialog.setContentView(R.layout.insufficient_amount_dialog);

        //doneDialog.create();
        doneDialog.show();

        TextView tv_ok = (TextView) doneDialog.findViewById(R.id.tv_ok);
        TextView tv_account_total = (TextView) doneDialog.findViewById(R.id.tv_account_total);
        TextView tv_wd_amount = (TextView) doneDialog.findViewById(R.id.tv_wd_amount);

        tv_account_total.setText("ACCOUNT TOTAL : "+total_bal+" TZS");  //global.getStr_branch_balance()
        tv_wd_amount.setText("WITHDRAW : "+str_amount+" TZS");

        tv_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                doneDialog.dismiss();
            }
        });
    }



    /**
     * Hit web service and get Cash in hand balance
     */


    public class GetCashInHandBalanceAsyncTask extends AsyncTask<String, String, String> {

        OkHttpClient httpClient = new OkHttpClient();
        String resPonse = "";
        String message = "";

        @Override
        protected void onPreExecute() {

         //   gif_loader.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... params) {

            try {

                ArrayList<String> al_str_key = new ArrayList<>();
                ArrayList<String> al_str_value = new ArrayList<>();

                al_str_key.add(GlobalConstants.USER_BRANCH_ID);
                al_str_value.add(global.getAl_login_list().get(0).get(GlobalConstants.USER_BRANCH_ID));

                al_str_key.add(GlobalConstants.ACTION);
                al_str_value.add("get_cash_in_hand_balance");

                for (int i =0 ; i < al_str_key.size() ; i++)
                {
                    Log.i("Key",""+ al_str_key.get(i));
                    Log.i("Value",""+ al_str_value.get(i));
                }

                message = ws.GetCashInHandBalanceService(context, al_str_key, al_str_value);

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
                tv_total_amount.setText(global.getStr_cash_in_hand_balance()+" TZS");
            }
            else {
                Toast.makeText(context, "" + message, Toast.LENGTH_SHORT).show();
            }

        }

    }



    /**
     * Hit web service and get Cash in hand balance
     */


    public class GetBankBalanceAsyncTask extends AsyncTask<String, String, String> {

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

                message = ws.GetBankBranchBalanceService(context, al_str_key, al_str_value);

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {

         //   gif_loader.setVisibility(View.INVISIBLE);

            if (message.equalsIgnoreCase("true"))
            {
                tv_bank_balance.setText(global.getStr_bank_bra_bal()+" TZS");
            }
            else {
                Toast.makeText(context, "" + message, Toast.LENGTH_SHORT).show();
            }

        }

    }


    /**
     * Hit Service and assign money
     */

    public class AssignExpenseBalanceAsyncTask extends AsyncTask<String, String, String>
    {

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

                al_str_key.add(GlobalConstants.HAND_EXP_REASON);
                al_str_value.add(str_rsn);

                al_str_key.add(GlobalConstants.HAND_AMOUNT);
                al_str_value.add(str_amount);

                al_str_key.add(GlobalConstants.ACTION);
                al_str_value.add("add_expense_balance");

                for (int i =0 ; i < al_str_key.size() ; i++)
                {
                    Log.i("Key",""+ al_str_key.get(i));
                    Log.i("Value",""+ al_str_value.get(i));
                }

                message = ws.AssignExpenseBalService(context, al_str_key, al_str_value);

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
                finish();
            }
            else {
                Toast.makeText(context, "" + message, Toast.LENGTH_SHORT).show();
            }

        }

    }



}
