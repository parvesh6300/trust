package WebServicesHandler;

import android.content.Context;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

import dcube.com.trust.utils.Global;

/**
 * Created by Sagar on 06/12/16.
 */
public class WebServices {


//    ArrayList<String> al_ParemeterKeys ;
//    ArrayList<String> al_ParemeterValues ;
//    String response;

    public static String LoginService(Context context, ArrayList<String> mParemeterKeys, ArrayList<String> mParemeterValues)
    {
        String response;

        ArrayList<HashMap<String,String>> al_login_user;

        Global global = (Global) context.getApplicationContext();

        String message = "Some Error occured";

        try {

            response = callApiWithPerameter(GlobalConstants.TRUST_URL,mParemeterKeys,mParemeterValues);

            Log.i("Login", "Login : " + response);

            JSONObject jsonObject = new JSONObject(response);

            JSONObject jsonObject1 = jsonObject.getJSONObject("user_info");

            String user_id = jsonObject1.optString(GlobalConstants.USER_ID);

            String status = jsonObject.optString(GlobalConstants.STATUS);
            message = jsonObject.optString(GlobalConstants.MESSAGE);

            if (status.equalsIgnoreCase("1"))
            {
                al_login_user = new ArrayList<>();

                HashMap<String, String> map = new HashMap<String, String>();

                map.put(GlobalConstants.USER_ID,jsonObject1.optString(GlobalConstants.USER_ID));
                map.put(GlobalConstants.USER_NAME,jsonObject1.optString(GlobalConstants.USER_NAME));
                map.put(GlobalConstants.USER_PASSWORD,jsonObject1.optString(GlobalConstants.USER_PASSWORD));
                map.put(GlobalConstants.USER_BRANCH_ID,jsonObject1.optString(GlobalConstants.USER_BRANCH_ID));
                map.put(GlobalConstants.USER_ROLE_ID,jsonObject1.optString(GlobalConstants.USER_ROLE_ID));
                map.put(GlobalConstants.USER_CONTACT,jsonObject1.optString(GlobalConstants.USER_CONTACT));
                map.put(GlobalConstants.USER_EMAIL,jsonObject1.optString(GlobalConstants.USER_EMAIL));
                map.put(GlobalConstants.USER_IS_ACTIVE,jsonObject1.optString(GlobalConstants.USER_IS_ACTIVE));
                map.put(GlobalConstants.USER_STATUS,jsonObject1.optString(GlobalConstants.USER_STATUS));
                map.put(GlobalConstants.USER_CREATED,jsonObject1.optString(GlobalConstants.USER_CREATED));
                map.put(GlobalConstants.USER_DEVICE_TYPE,jsonObject1.optString(GlobalConstants.USER_DEVICE_TYPE));
                map.put(GlobalConstants.USER_DEVICE_TOKEN,jsonObject1.optString(GlobalConstants.USER_DEVICE_TOKEN));

                al_login_user.add(map);

                global.setAl_login_list(al_login_user);
            }

            return "true";

        } catch (Exception e) {
            e.printStackTrace();
        }

        return message;
    }




    public static String callApiWithPerameter(String url, ArrayList<String> mParemeterKeys, ArrayList<String> mParemeterValues) throws Exception {

        StringBuilder result;
        String data = null;
        for (int i = 0; i < mParemeterKeys.size(); i++) {
            if (i == 0)
                data = URLEncoder.encode(mParemeterKeys.get(i), "UTF-8") + "=" + URLEncoder.encode(mParemeterValues.get(i), "UTF-8");
            else {
                data += "&" + URLEncoder.encode(mParemeterKeys.get(i), "UTF-8") + "=" + URLEncoder.encode(mParemeterValues.get(i), "UTF-8");

            }
        }
        // URLEncoder.encode("login_type", "UTF-8");

        // URL testUrl = new URL(fb_login_url);
        URL testUrl = new URL(url);
        result = new StringBuilder();
        long start = System.nanoTime();
        URLConnection testConnection = testUrl.openConnection();
        testConnection.setDoOutput(true);
        testConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        testConnection.setConnectTimeout(30000);
        OutputStreamWriter wr = new OutputStreamWriter(testConnection.getOutputStream());
        wr.write(data);
        wr.flush();
        BufferedReader in = new BufferedReader(new InputStreamReader(testConnection.getInputStream()));
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            result.append(inputLine);
            result.append("\n");
        }
        in.close();

        return result.toString();

    }


}
