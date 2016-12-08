package WebServicesHandler;

import android.app.Application;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Sagar on 06/12/16.
 */
public class MyApplication extends Application {


    // ******************************* Login Getter and Setter  *************************************************


    public ArrayList<HashMap<String, String>> getAl_login_list() {
        return al_login_list;
    }

    public void setAl_login_list(ArrayList<HashMap<String, String>> al_login_list) {
        this.al_login_list = al_login_list;
    }

    public ArrayList<HashMap<String,String>> al_login_list;





}
