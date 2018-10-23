package test.juyoufuli.com.myapplication.mvp.presenter;

import android.app.Application;

import com.jess.arms.di.scope.ActivityScope;
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
import test.juyoufuli.com.myapplication.mvp.entity.ArticleBean;
import test.juyoufuli.com.myapplication.mvp.entity.ArticleResponse;
import test.juyoufuli.com.myapplication.mvp.model.contract.SystemDataDetailsContract;
import test.juyoufuli.com.myapplication.mvp.model.contract.SystemDataDetailsItemContract;
import test.juyoufuli.com.myapplication.mvp.ui.home.adapter.ArticleAdapter;
import test.juyoufuli.com.myapplication.mvp.ui.searchview.adapter.SearchAdapter;

/**
 * Author : ludf
 * Created Time : 2018-09-29  13:34
 * Description:
 */
@FragmentScope
public class SystemDataDetailsItemPresenter extends BasePresenter<SystemDataDetailsItemContract.Model, SystemDataDetailsItemContract.View> {

    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    AppManager mAppManager;
    @Inject
    Application mApplication;
    @Inject
    List<ArticleBean> mSystemData;
    @Inject
    SearchAdapter mAdapter;


    @Inject
    public SystemDataDetailsItemPresenter(SystemDataDetailsItemContract.Model model, SystemDataDetailsItemContract.View rootView) {
        super(model, rootView);
    }

    public void requestSystemDataList(String index, String cid) {
        mModel.getSystemData(index, cid)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 2))
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<ArticleResponse>(mErrorHandler) {

                    @Override
                    public void onNext(ArticleResponse response) {
                        mRootView.refreshData(response);

                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}
