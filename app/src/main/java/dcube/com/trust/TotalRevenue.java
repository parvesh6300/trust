package dcube.com.trust;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;

public class TotalRevenue extends AppCompatActivity {


    ArrayList<Double> time= new ArrayList<>();
    ArrayList<Double> revenue= new ArrayList<>();

    Double[] ar_time= new Double[]{};
    Double[] ar_revenue= new Double[]{};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total_revenue);


        getSupportActionBar().hide();

        GraphView graph = (GraphView) findViewById(R.id.graph);

        addValuesInList();


        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {


                new DataPoint(0, 0),
                    new DataPoint(1, 5),
                    new DataPoint(2, 3)
        });
        graph.addSeries(series);


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
