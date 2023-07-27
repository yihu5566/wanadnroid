package test.juyoufuli.com.myapplication.mvp.ui.gongzhonghao

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import com.jess.arms.base.BaseActivity
import com.jess.arms.di.component.AppComponent
import com.jess.arms.utils.ArmsUtils
import com.jess.arms.utils.Preconditions
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import kotlinx.android.synthetic.main.activity_wechat_search_history.*
import test.juyoufuli.com.myapplication.R
import test.juyoufuli.com.myapplication.app.listener.CustomSearchListener
import test.juyoufuli.com.myapplication.app.utils.ToastUtils
import test.juyoufuli.com.myapplication.app.view.CustomSearchView
import test.juyoufuli.com.myapplication.di.component.DaggerWeChatSearchHistoryComponent
import test.juyoufuli.com.myapplication.di.module.WeChatSearchHistoryModule
import test.juyoufuli.com.myapplication.mvp.entity.ArticleBean
import test.juyoufuli.com.myapplication.mvp.entity.ArticleResponse
import test.juyoufuli.com.myapplication.mvp.entity.HotWordResponse
import test.juyoufuli.com.myapplication.mvp.model.contract.WeChatSearchContract
import test.juyoufuli.com.myapplication.mvp.presenter.WeChatSearchViewPresenter
import test.juyoufuli.com.myapplication.mvp.ui.searchview.adapter.BaseRecyclerViewAdapter
import test.juyoufuli.com.myapplication.mvp.ui.searchview.adapter.SearchAdapter
import test.juyoufuli.com.myapplication.mvp.ui.webview.WebViewActivity
import javax.inject.Inject


/**
 * @Author : dongfang
 * @Created Time : 2019-03-26  11:08
 * @Description:
 */
class WeChatSearchHistoryActivity : BaseActivity<WeChatSearchViewPresenter>(), WeChatSearchContract.View, View.OnClickListener {
    @JvmField
    @BindView(R.id.toolbar_back)
    internal var toolbar_back: RelativeLayout? = null
    @JvmField
    @BindView(R.id.toolbar_title)
    internal var toolbar_title: TextView? = null

    @JvmField
    @BindView(R.id.rlv_search_result)
    internal var rlvSearchResult: RecyclerView? = null

    @JvmField
    @BindView(R.id.refreshLayout)
    internal var mRefreshLayout: SmartRefreshLayout? = null
    @JvmField
    @BindView(R.id.sv_history)
    internal var mSearchView: CustomSearchView? = null

    @JvmField
    @Inject
    internal var mAdapter: SearchAdapter? = null

    @JvmField
    @Inject
    internal var mLayoutManager: RecyclerView.LayoutManager? = null

    internal var page: Int = 0
    internal var totalPage: Int = 0

    internal var searchContent: String = ""


    /**
     * 默认的公众号id
     */
    var cid: String = ""

    override fun setupActivityComponent(appComponent: AppComponent) {
        DaggerWeChatSearchHistoryComponent.builder().appComponent(appComponent)
                .weChatSearchHistoryModule(WeChatSearchHistoryModule(this)).build().inject(this)
    }

    override fun initView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_wechat_search_history
    }

    override fun initData(savedInstanceState: Bundle?) {
        ArmsUtils.configRecyclerView(rlvSearchResult!!, mLayoutManager)
        rlvSearchResult!!.adapter = mAdapter
        cid = intent.getStringExtra("cid").toString()
        initRecyclerView()

        mAdapter!!.setOnItemClickListener(object : BaseRecyclerViewAdapter.OnItemClickListener<ArticleBean> {
            override fun onItemClick(clickId: Int, position: Int, item: ArticleBean) {
                val intent = Intent(this@WeChatSearchHistoryActivity, WebViewActivity::class.java)
                intent.putExtra("link", item.link)
                intent.putExtra("title", item.title)
                launchActivity(intent)
            }
        })
    }

    override fun onResume() {
        super.onResume()
        toolbar_title!!.text = "搜索"
        toolbar_back!!.visibility = View.VISIBLE
        toolbar_back!!.setOnClickListener(this)

        mSearchView!!.setCustomSearchListener(object : CustomSearchListener {
            // 当点击搜索按钮时触发该方法
            override fun OnContentChangeListener(query: String) {
                searchContent = query
            }

            // 当搜索内容改变时触发该方法
            override fun OnSearchButtonPressListener(newText: Boolean) {
                page = 0
                mAdapter!!.clearList()
                mAdapter!!.notifyDataSetChanged()
                mPresenter!!.getSearchResult(cid, page, searchContent)
                hideSoftInput()
            }
        })


    }

    /**
     * 隐藏键盘
     */
    fun hideSoftInput() {
        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (inputMethodManager.isActive()) {
            inputMethodManager.hideSoftInputFromWindow(this@WeChatSearchHistoryActivity.getCurrentFocus()!!.getWindowToken(), 0)
        }

    }


    private fun initRecyclerView() {
        mRefreshLayout!!.setEnableRefresh(false)//启用刷新
        mRefreshLayout!!.setEnableLoadMore(true)//启用加载
        //加载更多
        mRefreshLayout!!.setOnLoadMoreListener { refreshlayout ->
            if (totalPage > page) {
                mPresenter!!.getSearchResult(cid, page, "java")
            }
            Handler().postDelayed({
                refreshlayout.finishLoadMore()
            }, 1000)

        }

    }

    override fun launchActivity(intent: Intent) {
        Preconditions.checkNotNull(intent)
        ArmsUtils.startActivity(intent)
    }

    override fun getActivity(): Activity {
        return this
    }

    override fun refreshList(list: ArticleResponse) {
        mRefreshLayout!!.finishLoadMore()
        if (list.data.total == 0) {
            ToastUtils.showToast(this, "查询不到数据")
            rl_search_finish.visibility = View.VISIBLE
            mRefreshLayout!!.visibility = View.GONE
            return
        } else {
            rl_search_finish.visibility = View.GONE
            mRefreshLayout!!.visibility = View.VISIBLE
        }

        page = list.data.curPage
        totalPage = list.data.total

        if (page == 1) {
            mAdapter!!.clearList()
            mAdapter!!.mList = list.data.datas
        } else {
            mAdapter!!.appendData(list.data.datas)

        }
        mAdapter!!.notifyDataSetChanged()

    }

    override fun refreshHotWord(response: HotWordResponse) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showMessage(message: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.toolbar_back -> killMyself()

        }

    }

    override fun killMyself() {
        finish()
    }

}