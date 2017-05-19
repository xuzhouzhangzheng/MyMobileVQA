package com.example.abcd.vqa;

import android.os.Message;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class NetworkUtil {

    public static String  getHttpJsonByurlconnection(URL url)
    {
        HttpURLConnection conn;

        try
        {

            Log.v("zms","使用httpurlconnection");
            ByteArrayOutputStream os=new ByteArrayOutputStream();
            byte[] data =new byte[1024];
            int len=0;
            Log.v("URL",url.toString());
            conn=(HttpURLConnection)url.openConnection();
            conn.setRequestMethod("GET");
            Log.v("zms","before connect");
            if(conn.getResponseCode() == 200){
                System.out.print("OK");
                InputStream in = conn.getInputStream();
                while ((len=in.read(data))!=-1)
                {
                    os.write(data,0,len);
                }
                in.close();
                String json_results = new String(os.toByteArray());
                Log.v("Result",json_results);

                return json_results;
            }
            else
            {
                Log.v("zms","Wrong Code:"+conn.getResponseCode());
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        finally {

        }
        return null;
    }



}