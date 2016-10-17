package dcube.com.trust;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import dcube.com.trust.utils.StockAdapter;

public class StockControl extends AppCompatActivity implements View.OnClickListener {

    TextView tv_quantity;

    ListView stock_list;

    int i;

    ArrayList<String> al_product= new ArrayList<>();
    ArrayList<String> al_category= new ArrayList<>();
    ArrayList<String> al_quantity= new ArrayList<>();

    StockAdapter stockAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_stock_control);

        getSupportActionBar().hide();


        tv_quantity=(TextView)findViewById(R.id.tv_quantity);

        stock_list=(ListView)findViewById(R.id.stock_list);




        fillCategoryInList();
        fillProductInList();
        fillQuantityInList();


        stockAdapter= new StockAdapter(this,al_product,al_category,al_quantity);

        stock_list.setAdapter(stockAdapter);

//        tv_quantity.setOnClickListener(this);
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
    public void onClick(View view) {

        i=0;


        Dialog dialog = new Dialog(this);


  //      QuantityDialog dialog1= new QuantityDialog(this,R.layout.quantitydialog);

        dialog.setContentView(R.layout.quantitydialog);

        final TextView tv_quantity= (TextView)dialog.findViewById(R.id.tv_quantity);
        TextView tv_plus= (TextView)dialog.findViewById(R.id.tv_plus);
        TextView tv_minus= (TextView)dialog.findViewById(R.id.tv_minus);
        TextView tv_current_time= (TextView)dialog.findViewById(R.id.tv_current_time);
        TextView tv_product_name= (TextView)dialog.findViewById(R.id.tv_product_name);
        TextView tv_product_cat= (TextView)dialog.findViewById(R.id.tv_product_cat);
        TextView tv_request= (TextView)dialog.findViewById(R.id.tv_request);

        tv_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                i++;

                tv_quantity.setText(String.valueOf(i));

            }
        });


        tv_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (i>0){
                    i--;
                }

                tv_quantity.setText(String.valueOf(i));

            }
        });



        dialog.setCancelable(true);
        dialog.create();
        dialog.show();


    }


}
