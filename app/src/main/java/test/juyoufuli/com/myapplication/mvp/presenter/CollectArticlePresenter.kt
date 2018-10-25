package test.juyoufuli.com.myapplication.mvp.presenter

import android.app.Application
import com.jess.arms.di.scope.ActivityScope
import com.jess.arms.integration.AppManager
import com.jess.arms.mvp.BasePresenter
import com.jess.arms.utils.RxLifecycleUtils
import com.trello.rxlifecycle2.LifecycleTransformer
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import me.jessyan.rxerrorhandler.core.RxErrorHandler
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber
import test.juyoufuli.com.myapplication.mvp.entity.ArticleResponse
import test.juyoufuli.com.myapplication.mvp.entity.LoginResponse
import test.juyoufuli.com.myapplication.mvp.model.contract.CollectArticleContract
import javax.inject.Inject

/**
 * Author : dongfang
 * Created Time : 2018-10-24  15:37
 * Description:
 */
@ActivityScope
class CollectArticlePresenter
@Inject
constructor(model: CollectArticleContract.Model, rootView: CollectArticleContract.View) : BasePresenter<CollectArticleContract.Model, CollectArticleContract.View>(model, rootView) {
    @JvmField
    @Inject
    internal var mErrorHandler: RxErrorHandler? = null
    @JvmField
    @Inject
    internal var mAppManager: AppManager? = null
    @JvmField
    @Inject
    internal var mApplication: Application? = null

    fun requestCollectArticleList(apge: String) {

        mModel.getArticleCollect(apge)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle<ArticleResponse>(mRootView))
                .subscribe(object : ErrorHandleSubscriber<ArticleResponse>(mErrorHandler!!) {

                    override fun onNext(response: ArticleResponse) {
                        mRootView.getArticleCollectSucceed(response)
                    }
                })


    }


    fun cancelCollectArticle(id: String, originId: String, position: Int) {
        mModel.cancelCollectArticle(id, originId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle<LoginResponse>(mRootView))
                .subscribe(object : ErrorHandleSubscriber<LoginResponse>(mErrorHandler!!) {

                    override fun onNext(systemDataResponse: LoginResponse) {
                        mRootView.cancelArticleCollectSucceed(position)
                    }
                })
    }
}
