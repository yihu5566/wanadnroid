package test.juyoufuli.com.myapplication.di

import com.we.jetpackmvvm.base.viewmodel.BaseViewModel
import com.we.westarry.app.EventViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import test.juyoufuli.com.myapplication.app.AppViewModel
import test.juyoufuli.com.myapplication.app.net.NetworkApi
import test.juyoufuli.com.myapplication.mvp.viewmodel.HomeRepository

/**
 * @Author : dongfang
 * @Created Time : 2021-12-03  17:16
 * @Description:
 */

val dataSourceModule = module {
    single { NetworkApi() }
}

val viewModelModule = module {
    single { AppViewModel() }
    single { EventViewModel() }
    viewModel { BaseViewModel() }
    single { HomeRepository() }


}