package test.juyoufuli.com.myapplication.mvp.presenter;

import android.app.Application;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.OnLifecycleEvent;

import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;
import test.juyoufuli.com.myapplication.app.net.HttpUtil;
import test.juyoufuli.com.myapplication.mvp.entity.ArticleResponse;
import test.juyoufuli.com.myapplication.mvp.entity.SystemBean;
import test.juyoufuli.com.myapplication.mvp.entity.SystemDataRespons;
import test.juyoufuli.com.myapplication.mvp.model.contract.SystemDataContract;
import test.juyoufuli.com.myapplication.mvp.ui.tab.adapter.SystemDataAdapter;

/**
 * Author : ludf
 * Created Time : 2018-09-29  13:34
 * Description:
 */
@FragmentScope
public class SystemDataPresenter extends BasePresenter<SystemDataContract.Model, SystemDataContract.View> {

    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    AppManager mAppManager;
    @Inject
    Application mApplication;
    @Inject
    List<SystemBean> mSystemData;
    @Inject
    SystemDataAdapter mAdapter;

    @Inject
    public SystemDataPresenter(SystemDataContract.Model model, SystemDataContract.View rootView) {
        super(model, rootView);
    }


    public void requestSystemDataList() {

        mModel.getSystemData()
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 2))
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<SystemDataRespons>(mErrorHandler) {

                    @Override
                    public void onNext(SystemDataRespons response) {
                        mSystemData.addAll(response.getData());
                        mAdapter.notifyDataSetChanged();
                    }
                });

//        HttpUtil.executeHttpRequest(mModel.getSystemData(), mRootView, new ErrorHandleSubscriber<List<SystemBean>>(mErrorHandler) {
//            @Override
//            public void onNext(List<SystemBean> systemDataResponse) {
//
//            }
//        });

    }
}
