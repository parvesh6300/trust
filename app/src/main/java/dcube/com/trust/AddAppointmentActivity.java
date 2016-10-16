package dcube.com.trust;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ArrayAdapter;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_appointment);

        date = (TextView) findViewById(R.id.datepicker);
        time = (TextView) findViewById(R.id.timepicker);

        String[] SERVICES = getResources().getStringArray(R.array.servicelist);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, SERVICES);
        service = (BetterSpinner) findViewById(R.id.service);
        service.setAdapter(adapter);

        date.setOnClickListener(new View.OnClickListener() {
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

        time.setOnClickListener(new View.OnClickListener() {
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
}

