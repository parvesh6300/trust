package dcube.com.trust.utils;

/**
 * Created by Rohit on 11/10/16.
 */

import android.app.Application;

import java.util.ArrayList;
import java.util.HashMap;

public class Global extends Application {


    public int quantity_selected;

    public int getQuantity_selected() {
        return quantity_selected;
    }

    public void setQuantity_selected(int quantity_selected) {
        this.quantity_selected = quantity_selected;
    }
    // ******************************* Login Getter and Setter  *************************************************

    public ArrayList<HashMap<String, String>> getAl_login_list() {
        return al_login_list;
    }

    public void setAl_login_list(ArrayList<HashMap<String, String>> al_login_list) {
        this.al_login_list = al_login_list;
    }

    public ArrayList<HashMap<String,String>> al_login_list;

    public String str_un = "";
    public String str_pwd="";

    public String getStr_role() {
        return str_role;
    }

    public void setStr_role(String str_role) {
        this.str_role = str_role;
    }

    public String str_role="";

    public String getStr_un() {
        return str_un;
    }

    public void setStr_un(String str_un) {
        this.str_un = str_un;
    }

    public String getStr_pwd() {
        return str_pwd;
    }

    public void setStr_pwd(String str_pwd) {
        this.str_pwd = str_pwd;
    }

    public int getInt_role_id() {
        return int_role_id;
    }

    public void setInt_role_id(int int_role_id) {
        this.int_role_id = int_role_id;
    }

    public int int_role_id=2;




    // ******************************* PRoduct Details Getter and Setter  *************************************************


    public ArrayList<HashMap<String, String>> getAl_product_details() {
        return al_product_details;
    }

    public void setAl_product_details(ArrayList<HashMap<String, String>> al_product_details) {
        this.al_product_details = al_product_details;
    }

    public ArrayList<HashMap<String,String>> al_product_details;





    // ******************************* Selected PRoduct Details Getter and Setter  *************************************************



    public ArrayList<String> al_select_product;

    public ArrayList<String> getAl_select_product() {
        return al_select_product;
    }

    public void setAl_select_product(ArrayList<String> al_select_product)
    {
        this.al_select_product = al_select_product;
    }


    public ArrayList<String> getAl_selected_product_quantity() {
        return al_selected_product_quantity;
    }

    public ArrayList<String> getAl_selected_product_category() {
        return al_selected_product_category;
    }

    public void setAl_selected_product_category(ArrayList<String> al_selected_product_category) {
        this.al_selected_product_category = al_selected_product_category;
    }

    public ArrayList<String> getAl_selected_product_sku() {
        return al_selected_product_sku;
    }

    public void setAl_selected_product_sku(ArrayList<String> al_selected_product_sku) {
        this.al_selected_product_sku = al_selected_product_sku;
    }

    public ArrayList<String> getAl_selected_product_price() {
        return al_selected_product_price;
    }

    public void setAl_selected_product_price(ArrayList<String> al_selected_product_price) {
        this.al_selected_product_price = al_selected_product_price;
    }

    public void setAl_selected_product_quantity(ArrayList<String> al_selected_product_quantity) {
        this.al_selected_product_quantity = al_selected_product_quantity;
    }

    public ArrayList<String> al_selected_product_quantity;


    public ArrayList<String> al_selected_product_category;
    public ArrayList<String> al_selected_product_sku;
    public ArrayList<String> al_selected_product_price;

    public ArrayList<String> getAl_selected_product_name() {
        return al_selected_product_name;
    }

    public void setAl_selected_product_name(ArrayList<String> al_selected_product_name) {
        this.al_selected_product_name = al_selected_product_name;
    }

    public ArrayList<String> al_selected_product_name;




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

/*
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

*/


    public ArrayList<HashMap<String, String>> getAl_element_plan() {
        return al_element_plan;
    }

    public void setAl_element_plan(ArrayList<HashMap<String, String>> al_element_plan) {
        this.al_element_plan = al_element_plan;
    }

    public ArrayList<HashMap<String,String>> al_element_plan;


