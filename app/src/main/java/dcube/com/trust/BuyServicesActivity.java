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
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import dcube.com.trust.utils.ServiceListAdapter;

public class BuyServicesActivity extends Activity {

    ListView servicelist;
    ServiceListAdapter adapter;

    TextView buy;

    ArrayList<String> name = new ArrayList<>();
    ArrayList<String> serviceCost = new ArrayList<>();

    EditText search;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_services);


        addData();

        servicelist = (ListView) findViewById(R.id.servicelist);

        buy = (TextView) findViewById(R.id.buy);
        search = (EditText) findViewById(R.id.search);


        adapter = new ServiceListAdapter(this,name,serviceCost);
        servicelist.setAdapter(adapter);


        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CustomDialogClass cdd = new CustomDialogClass(BuyServicesActivity.this);
                cdd.show();
            }
        });



        servicelist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                adapter.setSelectedIndex(i);
                adapter.notifyDataSetChanged();

            }
        });



        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                name = new ArrayList<>();
                serviceCost = new ArrayList<>();

            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void afterTextChanged(Editable s) {

                Log.e("TextWatcherTest", "afterTextChanged:\t" +s.toString());

                if(s.toString().equalsIgnoreCase("IUCD"))
                {
                    name.add("CPAC + IUCD");
                    serviceCost.add("150,000");
                }

                else if(s.toString().equalsIgnoreCase("Ult"))
                {
                    name.add("Ultrasound");
                    serviceCost.add("20,000");
                }
                else
                {
                    addData();
                }

                adapter = new ServiceListAdapter(BuyServicesActivity.this,name,serviceCost);
                servicelist.setAdapter(adapter);
            }
        });


    }

    public  void addData(){

        name.add("Provider Counsultation");
        serviceCost.add("30,000");

        name.add("CPAC + IUCD");
        serviceCost.add("150,000");

        name.add("Gynaecologist");
        serviceCost.add("50,000");

        name.add("Pap Smear");
        serviceCost.add("70,000");

        name.add("Ultrasound");
        serviceCost.add("20,000");

    }

    public class CustomDialogClass extends Dialog {

        public Activity c;

        public TextView cancel;
        public TextView confirm;

        public ListView selected;

        ServiceSelectedAdapter selectedAdapter;

        public CustomDialogClass(Activity a) {
            super(a);
            // TODO Auto-generated constructor stub
            this.c = a;
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);

            setContentView(R.layout.buy_service_dialog);

            confirm = (TextView) findViewById(R.id.confirm);
            cancel = (TextView) findViewById(R.id.cancel);
            selected = (ListView) findViewById(R.id.selected_product_list);

            selectedAdapter = new ServiceSelectedAdapter(BuyServicesActivity.this,name,serviceCost);
            selected.setAdapter(selectedAdapter);

            confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    dismiss();

                    startActivity(new Intent(BuyServicesActivity.this,GenerateInvoiceActivity.class));
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


}
