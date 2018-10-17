package test.juyoufuli.com.myapplication.mvp.presenter;

import android.app.Application;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.OnLifecycleEvent;

import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;

import java.util.List;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import test.juyoufuli.com.myapplication.app.net.HttpUtil;
import test.juyoufuli.com.myapplication.mvp.entity.SystemBean;
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

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    void onCreate() {
        requestSystemDataList();//打开 App 时自动加载列表
    }


    public void requestSystemDataList() {

        HttpUtil.executeHttpRequest(mModel.getSystemData(), mRootView, new ErrorHandleSubscriber<List<SystemBean>>(mErrorHandler) {
            @Override
            public void onNext(List<SystemBean> systemDataResponse) {
                mSystemData.addAll(systemDataResponse);
                mAdapter.notifyDataSetChanged();
            }
        });

    }
}
