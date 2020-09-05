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

//    public  String postResponse(String oriUrl, String data){
//        try{
//            PostHttpResponseTask postHttpResponseTask = new PostHttpResponseTask();
//            return postHttpResponseTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,oriUrl, data).get();
//        }catch (ExecutionException e){
//            e.printStackTrace();
//        }catch (InterruptedException e){
//            e.printStackTrace();
//        }
//        return null;
//    }

    private String getHttpResponse(String allConfigUrl) {
        BufferedReader in = null;
//        System.out.println("我被调用啦");
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
            Log.d(TAG, "getHttpResponse: " + result);
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


//    private String postHttpResponse(String oriUrl, String data){
//        try{
//            URL url = new URL(oriUrl);
//            URLConnection connection = (URLConnection) url.openConnection();
//            connection.addRequestProperty("encoding", "UTF-8");//添加请求属性
//            connection.setDoInput(true);//允许输入
//            connection.setDoOutput(true);//允许输出
////            connection.setRequestMethod("POST");//POST请求 要在获取输入输出流之前设置  否则报错
//            connection.setConnectTimeout(500);
//            connection.setReadTimeout(500);
//            connection.connect();
//
//            //输出
//            OutputStream os;
//            os = connection.getOutputStream();
//            OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");
//            BufferedWriter bw = new BufferedWriter(osw);
//            bw.write(data);
//            bw.flush();
//
//            //输入
//            InputStream in = connection.getInputStream();
//            InputStreamReader isr = new InputStreamReader(in,"UTF-8");
//            BufferedReader br = new BufferedReader(isr);
//
//            String line;
//            StringBuilder sb = new StringBuilder();
//            while((line = br.readLine()) != null)
//            {
//                sb.append(line);
//            }
//            bw.close();
//            osw.close();
//            os.close();
//            br.close();
//            isr.close();
//            in.close();
//
//            return sb.toString();
//        }catch (SocketException e){
//            return null;
//        }catch(MalformedURLException e){
//            e.printStackTrace();
//        }catch(IOException e){
//            e.printStackTrace();
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

//    public class PostHttpResponseTask extends AsyncTask<String, Void, String> {
//        @Override
//        protected String doInBackground(String... objects){
//            return postHttpResponse(objects[0], objects[1]);
//        }
//    }
}
