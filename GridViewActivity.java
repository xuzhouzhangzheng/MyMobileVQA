package com.example.abcd.vqa;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import static com.example.abcd.vqa.Temporary_Image_Integar.MyRPositionMap;

public class GridViewActivity extends AppCompatActivity{

public final static String EXTRA_GRID = "com.example.abcd.myapplication";
    private TextView mActionText;
    private GridAdapter mGridAdapter;
    private static final int MENU_SELECT_ALL = 0;
    private static final int MENU_UNSELECT_ALL = MENU_SELECT_ALL + 1;
    private HashMap<Integer, Boolean> mSelectMap = new HashMap<Integer, Boolean>();
    GridView gridview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Log.v("Array Length:",Integer.toString(Temporary_Image_Integar.MyRPositionMap.length));

        gridview = (GridView) findViewById(R.id.gridview);
        gridview.setChoiceMode(GridView.CHOICE_MODE_MULTIPLE_MODAL);
        mGridAdapter = new GridAdapter(this);
        gridview.setMultiChoiceModeListener(new MultiselectListener());
        gridview.setAdapter(mGridAdapter);// 数据适配
        Log.v("onCreate","enter onCreate");
//        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                startDisplayActivity(position);
//            }
//        });
    }

    private void startDisplayActivity()
    {
        Intent intent = new Intent(this,DisplayActivity.class);
        SerializableBoolMap smap = new SerializableBoolMap();
        smap.setMap(mSelectMap);
        intent.putExtra(EXTRA_GRID,smap);
        startActivity(intent);
    }



    private class GridAdapter extends BaseAdapter {

        private Context mycontext;

        @Override
        public int getCount() {
            return MyRPositionMap.length;
        }

        public GridAdapter(Context c)
        {
            mycontext = c;
        }

        @SuppressWarnings("deprecation")
        @Override
        /* 得到View */
        public View getView(int position, View convertView, ViewGroup parent) {
            GridItem item;
            if (convertView == null) {
                item = new GridItem(mycontext);
                item.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.FILL_PARENT,
                        AbsListView.LayoutParams.FILL_PARENT));
                item.setLayoutParams(new GridView.LayoutParams(350,350));
                item.setPadding(8,8,8,8);
            } else {
                item = (GridItem) convertView;
            }
            item.setImgResId((int)getItem(position));
            item.setChecked(mSelectMap.get(position) == null ? false
                    :mSelectMap.get(position));
            return item;
        }

        @Override
        public Integer getItem(int position) {
            // TODO Auto-generated method stub
            return Integer.valueOf(Temporary_Image_Integar.MyRPositionMap[position]);
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        public int calculateInSampleSize(BitmapFactory.Options options,
                                                int reqWidth, int reqHeight) {
            // 源图片的高度和宽度
            final int height = options.outHeight;
            final int width = options.outWidth;
            int inSampleSize = 1;
            if (height > reqHeight || width > reqWidth) {
                // 计算出实际宽高和目标宽高的比率
                final int heightRatio = Math.round((float) height / (float) reqHeight);
                final int widthRatio = Math.round((float) width / (float) reqWidth);
                // 选择宽和高中最小的比率作为inSampleSize的值，这样可以保证最终图片的宽和高
                // 一定都会大于等于目标的宽和高。
                inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
            }
            return inSampleSize;
        }

        public Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                             int reqWidth, int reqHeight) {
            // 第一次解析将inJustDecodeBounds设置为true，来获取图片大小
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeResource(res, resId, options);
            // 调用上面定义的方法计算inSampleSize值
            options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
            // 使用获取到的inSampleSize值再次解析图片
            options.inJustDecodeBounds = false;
            return BitmapFactory.decodeResource(res, resId, options);
        }
    }

    private class MultiselectListener implements AbsListView.MultiChoiceModeListener{
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            // TODO Auto-generated method stub
            // 得到布局文件的View
            Log.v("onCreateActionMode","enter onCreateActionMode");
            View v = LayoutInflater.from(gridview.getContext()).inflate(R.layout.selcetactionbar_layout,
                    null);
            mode.setCustomView(v);
            mActionText = (TextView) v.findViewById(R.id.action_text);
            // 设置显示内容为GridView选中的项目个数
            if(mActionText == null)
            {
                Log.v("mActionText","mActionText is null");
            }
            else {
                mActionText.setText(formatString(gridview.getCheckedItemCount()));
                // 设置动作条的视图
                // 得到菜单
            }
            getMenuInflater().inflate(R.menu.selcet_menu, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            // TODO Auto-generated method stub
        /* 初始状态下,如果选中的项数不等于总共的项数,设置"全选"的状态为True */
            menu.getItem(MENU_SELECT_ALL).setEnabled(
                    gridview.getCheckedItemCount() != gridview.getCount());
            return true;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            // TODO Auto-generated method stub
        /*
         * 当点击全选的时候,全选 点击全不选的时候全不选
         */
            switch (item.getItemId()) {
                case R.id.menu_select:
                    for (int i = 0; i < gridview.getCount(); i++) {
                        gridview.setItemChecked(i, true);
                        mSelectMap.put(i, true);
                    }
                    break;
                case R.id.menu_unselect:
                    for (int i = 0; i < gridview.getCount(); i++) {
                        gridview.setItemChecked(i, false);
                        mSelectMap.clear();
                    }
                    break;
                case R.id.godisplay:
                    startDisplayActivity();
            }
            return true;
        }

        private String formatString(int count) {
            return String.format("选中%s个",count);
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            // TODO Auto-generated method stub
            mGridAdapter.notifyDataSetChanged();
        }

        @Override
        public void onItemCheckedStateChanged(ActionMode mode, int position,
                                              long id, boolean checked) {
            // TODO Auto-generated method stub
            // 当每个项状态改变的时候的操作
            Log.v("onItemCheckedState","position:"+position);
            if(mActionText!=null) {
                mActionText.setText(formatString(gridview.getCheckedItemCount()));
            }
            mSelectMap.put(position, checked);/* 放入选中的集合中 */

            mode.invalidate();
        }
    }
}
