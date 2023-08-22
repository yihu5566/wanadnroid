package test.juyoufuli.com.myapplication.app.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import test.juyoufuli.com.myapplication.R

/**
 * Author : ludf
 * Created Time : 2018-09-29  16:06
 * Description:
 */
object ImageLoaderUtils {
    /**
     * 带缓存的加载方式
     *
     * @param imageView
     * @param objUrl
     */
    fun loadImage(imageView: ImageView, objUrl: String, context: Context) {
        val options = RequestOptions()
            .placeholder(R.drawable.loading) // 正在加载中的图片
            .error(R.drawable.error) // 加载失败的图片
            .diskCacheStrategy(DiskCacheStrategy.ALL) // 磁盘缓存策略
        Glide.with(context)
            .load(objUrl) // 图片地址
            .apply(options) // 参数
            .into(imageView) // 需要显示的ImageView控件
    }
}