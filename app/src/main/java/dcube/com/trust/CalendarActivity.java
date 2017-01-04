package dcube.com.trust;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
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
    TextView tv_continue;

    public static Handler h;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_calendar);

        cn = new CheckNetConnection(context);

        global = (Global) getApplicationContext();

        list = (ListView) findViewById(R.id.list);
        gif_loader = (GifTextView) findViewById(R.id.gif_loader);
        tv_continue = (TextView) findViewById(R.id.tv_continue);

//        events = new HashSet<>();
//        events.add(new Date());

        cv = ((CalendarCustomView)findViewById(R.id.calendar_view));
 //       cv.updateCalendar(events);


        if (global.isAppointmentSelected())
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
                // show returned day
//                DateFormat df = SimpleDateFormat.getDateInstance();
//                Toast.makeText(CalendarActivity.this, df.format(date), Toast.LENGTH_SHORT).show();

//                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                events = new HashSet<>();
                events.add(date);
                cv.updateCalendar(events);

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

                al_str_key.add(GlobalConstants.ACTION);
                al_str_value.add("get_appointments");

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
                calendarListAdapter= new CalendarListAdapter(context);
                list.setAdapter(calendarListAdapter);
            }
            else
            {
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

