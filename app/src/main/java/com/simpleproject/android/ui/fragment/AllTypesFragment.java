package com.simpleproject.android.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.simpleproject.android.R;


/**
 * 作者： 钟雄辉
 * 时间： 2018/9/26
 * 描述： 分类
 **/
public class AllTypesFragment extends Fragment {


    public AllTypesFragment() {
    }

    public static AllTypesFragment newInstance() {
        AllTypesFragment fragment = new AllTypesFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home_page, container, false);
    }


}
