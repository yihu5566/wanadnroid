package test.juyoufuli.com.myapplication.mvp.ui.navigation

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ExpandableListView
import butterknife.BindView
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.LogUtils
import com.kingja.loadsir.core.LoadService
import test.juyoufuli.com.myapplication.R
import test.juyoufuli.com.myapplication.app.BaseFragment
import test.juyoufuli.com.myapplication.databinding.FragmentNavigationBinding
import test.juyoufuli.com.myapplication.mvp.entity.Article
import test.juyoufuli.com.myapplication.mvp.ui.navigation.adapter.NavigationAdapter
import test.juyoufuli.com.myapplication.mvp.ui.webview.WebViewActivity


/**
 * Author : dongfang
 * Created Time : 2018-11-12  11:22
 * Description:
 */
class NavigationFragment : BaseFragment<FragmentNavigationBinding>(),
    ExpandableListView.OnChildClickListener {

    @JvmField
    @BindView(R.id.rlv_navigation)
    var mExpandableListView: ExpandableListView? = null
    var parentList = mutableListOf<String>()
    var childList = mutableListOf<List<Article>>()
    var mAdapter: NavigationAdapter? = null

    var isExpand: Boolean = false

    var mBaseLoadService: LoadService<*>? = null


    override fun initData(savedInstanceState: Bundle?) {
        initRecyclerView()
    }

    override fun initView(savedInstanceState: Bundle?) {
        TODO("Not yet implemented")
    }

    override fun attachBinding(): FragmentNavigationBinding {
        val rootView = FragmentNavigationBinding.inflate(LayoutInflater.from(requireContext()))
        return rootView
    }

    override fun invalidate() {

    }

    private fun initRecyclerView() {
        parentList = ArrayList()
        //初始化详情
        childList = ArrayList()
//        mPresenter!!.getNavigation()
        mAdapter = NavigationAdapter(
            requireContext(),
            parentList as ArrayList<String>,
            childList as ArrayList<List<Article>>
        )
        mExpandableListView!!.setOnChildClickListener(this)
        mExpandableListView!!.setAdapter(mAdapter)

    }

//    override fun refreshAdapterList(response: NavigationResponse) {
//        mBaseLoadService!!.showSuccess()
//        if (response.data.isEmpty()) {
//            mBaseLoadService!!.showCallback(EmptyCallback::class.java)
//            return
//        }
//        for (item in response.data) {
//            parentList.add(item.name)
//            childList.addAll(listOf(item.articles))
//        }
//        mAdapter!!.notifyDataSetChanged()
//    }

    override fun onChildClick(
        parent: ExpandableListView?,
        v: View?,
        groupPosition: Int,
        childPosition: Int,
        id: Long
    ): Boolean {
        LogUtils.d("---onChildClick---")
        val intent = Intent(activity, WebViewActivity::class.java)
        val bean = childList!![groupPosition][childPosition] as Article
        intent.putExtra("link", bean.link)
        intent.putExtra("title", bean.title)
        ActivityUtils.startActivity(intent)
        return true
    }
}