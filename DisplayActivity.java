package com.example.abcd.vqa;

import android.app.ActionBar;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by abcd on 2017/3/22.
 */

public class DisplayActivity extends AppCompatActivity {

    int img_position;
    int current_img_position;
    int cursor;
    EditText et1;
    ImageView displayview;
    TextView dspTextView;
    ArrayList<Integer> checked_number = new ArrayList<Integer>();
    Integer[] position_array;
    SerializableMap serializableanswermap = new SerializableMap();
    ArrayList<String> answers;


    //新建Handler的对象，在这里接收Message，然后启动Dialog进行显示
    private Handler handler = new Handler(){
    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        switch (msg.what) {
            case 1:
                String response = (String) msg.obj;
                //进行JSON解析
                if(response != null)
                {
                    try{
                        JSONObject jsonObject = new JSONObject(response);
                        boolean status = jsonObject.getBoolean("status");
                        if(status == true)
                        {
                            //服务器返回5个答案
                            showdialog();
                        }
                        else
                        {
                            //服务器返回错误提示，显示Message
                            String message = jsonObject.getString("message");
                        }
                    }catch (JSONException e)
                    {
                        e.printStackTrace();
                    }
                }
                System.out.print(response);
                break;

            default:
                break;
            }
        }
    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dsiplay);

        android.support.v7.app.ActionBar actionBar = this.getSupportActionBar();
        if(actionBar!=null) {
            actionBar.setDisplayShowCustomEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
            LayoutInflater inflator = LayoutInflater.from(this);
            View v = inflator.inflate(R.layout.actionbar_layout, null);
            ((LinearLayout) v).setGravity(Gravity.CENTER);
            actionBar.setCustomView(v);
            //actionBar.setHomeAsUpIndicator(R.drawable.back);
            dspTextView = (TextView)findViewById(R.id.description_text);
            if(dspTextView == null)
            {
                Log.v("dspTextView","despTextView is null");
            }
        }
        else
        {
            Log.v("ActionBar","ActionBar is Null");
        }

        Intent intent = getIntent();
        SerializableBoolMap set = (SerializableBoolMap) intent.getSerializableExtra(GridViewActivity.EXTRA_GRID);
        HashMap<Integer, Boolean> hashMap = (HashMap<Integer, Boolean>) set.getMap();
        for(Map.Entry<Integer,Boolean> entry:hashMap.entrySet())
        {
            if(entry.getValue()==true)
            {
                Integer position = entry.getKey();
                checked_number.add(position);
            }
        }

        Integer [] temp_array = new Integer[checked_number.size()];
        position_array = (Integer[]) checked_number.toArray(temp_array);
        cursor = 0;

        displayview = (ImageView)findViewById(R.id.imageViewinDly);
        img_position = position_array[cursor%position_array.length];
        displayview.setImageResource(Temporary_Image_Integar.MyRPositionMap[img_position]);
        et1 = (EditText)findViewById(R.id.edittext);
        dspTextView.setText("Picture: "+(cursor+1));

        //此按钮的操作要发送HTTP请求，返回JSON数据并解析，然后显示在对话框中
        Button button1 = (Button)findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accessThread1 mythread1=new accessThread1();
                mythread1.start();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                //Toast.makeText(this.getApplicationContext(),"Home Button",Toast.LENGTH_LONG).show();
                finish();
                break;
            case R.id.item_confirm:
                Toast.makeText(this.getApplicationContext(),"Confirm Button",Toast.LENGTH_LONG).show();
                break;
            case R.id.item_next:
                //Toast.makeText(this.getApplicationContext(),"Next Button",Toast.LENGTH_LONG).show();
                img_position = position_array[++cursor%position_array.length];
                displayview.setImageResource(Temporary_Image_Integar.MyRPositionMap[img_position % Temporary_Image_Integar.MyRPositionMap.length]);
                dspTextView.setText("Picture: "+(cursor+1));
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    private class  accessThread1 extends Thread{

        @Override
        public void run() {

            Log.v("zms","线程accessThread开始");
            Message msg1=handler.obtainMessage();
            msg1.what=1;

            Map<String,String> params;
            params = new HashMap<String,String>();

            //查找到该图片的ID，并作为HTTP GET请求的参数
            String img_id;
            img_id = Integer.toString(Temporary_Image_Integar.MyIdPositionMap[img_position]);
            params.put("img_id",img_id);
            //得到问题
            params.put("q",et1.getText().toString());

            try {
                URL url;
                // StringBuilder是用来组拼请求地址和参数
                StringBuilder sb = new StringBuilder();
                sb.append("http://159.226.21.45:8000/q").append("?");
                if (params != null && params.size() != 0) {
                    for (Map.Entry<String, String> entry : params.entrySet()) {
                        // 如果请求参数中有中文，需要进行URLEncoder编码 gbk/utf8
                        sb.append(entry.getKey()).append("=").append(URLEncoder.encode(entry.getValue(), "utf-8"));
                        sb.append("&");
                    }
                    sb.deleteCharAt(sb.length() - 1);
                }
                url = new URL(sb.toString());
                msg1.obj=NetworkUtil.getHttpJsonByurlconnection(url);
                jsonDecode((String) msg1.obj);
                handler.sendMessage(msg1);
                super.run();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }

    }


    private void showdialog()
    {
        DisplayDialogFragment ddl = DisplayDialogFragment.newinstance(answers,serializableanswermap);
        ddl.show(getFragmentManager(),"dialog");
        TextView tv1 = (TextView)findViewById(R.id.textView);
        //tv1.setText("你好");
    }


    private void jsonDecode(String jsonstring)
    {
        Map<String,String> answermap = new HashMap<String,String>();
        answers = new ArrayList<String>();

        try {
            //提取第一层对象
            JSONObject top_json = new JSONObject(jsonstring);
            //提取其中payload对应的JSON数组
            JSONArray payload_json = top_json.getJSONArray("payload");

            for (int i=0;i<payload_json.length();i++)
            {
                //提取payload数组中的每个小数组
                JSONArray item = payload_json.getJSONArray(i);
                //提取其中的候选答案和概率值
                answermap.put((String) item.get(0),item.get(1).toString()+"%");
                answers.add((String) item.get(0));
                Log.v((String) item.get(0),item.get(1).toString()+"%");
            }

            serializableanswermap.setMap(answermap);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

}
