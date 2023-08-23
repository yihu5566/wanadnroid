package test.juyoufuli.com.myapplication.mvp.viewmodel

import com.airbnb.mvrx.MavericksState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.launch
import test.juyoufuli.com.myapplication.app.MvRxViewModel
import test.juyoufuli.com.myapplication.app.net.apiService
import test.juyoufuli.com.myapplication.mvp.entity.ArticleBean
import test.juyoufuli.com.myapplication.mvp.entity.BannerInfo
import test.juyoufuli.com.myapplication.mvp.entity.WanApiResponse

/**
 * @Author : dongfang
 * @Created Time : 2023-08-21  11:03
 * @Description:
 */

data class HomeDaggerState(
    val bannerList: List<BannerInfo> = mutableListOf(),
    val artList: List<ArticleBean> = mutableListOf(),
    val isLoadingMore: Boolean = true,
    val pullToRefresh: Boolean = false,
    val pager: Int = 1,
) : MavericksState


class HomeDaggerViewModel(state: HomeDaggerState) :
    MvRxViewModel<HomeDaggerState>(state) {

    init {
        requestBannerDataList()
        mergeArtList()
    }

    fun requestBannerDataList() {
        request({ apiService.getBannerList() }, {
            setState {
                copy(bannerList = it)
            }
        })
    }

    fun requestTopDataList() {
        request({ apiService.getTopArticle() }, {
        })
    }

    fun requestArtDataList(pager: Int) {
        request({ apiService.getArticleList(pager) }, {
            setState {
                copy(
                    artList = artList + it.datas,
                    pager = pager + 1,
                    isLoadingMore = pager < it.pageCount
                )
            }
        })
    }


    //合并一次
    fun mergeArtList() {
        setState { copy(pullToRefresh = true) }
        viewModelScope.launch {
            val wanApiResponseFlow = flow {
                emit(apiService.getTopArticle())
            }.flowOn(Dispatchers.IO)
            val wanApiResponseFlow1 = flow {
                emit(apiService.getArticleList(1))
            }.flowOn(Dispatchers.IO)
            wanApiResponseFlow.flatMapMerge { wan1 ->
                wanApiResponseFlow1.transform {
                    emit(
                        WanApiResponse(
                            errorCode = 0,
                            errorMsg = "",
                            data = wan1.data + it.data.datas
                        )
                    )
                }
            }.collect {
                setState {
                    copy(
                        artList = it.data,
                        pullToRefresh = false
                    )
                }
            }

        }
    }

}
