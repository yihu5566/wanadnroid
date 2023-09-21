package test.juyoufuli.com.myapplication.mvp.ui.searchview

import com.airbnb.mvrx.Async
import com.airbnb.mvrx.MavericksState
import com.airbnb.mvrx.MavericksViewModelFactory
import com.airbnb.mvrx.Uninitialized
import com.airbnb.mvrx.ViewModelContext
import org.koin.android.ext.android.inject
import test.juyoufuli.com.myapplication.app.MvRxViewModel
import test.juyoufuli.com.myapplication.mvp.entity.HotWordData
import test.juyoufuli.com.myapplication.mvp.entity.ProjectDatas
import test.juyoufuli.com.myapplication.mvp.entity.WanApiResponse
import test.juyoufuli.com.myapplication.mvp.repository.HomeRepository

/**
 * @Author : dongfang
 * @Created Time : 2023-08-21  11:03
 * @Description:
 */

data class SearchState(
    val searchHotWord: Async<WanApiResponse<List<HotWordData>>> = Uninitialized,
    val SearchDetailsList: List<ProjectDatas> = emptyList(),
    val pager: Int = 1,
    val cid: Int = 0,
    val selectIndex: Int = 0,
    val isLoadFinish: Boolean = false
) : MavericksState

class SearchViewModel(state: SearchState, private val repository: HomeRepository) :
    MvRxViewModel<SearchState>(state) {

    init {
        requestSearchCategory()
    }

    private fun requestSearchCategory() {
        repository.getHotWord().execute {
            copy(searchHotWord = it)
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
