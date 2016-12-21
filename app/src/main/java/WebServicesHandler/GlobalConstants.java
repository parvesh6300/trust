package WebServicesHandler;

/**
 * Created by Sagar on 06/12/16.
 */
public class GlobalConstants {


    public static final String TRUST_URL = "http://dcubetechnologies.com/Trust_Backend/api/webservice.php";

    //http://dcubetechnologies.com/Trust_Backend/src/api/webservice.php

    public static final String STATUS = "status";
    public static final String MESSAGE = "msg";
    public static final String ACTION = "action";


    // *************************************  LOGIN VARIABLES  ****************************************************


    public static final String LOGIN_USER_NAME = "email";
    public static final String LOGIN_PASSWORD = "password";
    public static final String LOGIN_USER_ROle = "role_id";
    public static final String LOGIN_DEVICE_TYPE = "devic_type";
    public static final String LOGIN_DEVICE_TOKEN = "device_token";

    public static final String USER_ID = "user_id";
    public static final String USER_NAME = "username";
    public static final String USER_BRANCH = "branch";
    public static final String USER_ROLE_ID = "role_id";
    public static final String USER_CONTACT = "contact";
    public static final String USER_IS_ACTIVE = "is_active";
    public static final String USER_STATUS = "status";
    public static final String USER_CREATED = "created";
    public static final String USER_PASSWORD = "password";
    public static final String USER_EMAIL = "email";
    public static final String USER_DEVICE_TYPE = "devic_type";
    public static final String USER_DEVICE_TOKEN = "device_token";


    // ************************************* GET PRODUCT VARIABLES  ****************************************************

    public static final String PRODUCT_ID = "product_id";
    public static final String PRODUCT_SKU = "SKU";
    public static final String PRODUCT_NAME = "name";
    public static final String PRODUCT_CATEGORY = "category";
    public static final String PRODUCT_PRICE = "price";
    public static final String PRODUCT_IN_STOCK = "in_stock";
    public static final String PRODUCT_QUANTITY = "quantity";


// ************************************* GET Service VARIABLES  ****************************************************

    public static final String SERVICE_ID = "service_id";
    public static final String SERVICE_NAME = "name";
    public static final String SERVICE_PRICE = "price";


// ************************************* GET PLAN VARIABLES  ****************************************************

    public static final String PLAN_ID = "plan_id";
    public static final String PLAN_PRICE = "plan_price";
    public static final String PLAN_DURATION = "plan_duration";
    public static final String PLAN_NAME = "plan_name";


    public static final String PLAN_PRODUCT_ID = "product_id";
    public static final String PLAN_PRODUCT_SKU = "SKU";
    public static final String PLAN_PRODUCT_NAME = "product_name";
    public static final String PLAN_PRODUCT_CATEGORY = "product_category";
    public static final String PLAN_PRODUCT_PRICE = "product_price";
    public static final String PLAN_PRODUCT_IN_STOCK = "in_stock";

    public static final String PLAN_SERVICE_ID = "service_id";
    public static final String PLAN_SERVICE_NAME = "service_name";
    public static final String PLAN_SERVICE_PRICE = "service_price";


// *************************************  ADD CLIENT VARIABLES  ****************************************************

    public static final String CLIENT_NAME = "name";
    public static final String CLIENT_AGE = "age";
    public static final String CLIENT_CONTACT = "contact";
    public static final String CLIENT_EMER_CONTACT = "emergency_contact";
    public static final String CLIENT_AREA = "area";
    public static final String CLIENT_MED_HISTORY = "medical_history";
    public static final String CLIENT_CONTRA_HISTORY = "contraceptive_history";
    public static final String CLIENT_SEX = "sex";
    public static final String CLIENT_CHILD = "children";
    public static final String CLIENT_HIV_TEST = "hiv_tested";
    public static final String CLIENT_REACH_WAY = "how_you_reached_us";


    // ************************************* Add/GET Apppointment VARIABLES  ****************************************************

    public static final String APMT_CLIENT_ID = "client_id";
    public static final String APMT_ID = "appointment_id";
    public static final String APMT_PLAN_ID = "plan_id";
    public static final String APMT_SERVICE_ID = "service_id";
    public static final String APMT_DATE = "date";
    public static final String APMT_TIME= "time";


    // ************************************* Search Client VARIABLES  ****************************************************

    public static final String SRC_CLIENT_BRANCH = "branch";
    public static final String SRC_CLIENT_KEYWORD = "keyword";

    public static final String SRC_CLIENT_NAME = "name";
    public static final String SRC_CLIENT_ID = "client_id";
    public static final String SRC_CLIENT_AGE = "age";
    public static final String SRC_CLIENT_CONTACT = "contact";
    public static final String SRC_CLIENT_EMER_CONTACT = "emergency_contact";
    public static final String SRC_CLIENT_AREA = "area";
    public static final String SRC_CLIENT_MED_HIS = "medical_history";
    public static final String SRC_CLIENT_CONTRA_HIS = "contraceptive_history";
    public static final String SRC_CLIENT_HIV_TESTED = "hiv_tested";
    public static final String SRC_CLIENT_HOW_U_REACH = "how_you_reached_us";


    // ************************************* Add to cart VARIABLES  ****************************************************

    public static final String CART_CLIENT_ID = "id";
    public static final String CART_ITEM_TYPE = "item_type";
    public static final String CART_ITEM_ID = "item_id";
    public static final String CART_AMOUNT = "amount";



    // ************************************* GET CART VARIABLES  ****************************************************

    public static final String GET_CART_ID = "cart_id";
    public static final String GET_CART_CLIENT_ID = "client_id";
    public static final String GET_CART_ITEM_TYPE = "item_type";
    public static final String GET_CART_ITEM_ID = "item_id";
    public static final String GET_CART_AMOUNT = "amount";
    public static final String GET_CART_ITEM_NAME = "item_name";
    public static final String GET_CART_ITEM_PRICE = "item_price";
    public static final String GET_CART_ITEM_DESC = "item_desc";
    public static final String GET_CART_ITEM_CREATED = "created";
    public static final String GET_CART_MAX_STOCK = "maximum_in_stock";

    public static final String GET_CART_TOTAL_ITEMS = "total_items_in_cart";


    // ************************************* PAYMENT VARIABLES  ****************************************************

    public static final String PAYMENT_CLIENT_ID = "client_id";
    public static final String PAYMENT_MODE = "payment_mode";
    public static final String PAYMENT_TYPE = "payment_type";
    public static final String PAYMENT_AMOUNT = "amount";



}
