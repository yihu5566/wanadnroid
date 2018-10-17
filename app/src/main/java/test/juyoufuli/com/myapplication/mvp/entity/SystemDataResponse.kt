package test.juyoufuli.com.myapplication.mvp.entity

/**
 * Author : ludf
 * Created Time : 2018-09-28  15:11
 * Description:
 */

data class SystemDataRespons(
    val data: List<SystemBean>,
    val errorCode: Int,
    val errorMsg: String
)

data class SystemBean(
    val children: List<Children>,
    val courseId: Int,
    val id: Int,
    val name: String,
    val order: Int,
    val parentChapterId: Int,
    val visible: Int
)

data class Children(
    val children: List<Any>,
    val courseId: Int,
    val id: Int,
    val name: String,
    val order: Int,
    val parentChapterId: Int,
    val visible: Int
)