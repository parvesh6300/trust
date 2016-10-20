package dcube.com.trust;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import dcube.com.trust.utils.PlanListAdapter;
import dcube.com.trust.utils.ProductSelectedAdapter;

public class BuyPlanActivity extends Activity{

    ListView servicelist;
    PlanListAdapter adapter;

    TextView buy;

    static ArrayList<String> name = new ArrayList<>();
    static ArrayList<String> productCost = new ArrayList<>();
    static ArrayList<String> serviceCost = new ArrayList<>();

    EditText search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_plan);

        addData();

        servicelist = (ListView) findViewById(R.id.servicelist);
        adapter = new PlanListAdapter(this,name,productCost,serviceCost);

        buy = (TextView) findViewById(R.id.buy);

        servicelist.setAdapter(adapter);
        search = (EditText) findViewById(R.id.search);

        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CustomDialogClass cdd = new CustomDialogClass(BuyPlanActivity.this);
                cdd.show();
            }
        });

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                name = new ArrayList<>();
                productCost = new ArrayList<>();
                serviceCost = new ArrayList<>();

            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void afterTextChanged(Editable s) {

                Log.e("TextWatcherTest", "afterTextChanged:\t" +s.toString());

                if(s.toString().equalsIgnoreCase("condom"))
                {
                    name.add("Bull Condom");
                    productCost.add("Condom");
                    serviceCost.add("5");

                    name.add("Fiesta");
                    productCost.add("Condom");
                    serviceCost.add("12");
                }
                else
                if(s.toString().equalsIgnoreCase("implant"))
                {
                    name.add("Implanon Implants");
                    productCost.add("implant");
                    serviceCost.add("23");

                    name.add("Jadelle");
                    productCost.add("implant");
                    serviceCost.add("7");
                }
                else
                {
                    addData();
                }

                adapter = new PlanListAdapter(BuyPlanActivity.this,name,productCost,serviceCost);
                servicelist.setAdapter(adapter);
            }
        });
    }

    public class CustomDialogClass extends Dialog {

        public Activity c;

        public TextView cancel;
        public TextView confirm;

        public ListView selected;

        ProductSelectedAdapter selectedAdapter;

        public CustomDialogClass(Activity a) {
            super(a);
            // TODO Auto-generated constructor stub
            this.c = a;
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.buy_product_dialog);

            confirm = (TextView) findViewById(R.id.confirm);
            cancel = (TextView) findViewById(R.id.cancel);
            selected = (ListView) findViewById(R.id.selected_product_list);

            selectedAdapter = new ProductSelectedAdapter(BuyPlanActivity.this);
            selected.setAdapter(selectedAdapter);

            confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    startActivity(new Intent(BuyPlanActivity.this,GenerateInvoiceActivity.class));
                }
            });

            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    dismiss();
                }
            });
        }
    }

    public  void addData(){
        name.add("IUCD Sleek/Copper T/Safeload");
        productCost.add("5,000");
        serviceCost.add("20,000");

        name.add("Silverline");
        productCost.add("15,000");
        serviceCost.add("20,000");

        name.add("CPAC + IUCD");
        productCost.add("");
        serviceCost.add("150,000");

        name.add("Gynaecologist");
        productCost.add("");
        serviceCost.add("50,000");

        name.add("Family Planning Counseling");
        productCost.add("Free");
        serviceCost.add("Free");

    }
}
