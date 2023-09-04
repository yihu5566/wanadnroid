package test.juyoufuli.com.myapplication.mvp.ui.gongzhonghao

import com.airbnb.mvrx.Async
import com.airbnb.mvrx.MavericksState
import com.airbnb.mvrx.MavericksViewModelFactory
import com.airbnb.mvrx.Uninitialized
import com.airbnb.mvrx.ViewModelContext
import org.koin.android.ext.android.inject
import test.juyoufuli.com.myapplication.app.MvRxViewModel
import test.juyoufuli.com.myapplication.mvp.entity.WanApiResponse
import test.juyoufuli.com.myapplication.mvp.entity.WeChatData
import test.juyoufuli.com.myapplication.mvp.repository.HomeRepository

/**
 * @Author : dongfang
 * @Created Time : 2023-08-21  11:03
 * @Description:
 */

data class WeChatPublishState(
    val WeChatPublishBean: Async<WanApiResponse<List<WeChatData>>> = Uninitialized,
) : MavericksState


class WeChatPublishViewModel(state: WeChatPublishState, private val repository: HomeRepository) :
    MvRxViewModel<WeChatPublishState>(state) {

    init {
        getWeChatPublishDataList()
    }

    private fun getWeChatPublishDataList() {
        repository.getWeChatPublishDataList().execute {
            copy(WeChatPublishBean = it)
        }
    }


    companion object : MavericksViewModelFactory<WeChatPublishViewModel, WeChatPublishState> {
        override fun create(
            viewModelContext: ViewModelContext,
            state: WeChatPublishState
        ): WeChatPublishViewModel {
            val api: HomeRepository by viewModelContext.activity.inject()
            return WeChatPublishViewModel(state, api)
        }
    }

}
