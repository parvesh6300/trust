package dcube.com.trust;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;

/**
 * Created by a7med on 28/06/2015.
 */
public class CalendarCustomView extends LinearLayout
{
	// for logging
	private static final String LOGTAG = "Calendar View";

	// how many days to show, defaults to six weeks, 42 days
	private static final int DAYS_COUNT = 42;

	// default date format
	private static final String DATE_FORMAT1 = "MMM";
	private static final String DATE_FORMAT2 = "yyyy";
	private static final String DATE_FORMAT3 = "dd";

	// date format
	private String dateFormat1;
	private String dateFormat2;
	private String dateFormat3;

	// current displayed month
	private Calendar currentDate = Calendar.getInstance();

	//event handling
	private EventHandler eventHandler = null;

	// internal components
	private LinearLayout header;
	private TextView btnPrev;
	private TextView btnNext;
	private TextView txtMonth;
	private TextView txtYear;
	private TextView date;
	private GridView grid;

	public CalendarCustomView(Context context)
	{
		super(context);
	}

	public CalendarCustomView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		initControl(context, attrs);
	}

	public CalendarCustomView(Context context, AttributeSet attrs, int defStyleAttr)
	{
		super(context, attrs, defStyleAttr);
		initControl(context, attrs);
	}

	/**
	 * Load control xml layout
	 */
	private void initControl(Context context, AttributeSet attrs)
	{
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.control_calendar, this);

		loadDateFormat(attrs);
		assignUiElements();
		assignClickHandlers();

		updateCalendar();
	}

	private void loadDateFormat(AttributeSet attrs)
	{
		TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.CalendarCustomView);

		try
		{
			// try to load provided date format, and fallback to default otherwise
			dateFormat1 = ta.getString(R.styleable.CalendarCustomView_dateFormat);
			dateFormat2 = ta.getString(R.styleable.CalendarCustomView_dateFormat);

			if (dateFormat1 == null ||dateFormat2 == null  )
				dateFormat1 = DATE_FORMAT1;
			    dateFormat2 = DATE_FORMAT2;
				dateFormat3 = DATE_FORMAT3;
		}
		finally
		{
			ta.recycle();
		}
	}
	private void assignUiElements()
	{
		// layout is inflated, assign local variables to components
		header = (LinearLayout)findViewById(R.id.calendar_header);
		btnPrev = (TextView)findViewById(R.id.calendar_prev_button);
		btnNext = (TextView)findViewById(R.id.calendar_next_button);
		txtMonth = (TextView)findViewById(R.id.calendar_month_display);
		txtYear = (TextView)findViewById(R.id.year);

		grid = (GridView)findViewById(R.id.calendar_grid);
	}

	private void assignClickHandlers()
	{
		// add one month and refresh UI
		btnNext.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				currentDate.add(Calendar.MONTH, 1);
				updateCalendar();
			}
		});

		// subtract one month and refresh UI
		btnPrev.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{

				currentDate.add(Calendar.MONTH, -1);
				updateCalendar();

			}
		});

		grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View View, int i, long l) {

				eventHandler.onDayLongPress((Date)adapterView.getItemAtPosition(i));
				Log.e("Date","Date "+adapterView.getItemAtPosition(i).toString());
			}
		});
	}

	/**
	 * Display dates correctly in grid
	 */
	public void updateCalendar()
	{
		updateCalendar(null);
	}

	/**
	 * Display dates correctly in grid
	 */
	public void updateCalendar(HashSet<Date> events)
	{
		ArrayList<Date> cells = new ArrayList<>();
		Calendar calendar = (Calendar)currentDate.clone();

		// determine the cell for current month's beginning
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		int monthBeginningCell = calendar.get(Calendar.DAY_OF_WEEK) - 1;

		// move calendar backwards to the beginning of the week
		calendar.add(Calendar.DAY_OF_MONTH, -monthBeginningCell);

		// fill cells
		while (cells.size() < DAYS_COUNT)
		{
			cells.add(calendar.getTime());
			calendar.add(Calendar.DAY_OF_MONTH, 1);
		}

		// update grid
		grid.setAdapter(new CalendarAdapter(getContext(), cells, events));

		// update title
		SimpleDateFormat sdf1 = new SimpleDateFormat(dateFormat1);
		SimpleDateFormat sdf2 = new SimpleDateFormat(dateFormat2);
		SimpleDateFormat sdf3 = new SimpleDateFormat(dateFormat3);

		int month = calendar.get(Calendar.MONTH);

		if(month < 11  && month > 1) {

			txtMonth.setText(theMonth(month-1));
			btnPrev.setText(theMonth(month-2));
			btnNext.setText(theMonth(month));
		}
		else if(month == 12 || month == 11 || month == 0 || month == 1)
		{

			switch (month) {

				case 12: {

					txtMonth.setText("December");
					btnPrev.setText("November");
					btnNext.setText("January");

					break;
				}

				case 11: {

					txtMonth.setText("December");
					btnPrev.setText("November");
					btnNext.setText("January");

					break;
				}
				case 0: {

					txtMonth.setText("January");
					btnPrev.setText("December");
					btnNext.setText("February");

					break;
				}

				case 1: {

					txtMonth.setText("February");
					btnPrev.setText("January");
					btnNext.setText("March");

					break;
				}

			}
		}

		txtYear.setText(String.valueOf(calendar.get(Calendar.YEAR)));

		//txtYear.setText(sdf2.format(currentDate.getTime()));
	}

	public static String theMonth(int month){

		String[] monthNames = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
		return monthNames[month];
	}

	private class CalendarAdapter extends ArrayAdapter<Date>
	{
		// days with events
		private HashSet<Date> eventDays;

		// for view inflation
		private LayoutInflater inflater;

		public CalendarAdapter(Context context, ArrayList<Date> days, HashSet<Date> eventDays)
		{
			super(context, R.layout.control_calendar_day, days);
			this.eventDays = eventDays;
			inflater = LayoutInflater.from(context);
		}

		@Override
		public View getView(int position, View view, ViewGroup parent)
		{
			// day in question
			Date date = getItem(position);
			int day = date.getDate();
			int month = date.getMonth();
			int year = date.getYear();

			// today
			Date today = new Date();

			// inflate item if it does not exist yet
			if (view == null)
				view = inflater.inflate(R.layout.control_calendar_day, parent, false);

			// if this day has an event, specify event image
			view.setBackgroundResource(0);

/*
			if (eventDays != null)
			{
				for (Date eventDate : eventDays)
				{
//
					if (eventDate.getDate() == day && eventDate.getMonth() == month && eventDate.getYear() == year)
					{
						// mark this day for event
						view.setBackgroundResource(R.drawable.circle_calendar);

						break;
					}
				}
			}
*/

			// clear styling
			((TextView)view).setTypeface(null, Typeface.NORMAL);
			((TextView)view).setTextColor(Color.BLACK);

			if (month != today.getMonth() || year != today.getYear())
			{
				// if this day is outside current month, grey it out
				((TextView)view).setTextColor(getResources().getColor(R.color.greyed_out));
				view.setAlpha(0.8f);
				view.setBackgroundResource(R.drawable.white_circle);

			}

			else if (day == today.getDate() || eventDays != null  )
			{
				// if it is today, set it to blue/bold
				((TextView)view).setTextColor(getResources().getColor(R.color.white));
				view.setBackgroundResource(R.drawable.circle_calendar);

				if (eventDays != null)
				{
					for (Date eventDate : eventDays)
					{

//  eventDate.getDate() == day &&
						if (eventDate.getDate() == day && eventDate.getMonth() == month && eventDate.getYear() == year)
						{
							// mark this day for event
							//view.setBackgroundResource(R.drawable.circle_calendar);

							((TextView)view).setTextColor(getResources().getColor(R.color.white));
							view.setBackgroundResource(R.drawable.circle_calendar);

							break;
						}

						// if it is not clicked, set it to blue/bold
						else
						{
							((TextView)view).setTextColor(getResources().getColor(R.color.textColor));
							view.setBackgroundResource(R.drawable.white_circle);
						}

					}
				}

			}

			else
			{
				// if it is today, set it to blue/bold
				((TextView)view).setTextColor(getResources().getColor(R.color.textColor));
				view.setBackgroundResource(R.drawable.white_circle);
			}

			// set text
			((TextView)view).setText(String.valueOf(date.getDate()));

			return view;
		}
	}
	/**
	 * Assign event handler to be passed needed events
	 */
	public void setEventHandler(EventHandler eventHandler)
	{
		this.eventHandler = eventHandler;
	}

	/**
	 * This interface defines what events to be reported to
	 * the outside world
	 */
	public interface EventHandler
	{
		void onDayLongPress(Date date);
	}
}
