package test.juyoufuli.com.myapplication.mvp.viewmodel

import com.airbnb.mvrx.MavericksState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import test.juyoufuli.com.myapplication.app.MvRxViewModel
import test.juyoufuli.com.myapplication.app.net.apiService
import test.juyoufuli.com.myapplication.app.utils.LogUtils
import test.juyoufuli.com.myapplication.mvp.entity.ArticleBean
import test.juyoufuli.com.myapplication.mvp.entity.BannerInfo

/**
 * @Author : dongfang
 * @Created Time : 2023-08-21  11:03
 * @Description:
 */

data class HomeDaggerState(
    val bannerList: List<BannerInfo> = mutableListOf(),
    val artList: List<ArticleBean> = mutableListOf(),
    val isLoadingMore: Boolean = false,
    val pullToRefresh: Boolean = false,
    val pager: Int = 1,
) : MavericksState


class HomeDaggerViewModel(state: HomeDaggerState) :
    MvRxViewModel<HomeDaggerState>(state) {
    private val artListTotale = mutableListOf<ArticleBean>()

    init {
        requestBannerDataList()
        requestTopDataList()
        requestArtDataList(1)
    }

    fun requestBannerDataList() {
        request({ apiService.getBannerList() }, {
            setState {
                copy(
                    bannerList + it,
                    isLoadingMore = false,
                    pullToRefresh = false
                )
            }
        })
    }

    fun requestTopDataList() {
        request({ apiService.getTopArticle() }, {
            artListTotale.addAll(0, it)
            setState {
                copy(
                    bannerList,
                    artList + artListTotale,
                    isLoadingMore = false,
                    pullToRefresh = false
                )
            }
        })
    }

    fun requestArtDataList(pager: Int) {
        request({ apiService.getArticleList(pager) }, {
            artListTotale.addAll(it.datas)
            setState {
                copy(
                    bannerList,
                    artList + artListTotale,
                    isLoadingMore = pager != 1,
                    pullToRefresh = false,
                    pager = pager + 1
                )
            }
        })
    }


    private fun mergeArtList() {
        viewModelScope.launch {
            val wanApiResponseFlow = flow {
                emit(apiService.getTopArticle())
            }.flowOn(Dispatchers.IO)

            val wanApiResponseFlow1 = flow {
                emit(apiService.getArticleList(1))
            }.flowOn(Dispatchers.IO)

        }
    }

}
