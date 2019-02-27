package test.juyoufuli.com.myapplication.mvp.ui.account

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.app.AppCompatDelegate
import android.widget.Button
import android.widget.TextClock
import android.widget.TextView
import test.juyoufuli.com.myapplication.R
import test.juyoufuli.com.myapplication.app.utils.SPUtils

/**
 * @Author : dongfang
 * @Created Time : 2019-02-27  13:36
 * @Description:
 */
class SettingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var textView = findViewById<TextView>(R.id.tv_mode)
        setContentView(R.layout.activity_setting)
        findViewById<Button>(R.id.btn).setOnClickListener {

            val mode = SPUtils.get(applicationContext, "night_mode", false) as Boolean
            //进入方法就先注册上监听
            if (mode) {
                textView.text = "夜间模式开启"
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                textView.text = "夜间模式关闭"
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
            SPUtils.put(applicationContext, "night_mode", !mode)
            recreate()
        }
    }


}