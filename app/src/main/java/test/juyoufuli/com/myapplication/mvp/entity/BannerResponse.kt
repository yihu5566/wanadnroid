package test.juyoufuli.com.myapplication.mvp.entity

/**
 * Author : ludf
 * Created Time : 2018-09-29  15:27
 * Description:
 */


data class BannerResponse(
    val data: ArrayList<BannerInfor>,
    val errorCode: Int,
    val errorMsg: String
)

data class BannerInfor(
    val desc: String,
    val id: Int,
    val imagePath: String,
    val isVisible: Int,
    val order: Int,
    val title: String,
    val type: Int,
    val url: String
)