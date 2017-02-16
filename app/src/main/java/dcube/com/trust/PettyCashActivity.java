package dcube.com.trust;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import WebServicesHandler.CheckNetConnection;
import WebServicesHandler.GlobalConstants;
import WebServicesHandler.HideKeyboard;
import WebServicesHandler.WebServices;
import dcube.com.trust.utils.Global;
import dcube.com.trust.utils.PettyCashAdapter;
import okhttp3.OkHttpClient;
import pl.droidsonroids.gif.GifTextView;

public class PettyCashActivity extends Activity {

    Context context = this;
    ListView list_history;

    TextView tv_total_amount,tv_withdraw,tv_receipt;

    EditText ed_deposit_amount,ed_remark;

    GifTextView gif_loader;

    Global global;

    String str_deposit_amount,str_remark,str_branch;
    WebServices ws;

    CheckNetConnection cn;

    String branch_balance;

    GetBranchBalance get_balance;

    RelativeLayout rel_parent_layout;

    CustomDialogClass cdd;

    ImageView iv_receipt;

    PettyCashAdapter adapter;

    public static final String UPLOAD_KEY = "image";

    Bitmap photo;
    public static HashMap<String,String> data;

    private final int CAMERA_REQUEST = 1888;

    public boolean is_pic_selected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_petty_cash);

        global = (Global) getApplicationContext();

        cn = new CheckNetConnection(this);

        get_balance = new GetBranchBalance(this);

        rel_parent_layout = (RelativeLayout)  findViewById(R.id.rel_parent_layout);

        list_history=(ListView)findViewById(R.id.list_history);

        tv_total_amount=(TextView)findViewById(R.id.tv_total_amount);
        tv_withdraw=(TextView)findViewById(R.id.tv_withdraw);
        tv_receipt = (TextView) findViewById(R.id.tv_receipt);

        iv_receipt = (ImageView)findViewById(R.id.iv_receipt);

        ed_deposit_amount=(EditText)findViewById(R.id.ed_deposit_amount);
        ed_remark = (EditText) findViewById(R.id.ed_remark);

        gif_loader = (GifTextView) findViewById(R.id.gif_loader);

        str_branch = global.getAl_login_list().get(0).get(GlobalConstants.USER_BRANCH);

//        adapter = new PettyCashAdapter(context);
//        list_history.setAdapter(adapter);


        tv_withdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ed_deposit_amount.getText().toString().matches(""))
                {
                    Toast.makeText(context, "Enter Amount", Toast.LENGTH_SHORT).show();
                }
                else if (ed_deposit_amount.getText().toString().equalsIgnoreCase("0"))
                {
                    Toast.makeText(context, "Amount should be greater than 0", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    str_deposit_amount = ed_deposit_amount.getText().toString();

                    if (ed_remark.getText().toString().matches(""))
                    {
                        str_remark = "";
                    }
                    else
                    {
                        str_remark = ed_remark.getText().toString();
                    }

                    cdd = new CustomDialogClass(PettyCashActivity.this);
                    cdd.show();
                }

            }
        });

        rel_parent_layout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                HideKeyboard.hideSoftKeyboard(PettyCashActivity.this);
                // hideSoftKeyboard(DepositMoneyActivity.this);
                return false;
            }
        });



        tv_receipt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
        });


    }


    /**
     * Custom dialog show details of transaction
     */

    public class CustomDialogClass extends Dialog {

        public Activity c;

        public TextView cancel;
        public TextView confirm,tv_deposit,tv_total_amount,tv_balance;

        float int_total_amount,int_deposit,int_balance;


        public CustomDialogClass(Activity a) {
            super(a);
            // TODO Auto-generated constructor stub
            this.c = a;
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.deposit_dialog);

            confirm = (TextView) findViewById(R.id.confirm);
            cancel = (TextView) findViewById(R.id.cancel);
            tv_deposit = (TextView) findViewById(R.id.tv_deposit);
            tv_balance = (TextView) findViewById(R.id.tv_balance);
            tv_total_amount = (TextView) findViewById(R.id.tv_total_amount);

            branch_balance = global.getStr_branch_balance();

            tv_total_amount.setText("ACCOUNT TOTAL : "+branch_balance);
            tv_deposit.setText("WITHDRAW : "+str_deposit_amount);

            if (! branch_balance.equalsIgnoreCase(null))
            {
                int_total_amount = Float.parseFloat(branch_balance);
                int_deposit = Float.parseFloat(str_deposit_amount);
                int_balance = int_total_amount - int_deposit;

                tv_balance.setText("PROJECTED BALANCE : "+String.valueOf(int_balance));

            }
            else
            {
                Toast.makeText(context, "Account is very low", Toast.LENGTH_SHORT).show();
            }


            confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    dismiss();

                    if (cn.isNetConnected())
                    {
                        tv_deposit.setClickable(false);

                        if (is_pic_selected)
                        {
                            sendImage(photo);
                        }

                        new WdPettyCashAsyncTask().execute();
                    }
                    else
                    {
                        Toast.makeText(context, "Check Internet Connection", Toast.LENGTH_SHORT).show();
                    }

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

    /**
     * Save image in imageview
     * @param requestCode
     * @param resultCode
     * @param data
     */

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK)
        {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            iv_receipt.setImageBitmap(photo);

            is_pic_selected = true;

        }
    }


    /**
     * Hit the service and send petty cash
     */

    public class WdPettyCashAsyncTask extends AsyncTask<String, String, String> {

        OkHttpClient httpClient = new OkHttpClient();
        String resPonse = "";
        String message = "";

        @Override
        protected void onPreExecute() {

            gif_loader.setVisibility(View.VISIBLE);

            tv_withdraw.setClickable(false);

        }

        @Override
        protected String doInBackground(String... params) {

            try {

                ArrayList<String> al_str_key = new ArrayList<>();
                ArrayList<String> al_str_value = new ArrayList<>();

                al_str_key.add(GlobalConstants.USER_BRANCH_ID);
                al_str_value.add(global.getAl_login_list().get(0).get(GlobalConstants.USER_BRANCH_ID));

                al_str_key.add(GlobalConstants.PT_AMOUNT);
                al_str_value.add(str_deposit_amount);

                al_str_key.add(GlobalConstants.PT_REASON);
                al_str_value.add(str_remark);

                if (is_pic_selected)
                {
                    al_str_key.add(GlobalConstants.PT_IMAGE);
                    al_str_value.add(data.get(UPLOAD_KEY));
                }

                al_str_key.add(GlobalConstants.ACTION);
                al_str_value.add("add_receipt");

                for (int i =0 ; i < al_str_key.size() ; i++)
                {
                    Log.i("Key",""+ al_str_key.get(i));
                    Log.i("Value",""+ al_str_value.get(i));
                }

                message = ws.WdPettyCashService(context, al_str_key, al_str_value);

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {

            gif_loader.setVisibility(View.INVISIBLE);

            tv_withdraw.setClickable(true);

            if (message.equalsIgnoreCase("true"))
            {
                finish();
            }
            else
            {
                Toast.makeText(context, "" + message, Toast.LENGTH_SHORT).show();
            }

        }

    }


    /**
     * Convert image into string
     * @param bmp
     * @return
     */


    public String getStringImage(Bitmap bmp)
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }


    /**
     * Save image in Hash map
     * @param params
     */


    public void sendImage(Bitmap... params)
    {
        Bitmap bitmap = params[0];

        String uploadImage = getStringImage(bitmap);

        data = new HashMap<>();

        data.put(UPLOAD_KEY, uploadImage);

    }



}