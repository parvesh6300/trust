package dcube.com.trust;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import dcube.com.trust.utils.StockAdapter;

public class StockControl extends Activity  {

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

}
