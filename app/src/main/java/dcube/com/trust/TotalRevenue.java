package dcube.com.trust;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.DataPointInterface;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.OnDataPointTapListener;
import com.jjoe64.graphview.series.Series;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import WebServicesHandler.GlobalConstants;
import WebServicesHandler.WebServices;
import dcube.com.trust.utils.Global;
import okhttp3.OkHttpClient;
import pl.droidsonroids.gif.GifTextView;

public class TotalRevenue extends Activity {


    RadioGroup radio_group;
    RadioButton radio_daily,radio_weekly,radio_monthly,radio_yearly;

    ArrayList<Double> time= new ArrayList<>();
    ArrayList<Double> revenue= new ArrayList<>();

    ArrayList<Double> al_time_weekly;
    ArrayList<Double> al_revenue_weekly;

    Double[] ar_time= new Double[]{};
    Double[] ar_revenue= new Double[]{};

    ArrayList<String> al_time;

    GraphView graph;

    GifTextView gif_loader;

    Context context;
    Global global;
    WebServices ws;

    String str_as_per="";

    LineGraphSeries<DataPoint> series2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total_revenue);

        graph = (GraphView) findViewById(R.id.graph);

        global = (Global) getApplicationContext();

        gif_loader = (GifTextView) findViewById(R.id.gif_loader);

        radio_group= (RadioGroup)findViewById(R.id.radio_group);

        radio_daily=(RadioButton)findViewById(R.id.radio_daily);
        radio_weekly=(RadioButton)findViewById(R.id.radio_weekly);
        radio_monthly=(RadioButton)findViewById(R.id.radio_monthly);
        radio_yearly=(RadioButton)findViewById(R.id.radio_yearly);

        context = this;

        graph.removeAllSeries();

        radio_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                if (radio_daily.isChecked())
                {
                    str_as_per = "daily";
                    graph.removeAllSeries();

                    new TotalRevenueAsyncTask().execute();

                }


                else if (radio_weekly.isChecked())
                {
                    str_as_per = "weekly";

                    graph.removeAllSeries();

                    new TotalRevenueAsyncTask().execute();

                }



               else if (radio_monthly.isChecked())
                {
                    str_as_per = "monthly";
                    graph.removeAllSeries();

                    new TotalRevenueAsyncTask().execute();

                }


                else if (radio_yearly.isChecked())
                {

                    str_as_per = "yearly";
                    graph.removeAllSeries();

                    new TotalRevenueAsyncTask().execute();

                }

            }
        });


    }



    public void drawWeeklyGraph()
    {
        series2 = new LineGraphSeries<>(generateData());

        int count =  global.getAl_on_date().size();

        String[] dateArr;
        al_time = new ArrayList<>();

        if ( count == 12 )
        {
            dateArr = new String[global.getAl_on_date().size()];
            dateArr = global.getAl_on_date().toArray(dateArr);
            Arrays.sort(dateArr , Collections.<String>reverseOrder());
        }
        else
        {
            for (int i=0; i < count; i++)
            {
                String[] date_time = global.getAl_on_date().get(i).split("\\s+");

                String[] date = date_time[0].split("-");

                al_time.add(date[2]);

            }

            dateArr = new String[al_time.size()];
            dateArr = al_time.toArray(dateArr);
            Arrays.sort(dateArr , Collections.<String>reverseOrder());

        }


        String[] stockArr = new String[global.getAl_was_sum().size()];
        stockArr = global.getAl_was_sum().toArray(stockArr);
        Arrays.sort(stockArr , Collections.<String>reverseOrder());

        // set manual bounds
        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setMinY(0);
        graph.getViewport().setMaxY(Double.parseDouble(stockArr[0]));

        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(0);
        graph.getViewport().setMaxX(Double.parseDouble(dateArr[0]));


 //       graph.getViewport().setScrollable(true); // enables horizontal scrolling
        //     graph.getViewport().setScrollableY(true); // enables vertical scrolling
        //      graph.getViewport().setScalable(true); // enables horizontal zooming and scrolling
        //     graph.getViewport().setScalableY(true); // enables vertical zooming and scrolling

        graph.addSeries(series2);

        series2.setAnimated(true);
  //      series.setDrawAsPath(true);
 //       series.setThickness(10);
//        series2.setDrawDataPoints(true);
//        series2.setDataPointsRadius(10);


        series2.setOnDataPointTapListener(new OnDataPointTapListener() {
            @Override
            public void onTap(Series series, DataPointInterface dataPoint) {

                Toast.makeText(TotalRevenue.this, "Revenue "+dataPoint, Toast.LENGTH_SHORT).show();

            }
        });


        graph.getViewport().setScrollable(true);

//        StaticLabelsFormatter label = new StaticLabelsFormatter(graph);
//        label.setHorizontalLabels(new String[] {"Mon","Tue","Wed","Thu","Fri","Sat","Sun"});
//        graph.getGridLabelRenderer().setLabelFormatter(label);


        series2.setDrawBackground(true);
        series2.setBackgroundColor(getResources().getColor(R.color.colorPrimary));


    }





    public class TotalRevenueAsyncTask extends AsyncTask<String, String, String> {

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

                al_str_key.add(GlobalConstants.REVENUE_AS_PER);
                al_str_value.add(str_as_per);

                al_str_key.add(GlobalConstants.ACTION);
                al_str_value.add("get_revenue");

                message = ws.RevenueService(context, al_str_key, al_str_value);


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
                drawWeeklyGraph();
            }
            else {

                Toast.makeText(context, "" + message, Toast.LENGTH_SHORT).show();
            }

        }


    }




    private DataPoint[] generateData() {

        int count =  global.getAl_on_date().size();
        DataPoint[] values = new DataPoint[count];

        if ( count == 12 )
        {
            for (int i=0 ; i < count ; i++)
            {
                double x = Double.parseDouble(global.getAl_on_date().get(i));
                double y = Double.parseDouble(global.getAl_was_sum().get(i));

                DataPoint v = new DataPoint( x, y);
                values[i] = v;
            }
        }
        else
        {
            for (int i=0; i < count; i++)
            {
                String[] date_time = global.getAl_on_date().get(i).split("\\s+");

                String[] date = date_time[0].split("-");

                double x = Double.parseDouble(date[2]);
                double y = Double.parseDouble(global.getAl_was_sum().get(i));

                DataPoint v = new DataPoint( x, y);
                values[i] = v;
            }
        }


        return values;
    }


    public void drawMonthlyGraph()
    {
        /*
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {

                new DataPoint(al_time_weekly.get(0), al_revenue_weekly.get(0)),
                new DataPoint(al_time_weekly.get(1), al_revenue_weekly.get(1)),
                new DataPoint(al_time_weekly.get(2), al_revenue_weekly.get(2)),
                new DataPoint(al_time_weekly.get(3), al_revenue_weekly.get(3)),
                new DataPoint(al_time_weekly.get(4), al_revenue_weekly.get(4)),
                new DataPoint(al_time_weekly.get(5), al_revenue_weekly.get(5)),
                new DataPoint(al_time_weekly.get(6), al_revenue_weekly.get(6)),
                new DataPoint(al_time_weekly.get(7), al_revenue_weekly.get(7)),
                new DataPoint(al_time_weekly.get(8), al_revenue_weekly.get(8)),
                new DataPoint(al_time_weekly.get(9), al_revenue_weekly.get(9)),
                new DataPoint(al_time_weekly.get(10), al_revenue_weekly.get(10)),
                new DataPoint(al_time_weekly.get(11), al_revenue_weekly.get(11)),
                new DataPoint(al_time_weekly.get(12), al_revenue_weekly.get(12)),
                new DataPoint(al_time_weekly.get(13), al_revenue_weekly.get(13)),
                new DataPoint(al_time_weekly.get(14), al_revenue_weekly.get(14)),
                new DataPoint(al_time_weekly.get(15), al_revenue_weekly.get(15)),
                new DataPoint(al_time_weekly.get(16), al_revenue_weekly.get(16)),
                new DataPoint(al_time_weekly.get(17), al_revenue_weekly.get(17)),
                new DataPoint(al_time_weekly.get(18), al_revenue_weekly.get(18)),
                new DataPoint(al_time_weekly.get(19), al_revenue_weekly.get(19)),
                new DataPoint(al_time_weekly.get(20), al_revenue_weekly.get(20)),
                new DataPoint(al_time_weekly.get(21), al_revenue_weekly.get(21)),
                new DataPoint(al_time_weekly.get(22), al_revenue_weekly.get(22)),
                new DataPoint(al_time_weekly.get(23), al_revenue_weekly.get(23)),
                new DataPoint(al_time_weekly.get(24), al_revenue_weekly.get(24)),
                new DataPoint(al_time_weekly.get(25), al_revenue_weekly.get(25)),
                new DataPoint(al_time_weekly.get(26), al_revenue_weekly.get(26)),
                new DataPoint(al_time_weekly.get(27), al_revenue_weekly.get(27)),
                new DataPoint(al_time_weekly.get(28), al_revenue_weekly.get(28)),
                new DataPoint(al_time_weekly.get(29), al_revenue_weekly.get(29)),
                new DataPoint(al_time_weekly.get(30), al_revenue_weekly.get(30)),

        });
        graph.addSeries(series);

        */

        series2 = new LineGraphSeries<>(generateData());
        graph.addSeries(series2);

        series2.setAnimated(true);
        series2.setDrawAsPath(true);
        // series.setThickness(10);
        series2.setDrawBackground(true);
        series2.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        series2.setDrawDataPoints(true);
        series2.setDataPointsRadius(10);

        series2.setOnDataPointTapListener(new OnDataPointTapListener() {
            @Override
            public void onTap(Series series, DataPointInterface dataPoint) {

                Toast.makeText(TotalRevenue.this, "Revenue "+dataPoint, Toast.LENGTH_SHORT).show();

            }
        });


        graph.getViewport().setScrollable(true);
        series2.setDrawBackground(true);
        series2.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

//        StaticLabelsFormatter label = new StaticLabelsFormatter(graph);
//        label.setHorizontalLabels(new String[] {"05","10","15","20","25","30"});
//        graph.getGridLabelRenderer().setLabelFormatter(label);

    }


    public void drawYearlyGraph()
    {
/*
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {

                new DataPoint(al_time_weekly.get(0), al_revenue_weekly.get(0)),
                new DataPoint(al_time_weekly.get(1), al_revenue_weekly.get(1)),
                new DataPoint(al_time_weekly.get(2), al_revenue_weekly.get(2)),
                new DataPoint(al_time_weekly.get(3), al_revenue_weekly.get(3)),
                new DataPoint(al_time_weekly.get(4), al_revenue_weekly.get(4)),
                new DataPoint(al_time_weekly.get(5), al_revenue_weekly.get(5)),
                new DataPoint(al_time_weekly.get(6), al_revenue_weekly.get(6)),
                new DataPoint(al_time_weekly.get(7), al_revenue_weekly.get(7)),
                new DataPoint(al_time_weekly.get(8), al_revenue_weekly.get(8)),
                new DataPoint(al_time_weekly.get(9), al_revenue_weekly.get(9)),
                new DataPoint(al_time_weekly.get(10), al_revenue_weekly.get(10)),
                new DataPoint(al_time_weekly.get(11), al_revenue_weekly.get(11)),

        });
        graph.addSeries(series);
*/

        series2 = new LineGraphSeries<>(generateData());
        graph.addSeries(series2);

        series2.setAnimated(true);
        //      series.setDrawAsPath(true);
        //       series.setThickness(10);
        series2.setDrawBackground(true);
        series2.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        series2.setDrawDataPoints(true);
        series2.setDataPointsRadius(10);


        series2.setOnDataPointTapListener(new OnDataPointTapListener() {
            @Override
            public void onTap(Series series, DataPointInterface dataPoint) {

                Toast.makeText(TotalRevenue.this, "Revenue "+dataPoint, Toast.LENGTH_SHORT).show();

            }
        });


        graph.getViewport().setScrollable(true);

//        StaticLabelsFormatter label = new StaticLabelsFormatter(graph);
//        label.setHorizontalLabels(new String[] {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"});
//        graph.getGridLabelRenderer().setLabelFormatter(label);


        series2.setDrawBackground(true);
        series2.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

    }


    public void addValuesInList()
    {
        Log.e("Size","Date "+global.getAl_on_date().size());
/*
        if (global.getAl_on_date().size() == 12)
        {
            for (int i=0 ; i < global.getAl_on_date().size()  ; i++)
            {
                al_time_weekly.add(Double.parseDouble(global.getAl_on_date().get(i)));
            }
        }
        else
        {
            for (int i=0 ; i < global.getAl_on_date().size()  ; i++)
            {

                String[] date_time = global.getAl_on_date().get(i).split("\\s+");

                String[] date = date_time[0].split("-");

                al_time_weekly.add(Double.parseDouble(date[2]));

            }
        }


        for (int i = 0 ; i< global.getAl_was_sum().size() ; i++ )
        {
            al_revenue_weekly.add(Double.parseDouble(global.getAl_was_sum().get(i)));
        }


*/

        drawWeeklyGraph();
        /*
       if (global.getAl_on_date().size() == 7)
       {
           drawWeeklyGraph();
       }
       else if (global.getAl_on_date().size() >= 28)
       {
           drawMonthlyGraph();
       }
        else if (global.getAl_on_date().size() == 12 )
       {
           drawYearlyGraph();
       }
*/
    }



}
