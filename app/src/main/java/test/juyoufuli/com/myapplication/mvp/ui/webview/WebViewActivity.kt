package test.juyoufuli.com.myapplication.mvp.ui.webview

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.KeyEvent
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.just.agentweb.AgentWeb
import test.juyoufuli.com.myapplication.R

/**
 * Author : ludf
 * Created Time : 2018-09-30  10:40
 * Description:
 */
class WebViewActivity : AppCompatActivity() {
    @JvmField
    @BindView(R.id.fl_web)
    internal var flWeb: FrameLayout? = null
    @JvmField
    @BindView(R.id.toolbar_back)
    internal var toolbar_back: RelativeLayout? = null
    @JvmField
    @BindView(R.id.toolbar_share)
    internal var toolbar_share: RelativeLayout? = null

    @JvmField
    @BindView(R.id.toolbar_title)
    internal var toolbar_title: TextView? = null
    private var mAgentWeb: AgentWeb? = null
    private var link: String? = null
    private var title: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview)
        ButterKnife.bind(this)

    }

    override fun onStart() {
        super.onStart()
        val intent = intent
        link = intent.getStringExtra("link")
        title = intent.getStringExtra("title")
        toolbar_back!!.visibility = View.VISIBLE
        toolbar_share!!.visibility = View.VISIBLE
        toolbar_title!!.text = title
        toolbar_back!!.setOnClickListener { finish() }

        toolbar_share!!.setOnClickListener {
            val wechatIntent = Intent(Intent.ACTION_SEND)
            wechatIntent.setPackage("com.tencent.mm")
            wechatIntent.setType("text/plain")
            wechatIntent.putExtra(Intent.EXTRA_TEXT, link)
            startActivity(wechatIntent)

        }

        mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent(flWeb!!, LinearLayout.LayoutParams(-1, -1))
                .useDefaultIndicator()
                .createAgentWeb()
                .ready().go(link)
    }


    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {

        return if (mAgentWeb!!.handleKeyEvent(keyCode, event)) {
            true
        } else super.onKeyDown(keyCode, event)
    }

    override fun onPause() {
        mAgentWeb!!.webLifeCycle.onPause()
        super.onPause()

    }

    override fun onResume() {
        mAgentWeb!!.webLifeCycle.onResume()
        super.onResume()
    }

    override fun onDestroy() {
        mAgentWeb!!.webLifeCycle.onDestroy()
        super.onDestroy()
    }
}
