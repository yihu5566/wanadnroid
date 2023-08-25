package test.juyoufuli.com.myapplication.mvp.viewmodel

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import test.juyoufuli.com.myapplication.app.BaseRepository
import test.juyoufuli.com.myapplication.app.net.apiService

class HomeRepository() : BaseRepository() {

    fun sayHello() = flow {
        delay(2_000)
        emit("Hello")
    }

    fun requestBannerDataList() = flow {
        emit(apiService.getBannerList())
    }.flowOn(Dispatchers.IO)
}
