package test.juyoufuli.com.myapplication.mvp.ui.gongzhonghao

import com.airbnb.mvrx.MavericksState
import com.airbnb.mvrx.MavericksViewModelFactory
import com.airbnb.mvrx.ViewModelContext
import org.koin.android.ext.android.inject
import test.juyoufuli.com.myapplication.app.MvRxViewModel
import test.juyoufuli.com.myapplication.mvp.entity.ArticleBean
import test.juyoufuli.com.myapplication.mvp.repository.HomeRepository

/**
 * @Author : dongfang
 * @Created Time : 2023-08-21  11:03
 * @Description:
 */

data class WeChatPublishDetailsState(
    val articleBeanList: List<ArticleBean> = emptyList(),
    val isLoadFinish: Boolean = false,
    val page: Int = 1,

    ) : MavericksState


class WeChatPublishDetailsViewModel(
    state: WeChatPublishDetailsState,
    private val repository: HomeRepository
) : MvRxViewModel<WeChatPublishDetailsState>(state) {

    fun getArticleListDetailsList(cid: Int, page: Int) {
        repository.getArticleListDetailsList(cid, page).execute {
            if (it.complete) {
                val pageCount = it.invoke()?.data?.pageCount
                val listData = it.invoke()?.data?.datas ?: emptyList()
                copy(
                    articleBeanList = articleBeanList + listData,
                    isLoadFinish = page > (pageCount ?: 0),
                    page = page + 1
                )
            } else {
                copy()
            }
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
