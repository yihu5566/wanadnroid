package test.juyoufuli.com.myapplication.mvp.entity

/**
 * Author : dongfang
 * Created Time : 2018-10-31  10:55
 * Description:
 */


data class ProjectDetailsResponse(
    val data: ProjectDetailsData,
    val errorCode: Int,
    val errorMsg: String
)

data class ProjectDetailsData(
    val curPage: Int,
    val datas: List<ProjectDatas>,
    val offset: Int,
    val over: Boolean,
    val pageCount: Int,
    val size: Int,
    val total: Int
)

data class ProjectDatas(
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
    val projectLink: String,
    val publishTime: Long,
    val superChapterId: Int,
    val superChapterName: String,
    val tags: List<Tag>,
    val title: String,
    val type: Int,
    val userId: Int,
    val visible: Int,
    val zan: Int
)

data class Tag(
    val name: String,
    val url: String
)