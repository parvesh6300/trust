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

import WebServicesHandler.CheckNetConnection;
import WebServicesHandler.GlobalConstants;
import WebServicesHandler.WebServices;
import dcube.com.trust.utils.Global;
import okhttp3.OkHttpClient;
import pl.droidsonroids.gif.GifTextView;

public class TotalRevenue extends Activity {


    RadioGroup radio_group;
    RadioButton radio_daily,radio_weekly,radio_monthly,radio_yearly;


    ArrayList<String> al_time;

    GraphView graph;

    GifTextView gif_loader;

    Context context;
    Global global;
    WebServices ws;

    String str_as_per="";

    LineGraphSeries<DataPoint> series2;

    CheckNetConnection cn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total_revenue);

        graph = (GraphView) findViewById(R.id.graph);

        global = (Global) getApplicationContext();

        cn = new CheckNetConnection(this);

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

                    if (cn.isNetConnected())
                    {
                        new TotalRevenueAsyncTask().execute();
                    }
                    else {
                        Toast.makeText(context, "Check Internet Connection", Toast.LENGTH_SHORT).show();
                    }


                }


                else if (radio_weekly.isChecked())
                {
                    str_as_per = "weekly";

                    graph.removeAllSeries();

                    if (cn.isNetConnected())
                    {
                        new TotalRevenueAsyncTask().execute();
                    }
                    else {
                        Toast.makeText(context, "Check Internet Connection", Toast.LENGTH_SHORT).show();
                    }

                }



               else if (radio_monthly.isChecked())
                {
                    str_as_per = "monthly";
                    graph.removeAllSeries();

                    if (cn.isNetConnected())
                    {
                        new TotalRevenueAsyncTask().execute();
                    }
                    else {
                        Toast.makeText(context, "Check Internet Connection", Toast.LENGTH_SHORT).show();
                    }

                }


                else if (radio_yearly.isChecked())
                {

                    str_as_per = "yearly";
                    graph.removeAllSeries();

                    if (cn.isNetConnected())
                    {
                        new TotalRevenueAsyncTask().execute();
                    }
                    else
                    {
                        Toast.makeText(context, "Check Internet Connection", Toast.LENGTH_SHORT).show();
                    }

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

        String[] stockArr = new String[global.getAl_was_sum().size()];
        stockArr = global.getAl_was_sum().toArray(stockArr);
        Arrays.sort(stockArr , Collections.<String>reverseOrder());

        if ( count == 12 )
        {
            dateArr = new String[global.getAl_on_date().size()];
            dateArr = global.getAl_on_date().toArray(dateArr);
            Arrays.sort(dateArr , Collections.<String>reverseOrder());

        }
        else if (count == 24)
        {
            for (int i=0; i < count; i++)
            {
                String[] date_time = global.getAl_on_date().get(i).split("\\s+");

                String[] date = date_time[1].split(":");

                al_time.add(date[0]);
                Log.i("Hour",""+date[0]);

            }

            dateArr = new String[al_time.size()];
            dateArr = al_time.toArray(dateArr);
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


//        series2.setDrawBackground(true);
//        series2.setBackgroundColor(getResources().getColor(R.color.colorPrimary));


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


}
