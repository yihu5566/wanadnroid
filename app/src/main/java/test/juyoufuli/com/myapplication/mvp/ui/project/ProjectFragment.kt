package test.juyoufuli.com.myapplication.mvp.ui.project

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.ActivityUtils
import com.kingja.loadsir.core.LoadService
import com.paginate.Paginate
import test.juyoufuli.com.myapplication.R
import test.juyoufuli.com.myapplication.app.BaseFragment
import test.juyoufuli.com.myapplication.app.recyclerview.MultiItemTypeAdapter
import test.juyoufuli.com.myapplication.databinding.ActivityProgectMainBinding
import test.juyoufuli.com.myapplication.mvp.entity.ProjectData
import test.juyoufuli.com.myapplication.mvp.entity.ProjectDatas
import test.juyoufuli.com.myapplication.mvp.ui.project.adapter.ProjectAdapter
import test.juyoufuli.com.myapplication.mvp.ui.project.adapter.ProjectDetailsAdapter
import test.juyoufuli.com.myapplication.mvp.ui.project.adapter.ProjectRecycerDecoration
import test.juyoufuli.com.myapplication.mvp.ui.webview.WebViewActivity


/**
 * Author : dongfang
 * Created Time : 2018-10-31  09:27
 * Description:
 */
class ProjectFragment : BaseFragment<ActivityProgectMainBinding>() {
    var mList: ArrayList<ProjectData> = arrayListOf()
    var mAdapter: ProjectAdapter? = null
    var projectDetailsAdapter: ProjectDetailsAdapter? = null
    var detailsList: ArrayList<ProjectDatas> = arrayListOf()
    var totalPage: Int = 0
    var page: Int = 0
    var cid: String = "0"
    var mPaginate: Paginate? = null
    var isLoadingMore: Boolean = false

    override fun initData(savedInstanceState: Bundle?) {
        initRecyclerView()
        initPaginate()
    }

    override fun initView(savedInstanceState: Bundle?) {

    }

    override fun attachBinding(): ActivityProgectMainBinding {
        return ActivityProgectMainBinding.inflate(LayoutInflater.from(requireContext()))
    }

    override fun invalidate() {

    }

    var loadService: LoadService<*>? = null

    private fun initRecyclerView() {

        mAdapter = ProjectAdapter(requireContext(), mList)
        binding.rlvProject.layoutManager = LinearLayoutManager(activity)
        binding.rlvProject.addItemDecoration(
            ProjectRecycerDecoration(
                requireContext(),
                ProjectRecycerDecoration.VERTICAL_LIST,
                R.drawable.item_left_decoration,
                2
            )
        )
        binding.rlvProject.adapter = mAdapter
//        mPresenter?.getProject()
        mAdapter?.setOnItemClickListener(object : MultiItemTypeAdapter.OnItemClickListener {
            override fun onItemClick(view: View?, holder: RecyclerView.ViewHolder?, position: Int) {
                for (item in mList) {
                    item.isSelect = false
                }
                mList[position].isSelect = true

                mAdapter?.notifyDataSetChanged()
//                changeTab(data as ProjectData?)
            }

            override fun onItemLongClick(
                view: View?,
                holder: RecyclerView.ViewHolder?,
                position: Int
            ): Boolean {
                return false
            }
        })

        //初始化详情
        detailsList = ArrayList()
        projectDetailsAdapter = ProjectDetailsAdapter(requireContext(), detailsList)
        binding.rlvProjectContent.layoutManager = LinearLayoutManager(activity)
        binding.rlvProjectContent.addItemDecoration(
            ProjectRecycerDecoration(
                requireContext(),
                ProjectRecycerDecoration.VERTICAL_LIST,
                R.drawable.item_decoration,
                2
            )
        )
        binding.rlvProjectContent.adapter = projectDetailsAdapter
        projectDetailsAdapter?.setOnItemClickListener(object :
            MultiItemTypeAdapter.OnItemClickListener {
            override fun onItemClick(view: View?, holder: RecyclerView.ViewHolder?, position: Int) {
                val intent = Intent(activity, WebViewActivity::class.java)
                val data = detailsList[position]
                intent.putExtra("link", data.link)
                intent.putExtra("title", data.title)
                ActivityUtils.startActivity(intent)
            }

            override fun onItemLongClick(
                view: View?,
                holder: RecyclerView.ViewHolder?,
                position: Int
            ): Boolean {
//                if (viewid == R.id.tv_project_details_collect) {
//                    if (!data.collect) {
//                        LogUtils.debugInfo(data.id.toString() + "--true--" + position)
//                        mPresenter?.collectArticle(data.id.toString())
//                    } else {
//                        LogUtils.debugInfo(data.id.toString() + "--false--" + position)
//                        mPresenter?.cancelCollectArticle(data.id.toString())
//
//                    }
//                    detailsList!![position].collect = !data.collect
////                    mAdapter?.notifyItemChanged(position)
//
//                }
                return false
            }
        })


    }


//    private fun changeTab(data: ProjectData?) {
////        ArmsUtils.makeText(activity, data?.id.toString())
//        cid = data?.id.toString()
//        page = 0
//        detailsList?.clear()
//        projectDetailsAdapter?.notifyDataSetChanged()
//        mPresenter?.getProjectDetails(page.toString(), cid)
//    }


    private fun initPaginate() {
        if (mPaginate == null) {
            val callbacks = object : Paginate.Callbacks {
                override fun onLoadMore() {
//                    mPresenter?.getProjectDetails(page = page.toString(), cid = cid)
                }

                override fun isLoading(): Boolean {
                    return isLoadingMore
                }

                override fun hasLoadedAllItems(): Boolean {
                    return totalPage == page || totalPage == 0
                }
            }

            mPaginate = Paginate.with(binding.rlvProject, callbacks)
                .setLoadingTriggerThreshold(0)
                .build()
            mPaginate?.setHasMoreDataToLoad(false)
        }
    }

//    override fun refreshAdapterList(response: ProjectResponse) {
//        cid = response.data[0].id.toString()
//        response.data[0].isSelect = true
//        mList?.addAll(response.data)
//        mAdapter?.notifyDataSetChanged()
//        mPresenter?.getProjectDetails(page = page.toString(), cid = cid)
//
//    }
//
//    override fun refreshDetailsAdapterList(response: ProjectDetailsResponse) {
//        loadService?.showSuccess()
//        totalPage = response.data.pageCount
//        if (totalPage == 0) {
//            loadService?.showCallback(EmptyCallback::class.java)
//            return
//        }
//        page = response.data.curPage + 1
//
//        if (response.data.curPage == 0) {
//            detailsList?.clear()
//        }
//        detailsList?.addAll(response.data.datas)
//        projectDetailsAdapter?.notifyDataSetChanged()
//        isLoadingMore = false
//    }
}