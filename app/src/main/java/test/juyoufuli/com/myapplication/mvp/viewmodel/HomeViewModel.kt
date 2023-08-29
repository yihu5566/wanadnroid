package test.juyoufuli.com.myapplication.mvp.viewmodel

import com.airbnb.mvrx.Async
import com.airbnb.mvrx.MavericksState
import com.airbnb.mvrx.MavericksViewModelFactory
import com.airbnb.mvrx.Uninitialized
import com.airbnb.mvrx.ViewModelContext
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import test.juyoufuli.com.myapplication.app.MvRxViewModel
import test.juyoufuli.com.myapplication.app.net.apiService
import test.juyoufuli.com.myapplication.app.utils.LogUtils
import test.juyoufuli.com.myapplication.mvp.entity.ArticleBean
import test.juyoufuli.com.myapplication.mvp.entity.BannerInfo
import test.juyoufuli.com.myapplication.mvp.entity.WanApiResponse

/**
 * @Author : dongfang
 * @Created Time : 2023-08-21  11:03
 * @Description:
 */

data class HomeDaggerState(
    val bannerList: Async<WanApiResponse<List<BannerInfo>>> = Uninitialized,
    val artList: List<ArticleBean> = emptyList(),
    val isLoadingMore: Boolean = true,
    val pullToRefresh: Boolean = false,
    val pager: Int = 1,
) : MavericksState


class HomeDaggerViewModel(state: HomeDaggerState, val repository: HomeRepository) :
    MvRxViewModel<HomeDaggerState>(state) {

    init {
        requestBannerDataList()
        mergeArtList()
    }

    fun requestBannerDataList() {
        repository.requestBannerDataList().execute {
            copy(bannerList = it)
        }
//        request({ apiService.getBannerList() }, {
//            setState {
//                copy(bannerList = it)
//            }
//        })
    }

    fun requestArtDataList(pager: Int) {
        repository.requestArtDataList(pager).execute {
            LogUtils.d("------=====${it.invoke()}")
            val listData = it.invoke()?.data?.datas
            val pageCount = it.invoke()?.data?.pageCount
            if (it.complete) {
                copy(
                    artList = artList + (listData ?: emptyList()),
                    pager = pager + 1,
                    isLoadingMore = pager < (pageCount ?: 0)
                )
            } else {
                copy()
            }
        }

//        request({ apiService.getArticleList(pager) }, {
//            setState {
//                copy(
//                    artList = artList + it.datas,
//                    pager = pager + 1,
//                    isLoadingMore = pager < it.pageCount
//                )
//            }
//        })
    }


    //合并一次
    @OptIn(FlowPreview::class)
    fun mergeArtList() {
        setState { copy(pullToRefresh = true) }
        viewModelScope.launch {
            val wanApiResponseFlow = repository.requestArtTop()
            val wanApiResponseFlow1 = repository.requestArtDataList(1)
            wanApiResponseFlow.flatMapMerge { wan1 ->
                wanApiResponseFlow1.transform {
                    emit(
                        WanApiResponse(
                            errorCode = 0, errorMsg = "", data = wan1.data + it.data.datas
                        )
                    )
                }
            }.collect {
                setState {
                    copy(
                        artList = it.data, pullToRefresh = false, pager = 2
                    )
                }
            }

        }
    }

    fun collectArticle(id: String, position: Int) {
        request({ apiService.collectArticle(id) }, {
            setState {
                artList[position].collect = true
                copy(artList = artList)
            }
        })
    }

    fun cancelCollectArticle(id: String, position: Int) {
        request({ apiService.cancelCollectArticle(id) }, {

            setState {
                artList[position].collect = false
                copy(artList = artList)
            }
        })
    }

    companion object : MavericksViewModelFactory<HomeDaggerViewModel, HomeDaggerState> {
        override fun create(
            viewModelContext: ViewModelContext,
            state: HomeDaggerState
        ): HomeDaggerViewModel {
            val api: HomeRepository by viewModelContext.activity.inject()
            return HomeDaggerViewModel(state, api)
        }
    }

}
