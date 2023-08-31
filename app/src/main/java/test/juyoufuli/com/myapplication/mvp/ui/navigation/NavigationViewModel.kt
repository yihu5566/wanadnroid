package test.juyoufuli.com.myapplication.mvp.ui.navigation

import com.airbnb.mvrx.Async
import com.airbnb.mvrx.Loading
import com.airbnb.mvrx.MavericksState
import com.airbnb.mvrx.Uninitialized
import test.juyoufuli.com.myapplication.app.MvRxViewModel
import test.juyoufuli.com.myapplication.app.net.apiService
import test.juyoufuli.com.myapplication.mvp.entity.NavigationBean
import test.juyoufuli.com.myapplication.mvp.entity.WanApiResponse

/**
 * @Author : dongfang
 * @Created Time : 2023-08-21  11:03
 * @Description:
 */

data class NavigationState(
    val navigationData: Async<WanApiResponse<List<NavigationBean>>> = Uninitialized,
    val selectPosition: Int = 0
) : MavericksState


class NavigationViewModel(state: NavigationState) :
    MvRxViewModel<NavigationState>(state) {

    init {
        getNavigationData()
    }

    private fun getNavigationData() {
        withState { oldState ->
            if (oldState.navigationData is Loading) return@withState
            suspend { apiService.getNavigation() }.execute {
                copy(navigationData = it)
            }
        }
    }

    fun selectPositionItem(position: Int) {
        setState { copy(selectPosition = position) }
    }
}
