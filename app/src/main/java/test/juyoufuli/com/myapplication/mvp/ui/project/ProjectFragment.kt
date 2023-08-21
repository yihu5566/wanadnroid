package test.juyoufuli.com.myapplication.mvp.ui.project

import android.os.Bundle
import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import com.kingja.loadsir.core.LoadService
import com.paginate.Paginate
import test.juyoufuli.com.myapplication.app.BaseFragment
import test.juyoufuli.com.myapplication.databinding.ActivityProgectMainBinding
import test.juyoufuli.com.myapplication.mvp.entity.ProjectData
import test.juyoufuli.com.myapplication.mvp.entity.ProjectDatas
import test.juyoufuli.com.myapplication.mvp.entity.ProjectDetailsResponse
import test.juyoufuli.com.myapplication.mvp.entity.ProjectResponse
import test.juyoufuli.com.myapplication.mvp.presenter.ProjectPresenter
import test.juyoufuli.com.myapplication.mvp.ui.callback.EmptyCallback
import test.juyoufuli.com.myapplication.mvp.ui.project.adapter.ProjectAdapter
import test.juyoufuli.com.myapplication.mvp.ui.project.adapter.ProjectDetailsAdapter


/**
 * Author : dongfang
 * Created Time : 2018-10-31  09:27
 * Description:
 */
class ProjectFragment() : BaseFragment<ActivityProgectMainBinding>() {

    var mRecyclerView: RecyclerView? = null
    var rlvProjectContent: RecyclerView? = null
    var mList: ArrayList<ProjectData>? = null
    var mAdapter: ProjectAdapter? = null
    var projectDetailsAdapter: ProjectDetailsAdapter? = null
    var detailsList: ArrayList<ProjectDatas>? = null
    var totalPage: Int = 0
    var page: Int = 0
    var cid: String = "0"
    var mPaginate: Paginate? = null
    var isLoadingMore: Boolean = false
    var mPresenter: ProjectPresenter? = null


    override fun initData(savedInstanceState: Bundle?) {
//        initRecyclerView()
        initPaginate()
    }

    override fun initView(savedInstanceState: Bundle?) {
        mRecyclerView = binding?.rlvProject
        rlvProjectContent = binding?.rlvProjectContent
    }

    override fun attachBinding(): ActivityProgectMainBinding {
        return ActivityProgectMainBinding.inflate(LayoutInflater.from(requireContext()))
    }

    var loadService: LoadService<*>? = null

//    private fun initRecyclerView() {
//        mList = ArrayList()
//        mAdapter = ProjectAdapter(mList!!)
//        mRecyclerView?.layoutManager = LinearLayoutManager(activity)
////        mRecyclerView?.addItemDecoration(ProjectRecycerDecoration(context!!, ProjectRecycerDecoration.VERTICAL_LIST, R.drawable.item_left_decoration, 2))
//        mRecyclerView?.adapter = mAdapter
//        mPresenter?.getProject()
//        mAdapter?.setOnItemClickListener { view, viewType, data, position ->
//            for (item in mList!!)
//                item.isSelect = false
//
//            mList!![position].isSelect = true
//            mAdapter?.notifyDataSetChanged()
//            changeTab(data as ProjectData?)
//        }
//
//        //初始化详情
//        detailsList = ArrayList()
//        projectDetailsAdapter = ProjectDetailsAdapter(detailsList!!)
//        rlvProjectContent?.layoutManager = LinearLayoutManager(activity)
//        rlvProjectContent?.addItemDecoration(
//            ProjectRecycerDecoration(
//                context!!,
//                ProjectRecycerDecoration.VERTICAL_LIST,
//                R.drawable.item_decoration,
//                2
//            )
//        )
//        rlvProjectContent?.adapter = projectDetailsAdapter
//        projectDetailsAdapter?.setOnItemClickListener { view, viewType, data, position ->
//
//            val intent = Intent(activity, WebViewActivity::class.java)
//            val date = data as ProjectDatas
//            intent.putExtra("link", data.link)
//            intent.putExtra("title", data.title)
//            launchActivity(intent)
//        }
//
//
//        loadService = LoadSir.getDefault().register(rlvProjectContent) {
//
//        }
//
//
//        projectDetailsAdapter?.setChildClickListener(object :
//            ProjectDetailsHolder.ChildClickListener {
//            override fun viewClick(viewid: Int, position: Int, data: ProjectDatas) {
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
//            }
//        })
//    }


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
                    mPresenter?.getProjectDetails(page = page.toString(), cid = cid)
                }

                override fun isLoading(): Boolean {
                    return isLoadingMore
                }

                override fun hasLoadedAllItems(): Boolean {
                    return totalPage == page || totalPage == 0
                }
            }

            mPaginate = Paginate.with(rlvProjectContent, callbacks)
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