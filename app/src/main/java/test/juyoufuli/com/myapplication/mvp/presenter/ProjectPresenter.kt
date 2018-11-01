package test.juyoufuli.com.myapplication.mvp.presenter

import com.jess.arms.di.component.AppComponent
import com.jess.arms.di.scope.FragmentScope
import com.jess.arms.mvp.BasePresenter
import com.jess.arms.utils.ArmsUtils
import com.jess.arms.utils.RxLifecycleUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import me.jessyan.rxerrorhandler.core.RxErrorHandler
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber
import me.jessyan.rxerrorhandler.handler.RetryWithDelay
import test.juyoufuli.com.myapplication.mvp.contract.ProjectContract
import test.juyoufuli.com.myapplication.mvp.entity.LoginResponse
import test.juyoufuli.com.myapplication.mvp.entity.ProjectDetailsResponse
import test.juyoufuli.com.myapplication.mvp.entity.ProjectResponse
import javax.inject.Inject

/**
 * Author : dongfang
 * Created Time : 2018-10-31  09:35
 * Description:
 */
@FragmentScope
class ProjectPresenter @Inject
constructor(model: ProjectContract.Model, view: ProjectContract.View) : BasePresenter<ProjectContract.Model, ProjectContract.View>(model, view) {
    @JvmField
    @Inject
    var mErrorHandler: RxErrorHandler? = null
    @JvmField
    @Inject
    var mAppComponent: AppComponent? = null


    fun getProject() {
        mModel.getProjectData().subscribeOn(Schedulers.io())
                .retryWhen(RetryWithDelay(3, 2))
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle<ProjectResponse>(mRootView))
                .subscribe(object : ErrorHandleSubscriber<ProjectResponse>(mErrorHandler!!) {

                    override fun onNext(response: ProjectResponse) {
                        mRootView.refreshAdapterList(response)
                    }

                })
    }


    fun getProjectDetails(page: String, cid: String) {
        mModel.getProjectDetailsData(page, cid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle<ProjectDetailsResponse>(mRootView))
                .subscribe(object : ErrorHandleSubscriber<ProjectDetailsResponse>(mErrorHandler!!) {

                    override fun onNext(response: ProjectDetailsResponse) {
                        mRootView.refreshDetailsAdapterList(response)
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
                        ArmsUtils.makeText(mAppComponent!!.application(), "收藏成功")
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
                        ArmsUtils.makeText(mAppComponent!!.application(), "取消收藏")

                    }
                })
    }

}