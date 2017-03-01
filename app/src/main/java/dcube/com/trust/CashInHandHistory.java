package dcube.com.trust;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
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
import WebServicesHandler.WebServices;
import dcube.com.trust.utils.CashHistoryAdapter;
import dcube.com.trust.utils.Global;
import okhttp3.OkHttpClient;
import pl.droidsonroids.gif.GifTextView;

public class CashInHandHistory extends Activity implements DatePickerDialog.OnDateSetListener,View.OnClickListener{

    ListView lv_cash_his;

    LinearLayout lin_date_from,lin_date_to;
    TextView tv_date_from,tv_date_to;
    DatePickerDialog dpd_from,dpd_to;

    GifTextView gif_loader;
    Global global;
    String str_branch;
    WebServices ws;
    Context context = CashInHandHistory.this;

    CheckNetConnection cn;

    CashHistoryAdapter adapter;

    TextView tv_submit,tv_total_amount;

    boolean is_date_selected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_cash_in_hand_history);

        global = (Global) getApplicationContext();

        cn = new CheckNetConnection(this);

        lin_date_from=(LinearLayout)findViewById(R.id.lin_date_from);
        lin_date_to=(LinearLayout)findViewById(R.id.lin_date_to);

        tv_date_from=(TextView)findViewById(R.id.tv_date_from);
        tv_date_to=(TextView)findViewById(R.id.tv_date_to);
        tv_submit=(TextView)findViewById(R.id.tv_submit);
        tv_total_amount = (TextView) findViewById(R.id.tv_total_amount);

        gif_loader = (GifTextView) findViewById(R.id.gif_loader);

        lv_cash_his= (ListView)findViewById(R.id.lv_cash_his);


        lin_date_from.setOnClickListener(this);
        lin_date_to.setOnClickListener(this);
        tv_submit.setOnClickListener(this);


        if (cn.isNetConnected())
        {
            new GetCashInHandBalanceAsyncTask().execute();
        }
        else
        {
            Toast.makeText(context, "Check Internet Connection", Toast.LENGTH_SHORT).show();
        }


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
                is_date_selected = true;
                new CashHistoryAsyncTask().execute();
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
            dpd_from = DatePickerDialog.newInstance(CashInHandHistory.this,
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
            dpd_to = DatePickerDialog.newInstance(CashInHandHistory.this,
                    now.get(Calendar.YEAR),
                    now.get(Calendar.MONTH),
                    now.get(Calendar.DAY_OF_MONTH)
            );
            dpd_to.show(getFragmentManager(), "Datepickerdialog");

            now.add(Calendar.DATE,0);
            dpd_to.setMaxDate(now);
        }

        if (view == tv_submit)
        {
            finish();

        }

    }

    public class CashHistoryAsyncTask extends AsyncTask<String, String, String> {

        OkHttpClient httpClient = new OkHttpClient();
        String resPonse = "";
        String message = "";

        String format_date_from,format_date_to;
        String str_date_from,str_date_to;

        @Override
        protected void onPreExecute() {

            gif_loader.setVisibility(View.VISIBLE);

            if (is_date_selected)
            {
                try {

                    str_date_from = tv_date_from.getText().toString();
                    str_date_to = tv_date_to.getText().toString();

                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");  //HH:mm:ss
                    Date date;

                    date = format.parse(str_date_from); //+" 00:00:00"
                    Log.e("From","Date "+date);

                    format_date_from = format.format(date);
                    Log.e("From","Str "+format_date_from);

                    date = format.parse(str_date_to);  //+" 00:00:00"
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

//                al_str_key.add(GlobalConstants.BRANCH);
//                al_str_value.add(str_branch);

                if (is_date_selected)
                {
                    al_str_key.add(GlobalConstants.EXP_DATE_FROM);
                    al_str_value.add(format_date_from);

                    al_str_key.add(GlobalConstants.EXP_DATE_TO);
                    al_str_value.add(format_date_to);
                }

                al_str_key.add(GlobalConstants.ACTION);
                al_str_value.add("get_cash_in_hand_history");

                Log.i("Key",""+al_str_key);
                Log.i("Value",""+al_str_value);

                message = ws.CashInHandHistoryService(context, al_str_key, al_str_value);

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {

            gif_loader.setVisibility(View.INVISIBLE);

            is_date_selected = false;

            if (message.equalsIgnoreCase("true"))
            {
                lv_cash_his.setVisibility(View.VISIBLE);
                adapter= new CashHistoryAdapter(context);
                lv_cash_his.setAdapter(adapter);
                adapter.notifyDataSetChanged();

            }
            else
            {
                lv_cash_his.setVisibility(View.INVISIBLE);
                Toast.makeText(context, "" + message, Toast.LENGTH_SHORT).show();
            }

        }

    }



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
     * Hit web service and get Cash in hand balance
     */


    public class GetCashInHandBalanceAsyncTask extends AsyncTask<String, String, String> {

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

          //  gif_loader.setVisibility(View.INVISIBLE);

            new CashHistoryAsyncTask().execute();

            if (message.equalsIgnoreCase("true"))
            {
                tv_total_amount.setText(global.getStr_cash_in_hand_balance()+" TZS");
            }
            else {
                Toast.makeText(context, "" + message, Toast.LENGTH_SHORT).show();
            }

        }

    }



}
