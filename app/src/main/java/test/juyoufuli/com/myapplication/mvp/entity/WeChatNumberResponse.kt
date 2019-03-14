package test.juyoufuli.com.myapplication.mvp.entity

/**
 * @Author : dongfang
 * @Created Time : 2019-03-14  11:08
 * @Description:
 */


data class WeChatNumberResponse(
        val `data`: List<WeChatData>,
        val errorCode: Int,
        val errorMsg: String
)

data class WeChatData(
        val children: List<Any>,
        val courseId: Int,
        val id: Int,
        val name: String,
        val order: Int,
        val parentChapterId: Int,
        val userControlSetTop: Boolean,
        val visible: Int
)