    public int plan_selected_pos;

    public int getPlan_selected_pos() {
        return plan_selected_pos;
    }

    public void setPlan_selected_pos(int plan_selected_pos) {
        this.plan_selected_pos = plan_selected_pos;
    }



    // ******************************* Appointment Getter and Setter  *************************************************

    public ArrayList<HashMap<String, String>> getAl_apmt_details() {
        return al_apmt_details;
    }

    public void setAl_apmt_details(ArrayList<HashMap<String, String>> al_apmt_details) {
        this.al_apmt_details = al_apmt_details;
    }

    public ArrayList<HashMap<String,String>> al_apmt_details;


    public boolean isAppointmentSelected;

    public boolean isAppointmentSelected() {
        return isAppointmentSelected;
    }

    public void setAppointmentSelected(boolean appointmentSelected) {
        isAppointmentSelected = appointmentSelected;
    }


    public boolean isServiceAppointment;

    public boolean isServiceAppointment() {
        return isServiceAppointment;
    }

    public void setServiceAppointment(boolean serviceAppointment) {
        isServiceAppointment = serviceAppointment;
    }


    public int serviceAppointmentPos;

    public int getServiceAppointmentPos() {
        return serviceAppointmentPos;
    }

    public void setServiceAppointmentPos(int serviceAppointmentPos) {
        this.serviceAppointmentPos = serviceAppointmentPos;
    }

    // *******************************  Client Getter and Setter  *************************************************


    public String str_client_id;

    public String getStr_client_id() {
        return str_client_id;
    }

    public void setStr_client_id(String str_client_id) {
        this.str_client_id = str_client_id;
    }


    public boolean isCLientAdded;

    public boolean isCLientAdded() {
        return isCLientAdded;
    }

    public void setCLientAdded(boolean CLientAdded) {
        isCLientAdded = CLientAdded;
    }




    // ******************************* Search Client Getter and Setter  *************************************************

    public ArrayList<HashMap<String, String>> getAl_src_client_details() {
        return al_src_client_details;
    }

    public void setAl_src_client_details(ArrayList<HashMap<String, String>> al_src_client_details) {
        this.al_src_client_details = al_src_client_details;
    }

    public ArrayList<HashMap<String,String>> al_src_client_details;


    public int selected_client;

    public int getSelected_client() {
        return selected_client;
    }

    public void setSelected_client(int selected_client) {
        this.selected_client = selected_client;
    }




    // ******************************* CART Getter and Setter  *************************************************

    public ArrayList<HashMap<String, String>> getAl_cart_details() {
        return al_cart_details;
    }

    public void setAl_cart_details(ArrayList<HashMap<String, String>> al_cart_details) {
        this.al_cart_details = al_cart_details;
    }

    public ArrayList<HashMap<String,String>> al_cart_details;

    public int total_cart_items=0;

    public int getTotal_cart_items() {
        return total_cart_items;
    }

    public void setTotal_cart_items(int total_cart_items) {
        this.total_cart_items = total_cart_items;
    }

    public boolean isCart;

    public boolean isCart() {
        return isCart;
    }

    public void setCart(boolean cart) {
        isCart = cart;
    }


    // ******************************* Payment Getter and Setter  *************************************************

    public int payment_id;

    public int getPayment_id() {
        return payment_id;
    }

    public void setPayment_id(int payment_id) {
        this.payment_id = payment_id;
    }


    public String payment_amount;
    public String amount_to_pay;
    public String discount;

    public String getPayment_mode() {
        return payment_mode;
    }

    public void setPayment_mode(String payment_mode) {
        this.payment_mode = payment_mode;
    }

    public String payment_mode;

    public String getPayment_amount() {
        return payment_amount;
    }

    public void setPayment_amount(String payment_amount) {
        this.payment_amount = payment_amount;
    }

    public String getAmount_to_pay() {
        return amount_to_pay;
    }

    public void setAmount_to_pay(String amount_to_pay) {
        this.amount_to_pay = amount_to_pay;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }



