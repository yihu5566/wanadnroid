package test.juyoufuli.com.myapplication.di.module

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.jess.arms.di.scope.ActivityScope
import com.tbruyelle.rxpermissions2.RxPermissions
import dagger.Module
import dagger.Provides
import test.juyoufuli.com.myapplication.mvp.entity.ArticleBean
import test.juyoufuli.com.myapplication.mvp.entity.SystemBean
import test.juyoufuli.com.myapplication.mvp.model.SystemDataDetailsModel
import test.juyoufuli.com.myapplication.mvp.model.contract.SystemDataDetailsContract
import test.juyoufuli.com.myapplication.mvp.ui.home.adapter.ArticleAdapter
import test.juyoufuli.com.myapplication.mvp.ui.tab.adapter.SystemDataAdapter
import java.util.*

/**
 * Author : dongfang
 * Created Time : 2018-10-1213:41
 * Description:
 */
@Module
class SystemDataDetailsModel
(private val view:SystemDataDetailsContract.View)  {


    @ActivityScope
    @Provides
    internal fun provideSystemView(): SystemDataDetailsContract.View {
        return this.view
    }

    @ActivityScope
    @Provides
    internal fun provideSystemModel(model: SystemDataDetailsModel): SystemDataDetailsContract.Model {
        return model
    }

    @ActivityScope
    @Provides
    internal fun provideRxPermissions(): RxPermissions {
        return RxPermissions(view.activity!!)
    }

    @ActivityScope
    @Provides
    internal fun provideLayoutManager(): RecyclerView.LayoutManager {
        return LinearLayoutManager(view.activity)
    }

    @ActivityScope
    @Provides
    internal fun provideSystemList(): List<SystemBean> {
        return ArrayList()
    }

    @ActivityScope
    @Provides
    internal fun provideSystemNameList(): ArrayList<String> {
        return ArrayList()
    }
    @ActivityScope
    @Provides
    internal fun provideSystemAdapter(list: List<ArticleBean>): ArticleAdapter {
        return ArticleAdapter(list)
    }



}