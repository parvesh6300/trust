package dcube.com.trust.utils;

/**
 * Created by yadi on 11/10/16.
 */

import android.app.Application;

import java.util.ArrayList;
import java.util.HashMap;

public class Global extends Application {

    boolean readonly;
    boolean run;
    boolean fblogin;

    public ArrayList<HashMap<String, String>> ProductListing;

    public ArrayList<HashMap<String, String>> getProductListing() {
        return ProductListing;
    }

    public void setProductListing(ArrayList<HashMap<String, String>> productListing) {
        ProductListing = productListing;
    }

    public ArrayList<HashMap<String, String>> LoginListing;
    public ArrayList<HashMap<String, String>> SignupListing;
    public ArrayList<HashMap<String, String>> SalonDetailListing;
    public ArrayList<HashMap<String, String>> StylistListing;
    public ArrayList<HashMap<String, String>> BookedListing;

    // ******************************* Login Getter and Setter  *************************************************


    public ArrayList<HashMap<String, String>> getAl_login_list() {
        return al_login_list;
    }

    public void setAl_login_list(ArrayList<HashMap<String, String>> al_login_list) {
        this.al_login_list = al_login_list;
    }

    public ArrayList<HashMap<String,String>> al_login_list;


    @Override
    public void onCreate() {

        super.onCreate();
        FontsOverride.setDefaultFont(this, "MONOSPACE", "fonts/myriad_thin.ttf");
    }
}
