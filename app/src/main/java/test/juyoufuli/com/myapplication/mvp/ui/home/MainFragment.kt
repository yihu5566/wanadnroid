package test.juyoufuli.com.myapplication.mvp.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.airbnb.mvrx.fragmentViewModel
import com.airbnb.mvrx.withState
import com.paginate.Paginate
import test.juyoufuli.com.myapplication.R
import test.juyoufuli.com.myapplication.app.BaseFragment
import test.juyoufuli.com.myapplication.app.utils.ArmsUtils
import test.juyoufuli.com.myapplication.app.utils.LogUtils
import test.juyoufuli.com.myapplication.databinding.FragmentMainBinding
import test.juyoufuli.com.myapplication.mvp.entity.ArticleBean
import test.juyoufuli.com.myapplication.mvp.entity.BannerInfo
import test.juyoufuli.com.myapplication.mvp.ui.home.adapter2.DefaultItemHolder
import test.juyoufuli.com.myapplication.mvp.ui.home.adapter2.MainRecyclerViewAdapter
import test.juyoufuli.com.myapplication.mvp.ui.webview.WebViewActivity
import test.juyoufuli.com.myapplication.mvp.viewmodel.HomeDaggerViewModel

/**
 * Author : ludf
 * Created Time : 2018-09-27  15:50
 * Description:
 */
class MainFragment : BaseFragment<FragmentMainBinding>(), SwipeRefreshLayout.OnRefreshListener {

    internal var articleBeans = arrayListOf<ArticleBean>()

    internal lateinit var mAdapter: MainRecyclerViewAdapter

    private lateinit var mPaginate: Paginate
    private var isFrist: Boolean = true
    private var isLoadingMore: Boolean = false
    private var pager: Int = 1

    var mmBannerList: ArrayList<BannerInfo>? = null

    val viewModel: HomeDaggerViewModel by fragmentViewModel()

    override fun initData(savedInstanceState: Bundle?) {
        initRecyclerView()
        initPaginate()
    }

    override fun initView(savedInstanceState: Bundle?) {

    }


    override fun attachBinding(): FragmentMainBinding {
        return FragmentMainBinding.inflate(LayoutInflater.from(requireContext()))
    }

    private fun initPaginate() {
        val callbacks = object : Paginate.Callbacks {
            override fun onLoadMore() {
                LogUtils.d("加载更多。。。$isFrist")
                if (isFrist) {
                    isFrist = false
                } else {
                    viewModel.requestArtDataList(pager)
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
        mPaginate.setHasMoreDataToLoad(false)

    }

    private fun initRecyclerView() {
        binding.swipeRefreshLayout.setOnRefreshListener(this)
        ArmsUtils.configRecyclerView(binding.recyclerView, LinearLayoutManager(requireContext()))
        mAdapter = MainRecyclerViewAdapter(requireContext(), articleBeans)
        //我是分割线---------------------------------------
        binding.recyclerView.adapter = mAdapter

        mAdapter.setOnItemClickListener(object : DefaultItemHolder.OnItemClickListener {
            override fun onItemClick(position: Int) {
                if (position == 0) return
                val data = articleBeans[position - 1]
                val intent = Intent(activity, WebViewActivity::class.java)
                intent.putExtra("link", data.link)
                intent.putExtra("title", data.title)
            }

        })


        mAdapter.setChildClickListener(object : DefaultItemHolder.OnViewClickListener {
            override fun onViewClick(viewid: Int, position: Int, data: ArticleBean) {
                if (viewid == R.id.iv_favorite_article) {
                    if (!data.collect) {
                        LogUtils.d(data.id.toString() + "--true--" + position)
                        //                        mPresenter?.collectArticle(data.id.toString())
                    } else {
                        LogUtils.d(data.id.toString() + "--false--" + position)
                        //                        mPresenter?.cancelCollectArticle(data.id.toString())
                    }
                    articleBeans[position].collect = !data.collect
                    mAdapter.notifyItemChanged(position)

                }
            }
        })

    }


    /**
     * 下拉刷新的刷新
     */
    override fun onRefresh() {
        viewModel.requestTopDataList()
        viewModel.requestArtDataList(1)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        articleBeans.clear()
        mmBannerList?.clear()
    }

    override fun invalidate() = withState(viewModel) { state ->
        LogUtils.d("当前页数${state.pager}")
        articleBeans.clear()
        isLoadingMore = state.isLoadingMore
        pager = state.pager
        mAdapter.mBannerList = ArrayList(state.bannerList)
        articleBeans = ArrayList(state.artList)
        mAdapter.notifyDataSetChanged()
    }

}
