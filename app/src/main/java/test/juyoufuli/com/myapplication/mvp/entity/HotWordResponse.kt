package test.juyoufuli.com.myapplication.mvp.entity

/**
 * @Author : dongfang
 * @Created Time : 2018-11-16  14:19
 * @Description:
 */

data class HotWordResponse(
        val `data`: List<HotWordData>,
        val errorCode: Int,
        val errorMsg: String
)

data class HotWordData(
        val id: Int,
        val link: String,
        val name: String,
        val order: Int,
        val visible: Int
)