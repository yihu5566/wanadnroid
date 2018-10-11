package test.juyoufuli.com.myapplication.app.net;

import com.jess.arms.mvp.IView;
import com.jess.arms.utils.RxLifecycleUtils;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;

/**
 * Author : dongfang
 * Created Time : 2018-10-0817:31
 * Description:
 */
public class HttpUtil {


    public static Observable executeHttpRequest(Observable observable,IView mRootView){

       return observable.subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 2))
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView));
    }

}
