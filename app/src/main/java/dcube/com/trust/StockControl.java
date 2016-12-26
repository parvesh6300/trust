package dcube.com.trust;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import WebServicesHandler.GlobalConstants;
import WebServicesHandler.WebServices;
import dcube.com.trust.utils.Global;
import dcube.com.trust.utils.StockAdapter;
import okhttp3.OkHttpClient;
import pl.droidsonroids.gif.GifTextView;

public class StockControl extends Activity  {

    TextView tv_quantity;

    ListView stock_list;

    int i;

    ArrayList<String> al_product= new ArrayList<>();
    ArrayList<String> al_category= new ArrayList<>();
    ArrayList<String> al_quantity= new ArrayList<>();

    StockAdapter stockAdapter;

    Global global;
    GifTextView gif_loader;
    WebServices ws;
    Context context = StockControl.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_stock_control);

        global = (Global) getApplicationContext();

        tv_quantity=(TextView)findViewById(R.id.tv_quantity);

        stock_list=(ListView)findViewById(R.id.stock_list);
        gif_loader = (GifTextView) findViewById(R.id.gif_loader);


        fillCategoryInList();
        fillProductInList();
        fillQuantityInList();

        stockAdapter= new StockAdapter(this,al_product,al_category,al_quantity);

        stock_list.setAdapter(stockAdapter);


        new StockRequestAsyncTask().execute();
//        tv_quantity.setOnClickListener(this);
    }

    public void fillProductInList()
    {
        al_product.add("Bull Condom");
        al_product.add("Fiesta");
        al_product.add("Sleek/safe load");
        al_product.add("Silverline");

    }

    public void fillCategoryInList()
    {
        al_category.add("Condom");
        al_category.add("IUD");
        al_category.add("IUD");
        al_category.add("IUD");
    }

    public void fillQuantityInList()
    {
        al_quantity.add("1");
        al_quantity.add("2");
        al_quantity.add("3");
        al_quantity.add("4");

    }





    public class StockRequestAsyncTask extends AsyncTask<String, String, String> {

        OkHttpClient httpClient = new OkHttpClient();
        String resPonse = "";
        String message = "";
        String str_client_id;

        @Override
        protected void onPreExecute() {

            gif_loader.setVisibility(View.VISIBLE);

            str_client_id = global.getAl_src_client_details().get(global.getSelected_client()).
                    get(GlobalConstants.SRC_CLIENT_ID);
        }

        @Override
        protected String doInBackground(String... params) {

            try {

                for ( int j=0 ; j < global.al_selected_product_id.size() ; j++)
                {
                    ArrayList<String> al_str_key = new ArrayList<>();
                    ArrayList<String> al_str_value = new ArrayList<>();

                    al_str_key.add(GlobalConstants.CART_CLIENT_ID);
                    al_str_value.add(str_client_id);

                    al_str_key.add(GlobalConstants.CART_ITEM_ID);
                    al_str_value.add(global.al_selected_product_id.get(j));

                    al_str_key.add(GlobalConstants.CART_ITEM_TYPE);
                    al_str_value.add("product");

                    al_str_key.add(GlobalConstants.CART_AMOUNT);
                    al_str_value.add(global.al_selected_product_quantity.get(j));

                    al_str_key.add(GlobalConstants.ACTION);
                    al_str_value.add("add_to_cart");

                    message = ws.AddToCartService(context, al_str_key, al_str_value);

                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {

            gif_loader.setVisibility(View.GONE);

            if (message.equalsIgnoreCase("true"))
            {
                finish();
            }
            else {
                Toast.makeText(context, "" + message, Toast.LENGTH_SHORT).show();
            }

        }

    }



}
