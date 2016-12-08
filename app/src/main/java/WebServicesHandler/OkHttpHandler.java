package WebServicesHandler;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;

import okhttp3.FormBody;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Sagar on 06/12/16.
 */
public class OkHttpHandler extends AsyncTask {

    OkHttpClient httpClient = new OkHttpClient();
    String resPonse = "";

    @Override
    protected Object doInBackground(Object[] objects) {
        return null;
    }

    /**
     * Method use for Get Request
     *
     * @param url
     * @return
     * @throws IOException
     */
    String doGetRequest(String url) throws IOException {
        Request request = new Request.Builder().url(url).build();
        Response response = httpClient.newCall(request).execute();
        return response.body().string();
    }

    /**
     * Method use for Post Request
     *
     * @param url
     * @return
     * @throws IOException
     */
    String doPostRequest(String url) throws IOException {

        RequestBody body = new FormBody.Builder().build();

        Request request = new Request.Builder().url(url).post(body).build();
        Response response = httpClient.newCall(request).execute();
        return response.body().string();
    }


    private String doPostRequest(String url, ArrayList<String> _str_al_keys, ArrayList<String> _str_al_values) throws IOException {

        RequestBody body = null;

        Log.e("urll", url);

        FormBody.Builder builder = new FormBody.Builder();
        for (int i = 0; i < _str_al_keys.size(); i++) {
            body = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart(_str_al_keys.get(i), _str_al_values.get(i))
                    .build();
        }

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = httpClient.newCall(request).execute();
        return response.body().string();
    }


    public String callApiWithPerameter(String url, ArrayList<String> mParemeterKeys, ArrayList<String> mParemeterValues) throws Exception {

        StringBuilder result;
        String data = null;
        for (int i = 0; i < mParemeterKeys.size(); i++) {
            if (i == 0)
                data = URLEncoder.encode(mParemeterKeys.get(i), "UTF-8") + "=" + URLEncoder.encode(mParemeterValues.get(i), "UTF-8");
            else {
                data += "&" + URLEncoder.encode(mParemeterKeys.get(i), "UTF-8") + "=" + URLEncoder.encode(mParemeterValues.get(i), "UTF-8");

            }
        }
        // URLEncoder.encode("login_type", "UTF-8");

        // URL testUrl = new URL(fb_login_url);
        URL testUrl = new URL(url);
        result = new StringBuilder();
        long start = System.nanoTime();
        URLConnection testConnection = testUrl.openConnection();
        testConnection.setDoOutput(true);
        testConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        testConnection.setConnectTimeout(30000);
        OutputStreamWriter wr = new OutputStreamWriter(testConnection.getOutputStream());
        wr.write(data);
        wr.flush();
        BufferedReader in = new BufferedReader(new InputStreamReader(testConnection.getInputStream()));
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            result.append(inputLine);
            result.append("\n");
        }
        in.close();

        return result.toString();


    }



}
