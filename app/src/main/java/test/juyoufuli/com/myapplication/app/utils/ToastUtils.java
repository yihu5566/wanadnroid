package test.juyoufuli.com.myapplication.app.utils;


import android.content.Context;
import android.os.Debug;


import test.juyoufuli.com.myapplication.BuildConfig;
import test.juyoufuli.com.myapplication.WanBaseApplication;


/**
 * 类名：ToastUtils
 *
 * @author wuxin<br       />
 * 实现的主要功能: 不会重复显示的Toast
 * 创建日期：16/7/20
 */
public class ToastUtils {

    /**
     * @param context
     * @param text    要展示的文本
     */
    public static void showToast(final Context context, final String text) {
        if (context != null) {
            WanBaseApplication.Companion.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    BaseToastView.showToast(context.getApplicationContext(), text);
                }
            });
        }

    }

    /**
     * @param context
     * @param text    要展示的文本
     */
    public static void showDebugToast(final Context context, final String text) {
        if (BuildConfig.DEBUG) {
            if (context != null) {
                WanBaseApplication.Companion.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        BaseToastView.showToast(context.getApplicationContext(), text);
                    }
                });
            }
        }
    }


}
