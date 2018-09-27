package mvp;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;


/**
 * 作者： 钟雄辉
 * 时间： 2018/6/28
 * 描述： Mvp设计模式基类Activity
 **/
public abstract class MvpBaseActivity<P extends MvpBasePresenter> extends AppCompatActivity implements IMvpBaseView {
    protected P presenter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = createPresenter();
        presenter.attachView(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    protected abstract P createPresenter();

}
