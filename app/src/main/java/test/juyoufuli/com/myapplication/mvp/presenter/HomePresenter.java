package test.juyoufuli.com.myapplication.mvp.presenter;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.OnLifecycleEvent;

import com.jess.arms.mvp.BasePresenter;

import javax.inject.Inject;

import test.juyoufuli.com.myapplication.mvp.model.contract.HomeContract;

/**
 * Author : ludf
 * Created Time : 2018-09-30  13:40
 * Description:
 */
public class HomePresenter extends BasePresenter<HomeContract.Model, HomeContract.View> {
    @Inject
    public HomePresenter(HomeContract.Model model, HomeContract.View rootView) {
        super(model, rootView);
    }
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate() {
    }
}
