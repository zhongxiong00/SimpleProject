package com.simpleproject.android.widgets.dialog;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.simpleproject.android.R;

public class LoadingDialog extends DialogFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        View view = inflater.inflate(R.layout.dialog_loading, container, false);
        return view;
    }

    public void show(AppCompatActivity c, boolean cancelAble) {
        if (isAdded()) {
            getFragmentManager().beginTransaction().remove(this).commit();
        }
        if (c != null) {
            setCancelable(cancelAble);
            show(c.getSupportFragmentManager(), "loading");
        }

    }

    public void disMiss(Activity activity) {
        if (activity != null && !activity.isFinishing() && getDialog() != null && getDialog().isShowing()) {
            dismissAllowingStateLoss();
        }
    }
}