package test.juyoufuli.com.myapplication.mvp.ui.project

import com.airbnb.mvrx.Async
import com.airbnb.mvrx.MavericksState
import com.airbnb.mvrx.MavericksViewModelFactory
import com.airbnb.mvrx.Uninitialized
import com.airbnb.mvrx.ViewModelContext
import org.koin.android.ext.android.inject
import test.juyoufuli.com.myapplication.app.MvRxViewModel
import test.juyoufuli.com.myapplication.mvp.entity.ProjectData
import test.juyoufuli.com.myapplication.mvp.entity.ProjectDatas
import test.juyoufuli.com.myapplication.mvp.entity.WanApiResponse
import test.juyoufuli.com.myapplication.mvp.viewmodel.HomeRepository

/**
 * @Author : dongfang
 * @Created Time : 2023-08-21  11:03
 * @Description:
 */

data class ProjectState(
    val projectTitle: Async<WanApiResponse<List<ProjectData>>> = Uninitialized,
    val projectDetailsList: List<ProjectDatas> = emptyList(),
    val pager: Int = 1,
    val cid: Int = 0,
    val selectIndex: Int = 0,
) : MavericksState

class ProjectViewModel(state: ProjectState, private val repository: HomeRepository) :
    MvRxViewModel<ProjectState>(state) {

    init {
        requestProjectCategory()
    }

    private fun requestProjectCategory() {
        repository.requestProjectCategory().execute {
            copy(projectTitle = it)
        }
    }

    fun requestProjectCategoryDetailsList(pager: Int, cid: Int) {
        repository.requestProjectCategoryDetails(pager, cid).execute {
            val listData = it.invoke()?.data?.datas
//            val pageCount = it.invoke()?.data?.pageCount
            if (it.complete) {
                copy(
                    projectDetailsList = when (pager) {
                        1 -> (listData ?: emptyList())
                        else -> projectDetailsList + (listData ?: emptyList())
                    },
                    pager = pager + 1,
                )
            } else {
                copy()
            }
        }
    }

    fun changeTab(position: Int, projectData: ProjectData) {
        setState {
            copy(cid = projectData.id, pager = 1, selectIndex = position)
        }
    }


    companion object : MavericksViewModelFactory<ProjectViewModel, ProjectState> {
        override fun create(
            viewModelContext: ViewModelContext,
            state: ProjectState
        ): ProjectViewModel {
            val api: HomeRepository by viewModelContext.activity.inject()
            return ProjectViewModel(state, api)
        }
    }

}