    // ******************************* View Services Getter and Setter  *************************************************


    public ArrayList<HashMap<String, String>> getAl_view_service_details() {
        return al_view_service_details;
    }

    public void setAl_view_service_details(ArrayList<HashMap<String, String>> al_view_service_details) {
        this.al_view_service_details = al_view_service_details;
    }

    public ArrayList<HashMap<String,String>> al_view_service_details;




    // ******************************* View Plan Getter and Setter  *************************************************


    public ArrayList<HashMap<String, String>> getAl_view_plan_details() {
        return al_view_plan_details;
    }

    public void setAl_view_plan_details(ArrayList<HashMap<String, String>> al_view_plan_details) {
        this.al_view_plan_details = al_view_plan_details;
    }

    public ArrayList<HashMap<String,String>> al_view_plan_details;


    public boolean isPlanRenew;

    public boolean isPlanRenew() {
        return isPlanRenew;
    }

    public void setPlanRenew(boolean planRenew) {
        isPlanRenew = planRenew;
    }

    public int viewPlanSelectPos;

    public int getViewPlanSelectPos() {
        return viewPlanSelectPos;
    }

    public void setViewPlanSelectPos(int viewPlanSelectPos) {
        this.viewPlanSelectPos = viewPlanSelectPos;
    }


    public String str_selected_plan_id;

    public String getStr_selected_plan_id() {
        return str_selected_plan_id;
    }

    public void setStr_selected_plan_id(String str_selected_plan_id) {
        this.str_selected_plan_id = str_selected_plan_id;
    }



    // ******************************* Update Plan Getter and Setter  *************************************************


    public ArrayList<String> al_update_product_id;

    public ArrayList<String> getAl_update_product_id() {
        return al_update_product_id;
    }

    public void setAl_update_product_id(ArrayList<String> al_update_product_id) {
        this.al_update_product_id = al_update_product_id;
    }


    public ArrayList<String> getAl_update_product_qty() {
        return al_update_product_qty;
    }

    public void setAl_update_product_qty(ArrayList<String> al_update_product_qty) {
        this.al_update_product_qty = al_update_product_qty;
    }

    public ArrayList<String> getAl_update_service_id() {
        return al_update_service_id;
    }

    public void setAl_update_service_id(ArrayList<String> al_update_service_id) {
        this.al_update_service_id = al_update_service_id;
    }

    public ArrayList<String> getAl_update_service_qty() {
        return al_update_service_qty;
    }

    public void setAl_update_service_qty(ArrayList<String> al_update_service_qty) {
        this.al_update_service_qty = al_update_service_qty;
    }

    public ArrayList<String> al_update_product_qty = new ArrayList<>();

    public ArrayList<String> al_update_service_id= new ArrayList<>();

    public ArrayList<String> al_update_service_qty= new ArrayList<>();


    public ArrayList<String> al_update_service_type= new ArrayList<>();

    public ArrayList<String> getAl_update_product_type() {
        return al_update_product_type;
    }

    public void setAl_update_product_type(ArrayList<String> al_update_product_type) {
        this.al_update_product_type = al_update_product_type;
    }

    public ArrayList<String> getAl_update_service_type() {
        return al_update_service_type;
    }

    public void setAl_update_service_type(ArrayList<String> al_update_service_type) {
        this.al_update_service_type = al_update_service_type;
    }

    public ArrayList<String> al_update_product_type= new ArrayList<>();



    // ******************************* Stock Product Getter and Setter  *************************************************


    public ArrayList<HashMap<String, String>> getAl_stock_product() {
        return al_stock_product;
    }

    public void setAl_stock_product(ArrayList<HashMap<String, String>> al_stock_product) {
        this.al_stock_product = al_stock_product;
    }

    public ArrayList<HashMap<String,String>> al_stock_product = new ArrayList<>();


    public ArrayList<HashMap<String, String>> getAl_product_sold() {
        return al_product_sold;
    }

    public void setAl_product_sold(ArrayList<HashMap<String, String>> al_product_sold) {
        this.al_product_sold = al_product_sold;
    }

