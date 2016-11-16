package com.mersens.gank.fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.mersens.gank.R;
import com.mersens.gank.activity.WebActivity;

/**
 * Created by Mersens on 2016/11/10.
 */

public class AboutFragment extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_about, null);
        final TextView tv_js = (TextView) view.findViewById(R.id.tv_js);
        tv_js.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), WebActivity.class);
                intent.putExtra(WebActivity.URL, tv_js.getText().toString());
                startActivity(intent);
            }
        });
        final TextView tv_gh = (TextView) view.findViewById(R.id.tv_gh);
        tv_gh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), WebActivity.class);
                intent.putExtra(WebActivity.URL, tv_gh.getText().toString());
                startActivity(intent);
            }
        });
        builder.setView(view).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        }).setNegativeButton("取消", null);

        return builder.create();
    }


}
