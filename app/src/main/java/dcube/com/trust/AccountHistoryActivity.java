package dcube.com.trust;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
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
import dcube.com.trust.utils.AccountHistoryAdapter;
import dcube.com.trust.utils.FormatString;
import dcube.com.trust.utils.Global;
import okhttp3.OkHttpClient;
import pl.droidsonroids.gif.GifTextView;

public class AccountHistoryActivity extends Activity implements DatePickerDialog.OnDateSetListener,View.OnClickListener {

    ListView list_account;

    TextView tv_total_amount;

    TextView tv_submit;

    EditText ed_expense_amount,ed_reason;
    GifTextView gif_loader;
    Global global;
    String str_amount,str_reason,str_branch;
    WebServices ws;
    Context context = AccountHistoryActivity.this;

    AccountHistoryAdapter adapter;

    RelativeLayout rel_parent_layout;

    CheckNetConnection cn;

    LinearLayout lin_date_from,lin_date_to;

    TextView tv_date_from,tv_date_to;

    DatePickerDialog dpd_from,dpd_to;

    public boolean isDateSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_account_history);

        global = (Global) getApplicationContext();

        cn = new CheckNetConnection(this);

        rel_parent_layout = (RelativeLayout) findViewById(R.id.rel_parent_layout);

        gif_loader = (GifTextView) findViewById(R.id.gif_loader);

        list_account= (ListView)findViewById(R.id.list_expense);

        ed_reason=(EditText)findViewById(R.id.ed_reason);
        tv_total_amount=(TextView)findViewById(R.id.tv_total_amount);
        tv_submit=(TextView)findViewById(R.id.tv_submit);

        lin_date_from=(LinearLayout)findViewById(R.id.lin_date_from);
        lin_date_to=(LinearLayout)findViewById(R.id.lin_date_to);

        tv_date_from=(TextView)findViewById(R.id.tv_date_from);
        tv_date_to=(TextView)findViewById(R.id.tv_date_to);


        str_branch = global.getAl_login_list().get(0).get(GlobalConstants.USER_BRANCH);

        tv_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();

            }
        });


        if (cn.isNetConnected())
        {
            new GetBranchBalanceAsyncTask().execute();
            new AccountHistoryAsyncTask().execute();
        }
        else
        {
            Toast.makeText(context, "Check Internet Connection", Toast.LENGTH_SHORT).show();
        }


        rel_parent_layout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                HideKeyboard.hideSoftKeyboard(AccountHistoryActivity.this);

                return false;
            }
        });

        lin_date_from.setOnClickListener(this);
        lin_date_to.setOnClickListener(this);

    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {


        if (view== dpd_from)
        {
            String d = ""+year+"-"+(monthOfYear+1)+"-"+dayOfMonth;
            tv_date_from.setText(d);
        }


        if (view == dpd_to)
        {
            String d = ""+year+"-"+(monthOfYear+1)+"-"+dayOfMonth;
            tv_date_to.setText(d);

            if (cn.isNetConnected())
            {
                isDateSelected = true;
                new AccountHistoryAsyncTask().execute();
            }
            else
            {
                Toast.makeText(context, "Check Internet Connection", Toast.LENGTH_SHORT).show();
            }


        }


    }

    @Override
    public void onClick(View view) {

        if(view == lin_date_from)
        {

            Calendar now = Calendar.getInstance();
            dpd_from = DatePickerDialog.newInstance(AccountHistoryActivity.this,
                    now.get(Calendar.YEAR),
                    now.get(Calendar.MONTH),
                    now.get(Calendar.DAY_OF_MONTH)
            );
            dpd_from.show(getFragmentManager(), "Datepickerdialog");

            now.add(Calendar.DATE,0);
            dpd_from.setMaxDate(now);

        }

        if (view== lin_date_to)
        {
            Calendar now = Calendar.getInstance();
            dpd_to = DatePickerDialog.newInstance(AccountHistoryActivity.this,
                    now.get(Calendar.YEAR),
                    now.get(Calendar.MONTH),
                    now.get(Calendar.DAY_OF_MONTH)
            );
            dpd_to.show(getFragmentManager(), "Datepickerdialog");

            now.add(Calendar.DATE,0);
            dpd_to.setMaxDate(now);
        }
    }


    /**
     * Calls the web service and get account history
     */


    public class AccountHistoryAsyncTask extends AsyncTask<String, String, String> {

        OkHttpClient httpClient = new OkHttpClient();
        String resPonse = "";
        String message = "";
        String format_date_from,format_date_to;
        String str_date_from,str_date_to;


        @Override
        protected void onPreExecute() {

            gif_loader.setVisibility(View.VISIBLE);

            if (isDateSelected)
            {
                str_date_from = tv_date_from.getText().toString();
                str_date_to = tv_date_to.getText().toString();

                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date;

                try {

                    date = format.parse(str_date_from+" 00:00:00");
                    Log.e("From","Date "+date);

                    format_date_from = format.format(date);
                    Log.e("From","Str "+format_date_from);

                    date = format.parse(str_date_to+" 00:00:00");
                    Log.e("From","Date "+date);

                    format_date_to = format.format(date);
                    Log.e("From","Str "+format_date_to);


                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }

        }

        @Override
        protected String doInBackground(String... params) {

            try {

                ArrayList<String> al_str_key = new ArrayList<>();
                ArrayList<String> al_str_value = new ArrayList<>();

                al_str_key.add(GlobalConstants.USER_BRANCH_ID);
                al_str_value.add(global.getAl_login_list().get(0).get(GlobalConstants.USER_BRANCH_ID));

                if (isDateSelected)
                {
                    al_str_key.add(GlobalConstants.SOLD_START_DATE);
                    al_str_value.add(format_date_from);

                    al_str_key.add(GlobalConstants.SOLD_END_DATE);
                    al_str_value.add(format_date_to);
                }

                al_str_key.add(GlobalConstants.ACTION);
                al_str_value.add("get_branch_balance_history");

                for (int i =0 ; i < al_str_key.size() ; i++)
                {
                    Log.e("Key",""+ al_str_key.get(i));
                    Log.e("Value",""+ al_str_value.get(i));
                }

                message = ws.BranchBalanceHistoryService(context, al_str_key, al_str_value);

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {

            gif_loader.setVisibility(View.INVISIBLE);
            isDateSelected = false;

            if (message.equalsIgnoreCase("true"))
            {
                adapter= new AccountHistoryAdapter(AccountHistoryActivity.this);
                list_account.setAdapter(adapter);
            }
            else {
                Toast.makeText(context, "" + message, Toast.LENGTH_SHORT).show();
            }

        }

    }



    /**
     * Calls the web service and get current balance of Branch
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
                String formatted_bal =  global.getStr_branch_balance();

                formatted_bal = FormatString.getCommaInString(formatted_bal);

                tv_total_amount.setText(formatted_bal+" TZS");  //global.getStr_branch_balance()
            }
            else {
                Toast.makeText(context, "" + message, Toast.LENGTH_SHORT).show();
            }

        }

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

    public void updateList(Context context)
    {
        new GetBranchBalanceAsyncTask().execute();
    }


}
