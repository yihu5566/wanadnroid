package test.juyoufuli.com.myapplication.app.net;

import com.jess.arms.mvp.IView;
import com.jess.arms.utils.RxLifecycleUtils;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;
import test.juyoufuli.com.myapplication.app.BaseRequest;
import test.juyoufuli.com.myapplication.mvp.entity.BaseResponse;
import test.juyoufuli.com.myapplication.mvp.entity.SystemBean;

/**
 * Author : dongfang
 * Created Time : 2018-10-0817:31
 * Description:
 */
public class HttpUtil {

    public static void executeHttpRequest(Observable observable, IView mRootView, ErrorHandleSubscriber handleSubscriber) {

        observable.subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 2))
                .observeOn(AndroidSchedulers.mainThread())
                .map(new BaseRequest())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(handleSubscriber);

    }

}
