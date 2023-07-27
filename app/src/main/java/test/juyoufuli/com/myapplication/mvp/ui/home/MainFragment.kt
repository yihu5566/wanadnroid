package test.juyoufuli.com.myapplication.mvp.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import butterknife.BindView
import com.jess.arms.base.BaseFragment
import com.jess.arms.di.component.AppComponent
import com.jess.arms.utils.ArmsUtils
import com.jess.arms.utils.LogUtils
import com.jess.arms.utils.Preconditions.checkNotNull
import com.paginate.Paginate
import com.tbruyelle.rxpermissions2.RxPermissions
import test.juyoufuli.com.myapplication.R
import test.juyoufuli.com.myapplication.di.component.DaggerMainComponent
import test.juyoufuli.com.myapplication.di.module.MainModule
import test.juyoufuli.com.myapplication.mvp.contract.MainContract
import test.juyoufuli.com.myapplication.mvp.entity.ArticleBean
import test.juyoufuli.com.myapplication.mvp.entity.BannerInfor
import test.juyoufuli.com.myapplication.mvp.entity.BannerResponse
import test.juyoufuli.com.myapplication.mvp.presenter.MainPresenter
import test.juyoufuli.com.myapplication.mvp.ui.home.adapter2.DefaultItemHolder
import test.juyoufuli.com.myapplication.mvp.ui.home.adapter2.MainRecyclerViewAdapter
import test.juyoufuli.com.myapplication.mvp.ui.webview.WebViewActivity
import javax.inject.Inject

/**
 * Author : ludf
 * Created Time : 2018-09-27  15:50
 * Description:
 */
class MainFragment : BaseFragment<MainPresenter>(), MainContract.View, SwipeRefreshLayout.OnRefreshListener {
    @JvmField
    @BindView(R.id.recyclerView)
    internal var mRecyclerView: RecyclerView? = null
    @JvmField
    @BindView(R.id.swipeRefreshLayout)
    internal var mSwipeRefreshLayout: SwipeRefreshLayout? = null
    //internal var bannerGuideContent: BGABanner? = null
    @JvmField
    @Inject
    internal var mLayoutManager: RecyclerView.LayoutManager? = null
    @JvmField
    @Inject
    internal var mPermissions: RxPermissions? = null
    @JvmField
    @Inject
    internal var mUsers: ArrayList<ArticleBean>? = null
    @JvmField
    @Inject
    internal var mAdapter: MainRecyclerViewAdapter? = null

    private var mPaginate: Paginate? = null
    private var isLoadingMore: Boolean = false
    private var isFrist: Boolean = true
    var mmBannerList: ArrayList<BannerInfor>? = null

    override val fragment: Fragment
        get() = this
    override val rxPermissions: RxPermissions
        get() = mPermissions!!

    override fun setupFragmentComponent(appComponent: AppComponent) {
        DaggerMainComponent.builder().appComponent(appComponent).mainModule(MainModule(this)).build().inject(this)
    }

    override fun initView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_main, null)
    }

    override fun initData(savedInstanceState: Bundle?) {
        initRecyclerView()
        initPaginate()
        mPresenter!!.requestBannerDataList()
        mPresenter!!.mergeArticle(true)

//        mPresenter!!.requestTopArticle(true)
//        mPresenter!!.requestFromModel(true)

    }


    private fun initPaginate() {
        if (mPaginate == null) {
            val callbacks = object : Paginate.Callbacks {
                override fun onLoadMore() {
                    LogUtils.debugInfo("加载更多。。。")
                    if (isFrist) {
                        isFrist = false
                    } else {
                        mPresenter!!.requestFromModel(false)
                    }

                }

                override fun isLoading(): Boolean {
                    return isLoadingMore
                }

                override fun hasLoadedAllItems(): Boolean {
                    return false
                }
            }

            mPaginate = Paginate.with(mRecyclerView, callbacks)
                    .setLoadingTriggerThreshold(0)
                    .build()
            mPaginate!!.setHasMoreDataToLoad(false)
        }
    }

    private fun initRecyclerView() {
        mSwipeRefreshLayout!!.setOnRefreshListener(this)
        ArmsUtils.configRecyclerView(mRecyclerView!!, mLayoutManager)
        //我是分割线---------------------------------------
        mRecyclerView!!.adapter = mAdapter

        mAdapter!!.setOnItemClickListener(object : DefaultItemHolder.OnItemClickListener {

            override fun onItemClick(position: Int) {
                if (position == 0) return
                var data = mUsers!![position - 1]
                val intent = Intent(activity, WebViewActivity::class.java)
                intent.putExtra("link", data.link)
                intent.putExtra("title", data.title)
                launchActivity(intent)
            }

        })


        mAdapter!!.setChildClickListener(object : DefaultItemHolder.OnViewClickListener {
            override fun onViewClick(viewid: Int, position: Int, data: ArticleBean) {
                if (viewid == R.id.iv_favorite_article) {
                    if (!data.collect) {
                        LogUtils.debugInfo(data.id.toString() + "--true--" + position)
                        mPresenter!!.collectArticle(data.id.toString())
                    } else {
                        LogUtils.debugInfo(data.id.toString() + "--false--" + position)
                        mPresenter!!.cancelCollectArticle(data.id.toString())

                    }
                    mUsers!![position].collect = !data.collect
//                    mAdapter!!.notifyItemChanged(position)

                }
            }
        })

    }


    override fun setData(data: Any?) {

    }

    override fun startLoadMore() {
        isLoadingMore = true
    }

    override fun endLoadMore() {
        isLoadingMore = false
    }

    override fun showLoading() {
        mSwipeRefreshLayout!!.isRefreshing = true
    }

    override fun hideLoading() {
        mSwipeRefreshLayout!!.isRefreshing = false
    }

    override fun showMessage(message: String) {
        checkNotNull(message)
        ArmsUtils.snackbarText(message)
    }

    override fun launchActivity(intent: Intent) {
        checkNotNull(intent)
        ArmsUtils.startActivity(intent)
    }

    /**
     * 下拉刷新的刷新
     */
    override fun onRefresh() {
        mPresenter!!.mergeArticle(true)
    }


    override fun updateBanner(systemDataResponse: BannerResponse) {
        mmBannerList = systemDataResponse.data
        mAdapter!!.mBannerList = mmBannerList
        mAdapter!!.notifyDataSetChanged()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mUsers!!.clear()
        mmBannerList!!.clear()
    }

}
