package test.juyoufuli.com.myapplication.app.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.jess.arms.base.BaseApplication;

import test.juyoufuli.com.myapplication.R;

/**
 * Author : ludf
 * Created Time : 2018-09-29  16:06
 * Description:
 */
public class ImageLoaderUtils {

    /**
     * 带缓存的加载方式
     *
     * @param imageView
     * @param objUrl
     */
    public static void loadImage(ImageView imageView, Object objUrl, Context context) {

        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.main_tab)// 正在加载中的图片
                .error(R.drawable.tab) // 加载失败的图片
                .diskCacheStrategy(DiskCacheStrategy.ALL); // 磁盘缓存策略
        Glide.with(context)
                .load(objUrl) // 图片地址
                .apply(options) // 参数
                .into(imageView); // 需要显示的ImageView控件


    }
}
