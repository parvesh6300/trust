package dcube.com.trust.utils;

/**
 * Created by yadi on 11/10/16.
 */

import android.app.Application;

import java.util.ArrayList;
import java.util.HashMap;

public class Global extends Application {


    // ******************************* Login Getter and Setter  *************************************************

    public ArrayList<HashMap<String, String>> getAl_login_list() {
        return al_login_list;
    }

    public void setAl_login_list(ArrayList<HashMap<String, String>> al_login_list) {
        this.al_login_list = al_login_list;
    }

    public ArrayList<HashMap<String,String>> al_login_list;

    // ******************************* PRoduct Details Getter and Setter  *************************************************

    public ArrayList<HashMap<String, String>> getAl_product_details() {
        return al_product_details;
    }

    public void setAl_product_details(ArrayList<HashMap<String, String>> al_product_details) {
        this.al_product_details = al_product_details;
    }

    public ArrayList<HashMap<String,String>> al_product_details;


    // ******************************* SERVICE Details Getter and Setter  *************************************************

    public ArrayList<HashMap<String, String>> getAl_service_details() {
        return al_service_details;
    }

    public void setAl_service_details(ArrayList<HashMap<String, String>> al_service_details) {
        this.al_service_details = al_service_details;
    }

    public ArrayList<HashMap<String,String>> al_service_details;



    // ******************************* Plan Details Getter and Setter  *************************************************

    public ArrayList<HashMap<String,String>> al_plan_details;

    public ArrayList<HashMap<String, String>> getAl_plan_details() {
        return al_plan_details;
    }

    public void setAl_plan_details(ArrayList<HashMap<String, String>> al_plan_details) {
        this.al_plan_details = al_plan_details;
    }

    @Override
    public void onCreate() {

        super.onCreate();
        FontsOverride.setDefaultFont(this, "MONOSPACE", "fonts/myriad_thin.ttf");
    }
}
