package test.juyoufuli.com.myapplication.mvp.entity

/**
 * Author : ludf
 * Created Time : 2018-09-29  15:27
 * Description:
 */
data class BannerInfo(
    val desc: String,
    val id: Int,
    val imagePath: String,
    val isVisible: Int,
    val order: Int,
    val title: String,
    val type: Int,
    val url: String
)