package test.juyoufuli.com.myapplication.mvp.ui.tab

import android.content.Intent
import android.os.Bundle
import android.os.Message
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.jess.arms.base.BaseFragment
import com.jess.arms.di.component.AppComponent
import com.jess.arms.utils.ArmsUtils
import com.jess.arms.utils.LogUtils
import com.paginate.Paginate

import javax.inject.Inject

import butterknife.BindView
import com.kingja.loadsir.core.LoadService
import com.kingja.loadsir.core.LoadSir
import test.juyoufuli.com.myapplication.R
import test.juyoufuli.com.myapplication.di.component.DaggerSystemDataItemComponent
import test.juyoufuli.com.myapplication.di.module.SystemDataItemModule
import test.juyoufuli.com.myapplication.mvp.entity.ArticleBean
import test.juyoufuli.com.myapplication.mvp.entity.ArticleResponse
import test.juyoufuli.com.myapplication.mvp.model.contract.SystemDataDetailsItemContract
import test.juyoufuli.com.myapplication.mvp.presenter.SystemDataDetailsItemPresenter
import test.juyoufuli.com.myapplication.mvp.ui.callback.EmptyCallback
import test.juyoufuli.com.myapplication.mvp.ui.callback.LoadingCallback
import test.juyoufuli.com.myapplication.mvp.ui.home.adapter.ArticleAdapter
import test.juyoufuli.com.myapplication.mvp.ui.searchview.adapter.BaseRecyclerViewAdapter
import test.juyoufuli.com.myapplication.mvp.ui.searchview.adapter.SearchAdapter
import test.juyoufuli.com.myapplication.mvp.ui.webview.WebViewActivity

/**
 * Author : dongfang
 * Created Time : 2018-10-17  16:33
 * Description:
 */
class RecyclerViewFragment : BaseFragment<SystemDataDetailsItemPresenter>(), SystemDataDetailsItemContract.View {


    @JvmField
    @BindView(R.id.rlv_pager)
    internal var mRecyclerView: RecyclerView? = null
    @JvmField
    @Inject
    internal var mSystemData: List<ArticleBean>? = null
    @JvmField
    @Inject
    internal var mAdapter: SearchAdapter? = null
    @JvmField
    @Inject
    internal var layoutManager: RecyclerView.LayoutManager? = null
    private var cid: String = ""
    //上拉加载更多
    private var mPaginate: Paginate? = null
    private var isLoadingMore: Boolean = false
    private var page: Int = 0
    private var totalPage: Int = 0

    private var isFrist = true
    var mBaseLoadService: LoadService<*>? = null

    override val fragment: Fragment
        get() = this

    override fun setupFragmentComponent(appComponent: AppComponent) {
        DaggerSystemDataItemComponent.builder().appComponent(appComponent).systemDataItemModule(SystemDataItemModule(this)).build().inject(this)
    }

    override fun initView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val rootView = inflater.inflate(R.layout.view_pager_item, null)
        mBaseLoadService = LoadSir.getDefault().register(rootView) {
            mBaseLoadService!!.showCallback(LoadingCallback::class.java)
        }
        return mBaseLoadService!!.getLoadLayout()
    }

    override fun initData(savedInstanceState: Bundle?) {
        LogUtils.debugInfo("---initData---")
        if (isFrist) {
            isFrist = false
            cid = arguments!!.getString("cid")
            mRecyclerView!!.layoutManager = layoutManager
            mRecyclerView!!.adapter = mAdapter
            mPresenter!!.requestSystemDataList(page.toString(), cid)

            initPaginate()

            mAdapter!!.setOnItemClickListener(object : BaseRecyclerViewAdapter.OnItemClickListener<ArticleBean> {
                override fun onItemClick(clickId: Int, position: Int, item: ArticleBean) {
                    val intent = Intent(activity, WebViewActivity::class.java)
                    intent.putExtra("link", item.link)
                    intent.putExtra("title", item.title)
                    launchActivity(intent)
                }
            })

        }
    }

    override fun setData(data: Any?) {
        if (data != null && data is Message) {
            cid = data.data.getString("cid")
            mPresenter!!.requestSystemDataList(0.toString(), cid)
        }
    }


    private fun initPaginate() {
        if (mPaginate == null) {
            val callbacks = object : Paginate.Callbacks {
                override fun onLoadMore() {
//                    isLoadingMore = true
                    if (totalPage > page) {
                        mPresenter!!.requestSystemDataList(page.toString(), cid)
                    }

                }

                override fun isLoading(): Boolean {
                    return isLoadingMore
                }

                override fun hasLoadedAllItems(): Boolean {
                    return page == totalPage
                }
            }

            mPaginate = Paginate.with(mRecyclerView, callbacks)
                    .setLoadingTriggerThreshold(0)
                    .build()
            mPaginate!!.setHasMoreDataToLoad(false)
        }
    }

    override fun showMessage(message: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun launchActivity(intent: Intent) {

        ArmsUtils.startActivity(intent)
    }


    override fun refreshData(response: ArticleResponse) {
        mBaseLoadService!!.showSuccess()
        if (response.data.pageCount == 0) {
            mBaseLoadService!!.showCallback(EmptyCallback::class.java)
        }
        page = response.data.curPage
        if (response.data.curPage == 1) {
            mAdapter!!.clearList()
            mAdapter!!.list = response.data.datas
            totalPage = response.data.pageCount
        } else {
            mAdapter!!.appendData(response.data.datas)
        }
//        mRecyclerView!!.adapter = mAdapter
        mAdapter!!.notifyDataSetChanged()
    }
}
