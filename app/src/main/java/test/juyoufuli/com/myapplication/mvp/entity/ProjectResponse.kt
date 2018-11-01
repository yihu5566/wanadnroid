package test.juyoufuli.com.myapplication.mvp.entity

/**
 * Author : dongfang
 * Created Time : 2018-10-31  10:45
 * Description:
 */


data class ProjectResponse(
        val data: List<ProjectData>,
        val errorCode: Int,
        val errorMsg: String
)

data class ProjectData(
        val children: List<Any>,
        val courseId: Int,
        val id: Int,
        val name: String,
        val order: Int,
        val parentChapterId: Int,
        val userControlSetTop: Boolean,
        val visible: Int,
        var isSelect: Boolean
)