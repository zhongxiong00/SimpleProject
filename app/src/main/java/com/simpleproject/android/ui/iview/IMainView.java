package com.simpleproject.android.ui.iview;


import com.simpleproject.android.base.ui.iview.IBaseView;

/**
 * 作者： 钟雄辉
 * 时间： 2018/6/28
 * 描述： 主界面View接口
 **/
public interface IMainView extends IBaseView {
    void showUpdateDialog(String downUrl, boolean isForce);
}
