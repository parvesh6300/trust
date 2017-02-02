package dcube.com.trust;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;

import WebServicesHandler.CheckNetConnection;
import WebServicesHandler.GlobalConstants;
import WebServicesHandler.WebServices;
import dcube.com.trust.utils.CalendarListAdapter;
import dcube.com.trust.utils.Global;
import okhttp3.OkHttpClient;
import pl.droidsonroids.gif.GifTextView;

public class CalendarActivity extends Activity
{
    ListView list;
    CalendarListAdapter calendarListAdapter;
    GifTextView gif_loader;
    Context context = CalendarActivity.this;
    Global global;
    WebServices ws;

    CheckNetConnection cn;

    CalendarCustomView cv;
    HashSet<Date> events;
    TextView tv_continue,tv_apmt_size;

    String str_date;

    Calendar cl = Calendar.getInstance();

    public static Handler h;

    SimpleDateFormat format;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_calendar);

        cn = new CheckNetConnection(context);

        global = (Global) getApplicationContext();

        tv_apmt_size = (TextView) findViewById(R.id.tv_apmt_size);

        list = (ListView) findViewById(R.id.list);
        gif_loader = (GifTextView) findViewById(R.id.gif_loader);
        tv_continue = (TextView) findViewById(R.id.tv_continue);

//        events = new HashSet<>();
//        events.add(new Date());

        cv = ((CalendarCustomView)findViewById(R.id.calendar_view));
 //       cv.updateCalendar(events);


        format = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
        str_date = format.format(cl.getTime());


        if (global.isAppointmentSelected() || global.isServiceAppointment() )
        {
            tv_continue.setVisibility(View.VISIBLE);
        }
        else
        {
            tv_continue.setVisibility(View.GONE);
        }


        // assign event handler
        cv.setEventHandler(new CalendarCustomView.EventHandler()
        {
            @Override
            public void onDayLongPress(Date date)
            {

                str_date = format.format(date);

                events = new HashSet<>();
                events.add(date);
                cv.updateCalendar(events);

                new GetAppointmentAsyncTask().execute();

            }
        });


        tv_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(CalendarActivity.this,AddAppointmentActivity.class));
            }
        });


        if (cn.isNetConnected())
        {
            new GetAppointmentAsyncTask().execute();
        }
        else
        {
            Toast.makeText(context, "Check Internet Connection", Toast.LENGTH_SHORT).show();
        }

        h = new Handler() {

            public void handleMessage(Message msg) {
                super.handleMessage(msg);

                switch(msg.what) {

                    case 0:
                        finish();
                        break;

                }
            }

        };


    }


    public class GetAppointmentAsyncTask extends AsyncTask<String, String, String> {

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

                al_str_key.add(GlobalConstants.APMT_DATE);
                al_str_value.add(str_date);

                al_str_key.add(GlobalConstants.ACTION);
                al_str_value.add("get_appointments");

                Log.i("Key",""+al_str_key);
                Log.i("Value",""+al_str_value);

                message = ws.GetAppointmentService(context, al_str_key, al_str_value);

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

                tv_apmt_size.setText(String.valueOf(global.getAl_apmt_details().size()));

                list.setVisibility(View.VISIBLE);
                try {
                    calendarListAdapter= new CalendarListAdapter(context);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                list.setAdapter(calendarListAdapter);
                calendarListAdapter.notifyDataSetChanged();

            }
            else
            {
                tv_apmt_size.setText(String.valueOf(0));

                list.setVisibility(View.INVISIBLE);
              //  Toast.makeText(context, "" + message, Toast.LENGTH_SHORT).show();
            }

        }

    }

}

