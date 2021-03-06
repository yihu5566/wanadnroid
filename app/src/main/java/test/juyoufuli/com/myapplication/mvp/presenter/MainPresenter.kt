package test.juyoufuli.com.myapplication.mvp.presenter

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.OnLifecycleEvent
import android.support.v4.app.Fragment
import android.support.v4.app.SupportActivity
import com.jess.arms.di.scope.FragmentScope
import com.jess.arms.mvp.BasePresenter
import com.jess.arms.utils.RxLifecycleUtils
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import me.jessyan.rxerrorhandler.core.RxErrorHandler
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber
import me.jessyan.rxerrorhandler.handler.RetryWithDelay
import test.juyoufuli.com.myapplication.app.utils.LogUtils
import test.juyoufuli.com.myapplication.mvp.entity.*
import test.juyoufuli.com.myapplication.mvp.model.contract.MainContract
import test.juyoufuli.com.myapplication.mvp.ui.home.adapter2.MainRecyclerViewAdapter
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

/**
 * Author : ludf
 * Created Time : 2018-09-29  13:25
 * Description:
 */
@FragmentScope
class MainPresenter @Inject
constructor(model: MainContract.Model, rootView: MainContract.View) : BasePresenter<MainContract.Model, MainContract.View>(model, rootView) {
    @JvmField
    @Inject
    internal var mErrorHandler: RxErrorHandler? = null
    @JvmField
    @Inject
    internal var mUsers: ArrayList<ArticleBean>? = null
    @JvmField
    @Inject
    internal var mAdapter: MainRecyclerViewAdapter? = null
    internal var mTopArticle: ArrayList<ArticleBean> = ArrayList()

    private var lastUserId = 0
    private var preEndIndex: Int = 0

    /**
     * 使用 2017 Google IO 发布的 Architecture Components 中的 Lifecycles 的新特性 (此特性已被加入 Support library)
     * 使 `Presenter` 可以与 [SupportActivity] 和 [Fragment] 的部分生命周期绑定
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    internal fun onCreate() {
        //        requestUsers(true);//打开 App 时自动加载列表
        //        requestBannerDataList();


    }


    fun mergeArticle(pullToRefresh: Boolean) {
        var topArticle = mModel.getTopArticle()
        var users = mModel.getUsers(0, pullToRefresh)

        Observable.zip(topArticle, users,
                BiFunction<TopArticleResponse, ArticleResponse, ArrayList<ArticleBean>> { t1, t2 ->
                    val list = ArrayList<ArticleBean>()
                    list.addAll(t1.data)
                    list.addAll(t2.data.datas)
                    mTopArticle.clear()
                    mTopArticle.addAll(t1.data)
                    list
                }

        ).subscribeOn(Schedulers.io())
                .doOnSubscribe { disposable ->
                    if (pullToRefresh)
                        mRootView.showLoading()//显示下拉刷新的进度条
                    else
                        mRootView.startLoadMore()//显示上拉加载更多的进度条
                }
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally {
                    if (pullToRefresh)
                        mRootView.hideLoading()//隐藏下拉刷新的进度条
                    else
                        mRootView.endLoadMore()//隐藏上拉加载更多的进度条
                }
                .compose(RxLifecycleUtils.bindToLifecycle<ArrayList<ArticleBean>>(mRootView))
                .subscribe(object : ErrorHandleSubscriber<ArrayList<ArticleBean>>(mErrorHandler!!) {
                    override fun onNext(t: ArrayList<ArticleBean>) {
                        LogUtils.d("全部文章合并：：" + t.size)
                        mUsers!!.clear()
                        mAdapter!!.mList!!.clear()

                        mUsers!!.addAll(t)
                        mAdapter!!.mList = mUsers
                        mAdapter!!.notifyDataSetChanged()
                        preEndIndex = mUsers!!.size
                    }
                })

    }

    fun requestTopArticle(firstTime: Boolean) {
        //请求外部存储权限用于适配android6.0的权限管理机制
        mModel.getTopArticle()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle<TopArticleResponse>(mRootView))
                .subscribe(object : ErrorHandleSubscriber<TopArticleResponse>(mErrorHandler!!) {
                    override fun onNext(t: TopArticleResponse) {
                        mTopArticle.clear()
                        mTopArticle.addAll(t.data)
                        if (mAdapter!!.itemCount != 0) {
                            mUsers!!.addAll(0, t.data)
                            mAdapter!!.addList(mUsers)
                            preEndIndex = mUsers!!.size
                        }
                    }
                })
    }

    fun requestFromModel(pullToRefresh: Boolean) {
        if (pullToRefresh) lastUserId = 0//下拉刷新默认只请求第一页
        //关于RxCache缓存库的使用请参考 http://www.jianshu.com/p/b58ef6b0624b
        val isEvictCache = true//是否驱逐缓存,为ture即不使用缓存,每次下拉刷新即需要最新数据,则不使用缓存
//        if (pullToRefresh && isFirst) {//默认在第一次下拉刷新时使用缓存
//            isFirst = false;
//            isEvictCache = false;
//        }
        mModel.getUsers(lastUserId, isEvictCache)
                .subscribeOn(Schedulers.io())
                .retryWhen(RetryWithDelay(3, 2))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .doOnSubscribe { disposable ->
                    if (pullToRefresh)
                        mRootView.showLoading()//显示下拉刷新的进度条
                    else
                        mRootView.startLoadMore()//显示上拉加载更多的进度条
                }.subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally {
                    if (pullToRefresh)
                        mRootView.hideLoading()//隐藏下拉刷新的进度条
                    else
                        mRootView.endLoadMore()//隐藏上拉加载更多的进度条
                }
                .compose(RxLifecycleUtils.bindToLifecycle<ArticleResponse>(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(object : ErrorHandleSubscriber<ArticleResponse>(mErrorHandler!!) {
                    override fun onNext(users: ArticleResponse) {
                        //下一页的页码
                        lastUserId = users.data.curPage
                        //如果是下拉刷新则清空列表
                        if (pullToRefresh) {
                            mUsers!!.clear()
                            mAdapter!!.clearList()
                        }
                        if (users.data.curPage != 1) {
                            mUsers!!.addAll(users.data.datas)
                            mAdapter!!.appendData(users.data.datas)
                        } else {
                            mUsers!!.clear()
                            mAdapter!!.clearList()
                            mUsers!!.addAll(0, mTopArticle)
                            mUsers!!.addAll(users.data.datas)

                            mAdapter!!.addList(mUsers)
                            LogUtils.d("变化后长度：：" + mUsers!!.size + "置顶的文章" + mTopArticle.size)
                        }
                        //更新之前列表总长度,用于确定加载更多的起始位置
                        preEndIndex = mUsers!!.size
                        if (pullToRefresh)
                            mAdapter!!.notifyDataSetChanged()
                        else
                            mAdapter!!.notifyItemRangeInserted(preEndIndex, users.data.datas.size)
                    }
                })
    }


    fun requestBannerDataList() {
        mModel.getBanner()
                .subscribeOn(Schedulers.io())
                .retryWhen(RetryWithDelay(3, 2))
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle<BannerResponse>(mRootView))
                .subscribe(object : ErrorHandleSubscriber<BannerResponse>(mErrorHandler!!) {

                    override fun onNext(systemDataResponse: BannerResponse) {
                        mRootView.updateBanner(systemDataResponse)

                    }
                })
    }


    fun collectArticle(id: String) {
        mModel.collectArticle(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle<LoginResponse>(mRootView))
                .subscribe(object : ErrorHandleSubscriber<LoginResponse>(mErrorHandler!!) {

                    override fun onNext(systemDataResponse: LoginResponse) {

                    }
                })
    }


    fun cancelCollectArticle(id: String) {
        mModel.cancelCollectArticle(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle<LoginResponse>(mRootView))
                .subscribe(object : ErrorHandleSubscriber<LoginResponse>(mErrorHandler!!) {

                    override fun onNext(systemDataResponse: LoginResponse) {

                    }
                })
    }


    override fun onDestroy() {
        super.onDestroy()
        this.mAdapter = null
        this.mUsers = null
        this.mErrorHandler = null
    }
}
