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


    public ArrayList<HashMap<String,String>> al_selected_service;


    public ArrayList<String> al_selected_service_id;

    public ArrayList<HashMap<String, String>> getAl_selected_service() {
        return al_selected_service;
    }

    public void setAl_selected_service(ArrayList<HashMap<String, String>> al_selected_service) {
        this.al_selected_service = al_selected_service;
    }

    public ArrayList<String> getAl_selected_service_id() {
        return al_selected_service_id;
    }

    public void setAl_selected_service_id(ArrayList<String> al_selected_service_id) {
        this.al_selected_service_id = al_selected_service_id;
    }

    // ******************************* Plan Details Getter and Setter  *************************************************

    public ArrayList<HashMap<String,String>> al_plan_details;

    public ArrayList<HashMap<String, String>> getAl_plan_details() {
        return al_plan_details;
    }

    public void setAl_plan_details(ArrayList<HashMap<String, String>> al_plan_details) {
        this.al_plan_details = al_plan_details;
    }


    public ArrayList<HashMap<String,String>> al_selected_plan;

    public ArrayList<HashMap<String, String>> getAl_selected_plan() {
        return al_selected_plan;
    }

    public void setAl_selected_plan(ArrayList<HashMap<String, String>> al_selected_plan) {
        this.al_selected_plan = al_selected_plan;
    }


    public ArrayList<String> al_selected_plan_id;

    public ArrayList<String> getAl_selected_plan_id() {
        return al_selected_plan_id;
    }

    public void setAl_selected_plan_id(ArrayList<String> al_selected_plan_id) {
        this.al_selected_plan_id = al_selected_plan_id;
    }




    // ******************************* Search Client Getter and Setter  *************************************************

    public ArrayList<HashMap<String, String>> getAl_src_client_details() {
        return al_src_client_details;
    }

    public void setAl_src_client_details(ArrayList<HashMap<String, String>> al_src_client_details) {
        this.al_src_client_details = al_src_client_details;
    }

    public ArrayList<HashMap<String,String>> al_src_client_details;




    @Override
    public void onCreate() {

        super.onCreate();
        FontsOverride.setDefaultFont(this, "MONOSPACE", "fonts/myriad_thin.ttf");
    }
}
