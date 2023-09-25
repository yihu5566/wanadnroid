package test.juyoufuli.com.myapplication.mvp.ui.webview

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.just.agentweb.AgentWeb
import com.we.jetpackmvvm.ext.nav
import test.juyoufuli.com.myapplication.app.BaseFragment
import test.juyoufuli.com.myapplication.databinding.ActivityWebviewBinding

/**
 * Author : ludf
 * Created Time : 2018-09-30  10:40
 * Description:
 */
class WebViewFragment : BaseFragment<ActivityWebviewBinding>() {
    private lateinit var mAgentWeb: AgentWeb
    private var link: String? = null
    private var title: String? = null


    override fun initView(savedInstanceState: Bundle?) {
        arguments.let {
            link = it?.getString("link")
            title = it?.getString("title")

        }
        binding.toolbar.toolbarBack.visibility = View.VISIBLE
        binding.toolbar.toolbarShare.visibility = View.VISIBLE
        binding.toolbar.toolbarTitle.text = title
        binding.toolbar.toolbarBack.setOnClickListener { nav().popBackStack() }

        binding.toolbar.toolbarShare.setOnClickListener {
            val wechatIntent = Intent(Intent.ACTION_SEND)
            wechatIntent.setPackage("com.tencent.mm")
            wechatIntent.setType("text/plain")
            wechatIntent.putExtra(Intent.EXTRA_TEXT, link)
            startActivity(wechatIntent)
        }

        mAgentWeb = AgentWeb.with(this)
            .setAgentWebParent(binding.flWeb, LinearLayout.LayoutParams(-1, -1))
            .useDefaultIndicator()
            .createAgentWeb()
            .ready().go(link)
    }

    override fun attachBinding(): ActivityWebviewBinding {
        return ActivityWebviewBinding.inflate(LayoutInflater.from(requireContext()))
    }

//    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
//
//        return if (mAgentWeb.handleKeyEvent(keyCode, event)) {
//            true
//        } else super.onKeyDown(keyCode, event)
//    }

    override fun onPause() {
        mAgentWeb.webLifeCycle.onPause()
        super.onPause()

    }

    override fun onResume() {
        mAgentWeb.webLifeCycle?.onResume()
        super.onResume()
    }

    override fun onDestroy() {
        mAgentWeb.webLifeCycle.onDestroy()
        super.onDestroy()
    }

    override fun invalidate() {

    }
}