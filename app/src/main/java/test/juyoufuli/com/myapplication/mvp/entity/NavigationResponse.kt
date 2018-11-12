package test.juyoufuli.com.myapplication.mvp.entity

/**
 * Author : dongfang
 * Created Time : 2018-11-12  11:53
 * Description:
 */

data class NavigationResponse(
        val `data`: List<NavigationBean>,
        val errorCode: Int,
        val errorMsg: String
)

data class NavigationBean(
        val articles: List<Article>,
        val cid: Int,
        val name: String
)

data class Article(
        val apkLink: String,
        val author: String,
        val chapterId: Int,
        val chapterName: String,
        val collect: Boolean,
        val courseId: Int,
        val desc: String,
        val envelopePic: String,
        val fresh: Boolean,
        val id: Int,
        val link: String,
        val niceDate: String,
        val origin: String,
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