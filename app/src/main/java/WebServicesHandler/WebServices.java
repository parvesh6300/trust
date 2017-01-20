package WebServicesHandler;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
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

            String status = jsonObject.optString(GlobalConstants.STATUS);
            message = jsonObject.optString(GlobalConstants.MESSAGE);

            if (status.equalsIgnoreCase("1"))
            {
                al_login_user = new ArrayList<>();

                JSONObject jsonObject1 = jsonObject.getJSONObject("user_info");

                HashMap<String, String> map = new HashMap<String, String>();

                map.put(GlobalConstants.USER_ID,jsonObject1.optString(GlobalConstants.USER_ID));
                map.put(GlobalConstants.USER_NAME,jsonObject1.optString(GlobalConstants.USER_NAME));
                map.put(GlobalConstants.USER_PASSWORD,jsonObject1.optString(GlobalConstants.USER_PASSWORD));
                map.put(GlobalConstants.USER_BRANCH,jsonObject1.optString(GlobalConstants.USER_BRANCH));
                map.put(GlobalConstants.USER_BRANCH_ID,jsonObject1.optString(GlobalConstants.USER_BRANCH_ID));
                map.put(GlobalConstants.USER_ROLE_ID,jsonObject1.optString(GlobalConstants.USER_ROLE_ID));
                map.put(GlobalConstants.USER_ROLE,jsonObject1.optString(GlobalConstants.USER_ROLE));
                map.put(GlobalConstants.USER_CONTACT,jsonObject1.optString(GlobalConstants.USER_CONTACT));
                map.put(GlobalConstants.USER_EMAIL,jsonObject1.optString(GlobalConstants.USER_EMAIL));
                map.put(GlobalConstants.USER_IS_ACTIVE,jsonObject1.optString(GlobalConstants.USER_IS_ACTIVE));
                map.put(GlobalConstants.USER_STATUS,jsonObject1.optString(GlobalConstants.USER_STATUS));
                map.put(GlobalConstants.USER_CREATED,jsonObject1.optString(GlobalConstants.USER_CREATED));
                map.put(GlobalConstants.USER_DEVICE_TYPE,jsonObject1.optString(GlobalConstants.USER_DEVICE_TYPE));
                map.put(GlobalConstants.USER_DEVICE_TOKEN,jsonObject1.optString(GlobalConstants.USER_DEVICE_TOKEN));


                JSONObject jsonObject2 = jsonObject1.getJSONObject(GlobalConstants.USER_BRANCH_NAME);
                map.put(GlobalConstants.USER_BRANCH_NAME,jsonObject2.optString(GlobalConstants.USER_BRANCH_NAME));

                Log.e("Branch","Name "+jsonObject2.optString(GlobalConstants.USER_BRANCH_NAME));
                al_login_user.add(map);

                global.setAl_login_list(al_login_user);
                return "true";
            }



        } catch (Exception e) {
            e.printStackTrace();
        }

        return message;
    }


    public static String GetProductService(Context context, ArrayList<String> mParemeterKeys, ArrayList<String> mParemeterValues)
    {
        String response;

        ArrayList<HashMap<String,String>> al_product_detail;

        Global global = (Global) context.getApplicationContext();

        String message = "Some Error occured";

        try {

            response = callApiWithPerameter(GlobalConstants.TRUST_URL,mParemeterKeys,mParemeterValues);

            Log.i("GetProduct", "Product : " + response);

            JSONObject jsonObject = new JSONObject(response);

            String status = jsonObject.optString(GlobalConstants.STATUS);
            message = jsonObject.optString(GlobalConstants.MESSAGE);

            if (status.equalsIgnoreCase("1"))
            {
                al_product_detail = new ArrayList<>();

                JSONArray jsonArray = jsonObject.getJSONArray("products");

                for (int i = 0 ; i< jsonArray.length() ; i++)
                {
                    HashMap<String, String> map = new HashMap<String, String>();

                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                    map.put(GlobalConstants.PRODUCT_ID , jsonObject1.optString(GlobalConstants.PRODUCT_ID));
                    map.put(GlobalConstants.PRODUCT_SKU , jsonObject1.optString(GlobalConstants.PRODUCT_SKU));
                    map.put(GlobalConstants.PRODUCT_NAME , jsonObject1.optString(GlobalConstants.PRODUCT_NAME));
                    map.put(GlobalConstants.PRODUCT_CATEGORY , jsonObject1.optString(GlobalConstants.PRODUCT_CATEGORY));
                    map.put(GlobalConstants.PRODUCT_PRICE , jsonObject1.optString(GlobalConstants.PRODUCT_PRICE));
                    map.put(GlobalConstants.PRODUCT_IN_STOCK , jsonObject1.optString(GlobalConstants.PRODUCT_IN_STOCK));

                    al_product_detail.add(map);
                }

                global.setAl_product_details(al_product_detail);
                return "true";
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return message;
    }


    public static String GetServiceService(Context context, ArrayList<String> mParemeterKeys, ArrayList<String> mParemeterValues)
    {
        String response;

        ArrayList<HashMap<String,String>> al_service_detail;

        Global global = (Global) context.getApplicationContext();

        String message = "Some Error occured";

        try {

            response = callApiWithPerameter(GlobalConstants.TRUST_URL,mParemeterKeys,mParemeterValues);

            Log.i("GetService", "Service : " + response);

            JSONObject jsonObject = new JSONObject(response);

            String status = jsonObject.optString(GlobalConstants.STATUS);
            message = jsonObject.optString(GlobalConstants.MESSAGE);

            if (status.equalsIgnoreCase("1"))
            {
                al_service_detail = new ArrayList<>();

                JSONArray jsonArray = jsonObject.getJSONArray("services");

                for (int i = 0 ; i< jsonArray.length() ; i++)
                {
                    HashMap<String, String> map = new HashMap<String, String>();

                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                    map.put(GlobalConstants.SERVICE_ID , jsonObject1.optString(GlobalConstants.SERVICE_ID));
                    map.put(GlobalConstants.SERVICE_PRICE , jsonObject1.optString(GlobalConstants.SERVICE_PRICE));
                    map.put(GlobalConstants.SERVICE_NAME , jsonObject1.optString(GlobalConstants.SERVICE_NAME));

                    al_service_detail.add(map);
                }

                global.setAl_service_details(al_service_detail);
                return "true";
            }



        } catch (Exception e) {
            e.printStackTrace();
        }

        return message;
    }


    public static String AddClientService(Context context, ArrayList<String> mParemeterKeys, ArrayList<String> mParemeterValues)
    {
        String response;

        ArrayList<HashMap<String,String>> al_client;

        Global global = (Global) context.getApplicationContext();

        String message = "Some Error occured";

        try {

            response = callApiWithPerameter(GlobalConstants.TRUST_URL,mParemeterKeys,mParemeterValues);

            Log.i("ClientAdd", "AddClient : " + response);

            JSONObject jsonObject = new JSONObject(response);

            String status = jsonObject.optString(GlobalConstants.STATUS);
            message = jsonObject.optString(GlobalConstants.MESSAGE);

            if (status.equalsIgnoreCase("1"))
            {
                String client_id = jsonObject.optString(GlobalConstants.CLIENT_ID);

                global.setStr_client_id(client_id);

                return "true";
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return message;
    }


    public static String AddAppointmentService(Context context, ArrayList<String> mParemeterKeys, ArrayList<String> mParemeterValues)
    {
        String response;

        String message = "Some Error occured";

        try {

            response = callApiWithPerameter(GlobalConstants.TRUST_URL,mParemeterKeys,mParemeterValues);

            Log.i("AddAppointment", "Add : " + response);

            JSONObject jsonObject = new JSONObject(response);

            String status = jsonObject.optString(GlobalConstants.STATUS);
            message = jsonObject.optString(GlobalConstants.MESSAGE);

            if (status.equalsIgnoreCase("1"))
            {
                return "true";
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return message;
    }


    public static String GetAppointmentService(Context context, ArrayList<String> mParemeterKeys, ArrayList<String> mParemeterValues)
    {
        String response;

        ArrayList<HashMap<String,String>> al_apmt_detail;

        Global global = (Global) context.getApplicationContext();

        String message = "Some Error occured";

        try {
            global.getAl_apmt_details().clear();
        }
        catch (Exception e)
        {

        }

        try {

            response = callApiWithPerameter(GlobalConstants.TRUST_URL,mParemeterKeys,mParemeterValues);

            Log.i("GetApmt", "Appointments : " + response);

            JSONObject jsonObject = new JSONObject(response);

            String status = jsonObject.optString(GlobalConstants.STATUS);
            message = jsonObject.optString(GlobalConstants.MESSAGE);

            if (status.equalsIgnoreCase("1"))
            {
                al_apmt_detail = new ArrayList<>();

                JSONArray jsonArray = jsonObject.getJSONArray("appointments");

                for (int i = 0 ; i< jsonArray.length() ; i++)
                {
                    HashMap<String, String> map = new HashMap<String, String>();

                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                    map.put(GlobalConstants.APMT_ID , jsonObject1.optString(GlobalConstants.APMT_ID));
                    map.put(GlobalConstants.APMT_CLIENT_ID , jsonObject1.optString(GlobalConstants.APMT_CLIENT_ID));
                    map.put(GlobalConstants.APMT_PLAN_ID , jsonObject1.optString(GlobalConstants.APMT_PLAN_ID));
                    map.put(GlobalConstants.APMT_SERVICE_ID , jsonObject1.optString(GlobalConstants.APMT_SERVICE_ID));
                    map.put(GlobalConstants.APMT_TIME , jsonObject1.optString(GlobalConstants.APMT_TIME));

                    al_apmt_detail.add(map);
                }

                global.setAl_apmt_details(al_apmt_detail);

                return "true";
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return message;
    }


    public static String GetPlanService(Context context, ArrayList<String> mParemeterKeys, ArrayList<String> mParemeterValues)
    {
        String response;

        ArrayList<HashMap<String,String>> al_plan_detail;

        Global global = (Global) context.getApplicationContext();

        String message = "Some Error occured";

        try {

            response = callApiWithPerameter(GlobalConstants.TRUST_URL,mParemeterKeys,mParemeterValues);

            Log.i("GetPlan", "Plan : " + response);

            JSONObject jsonObject = new JSONObject(response);

            String status = jsonObject.optString(GlobalConstants.STATUS);
            message = jsonObject.optString(GlobalConstants.MESSAGE);

            if (status.equalsIgnoreCase("1"))
            {
                al_plan_detail = new ArrayList<>();

                JSONArray jsonArray = jsonObject.getJSONArray("plans");

                for (int i = 0 ; i< jsonArray.length() ; i++)
                {
                    HashMap<String, String> map = new HashMap<String, String>();

                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                    map.put(GlobalConstants.PLAN_ID , jsonObject1.optString(GlobalConstants.PLAN_ID));
                    map.put(GlobalConstants.PLAN_DURATION , jsonObject1.optString(GlobalConstants.PLAN_DURATION));
                    map.put(GlobalConstants.PLAN_PRICE , jsonObject1.optString(GlobalConstants.PLAN_PRICE));

                    map.put(GlobalConstants.PLAN_PRODUCT_ID , jsonObject1.optString(GlobalConstants.PLAN_PRODUCT_ID));
                    map.put(GlobalConstants.PLAN_PRODUCT_SKU , jsonObject1.optString(GlobalConstants.PLAN_PRODUCT_SKU));
                    map.put(GlobalConstants.PLAN_PRODUCT_NAME , jsonObject1.optString(GlobalConstants.PLAN_PRODUCT_NAME));
                    map.put(GlobalConstants.PLAN_PRODUCT_CATEGORY , jsonObject1.optString(GlobalConstants.PLAN_PRODUCT_CATEGORY));
                    map.put(GlobalConstants.PLAN_PRODUCT_PRICE , jsonObject1.optString(GlobalConstants.PLAN_PRODUCT_PRICE));
                    map.put(GlobalConstants.PLAN_PRODUCT_IN_STOCK , jsonObject1.optString(GlobalConstants.PLAN_PRODUCT_IN_STOCK));

                    map.put(GlobalConstants.PLAN_SERVICE_ID , jsonObject1.optString(GlobalConstants.PLAN_SERVICE_ID));
                    map.put(GlobalConstants.PLAN_SERVICE_PRICE , jsonObject1.optString(GlobalConstants.PLAN_SERVICE_PRICE));
                    map.put(GlobalConstants.PLAN_SERVICE_NAME , jsonObject1.optString(GlobalConstants.PLAN_SERVICE_NAME));

                    al_plan_detail.add(map);
                }

                global.setAl_plan_details(al_plan_detail);

                return "true";
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return message;
    }


    public static String SearchClientService(Context context, ArrayList<String> mParemeterKeys, ArrayList<String> mParemeterValues)
    {
        String response;

        ArrayList<HashMap<String,String>> al_client_detail;

        Global global = (Global) context.getApplicationContext();


        String message = "Some Error occured";

        try {

            response = callApiWithPerameter(GlobalConstants.TRUST_URL,mParemeterKeys,mParemeterValues);

            Log.i("SearchClient", "Client : " + response);

            JSONObject jsonObject = new JSONObject(response);

            String status = jsonObject.optString(GlobalConstants.STATUS);
            message = jsonObject.optString(GlobalConstants.MESSAGE);

            if (status.equalsIgnoreCase("1"))
            {
                al_client_detail = new ArrayList<>();

                JSONArray jsonArray = jsonObject.getJSONArray("clients");

                for (int i = 0 ; i< jsonArray.length() ; i++)
                {
                    HashMap<String, String> map = new HashMap<String, String>();

                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                    map.put(GlobalConstants.SRC_CLIENT_ID , jsonObject1.optString(GlobalConstants.SRC_CLIENT_ID));
                    map.put(GlobalConstants.SRC_CLIENT_NAME , jsonObject1.optString(GlobalConstants.SRC_CLIENT_NAME));
                    map.put(GlobalConstants.SRC_CLIENT_AGE , jsonObject1.optString(GlobalConstants.SRC_CLIENT_AGE));
                    map.put(GlobalConstants.SRC_CLIENT_CONTACT , jsonObject1.optString(GlobalConstants.SRC_CLIENT_CONTACT));
                    map.put(GlobalConstants.SRC_CLIENT_EMER_CONTACT , jsonObject1.optString(GlobalConstants.SRC_CLIENT_EMER_CONTACT));
                    map.put(GlobalConstants.SRC_CLIENT_AREA , jsonObject1.optString(GlobalConstants.SRC_CLIENT_AREA));
                    map.put(GlobalConstants.SRC_CLIENT_MED_HIS , jsonObject1.optString(GlobalConstants.SRC_CLIENT_MED_HIS));
                    map.put(GlobalConstants.SRC_CLIENT_CONTRA_HIS , jsonObject1.optString(GlobalConstants.SRC_CLIENT_CONTRA_HIS));
                    map.put(GlobalConstants.SRC_CLIENT_HIV_TESTED , jsonObject1.optString(GlobalConstants.SRC_CLIENT_HIV_TESTED));
                    map.put(GlobalConstants.SRC_CLIENT_HOW_U_REACH , jsonObject1.optString(GlobalConstants.SRC_CLIENT_HOW_U_REACH));

                    al_client_detail.add(map);
                }

                global.setAl_src_client_details(al_client_detail);

                return "true";
            }

            else{

                global.getAl_src_client_details().clear();

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return message;
    }


    public static String AddToCartService(Context context, ArrayList<String> mParemeterKeys, ArrayList<String> mParemeterValues)
    {
        String response;

        String message = "Some Error occured";

        try {

            response = callApiWithPerameter(GlobalConstants.TRUST_URL,mParemeterKeys,mParemeterValues);

            Log.i("AddCart", "CArt : " + response);

            JSONObject jsonObject = new JSONObject(response);

            String status = jsonObject.optString(GlobalConstants.STATUS);
            message = jsonObject.optString(GlobalConstants.MESSAGE);

            if (status.equalsIgnoreCase("1"))
            {
                return "true";
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        return message;
    }


    public static String GetCartService(Context context, ArrayList<String> mParemeterKeys, ArrayList<String> mParemeterValues)
    {
        String response;

        ArrayList<HashMap<String,String>> al_cart_detail;

        Global global = (Global) context.getApplicationContext();

        String message = "Some Error occured";

        try {

            response = callApiWithPerameter(GlobalConstants.TRUST_URL,mParemeterKeys,mParemeterValues);

            Log.i("GetCart", "Cart : " + response);

            JSONObject jsonObject = new JSONObject(response);

            String status = jsonObject.optString(GlobalConstants.STATUS);
            message = jsonObject.optString(GlobalConstants.MESSAGE);


            if (status.equalsIgnoreCase("1"))
            {
                al_cart_detail = new ArrayList<>();

                int items = jsonObject.optInt(GlobalConstants.GET_CART_TOTAL_ITEMS);

                JSONArray jsonArray = jsonObject.getJSONArray("items_in_cart");

                for (int i = 0 ; i< jsonArray.length() ; i++)
                {
                    HashMap<String, String> map = new HashMap<String, String>();

                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                    map.put(GlobalConstants.GET_CART_ID , jsonObject1.optString(GlobalConstants.GET_CART_ID));
                    map.put(GlobalConstants.GET_CART_CLIENT_ID , jsonObject1.optString(GlobalConstants.GET_CART_CLIENT_ID));

                    String str_item_type = jsonObject1.optString(GlobalConstants.GET_CART_ITEM_TYPE);
                    map.put(GlobalConstants.GET_CART_ITEM_TYPE , str_item_type);
                    map.put(GlobalConstants.GET_CART_ITEM_ID , jsonObject1.optString(GlobalConstants.GET_CART_ITEM_ID));
                    map.put(GlobalConstants.GET_CART_AMOUNT , jsonObject1.optString(GlobalConstants.GET_CART_AMOUNT));
                    map.put(GlobalConstants.GET_CART_ITEM_NAME , jsonObject1.optString(GlobalConstants.GET_CART_ITEM_NAME));
                    map.put(GlobalConstants.GET_CART_ITEM_PRICE , jsonObject1.optString(GlobalConstants.GET_CART_ITEM_PRICE));
                    map.put(GlobalConstants.GET_CART_ITEM_DESC , jsonObject1.optString(GlobalConstants.GET_CART_ITEM_DESC));
                    map.put(GlobalConstants.GET_CART_ITEM_CREATED , jsonObject1.optString(GlobalConstants.GET_CART_ITEM_CREATED));

                    if (str_item_type.equalsIgnoreCase("product"))
                    {
                        map.put(GlobalConstants.GET_CART_MAX_STOCK , jsonObject1.optString(GlobalConstants.GET_CART_MAX_STOCK));
                    }

                    al_cart_detail.add(map);
                }

                global.setAl_cart_details(al_cart_detail);
                global.setTotal_cart_items(items);

                return "true";
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return message;
    }


    public static String DeleteItemCartService(Context context, ArrayList<String> mParemeterKeys, ArrayList<String> mParemeterValues)
    {
        String response;

        String message = "Some Error occured";

        try {

            response = callApiWithPerameter(GlobalConstants.TRUST_URL,mParemeterKeys,mParemeterValues);

            Log.i("Delete", "CArtItem : " + response);

            JSONObject jsonObject = new JSONObject(response);

            String status = jsonObject.optString(GlobalConstants.STATUS);
            message = jsonObject.optString(GlobalConstants.MESSAGE);

            if (status.equalsIgnoreCase("1"))
            {
                return "true";
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        return message;
    }


    public static String UpdateCartItemService(Context context, ArrayList<String> mParemeterKeys, ArrayList<String> mParemeterValues)
    {
        String response;

        String message = "Some Error occured";

        try {

            response = callApiWithPerameter(GlobalConstants.TRUST_URL,mParemeterKeys,mParemeterValues);

            Log.i("Update", "CartItem : " + response);

            JSONObject jsonObject = new JSONObject(response);

            String status = jsonObject.optString(GlobalConstants.STATUS);
            message = jsonObject.optString(GlobalConstants.MESSAGE);

            if (status.equalsIgnoreCase("1"))
            {
                return "true";
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        return message;
    }


    public static String PaymentService(Context context, ArrayList<String> mParemeterKeys, ArrayList<String> mParemeterValues)
    {
        String response;

        String message = "Some Error occured";
        Global global = (Global) context.getApplicationContext();

        try {

            response = callApiWithPerameter(GlobalConstants.TRUST_URL,mParemeterKeys,mParemeterValues);

            Log.i("Payment", "Service : " + response);

            JSONObject jsonObject = new JSONObject(response);

            String status = jsonObject.optString(GlobalConstants.STATUS);
            message = jsonObject.optString(GlobalConstants.MESSAGE);


            if (status.equalsIgnoreCase("1"))
            {
                int payment_id = jsonObject.optInt(GlobalConstants.PAYMENT_ID);

                global.setPayment_id(payment_id);

                return "true";
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        return message;
    }


    public static String CheckOutService(Context context, ArrayList<String> mParemeterKeys, ArrayList<String> mParemeterValues)
    {
        String response;

        String message = "Some Error occured";
        Global global = (Global) context.getApplicationContext();

        try {

            response = callApiWithPerameter(GlobalConstants.TRUST_URL,mParemeterKeys,mParemeterValues);

            Log.i("Check", "Out : " + response);

            JSONObject jsonObject = new JSONObject(response);

            String status = jsonObject.optString(GlobalConstants.STATUS);
            message = jsonObject.optString(GlobalConstants.MESSAGE);


            if (status.equalsIgnoreCase("1"))
            {
                return "true";
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        return message;
    }


    public static String ViewServiceService(Context context, ArrayList<String> mParemeterKeys, ArrayList<String> mParemeterValues)
    {
        String response;

        ArrayList<HashMap<String,String>> al_view_service_details;

        Global global = (Global) context.getApplicationContext();

        String message = "Some Error occured";

        try {

            response = callApiWithPerameter(GlobalConstants.TRUST_URL,mParemeterKeys,mParemeterValues);

            Log.i("ViewService", "Service : " + response);

            JSONObject jsonObject = new JSONObject(response);

            String status = jsonObject.optString(GlobalConstants.STATUS);
            message = jsonObject.optString(GlobalConstants.MESSAGE);

            if (status.equalsIgnoreCase("1"))
            {
                al_view_service_details = new ArrayList<>();

                JSONArray jsonArray = jsonObject.getJSONArray("in_order");

                for (int i = 0 ; i< jsonArray.length() ; i++)
                {
                    HashMap<String, String> map = new HashMap<String, String>();

                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                    map.put(GlobalConstants.ORDER_ID , jsonObject1.optString(GlobalConstants.ORDER_ID));
                    map.put(GlobalConstants.ORDER_PMT_ID , jsonObject1.optString(GlobalConstants.ORDER_PMT_ID));
                    map.put(GlobalConstants.ORDER_CLIENT_ID , jsonObject1.optString(GlobalConstants.ORDER_CLIENT_ID));
                    map.put(GlobalConstants.ORDER_ITEM_ID , jsonObject1.optString(GlobalConstants.ORDER_ITEM_ID));
                    map.put(GlobalConstants.ORDER_ITEM_NAME , jsonObject1.optString(GlobalConstants.ORDER_ITEM_NAME));
                    map.put(GlobalConstants.ORDER_ITEM_TYPE , jsonObject1.optString(GlobalConstants.ORDER_ITEM_TYPE));
                    map.put(GlobalConstants.ORDER_ITEM_PRICE , jsonObject1.optString(GlobalConstants.ORDER_ITEM_PRICE));
                    map.put(GlobalConstants.ORDER_AMOUNT , jsonObject1.optString(GlobalConstants.ORDER_AMOUNT));
                    map.put(GlobalConstants.ORDER_CREATED , jsonObject1.optString(GlobalConstants.ORDER_CREATED));
                    map.put(GlobalConstants.ORDER_TO_PAID , jsonObject1.optString(GlobalConstants.ORDER_TO_PAID));
                    map.put(GlobalConstants.ORDER_ON_RATE , jsonObject1.optString(GlobalConstants.ORDER_ON_RATE));
                    map.put(GlobalConstants.ORDER_ONLY_ID , jsonObject1.optString(GlobalConstants.ORDER_ONLY_ID));

                    al_view_service_details.add(map);
                }

                global.setAl_view_service_details(al_view_service_details);
                return "true";
            }



        } catch (Exception e) {
            e.printStackTrace();
        }

        return message;
    }


    public static String ViewPlanService(Context context, ArrayList<String> mParemeterKeys, ArrayList<String> mParemeterValues)
    {
        String response;

        ArrayList<HashMap<String,String>> al_view_plan_details;

        Global global = (Global) context.getApplicationContext();

        String message = "Some Error occured";

        try {

            response = callApiWithPerameter(GlobalConstants.TRUST_URL,mParemeterKeys,mParemeterValues);

            Log.i("ViewPlan", "Plan : " + response);

            JSONObject jsonObject = new JSONObject(response);

            String status = jsonObject.optString(GlobalConstants.STATUS);
            message = jsonObject.optString(GlobalConstants.MESSAGE);

            if (status.equalsIgnoreCase("1"))
            {
                al_view_plan_details = new ArrayList<>();

                JSONArray jsonArray = jsonObject.getJSONArray("in_order");

                for (int i = 0 ; i< jsonArray.length() ; i++)
                {
                    HashMap<String, String> map = new HashMap<String, String>();

                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                    map.put(GlobalConstants.ORDER_ID , jsonObject1.optString(GlobalConstants.ORDER_ID));
                    map.put(GlobalConstants.ORDER_PMT_ID , jsonObject1.optString(GlobalConstants.ORDER_PMT_ID));
                    map.put(GlobalConstants.ORDER_CLIENT_ID , jsonObject1.optString(GlobalConstants.ORDER_CLIENT_ID));
                    map.put(GlobalConstants.ORDER_ITEM_ID , jsonObject1.optString(GlobalConstants.ORDER_ITEM_ID));
                    map.put(GlobalConstants.ORDER_ITEM_NAME , jsonObject1.optString(GlobalConstants.ORDER_ITEM_NAME));
                    map.put(GlobalConstants.ORDER_ITEM_TYPE , jsonObject1.optString(GlobalConstants.ORDER_ITEM_TYPE));
                    map.put(GlobalConstants.ORDER_ITEM_PRICE , jsonObject1.optString(GlobalConstants.ORDER_ITEM_PRICE));
                    map.put(GlobalConstants.ORDER_AMOUNT , jsonObject1.optString(GlobalConstants.ORDER_AMOUNT));
                    map.put(GlobalConstants.ORDER_CREATED , jsonObject1.optString(GlobalConstants.ORDER_CREATED));
                    map.put(GlobalConstants.ORDER_TO_PAID , jsonObject1.optString(GlobalConstants.ORDER_TO_PAID));
                    map.put(GlobalConstants.ORDER_ON_RATE , jsonObject1.optString(GlobalConstants.ORDER_ON_RATE));
                    map.put(GlobalConstants.ORDER_ONLY_ID , jsonObject1.optString(GlobalConstants.ORDER_ONLY_ID));

                    al_view_plan_details.add(map);
                }

                global.setAl_view_plan_details(al_view_plan_details);
                return "true";
            }



        } catch (Exception e) {
            e.printStackTrace();
        }

        return message;
    }


    public static String RenewPlanService(Context context, ArrayList<String> mParemeterKeys, ArrayList<String> mParemeterValues)
    {
        String response;

        ArrayList<HashMap<String,String>> al_view_plan_details;

        Global global = (Global) context.getApplicationContext();

        String message = "Some Error occured";

        try {

            response = callApiWithPerameter(GlobalConstants.TRUST_URL,mParemeterKeys,mParemeterValues);

            Log.i("Renew", "Plan : " + response);

            JSONObject jsonObject = new JSONObject(response);

            String status = jsonObject.optString(GlobalConstants.STATUS);
            message = jsonObject.optString(GlobalConstants.MESSAGE);

            if (status.equalsIgnoreCase("1"))
            {
                al_view_plan_details = new ArrayList<>();

                JSONArray jsonArray = jsonObject.getJSONArray("in_order");

                for (int i = 0 ; i< jsonArray.length() ; i++)
                {
                    HashMap<String, String> map = new HashMap<String, String>();

                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                    map.put(GlobalConstants.ORDER_ID , jsonObject1.optString(GlobalConstants.ORDER_ID));
                    map.put(GlobalConstants.ORDER_PMT_ID , jsonObject1.optString(GlobalConstants.ORDER_PMT_ID));
                    map.put(GlobalConstants.ORDER_CLIENT_ID , jsonObject1.optString(GlobalConstants.ORDER_CLIENT_ID));
                    map.put(GlobalConstants.ORDER_ITEM_ID , jsonObject1.optString(GlobalConstants.ORDER_ITEM_ID));
                    map.put(GlobalConstants.ORDER_ITEM_NAME , jsonObject1.optString(GlobalConstants.ORDER_ITEM_NAME));
                    map.put(GlobalConstants.ORDER_ITEM_TYPE , jsonObject1.optString(GlobalConstants.ORDER_ITEM_TYPE));
                    map.put(GlobalConstants.ORDER_ITEM_PRICE , jsonObject1.optString(GlobalConstants.ORDER_ITEM_PRICE));
                    map.put(GlobalConstants.ORDER_AMOUNT , jsonObject1.optString(GlobalConstants.ORDER_AMOUNT));
                    map.put(GlobalConstants.ORDER_CREATED , jsonObject1.optString(GlobalConstants.ORDER_CREATED));
                    map.put(GlobalConstants.ORDER_TO_PAID , jsonObject1.optString(GlobalConstants.ORDER_TO_PAID));
                    map.put(GlobalConstants.ORDER_ON_RATE , jsonObject1.optString(GlobalConstants.ORDER_ON_RATE));
                    map.put(GlobalConstants.ORDER_ONLY_ID , jsonObject1.optString(GlobalConstants.ORDER_ONLY_ID));

                    al_view_plan_details.add(map);
                }

                global.setAl_view_plan_details(al_view_plan_details);
                return "true";
            }



        } catch (Exception e) {
            e.printStackTrace();
        }

        return message;
    }




    public static String DepositHistoryService(Context context, ArrayList<String> mParemeterKeys, ArrayList<String> mParemeterValues)
    {
        String response;

        ArrayList<HashMap<String,String>> al_deposit_history;

        Global global = (Global) context.getApplicationContext();

        String message = "Some Error occured";

        try {

            response = callApiWithPerameter(GlobalConstants.TRUST_URL,mParemeterKeys,mParemeterValues);

            Log.i("Deposit", "History : " + response);

            JSONObject jsonObject = new JSONObject(response);

            String status = jsonObject.optString(GlobalConstants.STATUS);
            message = jsonObject.optString(GlobalConstants.MESSAGE);

            if (status.equalsIgnoreCase("1"))
            {
                al_deposit_history = new ArrayList<>();

                JSONArray jsonArray = jsonObject.getJSONArray("deposits_history");

                global.setInt_ac_balance(jsonObject.getInt(GlobalConstants.AC_BALANCE));

                for (int i = 0 ; i< jsonArray.length() ; i++)
                {
                    HashMap<String, String> map = new HashMap<String, String>();

                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                    map.put(GlobalConstants.DEPOSIT_ID , jsonObject1.optString(GlobalConstants.DEPOSIT_ID));
                    map.put(GlobalConstants.DEPOSIT_AMOUNT , jsonObject1.optString(GlobalConstants.DEPOSIT_AMOUNT));
                    map.put(GlobalConstants.DEPOSIT_REMARKS , jsonObject1.optString(GlobalConstants.DEPOSIT_REMARKS));
                    map.put(GlobalConstants.DEPOSIT_DATE , jsonObject1.optString(GlobalConstants.DEPOSIT_DATE));

                    al_deposit_history.add(map);
                }

                global.setAl_deposit_details(al_deposit_history);
                return "true";
            }



        } catch (Exception e) {
            e.printStackTrace();
        }

        return message;
    }


    public static String DepositMoneyService(Context context, ArrayList<String> mParemeterKeys, ArrayList<String> mParemeterValues)
    {
        String response;

        String message = "Some Error occured";

        try {

            response = callApiWithPerameter(GlobalConstants.TRUST_URL,mParemeterKeys,mParemeterValues);

            Log.i("Deposit", "Money : " + response);

            JSONObject jsonObject = new JSONObject(response);

            String status = jsonObject.optString(GlobalConstants.STATUS);
            message = jsonObject.optString(GlobalConstants.MESSAGE);

            if (status.equalsIgnoreCase("1"))
            {
                return "true";
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return message;
    }



    public static String GetProductStockService(Context context, ArrayList<String> mParemeterKeys, ArrayList<String> mParemeterValues)
    {
        String response;

        String message = "Some Error occured";

        ArrayList<HashMap<String,String>> al_stock_product;

        Global global = (Global) context.getApplicationContext();

        try {

            response = callApiWithPerameter(GlobalConstants.TRUST_URL,mParemeterKeys,mParemeterValues);

            Log.i("GetProduct", "Stock : " + response);

            JSONObject jsonObject = new JSONObject(response);

            String status = jsonObject.optString(GlobalConstants.STATUS);
            message = jsonObject.optString(GlobalConstants.MESSAGE);

            if (status.equalsIgnoreCase("1"))
            {
                al_stock_product = new ArrayList<>();

                JSONArray jsonArray = jsonObject.getJSONArray("products");

                for (int i = 0 ; i< jsonArray.length() ; i++)
                {
                    HashMap<String, String> map = new HashMap<String, String>();

                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                    map.put(GlobalConstants.PRODUCT_ID , jsonObject1.optString(GlobalConstants.PRODUCT_ID));
                    map.put(GlobalConstants.PRODUCT_SKU , jsonObject1.optString(GlobalConstants.PRODUCT_SKU));
                    map.put(GlobalConstants.PRODUCT_NAME , jsonObject1.optString(GlobalConstants.PRODUCT_NAME));
                    map.put(GlobalConstants.PRODUCT_CATEGORY , jsonObject1.optString(GlobalConstants.PRODUCT_CATEGORY));
                    map.put(GlobalConstants.PRODUCT_PRICE , jsonObject1.optString(GlobalConstants.PRODUCT_PRICE));
                    map.put(GlobalConstants.PRODUCT_IN_STOCK , jsonObject1.getString(GlobalConstants.PRODUCT_IN_STOCK ));

                    al_stock_product.add(map);
                }

                global.setAl_stock_product(al_stock_product);

                return "true";
            }
            else
            {
                global.getAl_stock_product().clear();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return message;
    }



    public static String ProductSoldService(Context context, ArrayList<String> mParemeterKeys, ArrayList<String> mParemeterValues)
    {
        String response;

        String message = "Some Error occured";
        ArrayList<HashMap<String,String>> al_product_sold;
        Global global = (Global) context.getApplicationContext();

        try {

            response = callApiWithPerameter(GlobalConstants.TRUST_URL,mParemeterKeys,mParemeterValues);

            Log.i("Product", "Sold : " + response);

            JSONObject jsonObject = new JSONObject(response);

            String status = jsonObject.optString(GlobalConstants.STATUS);
            message = jsonObject.optString(GlobalConstants.MESSAGE);

            if (status.equalsIgnoreCase("1"))
            {
                al_product_sold = new ArrayList<>();

                JSONArray jsonArray = jsonObject.getJSONArray("sold_products_list");

                for (int i = 0 ; i< jsonArray.length() ; i++)
                {
                    HashMap<String, String> map = new HashMap<String, String>();

                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                    int sold_items = jsonObject1.optInt(GlobalConstants.PRODUCT_SOLD_OUT);

                    map.put(GlobalConstants.PRODUCT_ID , jsonObject1.optString(GlobalConstants.PRODUCT_ID));
                    map.put(GlobalConstants.PRODUCT_NAME , jsonObject1.optString(GlobalConstants.PRODUCT_NAME));
                    map.put(GlobalConstants.PRODUCT_SOLD_OUT , String.valueOf(sold_items));

                    al_product_sold.add(map);
                }

                global.setAl_product_sold(al_product_sold);
                Log.e("Size",""+ global.getAl_product_sold().size());
                return "true";
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return message;
    }



    public static String UpdateStockService(Context context, ArrayList<String> mParemeterKeys, ArrayList<String> mParemeterValues)
    {
        String response;

        String message = "Some Error occured";

        try {

            response = callApiWithPerameter(GlobalConstants.TRUST_URL,mParemeterKeys,mParemeterValues);

            Log.i("Update", "Stock : " + response);

            JSONObject jsonObject = new JSONObject(response);

            String status = jsonObject.optString(GlobalConstants.STATUS);
            message = jsonObject.optString(GlobalConstants.MESSAGE);

            if (status.equalsIgnoreCase("1"))
            {
                return "true";
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        return message;
    }


    public static String AddExpenseService(Context context, ArrayList<String> mParemeterKeys, ArrayList<String> mParemeterValues)
    {
        String response;

        String message = "Some Error occured";

        try {

            response = callApiWithPerameter(GlobalConstants.TRUST_URL,mParemeterKeys,mParemeterValues);

            Log.i("Add", "Expense : " + response);

            JSONObject jsonObject = new JSONObject(response);

            String status = jsonObject.optString(GlobalConstants.STATUS);
            message = jsonObject.optString(GlobalConstants.MESSAGE);

            if (status.equalsIgnoreCase("1"))
            {
                return "true";
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return message;
    }


    public static String ExpenseHistoryService(Context context, ArrayList<String> mParemeterKeys, ArrayList<String> mParemeterValues)
    {
        String response;

        ArrayList<HashMap<String,String>> al_expense_history;

        Global global = (Global) context.getApplicationContext();

        String message = "Some Error occured";

        try {

            response = callApiWithPerameter(GlobalConstants.TRUST_URL,mParemeterKeys,mParemeterValues);

            Log.i("Expense", "History : " + response);

            JSONObject jsonObject = new JSONObject(response);

            String status = jsonObject.optString(GlobalConstants.STATUS);
            message = jsonObject.optString(GlobalConstants.MESSAGE);

            if (status.equalsIgnoreCase("1"))
            {
                al_expense_history = new ArrayList<>();

                JSONArray jsonArray = jsonObject.getJSONArray("get_in_expense");

                for (int i = 0 ; i< jsonArray.length() ; i++)
                {
                    HashMap<String, String> map = new HashMap<String, String>();

                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                    map.put(GlobalConstants.EXP_ID , jsonObject1.optString(GlobalConstants.EXP_ID));
                    map.put(GlobalConstants.EXP_AMOUNT , jsonObject1.optString(GlobalConstants.EXP_AMOUNT));
                    map.put(GlobalConstants.EXP_REASON , jsonObject1.optString(GlobalConstants.EXP_REASON));
                    map.put(GlobalConstants.EXP_DATE , jsonObject1.optString(GlobalConstants.EXP_DATE));

                    al_expense_history.add(map);
                }

                global.setAl_expense_details(al_expense_history);
                return "true";
            }



        } catch (Exception e) {
            e.printStackTrace();
        }

        return message;
    }


    public static String PendingPaymentService(Context context, ArrayList<String> mParemeterKeys, ArrayList<String> mParemeterValues)
    {
        String response;

        String message = "Some Error occured";
        Global global = (Global) context.getApplicationContext();
        ArrayList<HashMap<String ,String>> al_pending_pmt_details;
        ArrayList<String> al_order_id;

        try {

            response = callApiWithPerameter(GlobalConstants.TRUST_URL,mParemeterKeys,mParemeterValues);

            Log.i("Pending", "Payment : " + response);

            JSONObject jsonObject = new JSONObject(response);

            String status = jsonObject.optString(GlobalConstants.STATUS);
            message = jsonObject.optString(GlobalConstants.MESSAGE);


            if (status.equalsIgnoreCase("1"))
            {
                JSONArray jsonArray = jsonObject.getJSONArray("in_order");

                al_pending_pmt_details = new ArrayList<>();
                al_order_id = new ArrayList<>();

                global.setPendAmountPaid(jsonObject.getString(GlobalConstants.PEND_AMOUNT_PAID));
                global.setPendTotalCost(jsonObject.getString(GlobalConstants.PEND_TOTAL_AMOUNT));

                for (int i=0 ; i < jsonArray.length() ; i++)
                {

                    JSONObject obj = jsonArray.getJSONObject(i);

                    HashMap<String,String> map = new HashMap<>();

                    map.put(GlobalConstants.PEND_ID ,obj.optString(GlobalConstants.PEND_ID) );
                    map.put(GlobalConstants.PEND_PMT_ID ,obj.optString(GlobalConstants.PEND_PMT_ID) );
                    map.put(GlobalConstants.PEND_CLIENT_ID ,obj.optString(GlobalConstants.PEND_CLIENT_ID) );
                    map.put(GlobalConstants.PEND_ITEM_TYPE ,obj.optString(GlobalConstants.PEND_ITEM_TYPE) );
                    map.put(GlobalConstants.PEND_ITEM_ID ,obj.optString(GlobalConstants.PEND_ITEM_ID) );
                    map.put(GlobalConstants.PEND_AMOUNT ,obj.optString(GlobalConstants.PEND_AMOUNT) );
                    map.put(GlobalConstants.PEND_ORDER_ID ,obj.optString(GlobalConstants.PEND_ORDER_ID) );
                    map.put(GlobalConstants.PEND_ITEM_NAME ,obj.optString(GlobalConstants.PEND_ITEM_NAME) );
                    map.put(GlobalConstants.PEND_ITEM_PRICE ,obj.optString(GlobalConstants.PEND_ITEM_PRICE) );
                    map.put(GlobalConstants.PEND_DATE ,obj.optString(GlobalConstants.PEND_DATE) );


                    if (!al_order_id.contains(obj.optString(GlobalConstants.PEND_ORDER_ID)))
                    {
                        al_order_id.add(obj.optString(GlobalConstants.PEND_ORDER_ID));
                    }

                    al_pending_pmt_details.add(map);

                }

                global.setAl_pend_pmt_details(al_pending_pmt_details);
                global.setAl_order_id(al_order_id);

                return "true";
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        return message;
    }


    public static String ClearPendingPaymentService(Context context, ArrayList<String> mParemeterKeys, ArrayList<String> mParemeterValues)
    {
        String response;

        String message = "Some Error occured";
        Global global = (Global) context.getApplicationContext();
        ArrayList<HashMap<String ,String>> al_pending_pmt_details;

        try {

            response = callApiWithPerameter(GlobalConstants.TRUST_URL,mParemeterKeys,mParemeterValues);

            Log.i("Clear", "PendingPayment : " + response);

            JSONObject jsonObject = new JSONObject(response);

            String status = jsonObject.optString(GlobalConstants.STATUS);
            message = jsonObject.optString(GlobalConstants.MESSAGE);


            if (status.equalsIgnoreCase("1"))
            {


                return "true";
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        return message;
    }


    public static String WithdrawMoneyService(Context context, ArrayList<String> mParemeterKeys, ArrayList<String> mParemeterValues)
    {
        String response;

        String message = "Some Error occured";

        try {

            response = callApiWithPerameter(GlobalConstants.TRUST_URL,mParemeterKeys,mParemeterValues);

            Log.i("Withdraw", "Money : " + response);

            JSONObject jsonObject = new JSONObject(response);

            String status = jsonObject.optString(GlobalConstants.STATUS);
            message = jsonObject.optString(GlobalConstants.MESSAGE);

            if (status.equalsIgnoreCase("1"))
            {
                return "true";
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return message;
    }



    public static String RevenueService(Context context, ArrayList<String> mParemeterKeys, ArrayList<String> mParemeterValues)
    {
        String response;

        Global global = (Global) context.getApplicationContext();
        ArrayList<String> al_on_date;
        ArrayList<String> al_was_sum;

        String message = "Some Error occured";

        try {

            response = callApiWithPerameter(GlobalConstants.TRUST_URL,mParemeterKeys,mParemeterValues);

            Log.i("Revenue", "Service : " + response);

            JSONObject jsonObject = new JSONObject(response);
//
//            String status = jsonObject.optString(GlobalConstants.STATUS);
//            message = jsonObject.optString(GlobalConstants.MESSAGE);

//            if (status.equalsIgnoreCase("1"))
//            {
                JSONObject obj1 = jsonObject.getJSONObject("revenue");

                al_on_date = new ArrayList<>();
                al_was_sum = new ArrayList<>();

                JSONArray onDateArray = obj1.getJSONArray("ondate");
                JSONArray wasSumArray = obj1.getJSONArray("was_sum");

                for (int i =0 ; i < onDateArray.length() ; i++)
                {
                    al_on_date.add(onDateArray.get(i).toString());
                }

                for (int i = 0 ; i < wasSumArray.length() ; i++)
                {
                    al_was_sum.add(wasSumArray.get(i).toString());
                }

                global.setAl_on_date(al_on_date);
                global.setAl_was_sum(al_was_sum);

                return "true";
 //           }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return message;
    }


    public static String MoneyBankedService(Context context, ArrayList<String> mParemeterKeys, ArrayList<String> mParemeterValues)
    {
        String response;

        String message = "Some Error occured";

        try {

            response = callApiWithPerameter(GlobalConstants.TRUST_URL,mParemeterKeys,mParemeterValues);

            Log.i("Money", "Banked : " + response);

            JSONObject jsonObject = new JSONObject(response);

            String status = jsonObject.optString(GlobalConstants.STATUS);
            message = jsonObject.optString(GlobalConstants.MESSAGE);

            if (status.equalsIgnoreCase("1"))
            {
                return "true";
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return message;
    }



    public static String MoneyBankHistoryService(Context context, ArrayList<String> mParemeterKeys, ArrayList<String> mParemeterValues)
    {
        String response;

        ArrayList<HashMap<String,String>> al_money_bank_history;

        Global global = (Global) context.getApplicationContext();

        String message = "Some Error occured";

        try {

            response = callApiWithPerameter(GlobalConstants.TRUST_URL,mParemeterKeys,mParemeterValues);

            Log.i("MoneyBank", "History : " + response);

            JSONObject jsonObject = new JSONObject(response);

            String status = jsonObject.optString(GlobalConstants.STATUS);
            message = jsonObject.optString(GlobalConstants.MESSAGE);

            if (status.equalsIgnoreCase("1"))
            {
                al_money_bank_history = new ArrayList<>();

                JSONArray jsonArray = jsonObject.getJSONArray("info");

                for (int i = 0 ; i< jsonArray.length() ; i++)
                {
                    HashMap<String, String> map = new HashMap<String, String>();

                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                    map.put(GlobalConstants.MONEY_BANKED_DATE , jsonObject1.optString(GlobalConstants.MONEY_BANKED_DATE));
                    map.put(GlobalConstants.MONEY_BANKED_HIS_AMOUNT , jsonObject1.optString(GlobalConstants.MONEY_BANKED_HIS_AMOUNT));
                    map.put(GlobalConstants.MONEY_BANKED_REASON , jsonObject1.optString(GlobalConstants.MONEY_BANKED_REASON
                    ));

                    al_money_bank_history.add(map);
                }

                global.setAl_money_bank_history(al_money_bank_history);

                Log.e("Size",""+global.getAl_money_bank_history().size());

                return "true";
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return message;
    }


    public static String GetBranchBalanceService(Context context, ArrayList<String> mParemeterKeys, ArrayList<String> mParemeterValues)
    {
        String response;

        String message = "Some Error occured";

        Global global = (Global) context.getApplicationContext();

        try {

            response = callApiWithPerameter(GlobalConstants.TRUST_URL,mParemeterKeys,mParemeterValues);

            Log.i("Branch", "Balance : " + response);

            JSONObject jsonObject = new JSONObject(response);

            String status = jsonObject.optString(GlobalConstants.STATUS);
            message = jsonObject.optString(GlobalConstants.MESSAGE);

            if (status.equalsIgnoreCase("1"))
            {
                global.setStr_branch_balance(jsonObject.optString(GlobalConstants.BRANCH_BALANCE));
                return "true";
            }

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
