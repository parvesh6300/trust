package dcube.com.trust;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import WebServicesHandler.CheckNetConnection;
import WebServicesHandler.GlobalConstants;
import WebServicesHandler.WebServices;
import dcube.com.trust.utils.Global;
import lecho.lib.hellocharts.listener.LineChartOnValueSelectListener;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.LineChartView;
import okhttp3.OkHttpClient;
import pl.droidsonroids.gif.GifTextView;

public class HelloChartActivity extends Activity {


    Context context = this;

    private LineChartView chart;
    private LineChartData data;
    private int numberOfLines = 1;
    private int maxNumberOfLines = 4;
    private int numberOfPoints = 12;

    float[][] randomNumbersTab = new float[maxNumberOfLines][numberOfPoints];

    private boolean hasAxes = true;
    private boolean hasAxesNames = true;
    private boolean hasLines = true;
    private boolean hasPoints = true;
    private ValueShape shape = ValueShape.CIRCLE;
    private boolean isFilled = false;
    private boolean hasLabels = false;
    private boolean isCubic = false;
    private boolean hasLabelForSelected = false;
    private boolean pointsHaveDifferentColor;

    List<AxisValue> axisValues;


    RadioGroup radio_group;
    RadioButton radio_daily,radio_weekly,radio_monthly,radio_yearly;
    TextView tv_total_amount;

    ArrayList<String> al_time;

    GifTextView gif_loader;

    Global global;
    WebServices ws;

    String str_as_per="yearly",str_branch;

    CheckNetConnection cn;

    Integer[] int_stock_arr_sorted,int_date_arr_sorted;

    String[] dateArr;

    public String[] months = new String[] { "JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC" };

