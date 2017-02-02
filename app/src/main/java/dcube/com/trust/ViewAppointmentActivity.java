package dcube.com.trust;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import WebServicesHandler.CheckNetConnection;
import WebServicesHandler.GlobalConstants;
import WebServicesHandler.WebServices;
import dcube.com.trust.utils.FollowupListAdapter;
import dcube.com.trust.utils.Global;
import okhttp3.OkHttpClient;
import pl.droidsonroids.gif.GifTextView;

public class ViewAppointmentActivity extends Activity {

    ListView followUpList;
    FollowupListAdapter adapter;
    TextView tv_add_follow_up,tv_client_name,tv_contact,tv_service;
    Global global;

    GifTextView gif_loader;

    WebServices ws;
    Context context = ViewAppointmentActivity.this;

    String str_client_id;
    int pos;

    CheckNetConnection cn;

    int int_selected_day;
    int int_today;

    String str_date,str_time,str_remark;

    String format_time = "",format_date = "",str_time_pick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_view_appointment);

        global = (Global) getApplicationContext();

        cn = new CheckNetConnection(this);

        gif_loader = (GifTextView) findViewById(R.id.gif_loader);

        tv_add_follow_up = (TextView) findViewById(R.id.tv_add_follow_up);
        tv_client_name = (TextView) findViewById(R.id.tv_client_name);
        tv_contact = (TextView) findViewById(R.id.tv_contact);
        tv_service = (TextView) findViewById(R.id.tv_service);

        followUpList = (ListView) findViewById(R.id.followuplist);


        pos = global.getSelected_client();

        tv_client_name.setText(global.getAl_src_client_details().get(pos).get(GlobalConstants.SRC_CLIENT_NAME));
        tv_contact.setText(global.getAl_src_client_details().get(pos).get(GlobalConstants.SRC_CLIENT_CONTACT));
       // tv_service.setText(global.getAl_login_list().get(0).get(GlobalConstants.SRC_CLIENT_SER));

        tv_add_follow_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CustomDialogClass cdd = new CustomDialogClass(ViewAppointmentActivity.this);
                cdd.show();
            }
        });

        str_client_id = global.getAl_src_client_details().get(global.getSelected_client()).
                get(GlobalConstants.SRC_CLIENT_ID);


        if (cn.isNetConnected())
        {
            new GetAppointmentAsyncTask().execute();
        }
        else {
            Toast.makeText(context, "Check Internet Connection", Toast.LENGTH_SHORT).show();
        }

    }


    public class CustomDialogClass extends Dialog implements TimePickerDialog.OnTimeSetListener,DatePickerDialog.OnDateSetListener {

        public Activity c;

        public TextView tv_cancel, tv_timepick, tv_datepick;
        public TextView tv_add;
        public RelativeLayout datepicker, timepicker;
        public EditText ed_note;

        public CustomDialogClass(Activity a) {
            super(a);
            // TODO Auto-generated constructor stub
            this.c = a;
        }

        @Override
        protected void onCreate(Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);

            setContentView(R.layout.addfollowupdialog);

            datepicker = (RelativeLayout) findViewById(R.id.datepicker);
            timepicker = (RelativeLayout) findViewById(R.id.timepicker);
            ed_note = (EditText) findViewById(R.id.ed_note);
            tv_datepick = (TextView) findViewById(R.id.tv_datepick);
            tv_timepick = (TextView) findViewById(R.id.tv_timepick);

            tv_add = (TextView) findViewById(R.id.tv_add);
            tv_cancel = (TextView) findViewById(R.id.tv_cancel);

            datepicker.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Calendar now = Calendar.getInstance();
                    DatePickerDialog dpd = DatePickerDialog.newInstance(
                            CustomDialogClass.this,
                            now.get(Calendar.YEAR),
                            now.get(Calendar.MONTH),
                            now.get(Calendar.DAY_OF_MONTH)
                    );

                    now.add(Calendar.DATE,0);
                    dpd.setMinDate(now);

                    dpd.setAccentColor(getResources().getColor(R.color.mdtp_accent_color));
                    dpd.show(getFragmentManager(), "Datepickerdialog");
                }
            });

            timepicker.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Calendar now = Calendar.getInstance();
                    TimePickerDialog tpd = TimePickerDialog.newInstance(
                            CustomDialogClass.this,
                            now.get(Calendar.HOUR_OF_DAY),
                            now.get(Calendar.MINUTE),
                            false);

                    if (int_today == int_selected_day)
                    {
                        tpd.setMinTime(now.get(Calendar.HOUR_OF_DAY),Calendar.MINUTE,Calendar.SECOND);
                    }


                    tpd.setAccentColor(getResources().getColor(R.color.mdtp_accent_color));
                    tpd.show(getFragmentManager(), "Datepickerdialog");
                }
            });


            tv_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (validate())
                    {
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

                            dismiss();
                            new AddAppointmentAsyncTask().execute();

                        }
                        else
                        {
                            Toast.makeText(context, "Check Internet Connection", Toast.LENGTH_SHORT).show();
                        }
                    }

                }
            });

            tv_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    dismiss();
                }
            });


        }


        @Override
        public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {

//            String t = "";
//
//            t = hourOfDay+":"+minute+":"+second;
//
//            tv_timepick.setText(t);

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

            tv_timepick.setText(str_format_time);

        }

        @Override
        public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

            String d = ""+year+"-"+(monthOfYear+1)+"-"+dayOfMonth;

            int_selected_day = dayOfMonth;

            tv_datepick.setText(d);
        }

        public boolean validate()
        {
            if (tv_datepick.getText().toString().matches("Date"))
            {
                Toast.makeText(context, "Specify Date", Toast.LENGTH_SHORT).show();
            }
            else if (tv_timepick.getText().toString().matches("Time"))
            {
                Toast.makeText(context, "Specify Time", Toast.LENGTH_SHORT).show();
            }
            else if (ed_note.getText().toString().matches(""))
            {
                Toast.makeText(context, "Enter Remark", Toast.LENGTH_SHORT).show();
            }
            else
            {
                str_date = tv_datepick.getText().toString();
                str_time = str_time_pick ; //tv_timepick.getText().toString();
                str_remark = ed_note.getText().toString();

                return true;
            }

            return false;
        }

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

                al_str_key.add(GlobalConstants.APMT_CLIENT_ID);
                al_str_value.add(str_client_id);

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

            gif_loader.setVisibility(View.INVISIBLE);

            if (message.equalsIgnoreCase("true"))
            {
                adapter = new FollowupListAdapter(context);
                followUpList.setAdapter(adapter);
            }
            else {

                Toast.makeText(context, "" + message, Toast.LENGTH_SHORT).show();
            }

        }

    }



    public class AddAppointmentAsyncTask extends AsyncTask<String, String, String> {

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

                al_str_key.add(GlobalConstants.APMT_CLIENT_ID);
                al_str_value.add(str_client_id);

                al_str_key.add(GlobalConstants.APMT_SERVICE_ID);
                al_str_value.add(global.getAl_apmt_details().get(0).get(GlobalConstants.APMT_SERVICE_ID));

                al_str_key.add(GlobalConstants.APMT_TIME);
                al_str_value.add(format_time);

                al_str_key.add(GlobalConstants.APMT_REMARK);
                al_str_value.add(str_remark);

                al_str_key.add(GlobalConstants.APMT_is_FOLLOW_UP);
                al_str_value.add("1");

                al_str_key.add(GlobalConstants.ACTION);
                al_str_value.add("add_appointment");

                for (int i =0 ; i < al_str_value.size() ; i++)
                {
                    Log.i("Key",al_str_key.get(i));
                    Log.i("Value",al_str_value.get(i));
                }

                message = ws.AddAppointmentService(context, al_str_key, al_str_value);

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
                DialogClass dd = new DialogClass(ViewAppointmentActivity.this);
                dd.show();
//                CustomDialogClass cdd = new CustomDialogClass(ViewAppointmentActivity.this);
//                cdd.show();
            }
            else
            {
                Toast.makeText(context, "" + message, Toast.LENGTH_SHORT).show();
            }

        }

    }

    public class DialogClass extends Dialog {

        public Activity c;
        public Button ok;

        public DialogClass(Activity a) {
            super(a);
            // TODO Auto-generated constructor stub
            this.c = a;
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.custom_dialog);
            ok = (Button) findViewById(R.id.btn_yes);

            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                  //  CalendarActivity.h.sendEmptyMessage(0);
//                    ClientHomeActivity.h.sendEmptyMessage(0);
                    dismiss();
                    finish();
                }
            });
        }
    }

}
