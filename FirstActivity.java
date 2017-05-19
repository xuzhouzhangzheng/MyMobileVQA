package com.example.abcd.vqa;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.WindowManager;

import java.util.Random;

/**
 * Created by abcd on 2017/5/18.
 */


public class FirstActivity extends Activity {

    public final static String RANDOM_ARRAY = "com.example.abcd.vqa.FirstActivity.array";
    public final static String RANDOM_INDEX = "com.example.abcd.vqa.FirstActivity.index";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.first_activity);

        //此按钮的操作要发送HTTP请求，返回JSON数据并解析，然后显示在对话框中
        FButton button1 = (FButton)findViewById(R.id.button);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGridViewActivity();
            }
        });
    }

    private void startGridViewActivity()
    {
        Intent intent = new Intent(this,IntroduceActivity.class);
        int [] array = createRandomIndexOfImage();
        int index = 0;
        intent.putExtra(RANDOM_ARRAY,array);
        intent.putExtra(RANDOM_INDEX,index);
        startActivity(intent);
    }

    private int[] createRandomIndexOfImage()
    {
        Random rand = new Random();
        int [] randindex = new int[6];
        for(int i=0;i<=5;i++)
        {
            randindex[i] = rand.nextInt(125);
        }
        return randindex;
    }
}
