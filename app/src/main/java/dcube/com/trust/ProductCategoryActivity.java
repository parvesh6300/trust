package dcube.com.trust;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import dcube.com.trust.utils.Global;
import dcube.com.trust.utils.ProductCategoryAdapter;

public class ProductCategoryActivity extends Activity {

    ListView list;
    ProductCategoryAdapter adapter;

    Global global;

    ArrayList<String> products;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_category);

        global = (Global) getApplicationContext();
        adapter = new ProductCategoryAdapter(this);

        list = (ListView) findViewById(R.id.product_category_list);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                products = new ArrayList<String>();

                switch (i)
                {
                    case 0:
                    {
                        products.add("Trust Lily");

                        products.add("Bull Condom");
                        products.add("Fiesta");
                        break;
                    }
                    case 1:
                    {
                        products.add("Implanon Implants");
                        products.add("Jadelle");
                        break;
                    }
                    case 2:
                    {
                        products.add("Sleek /safe");
                        products.add("Silverline");
                        break;
                    }
                    case 3:
                    {
                        products.add("Implanon Implants");
                        products.add("Jadelle");
                        break;
                    }
                }

                startActivity(new Intent(ProductCategoryActivity.this, BuyProductActivity.class));
            }
        });

    }
}
