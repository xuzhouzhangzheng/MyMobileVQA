package com.example.abcd.vqa;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

public class GridViewActivity extends AppCompatActivity {

public final static String EXTRA_GRID = "com.example.abcd.myapplication";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Log.v("Array Length:",Integer.toString(Temporary_Image_Integar.MyRPositionMap.length));

        GridView gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(new ImageAdapter(this));

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startDisplayActivity(position);
            }
        });
    }

    private void startDisplayActivity(int imgId)
    {
        Intent intent = new Intent(this,DisplayActivity.class);
        intent.putExtra(EXTRA_GRID,imgId);
        startActivity(intent);
    }

}
