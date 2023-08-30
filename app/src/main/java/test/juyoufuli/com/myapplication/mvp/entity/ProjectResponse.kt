package test.juyoufuli.com.myapplication.mvp.entity

/**
 * Author : dongfang
 * Created Time : 2018-10-31  10:45
 * Description:
 */


data class ProjectData(
    val children: List<Any>,
    val courseId: Int,
    val id: Int,
    val name: String,
    val order: Int,
    val parentChapterId: Int,
    val userControlSetTop: Boolean,
    val visible: Int,
    //1为选中
    var isSelect: String? = "0"
)