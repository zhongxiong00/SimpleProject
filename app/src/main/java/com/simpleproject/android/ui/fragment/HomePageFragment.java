package com.simpleproject.android.ui.fragment;

import android.view.View;

import com.simpleproject.android.R;
import com.simpleproject.android.base.ui.fragment.BaseFragment;
import com.simpleproject.android.presenter.HomePagePresenter;
import com.simpleproject.android.ui.activity.WebActivity;
import com.simpleproject.android.ui.iview.IHomePageView;

/**
 * 作者： 钟雄辉
 * 时间： 2018/9/26
 * 描述： 首页
 **/
public class HomePageFragment extends BaseFragment<HomePagePresenter> implements IHomePageView {

    public static HomePageFragment newInstance() {
        HomePageFragment fragment = new HomePageFragment();
        return fragment;
    }


    @Override
    public int getLayoutId() {
        return R.layout.fragment_home_page;
    }

    @Override
    protected void initView(View view) {
        view.findViewById(R.id.tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebActivity.startThere(getContext(), "http://www.baidu.com");
            }
        });
    }


    @Override
    protected HomePagePresenter createPresenter() {
        return new HomePagePresenter();
    }
}
