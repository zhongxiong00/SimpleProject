package com.simpleproject.android.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;


import com.simpleproject.android.Constants;
import com.simpleproject.android.R;
import com.simpleproject.android.base.ui.activity.BaseActivity;
import com.simpleproject.android.manager.UpdateManager;
import com.simpleproject.android.presenter.MainPresenter;
import com.simpleproject.android.ui.fragment.AllTypesFragment;
import com.simpleproject.android.ui.fragment.HomePageFragment;
import com.simpleproject.android.ui.fragment.MineFragment;
import com.simpleproject.android.ui.fragment.ShoppingCartFragment;
import com.simpleproject.android.ui.iview.IMainView;
import com.simpleproject.android.utils.FileUtils;
import com.simpleproject.android.widgets.FixedViewPage;
import com.simpleproject.android.widgets.MainTabView;

import java.util.List;

import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;


/**
 * 作者： 钟雄辉
 * 时间： 2018/6/28
 * 描述： 主界面
 **/
public class MainActivity extends BaseActivity<MainPresenter> implements IMainView, EasyPermissions.PermissionCallbacks {
    private TabLayout mTabView;
    private FixedViewPage mVpContent;
    private Fragment[] mPages;
    private UpdateManager mUpdateManager;


    public static void startThere(Context c) {
        Intent intent = new Intent(c, MainActivity.class);
        c.startActivity(intent);
    }


    @Override
    protected int layoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initLayoutView(Bundle savedInstanceState) {
        //presenter.checkSign(this);
        //presenter.checkUpdate(this);
        mVpContent = findViewById(R.id.vp_content);
        mTabView = findViewById(R.id.tab_main_layout);
        initFragment();
        mVpContent.setOffscreenPageLimit(4);
        mVpContent.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return mPages[i];
            }

            @Override
            public int getCount() {
                return mPages.length;
            }
        });
        mTabView.addOnTabSelectedListener(new TabSelectAdapter() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mVpContent.setCurrentItem(tab.getPosition());
            }
        });
        mTabView.addTab(mTabView.newTab().setCustomView(new MainTabView(this).setIconRecource(R.drawable.selector_tab_homepage).setText("首页")));
        mTabView.addTab(mTabView.newTab().setCustomView(new MainTabView(this).setIconRecource(R.drawable.selector_tab_information).setText("分类")));
        mTabView.addTab(mTabView.newTab().setCustomView(new MainTabView(this).setIconRecource(R.drawable.selector_tab_message).setText("购物车")));
        mTabView.addTab(mTabView.newTab().setCustomView(new MainTabView(this).setIconRecource(R.drawable.selector_tab_mine).setText("我的")));
    }

    private void initFragment() {
        mPages = new Fragment[]{
                HomePageFragment.newInstance(),
                AllTypesFragment.newInstance(),
                ShoppingCartFragment.newInstance(),
                MineFragment.newInstance(),
        };
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }


    @Override
    protected MainPresenter createPresenter() {
        return new MainPresenter();
    }

    @Override
    public void onBackPressed() {
        //按返回键返回桌面
        moveTaskToBack(true);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        switch (requestCode) {
            case Constants.PermissionConstants.PERMISSION_STORAGE: //存储卡权限
                if (mUpdateManager != null) {
                    mUpdateManager.onPermissGranted();
                }
                break;
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        switch (requestCode) {
            case Constants.PermissionConstants.PERMISSION_STORAGE: //存储卡权限
                new AppSettingsDialog.Builder(this).
                        setTitle("申请权限")
                        .setRationale("请先开启存储权限").
                        build().show();
                break;
        }
    }

    @Override
    public void showUpdateDialog(String downUrl, boolean isForce) {
        if (mUpdateManager == null) {
            mUpdateManager = new UpdateManager(this, getSupportFragmentManager(), FileUtils.getCacheDir(), downUrl, isForce);
        }
        mUpdateManager.showUpdateDialog();
    }

    private static abstract class TabSelectAdapter implements TabLayout.OnTabSelectedListener {
        @Override
        public void onTabReselected(TabLayout.Tab tab) {


        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {

        }
    }
}
