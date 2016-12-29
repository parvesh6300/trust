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






    // ******************************* Selected PRoduct Details Getter and Setter  *************************************************


    public ArrayList<String> al_select_product;

    public ArrayList<String> getAl_select_product() {
        return al_select_product;
    }

    public void setAl_select_product(ArrayList<String> al_select_product) {
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



    // ******************************* Appointmemnt Getter and Setter  *************************************************

    public ArrayList<HashMap<String, String>> getAl_apmt_details() {
        return al_apmt_details;
    }

    public void setAl_apmt_details(ArrayList<HashMap<String, String>> al_apmt_details) {
        this.al_apmt_details = al_apmt_details;
    }

    public ArrayList<HashMap<String,String>> al_apmt_details;




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

    public int total_cart_items;

    public int getTotal_cart_items() {
        return total_cart_items;
    }

    public void setTotal_cart_items(int total_cart_items) {
        this.total_cart_items = total_cart_items;
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


    // ******************************* Stock Product Getter and Setter  *************************************************

    public ArrayList<HashMap<String, String>> getAl_stock_product() {
        return al_stock_product;
    }

    public void setAl_stock_product(ArrayList<HashMap<String, String>> al_stock_product) {
        this.al_stock_product = al_stock_product;
    }

    public ArrayList<HashMap<String,String>> al_stock_product;


    public ArrayList<HashMap<String, String>> getAl_product_sold() {
        return al_product_sold;
    }

    public void setAl_product_sold(ArrayList<HashMap<String, String>> al_product_sold) {
        this.al_product_sold = al_product_sold;
    }

    public ArrayList<HashMap<String,String>> al_product_sold;



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

    public ArrayList<HashMap<String,String>> al_expense_details;



    // ******************************* Pending PAyment Getter and Setter  *************************************************


    public ArrayList<HashMap<String,String>> al_pend_pmt_details;

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





    @Override
    public void onCreate() {

        super.onCreate();
        FontsOverride.setDefaultFont(this, "MONOSPACE", "fonts/myriad_thin.ttf");
    }
}