    public ArrayList<HashMap<String,String>> al_product_sold;

    public String str_total_sale;

    public String getStr_total_sale() {
        return str_total_sale;
    }

    public void setStr_total_sale(String str_total_sale) {
        this.str_total_sale = str_total_sale;
    }


    // ******************************* Deposit Getter and Setter  *************************************************


    public ArrayList<HashMap<String, String>> getAl_deposit_details() {
        return al_deposit_details;
    }

    public void setAl_deposit_details(ArrayList<HashMap<String, String>> al_deposit_details) {
        this.al_deposit_details = al_deposit_details;
    }

    public ArrayList<HashMap<String,String>> al_deposit_details;

    public int int_ac_balance;

    public int getInt_ac_balance() {
        return int_ac_balance;
    }

    public void setInt_ac_balance(int int_ac_balance) {
        this.int_ac_balance = int_ac_balance;
    }


    // ******************************* Expense Getter and Setter  *************************************************


    public ArrayList<HashMap<String, String>> getAl_expense_details() {
        return al_expense_details;
    }

    public void setAl_expense_details(ArrayList<HashMap<String, String>> al_expense_details) {
        this.al_expense_details = al_expense_details;
    }

    public ArrayList<HashMap<String,String>> al_expense_details = new ArrayList<>();


    public String str_total_expense = "0";

    public String getStr_total_expense() {
        return str_total_expense;
    }

    public void setStr_total_expense(String str_total_expense) {
        this.str_total_expense = str_total_expense;
    }

    public String str_exp_bal = "0";

    public String getStr_exp_bal() {
        return str_exp_bal;
    }

    public void setStr_exp_bal(String str_exp_bal) {
        this.str_exp_bal = str_exp_bal;
    }


    // ******************************* Pending PAyment Getter and Setter  *************************************************


    public ArrayList<HashMap<String,String>> al_pend_pmt_details = new ArrayList<>();

    public ArrayList<HashMap<String, String>> getAl_pend_pmt_details() {
        return al_pend_pmt_details;
    }

    public void setAl_pend_pmt_details(ArrayList<HashMap<String, String>> al_pend_pmt_details) {
        this.al_pend_pmt_details = al_pend_pmt_details;
    }


    public String getPendAmountToPay() {
        return pendAmountToPay;
    }

    public void setPendAmountToPay(String pendAmountToPay) {
        this.pendAmountToPay = pendAmountToPay;
    }

    public String getPendAmountPaid() {
        return pendAmountPaid;
    }

    public void setPendAmountPaid(String pendAmountPaid) {
        this.pendAmountPaid = pendAmountPaid;
    }

    public String getPendTotalCost() {

        return pendTotalCost;
    }

    public void setPendTotalCost(String pendTotalCost) {
        this.pendTotalCost = pendTotalCost;
    }

    public String pendTotalCost;
    public String pendAmountToPay;
    public String pendAmountPaid;

    public boolean isPendingPayment;

    public boolean isPendingPayment() {
        return isPendingPayment;
    }

    public void setPendingPayment(boolean pendingPayment) {
        isPendingPayment = pendingPayment;
    }


    public ArrayList<String> al_order_id;

    public ArrayList<String> getAl_order_id() {
        return al_order_id;
    }

    public void setAl_order_id(ArrayList<String> al_order_id) {
        this.al_order_id = al_order_id;
    }


    // ******************************* Revenue Getter and Setter  *************************************************

    public ArrayList<String> al_on_date;

    public ArrayList<String> getAl_on_date() {
        return al_on_date;
    }

    public void setAl_on_date(ArrayList<String> al_on_date) {
        this.al_on_date = al_on_date;
    }

    public ArrayList<String> getAl_was_sum() {

        return al_was_sum;
    }

    public void setAl_was_sum(ArrayList<String> al_was_sum) {
        this.al_was_sum = al_was_sum;
    }

    public ArrayList<String> al_was_sum;


    // ******************************* Money Bank Getter and Setter  *************************************************

