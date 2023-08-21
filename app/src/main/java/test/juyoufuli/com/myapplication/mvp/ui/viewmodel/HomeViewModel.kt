package test.juyoufuli.com.myapplication.mvp.ui.viewmodel

import com.airbnb.mvrx.Async
import com.airbnb.mvrx.MavericksState
import com.airbnb.mvrx.MavericksViewModel
import com.airbnb.mvrx.MavericksViewModelFactory
import com.airbnb.mvrx.Uninitialized
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import test.juyoufuli.com.myapplication.di.AssistedViewModelFactory
import test.juyoufuli.com.myapplication.di.daggerMavericksViewModelFactory

/**
 * @Author : dongfang
 * @Created Time : 2023-08-21  11:03
 * @Description:
 */


data class HomeDaggerState(val message: Async<String> = Uninitialized) : MavericksState

class HomeViewModel constructor(@Assisted state: HomeDaggerState) :
    MavericksViewModel<HomeDaggerState>(state) {


    @AssistedFactory
    interface Factory : AssistedViewModelFactory<HomeViewModel, HomeDaggerState> {
        override fun create(state: HomeDaggerState): HomeViewModel
    }

    companion object : MavericksViewModelFactory<HomeViewModel, HomeDaggerState> by daggerMavericksViewModelFactory()
}
