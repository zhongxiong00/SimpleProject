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

import device.ScreenUtils;


public class AppUpdateDialog extends DialogFragment {
    private boolean mIsForce;
    private static String PASS_FORCE = "PASS_FORCE";
    private TextView mTvCancel, mTvUpdate;
    private View.OnClickListener mUpdateClickListener;

    public static AppUpdateDialog newInstance(boolean isForceUpdate) {
        AppUpdateDialog dialog = new AppUpdateDialog();
        Bundle bundle = new Bundle();
        bundle.putBoolean(PASS_FORCE, isForceUpdate);
        dialog.setArguments(bundle);
        return dialog;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle param = getArguments();
        if (param != null) {
            mIsForce = param.getBoolean(PASS_FORCE);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        Window window = getDialog().getWindow();
        window.setBackgroundDrawableResource(android.R.color.transparent);
        getDialog().setCanceledOnTouchOutside(false);
        setCancelable(false);
        return inflater.inflate(R.layout.widget_dialog_update, container, false);
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
        mTvCancel = view.findViewById(R.id.tv_update_cancel);
        mTvUpdate = view.findViewById(R.id.tv_update_confirm);
        if (mIsForce) {
            mTvCancel.setVisibility(View.GONE);
        }
        mTvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissAllowingStateLoss();
            }
        });
        mTvUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mUpdateClickListener != null) {
                    mUpdateClickListener.onClick(v);
                }
            }
        });
    }

    public void setOnUpdateClickListener(View.OnClickListener listener) {
        mUpdateClickListener = listener;
    }
}
