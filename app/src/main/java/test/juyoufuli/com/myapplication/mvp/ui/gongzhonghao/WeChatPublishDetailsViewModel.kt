package test.juyoufuli.com.myapplication.mvp.ui.gongzhonghao

import com.airbnb.mvrx.Async
import com.airbnb.mvrx.MavericksState
import com.airbnb.mvrx.MavericksViewModelFactory
import com.airbnb.mvrx.Uninitialized
import com.airbnb.mvrx.ViewModelContext
import org.koin.android.ext.android.inject
import test.juyoufuli.com.myapplication.app.MvRxViewModel
import test.juyoufuli.com.myapplication.mvp.entity.ArticleBean
import test.juyoufuli.com.myapplication.mvp.entity.WanApiResponse
import test.juyoufuli.com.myapplication.mvp.entity.WanPageResponse
import test.juyoufuli.com.myapplication.mvp.repository.HomeRepository

/**
 * @Author : dongfang
 * @Created Time : 2023-08-21  11:03
 * @Description:
 */

data class WeChatPublishDetailsState(
    val WeChatPublishArticleData: Async<WanApiResponse<WanPageResponse<ArticleBean>>> = Uninitialized,
) : MavericksState


class WeChatPublishDetailsViewModel(
    state: WeChatPublishDetailsState,
    private val repository: HomeRepository
) :
    MvRxViewModel<WeChatPublishDetailsState>(state) {

    fun getArticleListDetailsList(page: Int, cid: String) {
        repository.getArticleListDetailsList(page, cid).execute {
            copy(WeChatPublishArticleData = it)
        }
    }

    companion object :
        MavericksViewModelFactory<WeChatPublishDetailsViewModel, WeChatPublishDetailsState> {
        override fun create(
            viewModelContext: ViewModelContext,
            state: WeChatPublishDetailsState
        ): WeChatPublishDetailsViewModel {
            val api: HomeRepository by viewModelContext.activity.inject()
            return WeChatPublishDetailsViewModel(state, api)
        }
    }

}
