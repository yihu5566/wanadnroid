package test.juyoufuli.com.myapplication.mvp.ui.system

import com.airbnb.mvrx.Async
import com.airbnb.mvrx.MavericksState
import com.airbnb.mvrx.MavericksViewModelFactory
import com.airbnb.mvrx.Uninitialized
import com.airbnb.mvrx.ViewModelContext
import org.koin.android.ext.android.inject
import test.juyoufuli.com.myapplication.app.MvRxViewModel
import test.juyoufuli.com.myapplication.mvp.entity.SystemBean
import test.juyoufuli.com.myapplication.mvp.entity.WanApiResponse
import test.juyoufuli.com.myapplication.mvp.repository.HomeRepository

/**
 * @Author : dongfang
 * @Created Time : 2023-08-21  11:03
 * @Description:
 */

data class SystemState(
    val systemBean: Async<WanApiResponse<List<SystemBean>>> = Uninitialized,
) : MavericksState


class SystemViewModel(state: SystemState, private val repository: HomeRepository) :
    MvRxViewModel<SystemState>(state) {

    init {
        getSystemDataList()
    }

    private fun getSystemDataList() {
        repository.getSystemDataList().execute {
            copy(systemBean = it)
        }
    }

    companion object : MavericksViewModelFactory<SystemViewModel, SystemState> {
        override fun create(
            viewModelContext: ViewModelContext,
            state: SystemState
        ): SystemViewModel {
            val api: HomeRepository by viewModelContext.activity.inject()
            return SystemViewModel(state, api)
        }
    }

}