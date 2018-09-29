package test.juyoufuli.com.myapplication.mvp.presenter;

import android.app.Application;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.OnLifecycleEvent;
import android.support.v7.widget.RecyclerView;

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
import test.juyoufuli.com.myapplication.mvp.entity.SystemBean;
import test.juyoufuli.com.myapplication.mvp.entity.SystemDataResponse;
import test.juyoufuli.com.myapplication.mvp.model.contract.SystemDataContract;

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
    RecyclerView.Adapter mAdapter;

    @Inject
    public SystemDataPresenter(SystemDataContract.Model model, SystemDataContract.View rootView) {
        super(model, rootView);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    void onCreate() {
        requestSystemDataList();//打开 App 时自动加载列表
    }
    public void requestSystemDataList() {
        mModel.getSystemData()
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 2))
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<SystemDataResponse>(mErrorHandler) {

                    @Override
                    public void onNext(SystemDataResponse systemDataResponse) {
                        mSystemData.addAll(systemDataResponse.getData());
                        mAdapter.notifyDataSetChanged();
                    }
                });
    }
}
