package com.java.jingjia.request;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.SocketException;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.ExecutionException;

public class HttpUtil {

    private final String TAG = "HttpUtil";
    private static HttpUtil INSTANCE = null;

    private HttpUtil() {
    }

    public static HttpUtil getServerHttpResponse() {
        if (INSTANCE == null) {
            INSTANCE = new HttpUtil();
        }
        return INSTANCE;
    }

    public String getResponse(String oriUrl) {
        try {
            GetHttpResponseTask getHttpResponseTask = new GetHttpResponseTask();
            String jsonString = getHttpResponseTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, oriUrl).get();
            return jsonString;
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getHttpResponse(String allConfigUrl) {
        BufferedReader in = null;
        try {
            URL url = new URL(allConfigUrl);
            URLConnection connection = (URLConnection) url.openConnection();
            connection.setRequestProperty("Charset", "UTF-8");
            connection.setDoInput(true);
            connection.setReadTimeout(2000);
            connection.setConnectTimeout(2000);
            connection.connect();

            StringBuilder result = new StringBuilder();
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
            in.close();
            return result.toString();
        } catch (SocketException e) {
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return null;
    }

    private class GetHttpResponseTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... allConfigUrl) {
            return getHttpResponse(allConfigUrl[0]);
        }
    }
}
