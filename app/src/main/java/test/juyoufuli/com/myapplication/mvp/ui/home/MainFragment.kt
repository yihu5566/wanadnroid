package test.juyoufuli.com.myapplication.mvp.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.paginate.Paginate
import test.juyoufuli.com.myapplication.R
import test.juyoufuli.com.myapplication.app.BaseFragment
import test.juyoufuli.com.myapplication.app.utils.ArmsUtils
import test.juyoufuli.com.myapplication.app.utils.LogUtils
import test.juyoufuli.com.myapplication.databinding.FragmentMainBinding
import test.juyoufuli.com.myapplication.mvp.entity.ArticleBean
import test.juyoufuli.com.myapplication.mvp.entity.BannerInfor
import test.juyoufuli.com.myapplication.mvp.entity.BannerResponse
import test.juyoufuli.com.myapplication.mvp.ui.home.adapter2.DefaultItemHolder
import test.juyoufuli.com.myapplication.mvp.ui.home.adapter2.MainRecyclerViewAdapter
import test.juyoufuli.com.myapplication.mvp.ui.webview.WebViewActivity
import javax.inject.Inject

/**
 * Author : ludf
 * Created Time : 2018-09-27  15:50
 * Description:
 */
class MainFragment() : BaseFragment<FragmentMainBinding>(), SwipeRefreshLayout.OnRefreshListener {

    @JvmField
    @Inject
    internal var mLayoutManager: RecyclerView.LayoutManager? = null

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

    override fun initData(savedInstanceState: Bundle?) {
        initRecyclerView()
        initPaginate()
//        mPresenter?.requestBannerDataList()
//        mPresenter?.mergeArticle(true)

//        mPresenter?.requestTopArticle(true)
//        mPresenter?.requestFromModel(true)

    }

    override fun initView(savedInstanceState: Bundle?) {
        TODO("Not yet implemented")
    }

    override fun attachBinding(): FragmentMainBinding {
        return FragmentMainBinding.inflate(LayoutInflater.from(requireContext()))
    }


    private fun initPaginate() {
        if (mPaginate == null) {
            val callbacks = object : Paginate.Callbacks {
                override fun onLoadMore() {
                    LogUtils.d("加载更多。。。")
                    if (isFrist) {
                        isFrist = false
                    } else {
//                        mPresenter?.requestFromModel(false)
                    }

                }

                override fun isLoading(): Boolean {
                    return isLoadingMore
                }

                override fun hasLoadedAllItems(): Boolean {
                    return false
                }
            }

            mPaginate = Paginate.with(binding.recyclerView, callbacks)
                .setLoadingTriggerThreshold(0)
                .build()
            mPaginate?.setHasMoreDataToLoad(false)
        }
    }

    private fun initRecyclerView() {
        binding.swipeRefreshLayout.setOnRefreshListener(this)
        ArmsUtils.configRecyclerView(binding.recyclerView, mLayoutManager)
        //我是分割线---------------------------------------
        binding.recyclerView.adapter = mAdapter

        mAdapter?.setOnItemClickListener(object : DefaultItemHolder.OnItemClickListener {

            override fun onItemClick(position: Int) {
                if (position == 0) return
                val data = mUsers!![position - 1]
                val intent = Intent(activity, WebViewActivity::class.java)
                intent.putExtra("link", data.link)
                intent.putExtra("title", data.title)
            }

        })


        mAdapter?.setChildClickListener(object : DefaultItemHolder.OnViewClickListener {
            override fun onViewClick(viewid: Int, position: Int, data: ArticleBean) {
                if (viewid == R.id.iv_favorite_article) {
                    if (!data.collect) {
                        LogUtils.d(data.id.toString() + "--true--" + position)
//                        mPresenter?.collectArticle(data.id.toString())
                    } else {
                        LogUtils.d(data.id.toString() + "--false--" + position)
//                        mPresenter?.cancelCollectArticle(data.id.toString())
                    }
                    mUsers!![position].collect = !data.collect
                    mAdapter?.notifyItemChanged(position)

                }
            }
        })

    }



    /**
     * 下拉刷新的刷新
     */
    override fun onRefresh() {
//        mPresenter?.mergeArticle(true)
    }
//
//    override fun updateBanner(systemDataResponse: BannerResponse) {
//        com.blankj.utilcode.util.LogUtils.d("数据返返回--¥${systemDataResponse.data.size}")
//        mmBannerList = systemDataResponse.data
//        mAdapter?.mBannerList = mmBannerList
//        mAdapter?.notifyDataSetChanged()
//    }

    override fun onDestroyView() {
        super.onDestroyView()
        mUsers?.clear()
        mmBannerList?.clear()
    }

}
