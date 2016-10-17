package dcube.com.trust;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog.OnDateSetListener;

import java.util.ArrayList;
import java.util.Calendar;

import dcube.com.trust.utils.SoldProductAdapter;

public class ProductSoldHistory extends FragmentActivity implements OnDateSetListener,View.OnClickListener {
    ListView lv_sold_products;

    ArrayList<String>  al_product= new ArrayList<>();
    ArrayList<String>  al_quantity= new ArrayList<>();
    ArrayList<String>  al_category= new ArrayList<>();

    LinearLayout lin_date_from,lin_date_to;

    SoldProductAdapter soldProductAdapter;

    TextView tv_date_from;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_sold_history);


        //getSupportActionBar().hide();

        lv_sold_products=(ListView)findViewById(R.id.lv_sold_products);

        lin_date_from=(LinearLayout)findViewById(R.id.lin_date_from);
        lin_date_to=(LinearLayout)findViewById(R.id.lin_date_to);


        tv_date_from=(TextView)findViewById(R.id.tv_date_from);

        fillProductInList();
        fillQuantityInList();
        fillCategoryInList();


        soldProductAdapter= new SoldProductAdapter(this,al_product,al_quantity,al_category);

        lv_sold_products.setAdapter(soldProductAdapter);

        lin_date_from.setOnClickListener(this);
        lin_date_to.setOnClickListener(this);

    }




    @Override
    public void onClick(View view) {

        if(view == lin_date_from)
        {

            Calendar now = Calendar.getInstance();
            DatePickerDialog dpd = DatePickerDialog.newInstance(ProductSoldHistory.this,
                    now.get(Calendar.YEAR),
                    now.get(Calendar.MONTH),
                    now.get(Calendar.DAY_OF_MONTH)
            );
            dpd.show(getFragmentManager(), "Datepickerdialog");

        }

        if (view== lin_date_to)
        {

        }

    }



    public void fillProductInList()
    {
        al_product.add("Bull Condom");
        al_product.add("Fiesta");
        al_product.add("Sleek/safe load");
        al_product.add("Silverline");
//        al_product.add("Product 5");
//        al_product.add("Product 6");
//        al_product.add("Product 7");
//        al_product.add("Product 8");



    }


    public void fillCategoryInList()
    {
        al_category.add("Condom");
        al_category.add("IUD");
        al_category.add("IUD");
        al_category.add("IUD");
//        al_category.add("Category 5");
//        al_category.add("Category 6");
//        al_category.add("Category 7");
//        al_category.add("Category 8");

    }


    public void fillQuantityInList()
    {
        al_quantity.add("1");
        al_quantity.add("2");
        al_quantity.add("3");
        al_quantity.add("4");
        al_quantity.add("5");
        al_quantity.add("6");
        al_quantity.add("7");
        al_quantity.add("8");

    }



    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String d = ""+dayOfMonth+"/"+(monthOfYear+1)+"/"+year;
        tv_date_from.setText(d);

    }
}
