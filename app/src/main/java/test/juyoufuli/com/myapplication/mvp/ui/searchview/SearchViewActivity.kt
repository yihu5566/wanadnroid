package test.juyoufuli.com.myapplication.mvp.ui.searchview

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RelativeLayout
import android.widget.TextView

import com.jess.arms.base.BaseActivity
import com.jess.arms.di.component.AppComponent
import com.jess.arms.utils.ArmsUtils
import com.paginate.Paginate

import javax.inject.Inject

import butterknife.BindView
import test.juyoufuli.com.myapplication.R
import test.juyoufuli.com.myapplication.di.component.DaggerSearchComponent
import test.juyoufuli.com.myapplication.di.module.SearchModule
import test.juyoufuli.com.myapplication.mvp.entity.ArticleBean
import test.juyoufuli.com.myapplication.mvp.entity.ArticleResponse
import test.juyoufuli.com.myapplication.mvp.model.contract.SearchContract
import test.juyoufuli.com.myapplication.mvp.presenter.SearchViewPresenter
import test.juyoufuli.com.myapplication.mvp.ui.searchview.adapter.BaseRecyclerViewAdapter
import test.juyoufuli.com.myapplication.mvp.ui.searchview.adapter.SearchAdapter
import test.juyoufuli.com.myapplication.mvp.ui.webview.WebViewActivity

import com.jess.arms.utils.Preconditions.checkNotNull

/**
 * Author : ludf
 * Created Time : 2018-09-30  15:56
 * Description:
 */
class SearchViewActivity : BaseActivity<SearchViewPresenter>(), SearchContract.View, View.OnClickListener {
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
    @BindView(R.id.et_search_word)
    internal var etSearchWord: EditText? = null
    @JvmField
    @BindView(R.id.btn_search_word)
    internal var btnSearchWord: Button? = null
    @JvmField
    @Inject
    internal var mAdapter: SearchAdapter? = null
    @JvmField
    @Inject
    internal var mLayoutManager: RecyclerView.LayoutManager? = null

    private var searchWord: String = ""

    private var page: Int = 0
    private var totalPage: Int = 0
    private var mPaginate: Paginate? = null
    private var isLoadingMore: Boolean = false

    private var isFirst = true

    override fun setupActivityComponent(appComponent: AppComponent) {
        DaggerSearchComponent.builder().appComponent(appComponent).searchModule(SearchModule(this)).build().inject(this)
    }

    override fun initView(savedInstanceState: Bundle?): Int {
        return R.layout.search_activity
    }

    override fun initData(savedInstanceState: Bundle?) {
        initRecyclerView()
        initPaginate()
    }

    private fun initPaginate() {
        if (mPaginate == null) {
            val callbacks = object : Paginate.Callbacks {
                override fun onLoadMore() {
                    if (!isFirst) {
                        if (totalPage > page) {
                            isLoadingMore = true
                            mPresenter!!.getSearchResult(page, searchWord)
                        }

                    } else {
                        isFirst = false
                    }
                }

                override fun isLoading(): Boolean {
                    return isLoadingMore
                }

                override fun hasLoadedAllItems(): Boolean {
                    return page == totalPage
                }
            }

            mPaginate = Paginate.with(rlvSearchResult, callbacks)
                    .setLoadingTriggerThreshold(0)
                    .build()
            mPaginate!!.setHasMoreDataToLoad(false)
        }
    }

    private fun initRecyclerView() {
        ArmsUtils.configRecyclerView(rlvSearchResult!!, mLayoutManager)
        rlvSearchResult!!.adapter = mAdapter

        mAdapter!!.setOnItemClickListener(object : BaseRecyclerViewAdapter.OnItemClickListener<ArticleBean> {
            override fun onItemClick(clickId: Int, position: Int, item: ArticleBean) {
                val intent = Intent(getActivity(), WebViewActivity::class.java)
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
        btnSearchWord!!.setOnClickListener(this)


    }

    override fun showLoading() {}

    override fun hideLoading() {}

    override fun showMessage(message: String) {

    }

    override fun launchActivity(intent: Intent) {
        checkNotNull(intent)
        ArmsUtils.startActivity(intent)
    }

    override fun killMyself() {
        finish()
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.toolbar_back -> killMyself()
            R.id.btn_search_word -> {
                if (mAdapter != null) {
                    mAdapter!!.clearList()
                    page = 0
                }
                isLoadingMore = true
                searchWord = etSearchWord!!.text.toString()
                mPresenter!!.getSearchResult(page, searchWord)
            }
        }

    }

    override fun getActivity(): Activity {
        return this
    }

    override fun refreshList(response: ArticleResponse) {
        page = response.data.curPage

        if (response.data.curPage == 1) {
            mAdapter!!.list = response.data.datas
            totalPage = response.data.pageCount
        } else {
            mAdapter!!.appendData(response.data.datas)
        }

        isLoadingMore = false


    }
}
