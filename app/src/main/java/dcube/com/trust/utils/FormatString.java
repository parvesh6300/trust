package dcube.com.trust.utils;

import android.util.Log;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * Created by Sagar on 14/10/17.
 */

public class FormatString {

    private final static String TAG = FormatString.class.getName();

    public static String getCommaInString(String str_bal)
    {
        Log.i(TAG,"str_bal "+str_bal);

        if (str_bal == null || str_bal.equalsIgnoreCase("") || str_bal.equalsIgnoreCase(" "))
        {
            str_bal = "0";
        }
        else
        {

            double dbl_bal = Double.parseDouble(str_bal);

            // Log.i(TAG,"dbl_bal "+ NumberFormat.getNumberInstance(Locale.US).format(dbl_bal));

            str_bal =  NumberFormat.getNumberInstance(Locale.US).format(dbl_bal);
        }

        Log.i(TAG,"str_bal "+str_bal);

        return str_bal;
    }





}
