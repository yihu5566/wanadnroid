package test.juyoufuli.com.myapplication.mvp.entity

/**
 * 分页实体
 *
 * @author LTP  2022/3/22
 */
data class WanPageResponse<T>(
    val curPage: Int,
    val datas: List<T>,
    val offset: Int,
    val over: Boolean,
    val pageCount: Int,
    val size: Int,
    val total: Int
)