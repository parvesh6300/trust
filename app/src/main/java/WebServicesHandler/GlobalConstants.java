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
    public static final String USER_BRANCH_ID = "branch_id";
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

    public static final String GET_PRODUCT_ACTION = "get_product_list";

    public static final String PRODUCT_ID = "product_id";
    public static final String PRODUCT_SKU = "SKU";
    public static final String PRODUCT_NAME = "name";
    public static final String PRODUCT_CATEGORY = "category";
    public static final String PRODUCT_PRICE = "price";
    public static final String PRODUCT_IN_STOCK = "in_stock";

// ************************************* GET Service VARIABLES  ****************************************************

    public static final String SERVICE_ID = "service_id";
    public static final String SERVICE_NAME = "name";
    public static final String SERVICE_PRICE = "price";


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


    // ************************************* Add Apppointment VARIABLES  ****************************************************

    public static final String APMT_CLIENT_NAME = "service_id";
    public static final String APMT_CLIENT_EMAIL = "name";
    public static final String APMT_CLIENT_SERVICE = "price";
    public static final String APMT_DATE = "price";
    public static final String APMT_TIME= "price";





    /*

    $name=$_POST['name'];
    $age=$_POST['age'];
    $contact=$_POST['contact'];
    $emergency_contact=$_POST['emergency_contact'];
    $area=$_POST['area'];
    $medical_history=$_POST['medical_history'];
    $contraceptive_history=$_POST['contraceptive_history'];
    $sex=$_POST['sex'];
    $children=$_POST['children'];
    $hiv_tested=$_POST['hiv_tested'];
    $how_you_reached_us=$_POST['how_you_reached_us'];

*/


}
