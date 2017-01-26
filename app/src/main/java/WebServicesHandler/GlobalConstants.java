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
    public static final String BRANCH = "branch";

    // *************************************  LOGIN VARIABLES  ****************************************************


    public static final String LOGIN_USER_NAME = "email";
    public static final String LOGIN_PASSWORD = "password";
    public static final String LOGIN_USER_ROLE_ID = "role_id";
    public static final String LOGIN_USER_ROLE = "role";
    public static final String LOGIN_DEVICE_TYPE = "devic_type";
    public static final String LOGIN_DEVICE_TOKEN = "device_token";

    public static final String USER_ID = "user_id";
    public static final String USER_NAME = "username";
    public static final String USER_BRANCH = "branch";
    public static final String USER_BRANCH_NAME = "branch_name";
    public static final String USER_BRANCH_ID = "branch_id";
    public static final String USER_ROLE_ID = "role_id";
    public static final String USER_ROLE = "role";
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
    public static final String PRODUCT_CATEGORY_ID = "category_id";
    public static final String PRODUCT_PRICE = "price";
    public static final String PRODUCT_IN_STOCK = "in_stock";
    public static final String PRODUCT_QUANTITY = "quantity";
    public static final String PRODUCT_SOLD_OUT = "sold_out";
    public static final String PRODUCT_SOLD_TOTAL = "get_soldout_a_product_in_sold";
    public static final String PRODUCT_REQUEST_STATUS = "status";

// ************************************* GET Service VARIABLES  ****************************************************

    public static final String SERVICE_ID = "service_id";
    public static final String SERVICE_NAME = "name";
    public static final String SERVICE_PRICE = "price";


// ************************************* GET PLAN VARIABLES  ****************************************************

    public static final String PLAN_ID = "plan_id";
    public static final String PLAN_PRICE = "price";
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

    public static final String PLAN_WITH_ELEMENTS = "elements_in_this_plan";
    public static final String PLAN_ELEMENTS = "elements";
    public static final String PLAN_ITEM_ID = "id";
    public static final String PLAN_ELEMENT_TYPE = "element_type";
    public static final String PLAN_ELEMENT_ID = "element_id";
    public static final String PLAN_ELEMENT_QUANTITY = "quantity";
    public static final String PLAN_ELEMENT_NAME = "name";
    public static final String PLAN_CREATED = "created";



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
    public static final String CLIENT_ID = "client_id";



    // ************************************* Add/GET Apppointment VARIABLES  ****************************************************

    public static final String APMT_CLIENT_ID = "client_id";
    public static final String APMT_ID = "appointment_id";
    public static final String APMT_PLAN_ID = "plan_id";
    public static final String APMT_SERVICE_ID = "service_id";
    public static final String APMT_DATE = "by_date";
    public static final String APMT_TIME= "time_of_appointment";
    public static final String APMT_REMARK= "remarks";
    public static final String APMT_is_FOLLOW_UP= "isfollowup";
    public static final String APMT_BRANCH= "branch";


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
    public static final String PAYMENT_USER_ID = "user_id";
    public static final String PAYMENT_MODE = "payment_mode";
    public static final String PAYMENT_TYPE = "payment_type";
    public static final String PAYMENT_AMOUNT = "amount";
    public static final String PAYMENT_ID = "payment_id";
    public static final String PAYMENT_DISCOUNT = "discounted";
    public static final String PAYMENT_IS_FULL_PAID = "is_full_paid";


    // ************************************* View Order VARIABLES  ****************************************************

    public static final String ORDER_ID = "order_id";
    public static final String ORDER_ONLY_ID = "id";
    public static final String ORDER_PMT_ID = "payment_id";
    public static final String ORDER_CLIENT_ID = "client_id";
    public static final String ORDER_ITEM_ID = "item_id";
    public static final String ORDER_ITEM_NAME = "item_name";
    public static final String ORDER_ITEM_TYPE = "item_type";
    public static final String ORDER_ITEM_PRICE = "item_price";
    public static final String ORDER_AMOUNT = "amount";
    public static final String ORDER_CREATED = "created";
    public static final String ORDER_TO_PAID = "to_paid";
    public static final String ORDER_ON_RATE = "on_rate";


    // ************************************* DEPOSIT Money VARIABLES  ****************************************************

    public static final String DEPOSIT_AMOUNT = "amount";
    public static final String DEPOSIT_REMARKS = "remarks";
    public static final String DEPOSIT_ID = "deposit_id";
    public static final String DEPOSIT_DATE = "date";

    public static final String AC_BALANCE = "total";

    // ************************************* STOCK VARIABLES  ****************************************************

    public static final String STOCK_ITEM_ID = "product_id";
    public static final String STOCK_ITEM_QTY = "quantity";
    public static final String STOCK_ITEM_DATE = "remarks";


    // ************************************* PRODUCTS SOLD OUT VARIABLES  ****************************************************

    public static final String SOLD_START_DATE = "start_date";
    public static final String SOLD_END_DATE = "end_date";


    // *************************************  EXPENSE VARIABLES  ****************************************************


    public static final String EXP_AMOUNT = "amount";
    public static final String EXP_REASON = "expense_reason";
    public static final String EXP_ID = "expense_id";
    public static final String EXP_DATE = "created";


    // *************************************  PENDING PAYMENT VARIABLES  ****************************************************

    public static final String PEND_ID = "id";
    public static final String PEND_PMT_ID = "payment_id";
    public static final String PEND_CLIENT_ID = "client_id";
    public static final String PEND_ITEM_TYPE = "item_type";
    public static final String PEND_ITEM_ID = "item_id";
    public static final String PEND_AMOUNT= "amount";
    public static final String PEND_ORDER_ID = "order_id";
    public static final String PEND_ITEM_NAME = "item_name";
    public static final String PEND_ITEM_PRICE = "item_price";
    public static final String PEND_DATE = "created";

    public static final String PEND_COST = "item_price";
    public static final String PEND_TOTAL_AMOUNT = "total_payable_price";
    public static final String PEND_AMOUNT_PAID = "paid_amount";



    // *************************************  WithDraw VARIABLES  ****************************************************


    public static final String WD_AMOUNT = "amount";
    public static final String WD_REASON = "reason";
    public static final String WD_USER_ID = "user_id";
    public static final String WD_DATE = "created";


    // *************************************  REVENUE VARIABLES  ****************************************************

    public static final String REVENUE_AS_PER = "as_per";


    // ************************************* MONEY BANKED VARIABLES  ****************************************************

    public static final String MONEY_BANKED_AMOUNT = "amount";

    public static final String MONEY_BANKED_HIS_AMOUNT = "amount";
    public static final String MONEY_BANKED_DATE = "date";
    public static final String MONEY_BANKED_REASON = "reason";


    // ************************************* Branch Balance VARIABLES  ****************************************************

    public static final String BRANCH_BALANCE = "net_balance";



}

