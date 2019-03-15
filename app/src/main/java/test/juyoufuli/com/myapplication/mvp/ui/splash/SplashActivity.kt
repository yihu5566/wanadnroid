package test.juyoufuli.com.myapplication.mvp.ui.splash

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.view.WindowManager
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.widget.ImageView
import kotlinx.android.synthetic.main.activity_splash.*
import test.juyoufuli.com.myapplication.R
import test.juyoufuli.com.myapplication.app.ui.home.MainActivity

/**
 * @Author : dongfang
 * @Created Time : 2019-03-14  17:52
 * @Description:
 */
class SplashActivity : AppCompatActivity() {
    var mianIntent: Intent? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN) //隐藏状态栏
        setContentView(R.layout.activity_splash)
        mianIntent = Intent(this@SplashActivity, MainActivity::class.java)
        transparencyAnimation(iv_splash)

    }

    fun transparencyAnimation(view: ImageView) {
        //初始化操作，参数传入0和1，即由透明度0变化到透明度为1
        var alphaAnimation = AlphaAnimation(1f, 0f)

        view.startAnimation(alphaAnimation)
        alphaAnimation.fillAfter = true
        alphaAnimation.duration = 2000

        alphaAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {

            }

            override fun onAnimationEnd(animation: Animation) {
                startActivity(mianIntent)
                finish()
            }

            override fun onAnimationRepeat(animation: Animation) {

            }
        })

    }

}