    public ArrayList<HashMap<String, String>> getAl_money_bank_history() {
        return al_money_bank_history;
    }

    public void setAl_money_bank_history(ArrayList<HashMap<String, String>> al_money_bank_history) {
        this.al_money_bank_history = al_money_bank_history;
    }

    public ArrayList<HashMap<String,String>> al_money_bank_history;



    // ******************************* Branch Balance Getter and Setter  *************************************************


    public String str_branch_balance="0";

    public String getStr_branch_balance() {
        return str_branch_balance;
    }

    public void setStr_branch_balance(String str_branch_balance) {
        this.str_branch_balance = str_branch_balance;
    }


    // ******************************* Money to bank Balance Getter and Setter  *************************************************


    public String getStr_money_to_bank() {
        return str_money_to_bank;
    }

    public void setStr_money_to_bank(String str_money_to_bank) {
        this.str_money_to_bank = str_money_to_bank;
    }

    public String str_money_to_bank="0";




    // ******************************* BANK Branch Balance Getter and Setter  *************************************************


    public String getStr_bank_bra_bal() {
        return str_bank_bra_bal;
    }

    public void setStr_bank_bra_bal(String str_bank_bra_bal) {
        this.str_bank_bra_bal = str_bank_bra_bal;
    }


    public String str_bank_bra_bal;

    public ArrayList<HashMap<String, String>> getAl_branch_bal_his() {
        return al_branch_bal_his;
    }

    public void setAl_branch_bal_his(ArrayList<HashMap<String, String>> al_branch_bal_his) {
        this.al_branch_bal_his = al_branch_bal_his;
    }

    public ArrayList<HashMap<String,String>> al_branch_bal_his;



    // ******************************* Petty Cash Getter and Setter  *************************************************

    public String getStr_petty_balance() {
        return str_petty_balance;
    }

    public void setStr_petty_balance(String str_petty_balance) {
        this.str_petty_balance = str_petty_balance;
    }

    public String str_petty_balance ="0";

    public ArrayList<HashMap<String, String>> getAl_petty_details() {
        return al_petty_details;
    }

    public void setAl_petty_details(ArrayList<HashMap<String, String>> al_petty_details) {
        this.al_petty_details = al_petty_details;
    }

    public ArrayList<HashMap<String,String>> al_petty_details;




    // ******************************* Client Info Getter and Setter  *************************************************


    public ArrayList<HashMap<String,String>> al_client_info;


    public ArrayList<HashMap<String, String>> getAl_client_info() {
        return al_client_info;
    }

    public void setAl_client_info(ArrayList<HashMap<String, String>> al_client_info) {
        this.al_client_info = al_client_info;

    }


    // ******************************* Cash in hand Getter and Setter  *************************************************


    public String getStr_cash_in_hand_balance() {
        return str_cash_in_hand_balance;
    }

    public void setStr_cash_in_hand_balance(String str_cash_in_hand_balance) {
        this.str_cash_in_hand_balance = str_cash_in_hand_balance;
    }

    public String str_cash_in_hand_balance;

    public ArrayList<HashMap<String, String>> getAl_cash_in_hand_his() {
        return al_cash_in_hand_his;
    }

    public void setAl_cash_in_hand_his(ArrayList<HashMap<String, String>> al_cash_in_hand_his) {
        this.al_cash_in_hand_his = al_cash_in_hand_his;
    }

    public ArrayList<HashMap<String,String>> al_cash_in_hand_his;


    // ******************************* Client History Getter and Setter  *************************************************

    public ArrayList<HashMap<String,String>> al_client_sales_history;


    public ArrayList<HashMap<String, String>> getAl_client_sales_history() {
        return al_client_sales_history;
    }

    public void setAl_client_sales_history(ArrayList<HashMap<String, String>> al_client_sales_history) {
        this.al_client_sales_history = al_client_sales_history;
    }





    @Override
    public void onCreate() {

        super.onCreate();
        FontsOverride.setDefaultFont(this, "MONOSPACE", "fonts/myriad_thin.ttf");
    }
}
