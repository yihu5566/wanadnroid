package test.juyoufuli.com.myapplication

import android.app.Application
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatDelegate
import com.kingja.loadsir.core.LoadSir
import test.juyoufuli.com.myapplication.app.utils.SPUtils
import test.juyoufuli.com.myapplication.di.AppComponent
import test.juyoufuli.com.myapplication.mvp.ui.callback.EmptyCallback
import test.juyoufuli.com.myapplication.mvp.ui.callback.LoadingCallback
import test.juyoufuli.com.myapplication.mvp.ui.callback.TimeoutCallback
import test.juyoufuli.com.myapplication.di.DaggerAppComponent


/**
 * Author : dongfang
 * Created Time : 2018-10-1209:19
 * Description:
 */
class WanBaseApplication : Application() {

    lateinit var appComponent: AppComponent

    companion object {
        val ui_handler: Handler = Handler(Looper.getMainLooper())

        fun runOnUiThread(runnable: Runnable?) {
            if (runnable != null) {
                ui_handler.post(runnable)
            }
        }
    }


    override fun onCreate() {
        super.onCreate()
        init()
    }

    private fun init() {

        appComponent = DaggerAppComponent.create()

        //判断是不是夜间模式
        val mode = SPUtils.get(this, "night_mode", false) as Boolean
        //进入方法就先注册上监听
        if (mode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
        LoadSir.beginBuilder()
            .addCallback(EmptyCallback())
            .addCallback(LoadingCallback())
            .addCallback(TimeoutCallback())
            .setDefaultCallback(LoadingCallback::class.java)
            .commit()

    }

    fun ComponentActivity.appComponent(): AppComponent {
        return (application as WanBaseApplication).appComponent
    }

}
