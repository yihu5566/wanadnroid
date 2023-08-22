package com.we.westarry.app

import com.we.jetpackmvvm.base.viewmodel.BaseViewModel
import com.we.jetpackmvvm.callback.livedata.event.EventLiveData


/**
 *
 * @Author : dongfang
 * @Created Time : 2021/12/8  10:11
 * @Description: APP全局的ViewModel，可以在这里发送全局通知替代EventBus，LiveDataBus等
 *
 */
class EventViewModel : BaseViewModel() {

    //照片路径
    val photoPathEvent = EventLiveData<String>()

}