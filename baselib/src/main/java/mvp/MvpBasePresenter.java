package mvp;

import java.lang.ref.WeakReference;

/**
 * 作者： 钟雄辉
 * 时间： 2018/6/28
 * 描述： Mvp设计模式基类presenter
 **/
public abstract class MvpBasePresenter<V extends IMvpBaseView> {

    private WeakReference<V> viewRef;


    /**
     * 界面创建，Presenter与界面取得联系
     */
    public void attachView(V view) {
        createModel();
        viewRef = new WeakReference<>(view);
    }

    /**
     * 获取界面的引用
     */
    protected V getView() {
        return viewRef == null ? null : viewRef.get();
    }

    /**
     * 判断界面是否销毁，接口返回设置数据必须调用此方法判断界面是否还存在
     */
    protected boolean isViewAttached() {
        return viewRef != null && viewRef.get() != null;
    }

    /**
     * 界面销毁，Presenter与界面断开联系
     */
    public void detachView() {
        if (viewRef != null) {
            viewRef.clear();
            viewRef = null;
        }
    }


    /**
     * 获取对应的Model
     */
    public abstract void createModel();
}