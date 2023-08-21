package test.juyoufuli.com.myapplication.mvp.viewmodel

import com.airbnb.mvrx.Async
import com.airbnb.mvrx.MavericksState
import com.airbnb.mvrx.MavericksViewModel
import com.airbnb.mvrx.Uninitialized
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

/**
 * @Author : dongfang
 * @Created Time : 2023-08-21  11:03
 * @Description:
 */

data class HomeDaggerState(val message: Async<String> = Uninitialized) : MavericksState

class HomeDaggerViewModel @AssistedInject constructor(@Assisted state: HomeDaggerState) :
    MavericksViewModel<HomeDaggerState>(state) {
}
