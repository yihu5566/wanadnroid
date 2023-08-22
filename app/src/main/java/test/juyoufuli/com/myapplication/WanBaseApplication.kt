package test.juyoufuli.com.myapplication

import android.app.Application
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatDelegate
import com.airbnb.mvrx.Mavericks
import com.kingja.loadsir.core.LoadSir
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import test.juyoufuli.com.myapplication.app.utils.SPUtils
import test.juyoufuli.com.myapplication.di.dataSourceModule
import test.juyoufuli.com.myapplication.di.viewModelModule
import test.juyoufuli.com.myapplication.mvp.ui.callback.EmptyCallback
import test.juyoufuli.com.myapplication.mvp.ui.callback.LoadingCallback
import test.juyoufuli.com.myapplication.mvp.ui.callback.TimeoutCallback


/**
 * Author : dongfang
 * Created Time : 2018-10-1209:19
 * Description:
 */
class WanBaseApplication : Application() {


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
        Mavericks.initialize(this)
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
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@WanBaseApplication)
            androidFileProperties()
            modules(dataSourceModule, viewModelModule)
        }
    }
}
