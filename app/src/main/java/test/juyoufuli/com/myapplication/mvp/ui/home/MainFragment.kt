package test.juyoufuli.com.myapplication.mvp.ui.home

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import butterknife.BindView
import cn.bingoogolapple.bgabanner.BGABanner
import com.jess.arms.base.BaseFragment
import com.jess.arms.di.component.AppComponent
import com.jess.arms.utils.ArmsUtils
import com.jess.arms.utils.LogUtils
import com.jess.arms.utils.Preconditions.checkNotNull
import com.paginate.Paginate
import com.tbruyelle.rxpermissions2.RxPermissions
import test.juyoufuli.com.myapplication.R
import test.juyoufuli.com.myapplication.app.utils.ImageLoaderUtils
import test.juyoufuli.com.myapplication.di.component.DaggerMainComponent
import test.juyoufuli.com.myapplication.di.module.MainModule
import test.juyoufuli.com.myapplication.mvp.entity.ArticleBean
import test.juyoufuli.com.myapplication.mvp.entity.BannerInfor
import test.juyoufuli.com.myapplication.mvp.entity.BannerResponse
import test.juyoufuli.com.myapplication.mvp.model.contract.MainContract
import test.juyoufuli.com.myapplication.mvp.presenter.MainPresenter
import test.juyoufuli.com.myapplication.mvp.ui.home.adapter.ArticleAdapter
import test.juyoufuli.com.myapplication.mvp.ui.home.adapter.ArticleItemHolder
import test.juyoufuli.com.myapplication.mvp.ui.webview.WebViewActivity
import java.util.*
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
    @JvmField
    @BindView(R.id.banner_guide_content)
    internal var bannerGuideContent: BGABanner? = null
    @JvmField
    @Inject
    internal var mLayoutManager: RecyclerView.LayoutManager? = null

    @JvmField
    @Inject
    internal var mAdapter: ArticleAdapter? = null

    @JvmField
    @Inject
    internal var mPermissions: RxPermissions? = null
    @JvmField
    @Inject
    internal var mUsers: MutableList<ArticleBean>? = null

    private var mPaginate: Paginate? = null
    private var isLoadingMore: Boolean = false

    override val fragment: Fragment
        get() = this
    override val rxPermissions: RxPermissions
        get() = mPermissions!!

    override fun setupFragmentComponent(appComponent: AppComponent) {
        DaggerMainComponent.builder().appComponent(appComponent).mainModule(MainModule(this)).build().inject(this)
    }

    override fun initView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.main_fragment, null)
    }

    override fun initData(savedInstanceState: Bundle?) {
        initRecyclerView()
        initPaginate()
        mPresenter!!.requestFromModel(true)
        mPresenter!!.requestBannerDataList()
    }

    private fun initBanner(mBannerList: List<BannerInfor>) {
        val imagePath = ArrayList<String>()
        val imageTitle = ArrayList<String>()
        for ((_, _, imagePath1, _, _, title) in mBannerList) {
            imagePath.add(imagePath1)
            imageTitle.add(title)
        }
        bannerGuideContent!!.setAdapter { bgaBanner: BGABanner, view: View, model: Any?, i: Int ->
            ImageLoaderUtils.loadImage(view as ImageView, model, context)

        }

        bannerGuideContent!!.setData(imagePath, imageTitle)
        bannerGuideContent!!.setDelegate { banner, itemView, model, position ->
            val intent = Intent(activity, WebViewActivity::class.java)
            intent.putExtra("link", mBannerList[position].url)
            intent.putExtra("title", mBannerList[position].title)
            launchActivity(intent)
        }
    }


    private fun initPaginate() {
        if (mPaginate == null) {
            val callbacks = object : Paginate.Callbacks {
                override fun onLoadMore() {
                    mPresenter!!.requestFromModel(false)
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
        //        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()){
        //            @Override
        //            public boolean canScrollVertically() {
        //                //解决ScrollView里存在多个RecyclerView时滑动卡顿的问题
        //                //如果你的RecyclerView是水平滑动的话可以重写canScrollHorizontally方法
        //                return false;
        //            }
        //        });


        mRecyclerView!!.adapter = mAdapter
        mAdapter!!.setOnItemClickListener { view, viewType, data, position ->
            //                     LogUtils.debugInfo(((Datas)data).getLink()+position);
            val intent = Intent(activity, WebViewActivity::class.java)
            intent.putExtra("link", (data as ArticleBean).link)
            intent.putExtra("title", data.title)
            launchActivity(intent)
        }
        mAdapter!!.setChildClickListener(object : ArticleItemHolder.ChildClickListener {
            override fun viewClick(viewid: Int, position: Int, data: ArticleBean) {
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

    override fun killMyself() {

    }

    override fun onRefresh() {
        mPresenter!!.requestFromModel(true)
    }


    override fun updateBanner(systemDataResponse: BannerResponse) {
        initBanner(systemDataResponse.data)

    }


}
