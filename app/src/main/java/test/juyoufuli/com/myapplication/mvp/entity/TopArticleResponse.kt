package test.juyoufuli.com.myapplication.mvp.entity

/**
 * Author : ludf
 * Created Time : 2018-09-27  14:02
 * Description:
 */


data class TopArticleResponse(
        val data: List<ArticleBean>,
        val errorCode: Int,
        val errorMsg: String
)


