package test.juyoufuli.com.myapplication.mvp.ui.tab

import android.os.Bundle
import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import com.blankj.utilcode.util.LogUtils
import com.kingja.loadsir.core.LoadService
import com.paginate.Paginate
import test.juyoufuli.com.myapplication.R
import test.juyoufuli.com.myapplication.app.BaseFragment
import test.juyoufuli.com.myapplication.databinding.ViewPagerItemBinding
import test.juyoufuli.com.myapplication.mvp.entity.ArticleBean
import javax.inject.Inject

/**
 * Author : dongfang
 * Created Time : 2018-10-17  16:33
 * Description:
 */
class RecyclerViewFragment : BaseFragment<ViewPagerItemBinding>() {


    @JvmField
    @BindView(R.id.rlv_pager)
    internal var mRecyclerView: RecyclerView? = null

    @JvmField
    @Inject
    internal var mSystemData: List<ArticleBean>? = null

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

    override fun initData(savedInstanceState: Bundle?) {
        LogUtils.d("---initData---")
        if (isFrist) {
            isFrist = false
            cid = arguments?.getString("cid").toString()
            mRecyclerView!!.layoutManager = layoutManager
            initPaginate()
        }
    }

    override fun initView(savedInstanceState: Bundle?) {

    }

    override fun attachBinding(): ViewPagerItemBinding {
        return ViewPagerItemBinding.inflate(LayoutInflater.from(requireContext()))
    }

    override fun invalidate() {

    }

    private fun initPaginate() {
        if (mPaginate == null) {
            val callbacks = object : Paginate.Callbacks {
                override fun onLoadMore() {
//                    isLoadingMore = true
                    if (totalPage > page) {
//                        mPresenter!!.requestSystemDataList(page.toString(), cid)
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

//    override fun refreshData(response: ArticleResponse) {
//        mBaseLoadService!!.showSuccess()
//        if (response.data.pageCount == 0) {
//            mBaseLoadService!!.showCallback(EmptyCallback::class.java)
//        }
//        page = response.data.curPage
//    }
}
