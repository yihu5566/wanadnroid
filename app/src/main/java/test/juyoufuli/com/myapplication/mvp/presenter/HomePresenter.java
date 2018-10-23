package test.juyoufuli.com.myapplication.mvp.presenter;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.OnLifecycleEvent;
import android.content.Context;
import android.view.View;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.LogUtils;
import com.jess.arms.utils.RxLifecycleUtils;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;
import test.juyoufuli.com.myapplication.app.ResponseErrorListenerImpl;
import test.juyoufuli.com.myapplication.mvp.entity.LoginResponse;
import test.juyoufuli.com.myapplication.mvp.model.HomeModel;
import test.juyoufuli.com.myapplication.mvp.model.contract.HomeContract;

/**
 * Author : ludf
 * Created Time : 2018-09-30  13:40
 * Description:
 */
//@ActivityScope
public class HomePresenter extends BasePresenter<HomeContract.Model, HomeContract.View> {

    //    @Inject
    RxErrorHandler mErrorHandler;
    private HomeContract.View mRootView;
    private HomeContract.Model mModel;


    //    @Inject
    public HomePresenter(HomeContract.Model model, HomeContract.View rootView) {
        super(model, rootView);
        this.mRootView = rootView;
        this.mModel=model;
        mErrorHandler = RxErrorHandler.builder().with(rootView.getActivity()).responseErrorListener(new ResponseErrorListenerImpl()).build();
    }


    public void LoginOut() {
        mModel.loginOut()
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 2))
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<String>(mErrorHandler) {

                    @Override
                    public void onNext(String response) {
                        mRootView.loginOutSucceed();
                    }

                });
    }
}
