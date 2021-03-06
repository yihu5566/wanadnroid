package test.juyoufuli.com.myapplication.mvp.entity

/**
 * Author : ludf
 * Created Time : 2018-09-27  14:02
 * Description:
 */


data class ArticleResponse(
        val data: Data,
        val errorCode: Int,
        val errorMsg: String
)

data class Data(
        val curPage: Int,
        val datas: ArrayList<ArticleBean>,
        val offset: Int,
        val over: Boolean,
        val pageCount: Int,
        val size: Int,
        val total: Int
)

data class ArticleBean(
        val apkLink: String,
        val author: String,
        val chapterId: Int,
        val chapterName: String,
        var collect: Boolean,
        val courseId: Int,
        val desc: String,
        val envelopePic: String,
        val fresh: Boolean,
        val id: Int,
        val link: String,
        val niceDate: String,
        val origin: String,
        var originId: Int,
        val projectLink: String,
        val publishTime: Long,
        val superChapterId: Int,
        val superChapterName: String,
        val tags: List<Any>,
        val title: String,
        val type: Int,
        val userId: Int,
        val visible: Int,
        val zan: Int
)