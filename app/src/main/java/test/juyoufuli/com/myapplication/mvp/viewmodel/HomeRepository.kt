package test.juyoufuli.com.myapplication.mvp.viewmodel

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class HomeRepository @Inject constructor() {

    fun sayHello() = flow {
        delay(2_000)
        emit("Hello")
    }
}
