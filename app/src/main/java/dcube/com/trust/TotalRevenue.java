package dcube.com.trust;

import android.app.Activity;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;

public class TotalRevenue extends Activity {


    RadioGroup radio_group;
    RadioButton radio_daily,radio_weekly,radio_monthly,radio_yearly;

    ArrayList<Double> time= new ArrayList<>();
    ArrayList<Double> revenue= new ArrayList<>();

    Double[] ar_time= new Double[]{};
    Double[] ar_revenue= new Double[]{};

    GraphView graph;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total_revenue);

        graph = (GraphView) findViewById(R.id.graph);

        addValuesInList();

        radio_group= (RadioGroup)findViewById(R.id.radio_group);

        radio_daily=(RadioButton)findViewById(R.id.radio_daily);
        radio_weekly=(RadioButton)findViewById(R.id.radio_weekly);
        radio_monthly=(RadioButton)findViewById(R.id.radio_monthly);
        radio_yearly=(RadioButton)findViewById(R.id.radio_yearly);

        radio_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                if (radio_daily.isChecked())
                {

                    LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {


                            new DataPoint(0, 0),
                            new DataPoint(4, 2),
                            new DataPoint(5, 5)
                    });
                    graph.addSeries(series);
                }


               else if (radio_monthly.isChecked())
                {

                    LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {


                            new DataPoint(0, 0),
                            new DataPoint(2, 4),
                            new DataPoint(5, 2)
                    });
                    graph.addSeries(series);
                }

               else if (radio_weekly.isChecked())
                {

                    LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {


                            new DataPoint(0, 0),
                            new DataPoint(4, 5),
                            new DataPoint(5, 3)
                    });
                    graph.addSeries(series);
                }


                else if (radio_yearly.isChecked())
                {

                    LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {


                            new DataPoint(0, 0),
                            new DataPoint(1, 5),
                            new DataPoint(2, 3)
                    });
                    graph.addSeries(series);
                }


            }
        });




        for (int i=0;i<5;i++)
        {

        }



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


}
