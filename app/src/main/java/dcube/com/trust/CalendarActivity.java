package dcube.com.trust;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;

import dcube.com.trust.utils.CalendarListAdapter;


public class CalendarActivity extends Activity
{
    ListView list;
    CalendarListAdapter calendarListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        calendarListAdapter= new CalendarListAdapter(this);
        //list.setAdapter(calendarListAdapter);

        HashSet<Date> events = new HashSet<>();
        events.add(new Date());

        CalendarCustomView cv = ((CalendarCustomView)findViewById(R.id.calendar_view));
        cv.updateCalendar(events);

        // assign event handler
        cv.setEventHandler(new CalendarCustomView.EventHandler()
        {
            @Override
            public void onDayLongPress(Date date)
            {
                // show returned day
                DateFormat df = SimpleDateFormat.getDateInstance();
                Toast.makeText(CalendarActivity.this, df.format(date), Toast.LENGTH_SHORT).show();
            }
        });
    }
}

