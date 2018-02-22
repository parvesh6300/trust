package dcube.com.trust;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import WebServicesHandler.CheckNetConnection;
import WebServicesHandler.GlobalConstants;
import WebServicesHandler.HideKeyboard;
import WebServicesHandler.WebServices;
import dcube.com.trust.utils.FormatString;
import dcube.com.trust.utils.Global;
import dcube.com.trust.utils.MoneyBankedAdapter;
import okhttp3.OkHttpClient;
import pl.droidsonroids.gif.GifTextView;

public class MoneyBankedActivity extends FragmentActivity implements DatePickerDialog.OnDateSetListener
//TimePickerDialog.OnTimeSetListener,
{


    ListView lv_money_banked;

    MoneyBankedAdapter moneyBankedAdapter;

    TextView tv_deposit,tv_money_bank;

    EditText ed_deposit_amount;
    GifTextView gif_loader;

    Global global;
    WebServices ws;

    CustomDialogClass cdd;
    String str_deposit_amount;

    RelativeLayout rel_parent_layout;

    CheckNetConnection cn;

    Context context;

    GetBranchBalance getBalance;

    RelativeLayout datepicker;
   // RelativeLayout timepicker;

    int int_selected_day;
    int int_today;

    TextView date;
   // TextView time;

    String str_time_pick,str_date,str_time;

    String format_time = "",format_date = "";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_money_banked);

        context = this;

        cn = new CheckNetConnection(context);

        rel_parent_layout = (RelativeLayout) findViewById(R.id.rel_parent_layout);

        global = (Global) context.getApplicationContext();

        getBalance = new GetBranchBalance(context);

        datepicker = (RelativeLayout) findViewById(R.id.datepicker);

       // timepicker = (RelativeLayout) findViewById(R.id.timepicker);

        date = (TextView) findViewById(R.id.datepick);

      //  time = (TextView) findViewById(R.id.timepick);

        gif_loader = (GifTextView) findViewById(R.id.gif_loader);

        lv_money_banked=(ListView)findViewById(R.id.lv_money_banked);

        ed_deposit_amount=(EditText)findViewById(R.id.ed_deposit_amount);

       // tv_total_amount=(TextView)findViewById(R.id.tv_total_amount);
        tv_deposit=(TextView)findViewById(R.id.tv_deposit);
        tv_money_bank = (TextView) findViewById(R.id.tv_money_bank);


        tv_deposit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ed_deposit_amount.getText().toString().matches(""))
                {
                    Toast.makeText(context, "Enter Amount", Toast.LENGTH_SHORT).show();
                }
                else if ((ed_deposit_amount.getText().toString().equalsIgnoreCase("0")))
                {
                    Toast.makeText(context, "Amount should be greater than 0", Toast.LENGTH_SHORT).show();
                }
                else if (date.getText().toString().matches("Date"))
                {
                    Toast.makeText(context, "Specify Date", Toast.LENGTH_SHORT).show();
                }
