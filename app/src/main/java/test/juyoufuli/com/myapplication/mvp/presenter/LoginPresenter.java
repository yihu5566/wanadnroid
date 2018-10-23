package test.juyoufuli.com.myapplication.mvp.presenter;

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
import test.juyoufuli.com.myapplication.mvp.entity.ArticleResponse;
import test.juyoufuli.com.myapplication.mvp.entity.BaseResponse;
import test.juyoufuli.com.myapplication.mvp.entity.LoginResponse;
import test.juyoufuli.com.myapplication.mvp.model.contract.LoginContract;

/**
 * Author : dongfang
 * Created Time : 2018-10-19  14:11
 * Description:
 */
@ActivityScope
public class LoginPresenter extends BasePresenter<LoginContract.Model, LoginContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;


    @Inject
    public LoginPresenter(LoginContract.Model model, LoginContract.View rootView) {
        super(model, rootView);
    }

    public void login(String name, String password) {
        mModel.login(name, password)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 2))
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<LoginResponse>(mErrorHandler) {

                    @Override
                    public void onNext(LoginResponse response) {
                        LogUtils.debugInfo(password + "--------------url");
                        ArmsUtils.makeText(mRootView.getActivity(), response.getErrorMsg());
                        mRootView.loginSucceed(response);
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                    }
                });

    }


    public void register(String name, String password, String repassword) {
        mModel.register(name, password, repassword)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 2))
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<LoginResponse>(mErrorHandler) {

                    @Override
                    public void onNext(LoginResponse response) {
                        LogUtils.debugInfo(password + "--------------url");
                        ArmsUtils.makeText(mRootView.getActivity(), response.getErrorMsg());
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                    }
                });

    }
}
