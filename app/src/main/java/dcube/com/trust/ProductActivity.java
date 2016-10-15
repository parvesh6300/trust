package dcube.com.trust;

import android.app.Activity;
import android.app.Dialog;
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

import dcube.com.trust.utils.ProductListAdapter;

public class ProductActivity extends Activity{

    ListView productlist;
    ProductListAdapter adapter;
    TextView buy;

    static ArrayList<String> name = new ArrayList<>();
    static ArrayList<String> category = new ArrayList<>();
    static ArrayList<String> quantity = new ArrayList<>();

    EditText search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        addData();

        productlist = (ListView) findViewById(R.id.productlist);
        adapter = new ProductListAdapter(this,name,category,quantity);

        buy = (TextView) findViewById(R.id.buy);

        productlist.setAdapter(adapter);
        search = (EditText) findViewById(R.id.search);

        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CustomDialogClass cdd = new CustomDialogClass(ProductActivity.this);
                cdd.show();

            }
        });


        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                name = new ArrayList<>();
                category = new ArrayList<>();
                quantity = new ArrayList<>();

            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void afterTextChanged(Editable s) {

                Log.e("TextWatcherTest", "afterTextChanged:\t" +s.toString());

                //Log.e("condom", "condom".substring(0,s.length()));

                if(s.toString().equalsIgnoreCase("condom"))
                {
                    name.add("Bull Condom");
                    category.add("Condom");
                    quantity.add("5");

                    name.add("Fiesta");
                    category.add("Condom");
                    quantity.add("12");
                }
                else
                if(s.toString().equalsIgnoreCase("implant"))
                {
                    name.add("Implanon Implants");
                    category.add("implant");
                    quantity.add("23");

                    name.add("Jadelle");
                    category.add("implant");
                    quantity.add("7");
                }
                else
                {
                    addData();
                }

                adapter = new ProductListAdapter(ProductActivity.this,name,category,quantity);
                productlist.setAdapter(adapter);
            }
        });
    }

    public class CustomDialogClass extends Dialog {

        public Activity c;

        public TextView cancel;
        public TextView confirm;

        public ListView selected;

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
            cancel = (TextView) findViewById(R.id.confirm);
            selected = (ListView) findViewById(R.id.selected_product_list);

            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                }
            });
        }
    }

    public  void addData(){
        name.add("Bull Condom");
        category.add("Condom");
        quantity.add("5");

        name.add("Fiesta");
        category.add("Condom");
        quantity.add("12");

        name.add("Implanon Implants");
        category.add("implant");
        quantity.add("23");

        name.add("Jadelle");
        category.add("implant");
        quantity.add("7");

        name.add("Safe Load");
        category.add("IUD");
        quantity.add("4");

        name.add("Silverline IUD");
        category.add("IUD");
        quantity.add("18");

        name.add("Trust Daisy");
        category.add("Emergency Pill");
        quantity.add("42");

        name.add("Depo Provera Contraceptive");
        category.add("Injectable");
        quantity.add("6");
    }
}
