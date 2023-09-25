package test.juyoufuli.com.myapplication.mvp.ui.searchview

import com.airbnb.mvrx.Async
import com.airbnb.mvrx.MavericksState
import com.airbnb.mvrx.MavericksViewModelFactory
import com.airbnb.mvrx.Uninitialized
import com.airbnb.mvrx.ViewModelContext
import org.koin.android.ext.android.inject
import test.juyoufuli.com.myapplication.app.MvRxViewModel
import test.juyoufuli.com.myapplication.mvp.entity.ArticleBean
import test.juyoufuli.com.myapplication.mvp.entity.HotWordData
import test.juyoufuli.com.myapplication.mvp.entity.WanApiResponse
import test.juyoufuli.com.myapplication.mvp.repository.HomeRepository

/**
 * @Author : dongfang
 * @Created Time : 2023-08-21  11:03
 * @Description:
 */

data class SearchState(
    val searchHotWord: Async<WanApiResponse<List<HotWordData>>> = Uninitialized,
    val searchArtList: List<ArticleBean> = emptyList(),
    val pager: Int = 1,
    val isLoadFinish: Boolean = false,
    val searchWord: String = ""
) : MavericksState

class SearchViewModel(state: SearchState, private val repository: HomeRepository) :
    MvRxViewModel<SearchState>(state) {

    init {
        requestSearchCategory()
    }

    fun changeSearchWord(searchKey: String) {
        if (searchKey.isEmpty()) {
            setState { copy(searchArtList = emptyList(), pager = 1) }
        } else {
            getSearchArtList(1, searchKey)
        }
    }

    private fun requestSearchCategory() {
        repository.getHotWord().execute {
            copy(searchHotWord = it)
        }
    }

    fun getSearchArtList(pager: Int, result: String) {
        repository.getSearchArtList(pager, result).execute {
            val listData = it.invoke()?.data?.datas
            val pageCount = it.invoke()?.data?.pageCount
            copy(
                searchArtList = when (pager) {
                    1 -> (listData ?: emptyList())
                    else -> searchArtList + (listData ?: emptyList())
                },
                pager = pager + 1,
                isLoadFinish = pager > (pageCount ?: 0)
            )
        }
    }


    companion object : MavericksViewModelFactory<SearchViewModel, SearchState> {
        override fun create(
            viewModelContext: ViewModelContext,
            state: SearchState
        ): SearchViewModel {
            val api: HomeRepository by viewModelContext.activity.inject()
            return SearchViewModel(state, api)
        }
    }

}
