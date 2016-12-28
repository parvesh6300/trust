package dcube.com.trust;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;

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

    GraphView graph;

    GifTextView gif_loader;

    Context context;
    Global global;
    WebServices ws;

    String str_as_per="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total_revenue);

        graph = (GraphView) findViewById(R.id.graph);

        addValuesInList();

        global = (Global) getApplicationContext();

        gif_loader = (GifTextView) findViewById(R.id.gif_loader);

        radio_group= (RadioGroup)findViewById(R.id.radio_group);

        radio_daily=(RadioButton)findViewById(R.id.radio_daily);
        radio_weekly=(RadioButton)findViewById(R.id.radio_weekly);
        radio_monthly=(RadioButton)findViewById(R.id.radio_monthly);
        radio_yearly=(RadioButton)findViewById(R.id.radio_yearly);


        graph.removeAllSeries();

        radio_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                if (radio_daily.isChecked())
                {
                    str_as_per = "daily";
                    graph.removeAllSeries();

                    new TotalRevenueAsyncTask().execute();

                    LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {

                            new DataPoint(0, 0),
                            new DataPoint(4, 2),
                            new DataPoint(5, 5)
                    });
                    graph.addSeries(series);
                }


                else if (radio_weekly.isChecked())
                {
                    str_as_per = "weekly";
                    graph.removeAllSeries();

                    al_revenue_weekly = new ArrayList<Double>();
                    al_time_weekly = new ArrayList<Double>();

                    addWeekValue();

                    new TotalRevenueAsyncTask().execute();
                    LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {

                            new DataPoint(al_time_weekly.get(0), al_revenue_weekly.get(0)),
                            new DataPoint(al_time_weekly.get(1), al_revenue_weekly.get(1)),
                            new DataPoint(al_time_weekly.get(2), al_revenue_weekly.get(2)),
                            new DataPoint(al_time_weekly.get(3), al_revenue_weekly.get(3)),
                            new DataPoint(al_time_weekly.get(4), al_revenue_weekly.get(4)),
                            new DataPoint(al_time_weekly.get(5), al_revenue_weekly.get(5)),
                            new DataPoint(al_time_weekly.get(6), al_revenue_weekly.get(6)),

                    });
                    graph.addSeries(series);
                }



               else if (radio_monthly.isChecked())
                {
                    str_as_per = "monthly";
                    graph.removeAllSeries();

                    al_revenue_weekly = new ArrayList<Double>();
                    al_time_weekly = new ArrayList<Double>();

                    addMonthlyValue();
                    drawMonthlyGraph();

                    new TotalRevenueAsyncTask().execute();

                }


                else if (radio_yearly.isChecked())
                {

                    str_as_per = "yearly";
                    graph.removeAllSeries();

                    new TotalRevenueAsyncTask().execute();
                    LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {

                            new DataPoint(0, 0),
                            new DataPoint(1, 5),
                            new DataPoint(2, 3)
                    });
                    graph.addSeries(series);
                }

            }
        });


    }


    public void addValuesInList()
    {
        time.add(1.0);
        time.add(2.0);
        time.add(3.0);
        time.add(4.0);
        time.add(5.0);


        revenue.add(1000.0);
        revenue.add(1550.0);
        revenue.add(1330.0);
        revenue.add(2530.0);
        revenue.add(3000.0);

        ar_time=time.toArray(ar_time);
        ar_revenue= revenue.toArray(ar_revenue);


    }


    public void addWeekValue()
    {
        for (double i=0 ; i < 7 ; i++)
        {
            al_time_weekly.add(i);
        }

        al_revenue_weekly.add(200d);
        al_revenue_weekly.add(300d);
        al_revenue_weekly.add(100d);
        al_revenue_weekly.add(500d);
        al_revenue_weekly.add(700d);
        al_revenue_weekly.add(400d);
        al_revenue_weekly.add(1000d);
    }


    public void addMonthlyValue()
    {
        for (double i=0 ; i < 31 ; i++)
        {
            al_time_weekly.add(i);
        }

        al_revenue_weekly.add(200d);
        al_revenue_weekly.add(300d);
        al_revenue_weekly.add(100d);
        al_revenue_weekly.add(500d);
        al_revenue_weekly.add(700d);
        al_revenue_weekly.add(400d);
        al_revenue_weekly.add(1000d);
        al_revenue_weekly.add(100d);
        al_revenue_weekly.add(2000d);
        al_revenue_weekly.add(200d);
        al_revenue_weekly.add(100d);
        al_revenue_weekly.add(1700d);
        al_revenue_weekly.add(1050d);
        al_revenue_weekly.add(200d);
        al_revenue_weekly.add(250d);
        al_revenue_weekly.add(120d);
        al_revenue_weekly.add(130d);
        al_revenue_weekly.add(400d);
        al_revenue_weekly.add(1050d);
        al_revenue_weekly.add(500d);
        al_revenue_weekly.add(700d);
        al_revenue_weekly.add(750d);
        al_revenue_weekly.add(700d);
        al_revenue_weekly.add(850d);
        al_revenue_weekly.add(720d);
        al_revenue_weekly.add(160d);
        al_revenue_weekly.add(130d);
        al_revenue_weekly.add(350d);
        al_revenue_weekly.add(130d);
        al_revenue_weekly.add(350d);
        al_revenue_weekly.add(130d);
        al_revenue_weekly.add(350d);
        al_revenue_weekly.add(130d);

    }


    public void drawMonthlyGraph()
    {
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

                message = ws.WithdrawMoneyService(context, al_str_key, al_str_value);


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

            }
            else {

                Toast.makeText(context, "" + message, Toast.LENGTH_SHORT).show();
            }

        }


    }



}
