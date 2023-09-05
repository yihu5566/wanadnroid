package test.juyoufuli.com.myapplication.mvp.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import test.juyoufuli.com.myapplication.app.BaseRepository
import test.juyoufuli.com.myapplication.app.net.apiService

class HomeRepository : BaseRepository() {
    fun requestBannerDataList() = flow {
        emit(apiService.getBannerList())
    }.flowOn(Dispatchers.IO)

    fun requestArtTop() = flow {
        emit(apiService.getTopArticle())
    }.flowOn(Dispatchers.IO)

    fun requestArtDataList(page: Int) = flow {
        emit(apiService.getArticleList(page))
    }.flowOn(Dispatchers.IO)

    fun getSystemDataList() = flow {
        emit(apiService.getSystemDataList())
    }.flowOn(Dispatchers.IO)

    fun getArticleListDetailsList(cid: Int, page: Int) = flow {
        emit(apiService.getSystemDataDetailsList(cid, page))
    }.flowOn(Dispatchers.IO)

    fun requestProjectCategory() = flow {
        emit(apiService.getProjectList())
    }.flowOn(Dispatchers.IO)

    fun requestProjectCategoryDetails(page: Int, cid: Int) = flow {
        emit(apiService.getProjectDetailsList(page, cid))
    }.flowOn(Dispatchers.IO)

    fun getWeChatPublishDataList() = flow {
        emit(apiService.getWeChatArticle())
    }.flowOn(Dispatchers.IO)
}
