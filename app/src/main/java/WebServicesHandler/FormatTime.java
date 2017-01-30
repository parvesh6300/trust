package WebServicesHandler;

import android.content.Context;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Sagar on 30/01/17.
 */
public class FormatTime {

    String str_time;
    Context context;


    public FormatTime(Context mcontext)
    {
        this.context = mcontext;
    }

    public String FormatTime(String mdate) throws ParseException
    {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = format.parse(mdate);
        Log.i("Date",""+date);

        SimpleDateFormat time_format = new SimpleDateFormat("hh:mm a");
        str_time = time_format.format(date);
        Log.i("Time",""+str_time);

        return str_time;
    }



}
