package dcube.com.trust;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.deepakbaliga.beautifulgraph.Plotter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.List;

import WebServicesHandler.CheckNetConnection;
import WebServicesHandler.GlobalConstants;
import WebServicesHandler.WebServices;
import dcube.com.trust.utils.Global;
import pl.droidsonroids.gif.GifTextView;

public class GraphActivity extends Activity {


    Plotter plotter_view;
    List<Integer> plots =  new ArrayList<>();
    int numberOfPlots = 15;

    RadioGroup radio_group;
    RadioButton radio_daily,radio_weekly,radio_monthly,radio_yearly;


    ArrayList<String> al_time;

    GraphView graph;

    GifTextView gif_loader;

    Context context;
    Global global;
    WebServices ws;

    String str_as_per="",str_branch;

    LineGraphSeries<DataPoint> series2;

    CheckNetConnection cn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        plotter_view = (Plotter) findViewById(R.id.plotter_view);

        global = (Global) getApplicationContext();

        cn = new CheckNetConnection(this);

        gif_loader = (GifTextView) findViewById(R.id.gif_loader);

        radio_group= (RadioGroup)findViewById(R.id.radio_group);

        radio_daily=(RadioButton)findViewById(R.id.radio_daily);
        radio_weekly=(RadioButton)findViewById(R.id.radio_weekly);
        radio_monthly=(RadioButton)findViewById(R.id.radio_monthly);
        radio_yearly=(RadioButton)findViewById(R.id.radio_yearly);

        context = this;

//        graph.removeAllSeries();

        str_branch = global.getAl_login_list().get(0).get(GlobalConstants.USER_BRANCH);

        plotData();

        radio_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                if (radio_daily.isChecked())
                {
                    str_as_per = "daily";

                //    graph.removeAllSeries();

                    if (cn.isNetConnected())
                    {
                      //  new TotalRevenueAsyncTask().execute();
                    }
                    else {
                        Toast.makeText(context, "Check Internet Connection", Toast.LENGTH_SHORT).show();
                    }


                }


                else if (radio_weekly.isChecked())
                {
                    str_as_per = "weekly";

                //    graph.removeAllSeries();

                    if (cn.isNetConnected())
                    {
                      //  new TotalRevenueAsyncTask().execute();
                    }
                    else {
                        Toast.makeText(context, "Check Internet Connection", Toast.LENGTH_SHORT).show();
                    }

                }



                else if (radio_monthly.isChecked())
                {
                    str_as_per = "monthly";
               //     graph.removeAllSeries();

                    if (cn.isNetConnected())
                    {
                      //  new TotalRevenueAsyncTask().execute();
                    }
                    else {
                        Toast.makeText(context, "Check Internet Connection", Toast.LENGTH_SHORT).show();
                    }

                }


                else if (radio_yearly.isChecked())
                {

                    str_as_per = "yearly";
              //      graph.removeAllSeries();

                    if (cn.isNetConnected())
                    {
                     //   new TotalRevenueAsyncTask().execute();
                    }
                    else
                    {
                        Toast.makeText(context, "Check Internet Connection", Toast.LENGTH_SHORT).show();
                    }

                }

            }
        });


        plotter_view.animate();

    }


    private void plotData(){

        plotter_view.invalidate();
        plots.clear();

        for(int i = numberOfPlots; i>0; i--)
        {
            plots.add((int)(Math.random()*10));
        }

        plotter_view.setRowCol(5,5);
        plotter_view.setPlots(plots);

    }

}
