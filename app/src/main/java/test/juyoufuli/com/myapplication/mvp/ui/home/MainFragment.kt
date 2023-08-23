package test.juyoufuli.com.myapplication.mvp.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.recyclerview.widget.LinearLayoutManager
import com.airbnb.mvrx.fragmentViewModel
import com.blankj.utilcode.util.ActivityUtils
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
import com.scwang.smartrefresh.layout.listener.OnRefreshListener
import test.juyoufuli.com.myapplication.R
import test.juyoufuli.com.myapplication.app.BaseFragment
import test.juyoufuli.com.myapplication.app.utils.ArmsUtils
import test.juyoufuli.com.myapplication.app.utils.LogUtils
import test.juyoufuli.com.myapplication.databinding.FragmentMainBinding
import test.juyoufuli.com.myapplication.mvp.entity.ArticleBean
import test.juyoufuli.com.myapplication.mvp.ui.home.adapter2.DefaultItemHolder
import test.juyoufuli.com.myapplication.mvp.ui.home.adapter2.MainRecyclerViewAdapter
import test.juyoufuli.com.myapplication.mvp.ui.webview.WebViewActivity
import test.juyoufuli.com.myapplication.mvp.viewmodel.HomeDaggerState
import test.juyoufuli.com.myapplication.mvp.viewmodel.HomeDaggerViewModel

/**
 * Author : ludf
 * Created Time : 2018-09-27  15:50
 * Description:
 */
class MainFragment : BaseFragment<FragmentMainBinding>(), OnRefreshListener, OnLoadMoreListener {

    internal var articleBeans = arrayListOf<ArticleBean>()
    internal lateinit var mAdapter: MainRecyclerViewAdapter
    private var pager: Int = 1
    val viewModel: HomeDaggerViewModel by fragmentViewModel()

    override fun initView(savedInstanceState: Bundle?) {
        initRecyclerView()
    }


    override fun attachBinding(): FragmentMainBinding {
        return FragmentMainBinding.inflate(LayoutInflater.from(requireContext()))
    }

    private fun initRecyclerView() {
        binding.swipeRefreshLayout.setOnRefreshListener(this)
        binding.swipeRefreshLayout.setOnLoadMoreListener(this)
        ArmsUtils.configRecyclerView(binding.recyclerView, LinearLayoutManager(requireContext()))
        mAdapter = MainRecyclerViewAdapter(requireContext(), articleBeans)
        //我是分割线---------------------------------------
        binding.recyclerView.adapter = mAdapter

        mAdapter.setOnItemClickListener(object : DefaultItemHolder.OnItemClickListener {
            override fun onItemClick(position: Int) {
                if (position == 0) return
                val data = mAdapter.getItem(position - 1) as ArticleBean
                val intent = Intent(activity, WebViewActivity::class.java)
                intent.putExtra("link", data.link)
                intent.putExtra("title", data.title)
                ActivityUtils.startActivity(intent)
            }

        })

        mAdapter.setChildClickListener(object : DefaultItemHolder.OnViewClickListener {
            override fun onViewClick(viewid: Int, position: Int, data: ArticleBean) {
                if (viewid == R.id.iv_favorite_article) {
                    if (!data.collect) {
                        LogUtils.d(data.id.toString() + "--true--" + position)
                        viewModel.collectArticle(data.id.toString(), position)
                    } else {
                        LogUtils.d(data.id.toString() + "--false--" + position)
                        viewModel.cancelCollectArticle(data.id.toString(), position)
                    }
                    articleBeans[position].collect = !data.collect
                    mAdapter.notifyItemChanged(position)

                }
            }
        })

    }


    override fun onDestroyView() {
        super.onDestroyView()
        articleBeans.clear()
    }

    override fun invalidate() {
        viewModel.onEach(HomeDaggerState::isLoadingMore) {
            if (it) {
                binding.swipeRefreshLayout.finishLoadMore()
            } else {
                binding.swipeRefreshLayout.finishLoadMoreWithNoMoreData()
            }
        }
        viewModel.onEach(HomeDaggerState::pullToRefresh) {
            if (!it) {
                binding.swipeRefreshLayout.finishRefresh()
            }
        }
        viewModel.onEach(HomeDaggerState::pager) {
            pager = it
        }
        viewModel.onEach(HomeDaggerState::bannerList) {
            mAdapter.mBannerList = it
            mAdapter.notifyItemChanged(0)
        }
        viewModel.onEach(HomeDaggerState::artList) {
            LogUtils.d("当前条目----${it.size}")
            articleBeans.clear()
            mAdapter.mList = ArrayList(it)
            mAdapter.notifyDataSetChanged()
        }

    }


    override fun onRefresh(refreshLayout: RefreshLayout) {
        viewModel.mergeArtList()
    }

    override fun onLoadMore(refreshLayout: RefreshLayout) {
        viewModel.requestArtDataList(pager)
    }
}
