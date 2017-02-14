package WebServicesHandler;

import android.app.Activity;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by Rohit on 13/02/17.
 */

public class HideKeyboard {

//    static Activity activity;
//
//    public HideKeyboard(Activity mactivity)
//    {
//        activity = mactivity;
//    }

    public static void hideSoftKeyboard(Activity activity)
    {
        InputMethodManager inputMethodManager = (InputMethodManager)
                activity.getSystemService(Activity.INPUT_METHOD_SERVICE);

        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
    }




}
