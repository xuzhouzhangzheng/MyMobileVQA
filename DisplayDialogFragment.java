package com.example.abcd.vqa;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by abcd on 2017/4/8.
 */

public class DisplayDialogFragment extends DialogFragment {

    ArrayList<String> answers;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        answers = getArguments().getStringArrayList("answerslist");
        SerializableMap serializablemap = (SerializableMap)getArguments().getSerializable("map");
        Map<String,String> answersmap = serializablemap.getMap();

        //测试answers和answermap是否为空
        if(answers == null || answersmap == null)
        {
            Log.v("Bundle","answers or answermap is null");
        }
        else {
            Log.v("Bundle","answers and answermap are existed objects!");
        }

        LayoutInflater lf = LayoutInflater.from(getActivity());
        View v = lf.inflate(R.layout.fragment_dialog,null,false);

        TextView tv1 = (TextView)v.findViewById(R.id.textView);
        tv1.setText("answer 1: "+answers.get(0));

        TextView tv2 = (TextView)v.findViewById(R.id.textView2);
        tv2.setText("answer 2: "+answers.get(1));

        TextView tv3 = (TextView)v.findViewById(R.id.textView3);
        tv3.setText("answer 3: "+answers.get(2));

        TextView tv4 = (TextView)v.findViewById(R.id.textView4);
        tv4.setText("answer 4: "+answers.get(3));

        TextView tv5 = (TextView)v.findViewById(R.id.textView5);
        tv5.setText("answer 5: "+answers.get(4));

        TextView tv6 = (TextView)v.findViewById(R.id.textView6);
        tv6.setText("Probability: "+answersmap.get(answers.get(0)));

        TextView tv7 = (TextView)v.findViewById(R.id.textView7);
        tv7.setText("Probability: "+answersmap.get(answers.get(1)));

        TextView tv8 = (TextView)v.findViewById(R.id.textView8);
        tv8.setText("Probability: "+answersmap.get(answers.get(2)));

        TextView tv9 = (TextView)v.findViewById(R.id.textView9);
        tv9.setText("Probability: "+answersmap.get(answers.get(3)));

        TextView tv10 = (TextView)v.findViewById(R.id.textView10);
        tv10.setText("Probability: "+answersmap.get(answers.get(4)));

        return new AlertDialog.Builder(getActivity()).setTitle("可能的答案").setView(v)
                .setNeutralButton(R.string.diglog_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create();

    }


    static DisplayDialogFragment newinstance(ArrayList<String> answerlist, SerializableMap serializableMap)
    {
        DisplayDialogFragment ddf = new DisplayDialogFragment();
        Bundle args = new Bundle();
        args.putStringArrayList("answerslist",answerlist);
        args.putSerializable("map",serializableMap);
        ddf.setArguments(args);
        return ddf;
    }


}
