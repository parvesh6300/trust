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
import dcube.com.trust.utils.DepositAdapter;
import dcube.com.trust.utils.FormatString;
import dcube.com.trust.utils.Global;
import okhttp3.OkHttpClient;
import pl.droidsonroids.gif.GifTextView;

public class DepositMoneyActivity extends FragmentActivity implements DatePickerDialog.OnDateSetListener
//TimePickerDialog.OnTimeSetListener,
{

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

    RelativeLayout datepicker;
    //RelativeLayout timepicker;

    int int_selected_day;
    int int_today;

    TextView date;
 //   TextView time;

    String str_time_pick,str_date,str_time;

    String format_time = "",format_date = "";


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

        datepicker = (RelativeLayout) findViewById(R.id.datepicker);

      //  timepicker = (RelativeLayout) findViewById(R.id.timepicker);

        date = (TextView) findViewById(R.id.tv_date_label);

     //   time = (TextView) findViewById(R.id.tv_time_label);



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
                else if (date.getText().toString().matches("Date"))
                {
                    Toast.makeText(DepositMoneyActivity.this, "Specify Date", Toast.LENGTH_SHORT).show();
                }
//                else if (time.getText().toString().matches("Time"))
//                {
//                    Toast.makeText(DepositMoneyActivity.this, "Specify Time", Toast.LENGTH_SHORT).show();
//                }
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

                    str_date = date.getText().toString();
                    str_time =  "00:00:00";        //str_time_pick ; //time.getText().toString();

                    cdd = new CustomDialogClass(DepositMoneyActivity.this);
                    cdd.show();
                }

            }
        });


        rel_parent_layout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                HideKeyboard.hideSoftKeyboard(DepositMoneyActivity.this);
               // hideSoftKeyboard(DepositMoneyActivity.this);
                return false;
            }
        });




        datepicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Calendar now = Calendar.getInstance();


                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        DepositMoneyActivity.this,
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
                        DepositMoneyActivity.this,
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

        callWebServices();

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
     * Custom dialog show details of transaction
     */

    public class CustomDialogClass extends Dialog {

        public Activity c;

        public Button cancel,confirm;
        public TextView tv_deposit,tv_total_amount,tv_balance;

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

            confirm = (Button) findViewById(R.id.confirm);
            cancel = (Button) findViewById(R.id.cancel);
            tv_deposit = (TextView) findViewById(R.id.tv_deposit);
            tv_balance = (TextView) findViewById(R.id.tv_balance);
            tv_total_amount = (TextView) findViewById(R.id.tv_total_amount);

            branch_balance = global.getStr_branch_balance();

            String bra_bal =  branch_balance;

            bra_bal = FormatString.getCommaInString(bra_bal);

            tv_total_amount.setText("ACCOUNT TOTAL : "+bra_bal);

           // tv_total_amount.setText("ACCOUNT TOTAL : "+branch_balance);


            String deposit_amnt =  str_deposit_amount;

            deposit_amnt = FormatString.getCommaInString(deposit_amnt);

            tv_deposit.setText("DEPOSIT : "+deposit_amnt);

          //  tv_deposit.setText("DEPOSIT : "+str_deposit_amount);

            if (! branch_balance.equalsIgnoreCase(null))
            {
                int_total_amount = Float.parseFloat(branch_balance);
                int_deposit = Float.parseFloat(str_deposit_amount);
                int_balance = int_total_amount + int_deposit;

                String str_bal =  String.valueOf(int_balance);

                str_bal = FormatString.getCommaInString(str_bal);

                tv_balance.setText("PROJECTED BALANCE : "+str_bal);  //String.valueOf(int_balance)

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
                String bra_bal =  global.getStr_branch_balance();

                bra_bal = FormatString.getCommaInString(bra_bal);

                tv_total_amount.setText(bra_bal+" TZS");

               // tv_total_amount.setText(global.getStr_branch_balance()+" TZS");
            }
            else
            {
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

                al_str_key.add(GlobalConstants.CREATED);
                al_str_value.add(format_time);

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
                date.setText("Date");

               // time.setText("Time");

                callWebServices();

                showDoneDialog();
            }
            else
            {
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

                ed_deposit_amount.setText("");
                ed_remark.setText("");


               // finish();

            }
        });
    }


    /**
     * Hit web service and get deposit history
     */


    private class DepositHistoryAsyncTask extends AsyncTask<String, String, String> {

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


    public void callWebServices()
    {
        if (cn.isNetConnected())
        {
            new GetBranchBalanceAsyncTask().execute();
            new DepositHistoryAsyncTask().execute();
        }
        else
        {
            Toast.makeText(context, "Check Internet Connection", Toast.LENGTH_SHORT).show();
        }

    }



}
