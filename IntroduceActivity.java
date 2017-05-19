package com.example.abcd.vqa;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by abcd on 2017/5/19.
 */

public class IntroduceActivity extends Activity {

    int [] index_array;
    int index;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去除标题栏，针对Activity有效，对AppCompatActivity无效
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.introduce_layout);

        Intent intent = getIntent();
        index_array = intent.getIntArrayExtra(FirstActivity.RANDOM_ARRAY);
        index = intent.getIntExtra(FirstActivity.RANDOM_INDEX,-1);
        FButton got_it_button = (FButton)findViewById(R.id.button3);
        TextView wordtext = (TextView)findViewById(R.id.wordtext);
        ImageView display_view = (ImageView)findViewById(R.id.imageViewinDly);
        display_view.setImageResource(Temporary_Image_Integar.MyRPositionMap[index_array[index]]);
        index++;
        wordtext.setText("Word"+"("+index+"/6)");
        got_it_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startNewActivity();
            }
        });
    }

    private void startNewActivity()
    {
        Intent new_intent = new Intent(this,IntroduceActivity.class);
        new_intent.putExtra(FirstActivity.RANDOM_ARRAY,index_array);
        new_intent.putExtra(FirstActivity.RANDOM_INDEX,index);
        startActivity(new_intent);
    }
}
