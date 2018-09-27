package com.simpleproject.android.widgets.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.simpleproject.android.R;
import com.simpleproject.android.widgets.HorizontalProgressBar;

import device.ScreenUtils;

public class AppUpdateProgressDialog extends DialogFragment {
    private HorizontalProgressBar mUpdateProgrss;
    private TextView mTvProgress;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        Window window = getDialog().getWindow();
        window.setBackgroundDrawableResource(android.R.color.transparent);
        getDialog().setCanceledOnTouchOutside(false);
        setCancelable(false);
        return inflater.inflate(R.layout.widget_update_progress, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.getWindow().setLayout((int) (ScreenUtils.getScreenWidth() * 0.7), ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mUpdateProgrss = view.findViewById(R.id.pb_progress_update);
        mTvProgress = view.findViewById(R.id.tv_progress_update);
    }

    public void setProgress(int progressNow) {
        if (mUpdateProgrss != null) {
            mUpdateProgrss.setProgress(progressNow);
        }
        if (mTvProgress != null) {
            mTvProgress.setText(progressNow + "%");
        }
    }

    public void setProgressMax(int max) {
        if (mUpdateProgrss != null) {
            mUpdateProgrss.setMax(max);
        }
    }
}
