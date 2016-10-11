package dcube.com.trust.utils;

/**
 * Created by yadi on 11/10/16.
 */

import android.app.Application;

import java.util.ArrayList;
import java.util.HashMap;

public class Global extends Application {

    //Updated your class body:
    boolean readonly;
    boolean run;
    boolean fblogin;

    public ArrayList<HashMap<String, String>> ReviewListing;
    public ArrayList<HashMap<String, String>> LoginListing;
    public ArrayList<HashMap<String, String>> SignupListing;
    public ArrayList<HashMap<String, String>> SalonDetailListing;
    public ArrayList<HashMap<String, String>> StylistListing;
    public ArrayList<HashMap<String, String>> BookedListing;

    @Override
    public void onCreate() {

        super.onCreate();
        FontsOverride.setDefaultFont(this, "MONOSPACE", "fonts/myriad_thin.ttf");
    }
}