    public String[] days = new String[] { "MON", "TUE", "WED", "THU", "FRI", "SAT", "SUN" };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello_chart);

        chart = (LineChartView) findViewById(R.id.chart);

       // setChartData();

        global = (Global) getApplicationContext();

        cn = new CheckNetConnection(this);



        gif_loader = (GifTextView) findViewById(R.id.gif_loader);

        tv_total_amount = (TextView) findViewById(R.id.tv_total_amount);

        radio_group= (RadioGroup)findViewById(R.id.radio_group);

        radio_daily=(RadioButton)findViewById(R.id.radio_daily);
        radio_weekly=(RadioButton)findViewById(R.id.radio_weekly);
        radio_monthly=(RadioButton)findViewById(R.id.radio_monthly);
        radio_yearly=(RadioButton)findViewById(R.id.radio_yearly);

        context = this;

        chart.setOnValueTouchListener(new ValueTouchListener());

        str_branch = global.getAl_login_list().get(0).get(GlobalConstants.USER_BRANCH);

        radio_yearly.setChecked(true);


        radio_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                if (radio_daily.isChecked())
                {
                    str_as_per = "daily";
                }

                else if (radio_weekly.isChecked())
                {
                    str_as_per = "weekly";
                }

                else if (radio_monthly.isChecked())
                {
                    str_as_per = "monthly";
                }


                else if (radio_yearly.isChecked())
                {

                    str_as_per = "yearly";
                }

                if (cn.isNetConnected())
                {
                    new TotalRevenueAsyncTask().execute();
                }
                else {
                    Toast.makeText(context, "Check Internet Connection", Toast.LENGTH_SHORT).show();
                }

            }
        });

        if (cn.isNetConnected())
        {
            new GetBranchBalanceAsyncTask().execute();
            new TotalRevenueAsyncTask().execute();
        }
        else {
            Toast.makeText(context, "Check Internet Connection", Toast.LENGTH_SHORT).show();
        }


    }



    public void drawWeeklyGraph()
    {
        int count =  global.getAl_on_date().size();

        int_stock_arr_sorted = new Integer[count];
        int_date_arr_sorted = new Integer[count];

        al_time = new ArrayList<>();

        String[] stockArr = new String[global.getAl_was_sum().size()];
        stockArr = global.getAl_was_sum().toArray(stockArr);

        for (int i =0 ; i < stockArr.length ; i++)
        {
            int_stock_arr_sorted[i] = Integer.parseInt(stockArr[i]);
        }

        Arrays.sort(int_stock_arr_sorted , Collections.<Integer>reverseOrder());

        if ( count == 12 )
        {
            dateArr = new String[global.getAl_on_date().size()];
            dateArr = global.getAl_on_date().toArray(dateArr);
        }
        else if (count == 24)
        {
            for (int i=0; i < count; i++)
            {
                String[] date_time = global.getAl_on_date().get(i).split("\\s+");

                String[] date = date_time[1].split(":");

                al_time.add(date[0]);

            }

            dateArr = new String[al_time.size()];
            dateArr = al_time.toArray(dateArr);
     //       Arrays.sort(dateArr , Collections.<String>reverseOrder());

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

       //     Arrays.sort(dateArr , Collections.<String>reverseOrder());

        }
        for (int i =0 ; i < count ; i++)
        {
            int_date_arr_sorted[i] = Integer.parseInt(dateArr[i]);
        }

        Arrays.sort(int_date_arr_sorted , Collections.<Integer>reverseOrder());


    }


    public void resetViewport()
    {
        // Reset viewport height range to (0,100)
        final Viewport v = new Viewport(chart.getMaximumViewport());

        v.bottom = 0;
        v.top = int_stock_arr_sorted[0];

        v.left =Integer.parseInt(dateArr[0]) ;
        v.right = int_date_arr_sorted[0] ;


        Log.i("Left",""+Integer.parseInt(dateArr[0]));
        Log.i("Bottom",""+0);
        Log.i("TOp",""+int_stock_arr_sorted[0]);
        Log.i("Right",""+int_date_arr_sorted[0]);


        chart.setMaximumViewport(v);
        chart.setCurrentViewport(v);

    }


    private void generateData() {

        List<Line> lines = new ArrayList<Line>();


        for (int i = 0; i < numberOfLines; ++i)
        {
            List<PointValue> values = new ArrayList<PointValue>();

            axisValues = new ArrayList<>();

            for (int j = 0; j < global.getAl_was_sum().size() ; j++ )
            {
                values.add(new PointValue(Integer.parseInt(dateArr[j]), Integer.parseInt(global.getAl_was_sum().get(j))));

            }

            Line line = new Line(values);
            line.setColor(ChartUtils.COLORS[i]);  // Color.parseColor("#FFFFFF")
            line.setShape(shape);
            line.setCubic(isCubic);
            line.setFilled(isFilled);
            line.setHasLabels(hasLabels);
            line.setHasLabelsOnlyForSelected(hasLabelForSelected);
            line.setHasLines(hasLines);
            line.setHasPoints(hasPoints);

            if (pointsHaveDifferentColor)
            {
                line.setPointColor(ChartUtils.COLORS[(i + 1) % ChartUtils.COLORS.length]);
            }

            lines.add(line);
        }

        data = new LineChartData(lines);

        Axis axisX;
        Axis axisY = new Axis().setHasLines(true);


        /*
        This if shows the week days in X axis
         */
        if (global.getAl_was_sum().size() == 7)
        {
            for (int i =0 ; i <7 ; i++)
            {
                axisValues.add(new AxisValue(Float.parseFloat(dateArr[i])).setLabel(days[i]));
            }

            axisX = new Axis(axisValues);

        }
         /*
        This if shows the months in X axis
         */
        else if (global.getAl_was_sum().size() == 12)
        {
            for (int i =0 ; i < 12 ; i++)
            {
                axisValues.add(new AxisValue(Float.parseFloat(dateArr[i])).setLabel(months[i]));
            }

            axisX = new Axis(axisValues);

        }

        else
        {
            axisX = new Axis();
        }

        axisX.setTextSize(20);
        axisY.setTextSize(20);
        axisY.setMaxLabelChars(10);

        data.setAxisXBottom(axisX);
        data.setAxisYLeft(axisY);

        data.setBaseValue(Float.NEGATIVE_INFINITY);
        chart.setLineChartData(data);

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

                al_str_key.add(GlobalConstants.USER_BRANCH_ID);
                al_str_value.add(global.getAl_login_list().get(0).get(GlobalConstants.USER_BRANCH_ID));

                al_str_key.add(GlobalConstants.REVENUE_AS_PER);
                al_str_value.add(str_as_per);

                al_str_key.add(GlobalConstants.BRANCH);
                al_str_value.add(str_branch);

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
                generateData();
                resetViewport();
            }
            else {

                Toast.makeText(context, "" + message, Toast.LENGTH_SHORT).show();
            }

        }


    }


    private class ValueTouchListener implements LineChartOnValueSelectListener {

        @Override
        public void onValueSelected(int lineIndex, int pointIndex, PointValue value)
        {

            Toast.makeText(context, "Selected: " + value, Toast.LENGTH_SHORT).show();
            Log.i("Selected",""+value);
        }

        @Override
        public void onValueDeselected() {
            // TODO Auto-generated method stub

        }

    }



    public class GetBranchBalanceAsyncTask extends AsyncTask<String, String, String> {

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

                al_str_key.add(GlobalConstants.USER_BRANCH_ID);
                al_str_value.add(global.getAl_login_list().get(0).get(GlobalConstants.USER_BRANCH_ID));

                al_str_key.add(GlobalConstants.ACTION);
                al_str_value.add("get_branch_balance");

                for (int i =0 ; i < al_str_key.size() ; i++)
                {
                    Log.i("Key",""+ al_str_key.get(i));
                    Log.i("Value",""+ al_str_value.get(i));
                }

                message = ws.GetBranchBalanceService(context, al_str_key, al_str_value);

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {

            gif_loader.setVisibility(View.INVISIBLE);

            if (message.equalsIgnoreCase("true"))
            {
                tv_total_amount.setText(global.getStr_branch_balance()+" Tsh");
            }
            else {
                Toast.makeText(context, "" + message, Toast.LENGTH_SHORT).show();
            }

        }

    }


}