//                else if (time.getText().toString().matches("Time"))
//                {
//                    Toast.makeText(context, "Specify Time", Toast.LENGTH_SHORT).show();
//                }
                else
                {
                    str_deposit_amount = ed_deposit_amount.getText().toString();

                    float account_total = Float.parseFloat(global.getStr_money_to_bank());
                    float deposit_amount = Float.parseFloat(str_deposit_amount);

                    str_date = date.getText().toString();
                    str_time =  "00:00:00";           // str_time_pick ; //time.getText().toString();

//                    cdd = new CustomDialogClass(MoneyBankedActivity.this);
//                    cdd.show();

                    if (account_total > deposit_amount)
                    {
                        cdd = new CustomDialogClass(MoneyBankedActivity.this);
                        cdd.show();
                    }
                    else
                    {
                        insufficientDialog();
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


        datepicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Calendar now = Calendar.getInstance();


                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        MoneyBankedActivity.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.setAccentColor(getResources().getColor(R.color.mdtp_accent_color));

                now.add(Calendar.DATE,0);
                //dpd.setMinDate(now);

                dpd.show(getFragmentManager(), "Datepickerdialog");

                dpd.setMaxDate(now);

            }
        });

        /*

        timepicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Calendar now = Calendar.getInstance();

                int_today = now.get(Calendar.DATE);

                int hour = now.get(Calendar.HOUR_OF_DAY);
                int min = now.get(Calendar.MINUTE);
                int sec = now.get(Calendar.SECOND);

                TimePickerDialog tpd = TimePickerDialog.newInstance(
                        MoneyBankedActivity.this,
                        now.get(Calendar.HOUR_OF_DAY),
                        now.get(Calendar.MINUTE),
                        false);

                tpd.setAccentColor(getResources().getColor(R.color.mdtp_accent_color));

                if (int_today == int_selected_day)
                {
                    //tpd.setMinTime(now.get(Calendar.HOUR_OF_DAY),Calendar.MINUTE,Calendar.SECOND);
                    tpd.setMaxTime(hour,min,sec);
                }

                tpd.show(getFragmentManager(), "Timepickerdialog"); //Datepickerdialog



            }
        });


        */



        if (cn.isNetConnected())
        {
          //  new GetBranchBalanceAsyncTask().execute();

            new GetMoneyToBankAsyncTask().execute();

            new MoneyBankHistoryAsyncTask().execute();
        }
        else
        {
            Toast.makeText(MoneyBankedActivity.this, "Check Internet Connection", Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

        String d = ""+year+"-"+(monthOfYear+1)+"-"+dayOfMonth;

        int_selected_day = dayOfMonth;

        date.setText(d);
    }

    /*

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {

        String str_format_time="";

        str_time_pick = hourOfDay+":"+minute+":"+second;

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try
        {
            Date date = format.parse("2017-01-30 "+str_time_pick);
            str_format_time = new SimpleDateFormat("hh:mm a").format(date);
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }

        Log.i("Time","Picker "+str_format_time);

        time.setText(str_format_time);
    }

    */

    /**
     * Transaction custom dialog
     */

    public class CustomDialogClass extends Dialog {

        public Activity c;

        public TextView tv_balance,tv_account_total,tv_deposit_money;
        public Button confirm,cancel;

        private String str_bal;

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

            confirm = (Button) findViewById(R.id.confirm);
            cancel = (Button) findViewById(R.id.cancel);

            tv_balance = (TextView) findViewById(R.id.tv_balance);
            tv_account_total = (TextView) findViewById(R.id.tv_account_total);
            tv_deposit_money = (TextView) findViewById(R.id.tv_deposit_money);

            float account_total = Float.parseFloat(global.getStr_money_to_bank());

            String money_to_bank = global.getStr_money_to_bank();

            money_to_bank = FormatString.getCommaInString(money_to_bank);

            tv_account_total.setText("Account Total : "+money_to_bank+" TZS");  //global.getStr_money_to_bank()

            float deposit_amount = Float.parseFloat(str_deposit_amount);

            str_deposit_amount = FormatString.getCommaInString(str_deposit_amount);

            tv_deposit_money.setText("Money to Bank : "+str_deposit_amount+" TZS");

            float balance = account_total - deposit_amount;

            str_bal = String.valueOf(balance);

            str_bal = FormatString.getCommaInString(str_bal);

            tv_balance.setText("Projected Balance : "+str_bal+" TZS");

            confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    dismiss();

                    if (cn.isNetConnected())
                    {
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                        try {

                            Date date = format.parse(str_date+" "+str_time);
                            Log.e("Date","Format "+date);

                            format_time = format.format(date);
                            Log.e("Time","Format "+format_time);

                            format_date = new SimpleDateFormat("yyyy-MM-dd").format(date);
                            Log.e("Date","Format "+format_date);

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }


                        new MoneyBankAsyncTask().execute();
                    }
                    else
                    {
                        Toast.makeText(MoneyBankedActivity.this, "Check Internet Connection", Toast.LENGTH_SHORT).show();
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

                al_str_key.add(GlobalConstants.MONEY_BANKED_REASON);
                al_str_value.add("Money Banked");

                al_str_key.add(GlobalConstants.CREATED);
                al_str_value.add(format_time);

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
                date.setText("Date");

               // time.setText("Time");

                ed_deposit_amount.setText("");

                showDoneDialog();

            }
            else
            {
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
                al_str_value.add("money_banked_history");

                for (int i =0 ; i < al_str_key.size() ; i++)
                {
                    Log.i("Key",""+ al_str_key.get(i));
                    Log.i("Value",""+ al_str_value.get(i));
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
     * Hit the service and get the money to be bank balance
     */

    public class GetMoneyToBankAsyncTask extends AsyncTask<String, String, String> {

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
                al_str_value.add("get_money_to_be_banked");

                for (int i =0 ; i < al_str_key.size() ; i++)
                {
                    Log.i("Key",""+ al_str_key.get(i));
                    Log.i("Value",""+ al_str_value.get(i));
                }

                message = ws.GetMoneyToBankService(context, al_str_key, al_str_value);

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

                String money_to_bank =  global.getStr_money_to_bank(); // global.getStr_money_to_bank()

                money_to_bank = FormatString.getCommaInString(money_to_bank);

                tv_money_bank.setText(money_to_bank+" TZS");

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

        Button tv_yes = (Button) doneDialog.findViewById(R.id.tv_yes);
        Button tv_no = (Button) doneDialog.findViewById(R.id.tv_no);
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
               // finish();

                new GetMoneyToBankAsyncTask().execute();

//                new GetBranchBalanceAsyncTask().execute();

                new MoneyBankHistoryAsyncTask().execute();

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

        Button tv_ok = (Button) doneDialog.findViewById(R.id.tv_ok);
        TextView tv_account_total = (TextView) doneDialog.findViewById(R.id.tv_account_total);
        TextView tv_wd_amount = (TextView) doneDialog.findViewById(R.id.tv_wd_amount);

        tv_account_total.setText("ACCOUNT TOTAL : "+global.getStr_money_to_bank()+" TZS");
        tv_wd_amount.setText("MONEY TO BANK : "+str_deposit_amount+" TZS");

        tv_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                doneDialog.dismiss();
            }
        });
    }


    /**
     * Show Loader
     * @param context
     * @param show boolean
     */


    public void loader(Context context,boolean show)
    {
        if (show)
        {
            gif_loader.setVisibility(View.VISIBLE);
        }
        else
        {
            gif_loader.setVisibility(View.INVISIBLE);
        }

    }


    /**
     * Hit Service and Update Balance
     * @param context
     */


    public void updateBalance(Context context)
    {
       // new GetBranchBalanceAsyncTask().execute();

        new GetMoneyToBankAsyncTask().execute();
    }




}


