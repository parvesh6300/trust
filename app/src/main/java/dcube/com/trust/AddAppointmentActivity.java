package dcube.com.trust;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog.OnDateSetListener;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog.OnTimeSetListener;
import com.weiwangcn.betterspinner.library.BetterSpinner;

import java.util.Calendar;

public class AddAppointmentActivity extends FragmentActivity implements OnTimeSetListener, OnDateSetListener{

    BetterSpinner service;

    TextView date;
    TextView time;
    TextView add;

    RelativeLayout datepicker;
    RelativeLayout timepicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_appointment);

        datepicker = (RelativeLayout) findViewById(R.id.datepicker);
        timepicker = (RelativeLayout) findViewById(R.id.timepicker);

        date = (TextView) findViewById(R.id.datepick);
        time = (TextView) findViewById(R.id.timepick);
        add = (TextView) findViewById(R.id.add);

        String[] SERVICES = getResources().getStringArray(R.array.servicelist);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, SERVICES);
        service = (BetterSpinner) findViewById(R.id.service);
        service.setAdapter(adapter);

        datepicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        AddAppointmentActivity.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.setAccentColor(getResources().getColor(R.color.mdtp_accent_color));
                dpd.show(getFragmentManager(), "Datepickerdialog");
            }
        });

        timepicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Calendar now = Calendar.getInstance();
                TimePickerDialog tpd = TimePickerDialog.newInstance(
                        AddAppointmentActivity.this,
                        now.get(Calendar.HOUR_OF_DAY),
                        now.get(Calendar.MINUTE),
                        false);

                tpd.setAccentColor(getResources().getColor(R.color.mdtp_accent_color));
                tpd.show(getFragmentManager(), "Datepickerdialog");
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomDialogClass cdd = new CustomDialogClass(AddAppointmentActivity.this);
                cdd.show();
            }
        });
    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {

        String t = "";

        if(hourOfDay < 12)
             t = "" + hourOfDay + ":"+minute+" AM";
        else
        if(hourOfDay == 12)
            t = "" + hourOfDay + ":"+minute+" PM";
        else
        if(hourOfDay > 12)
            t = "" + (hourOfDay-12) + ":"+minute+" PM";

        time.setText(t);
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String d = ""+dayOfMonth+"/"+(monthOfYear+1)+"/"+year;
        date.setText(d);
    }

    public class CustomDialogClass extends Dialog {

        public Activity c;
        public Button ok;

        public CustomDialogClass(Activity a) {
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

                    finish();
                }
            });
        }
    }
}

