package test.juyoufuli.com.myapplication.mvp.ui.navigation

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ExpandableListView
import android.widget.TextView
import butterknife.BindView
import com.jess.arms.base.BaseFragment
import com.jess.arms.di.component.AppComponent
import com.jess.arms.utils.ArmsUtils
import com.jess.arms.utils.LogUtils
import test.juyoufuli.com.myapplication.R
import test.juyoufuli.com.myapplication.di.component.DaggerNavigationComponent
import test.juyoufuli.com.myapplication.di.module.NavigationModule
import test.juyoufuli.com.myapplication.mvp.entity.Article
import test.juyoufuli.com.myapplication.mvp.entity.NavigationResponse
import test.juyoufuli.com.myapplication.mvp.model.contract.NavigationContract
import test.juyoufuli.com.myapplication.mvp.presenter.NavigationPresenter
import test.juyoufuli.com.myapplication.mvp.ui.home.adapter.NavigationAdapter
import test.juyoufuli.com.myapplication.mvp.ui.webview.WebViewActivity

/**
 * Author : dongfang
 * Created Time : 2018-11-12  11:22
 * Description:
 */
class NavigationFragment : BaseFragment<NavigationPresenter>(), NavigationContract.View, ExpandableListView.OnChildClickListener {


    @JvmField
    @BindView(R.id.rlv_navigation)
    var mExpandableListView: ExpandableListView? = null
    var parentList: ArrayList<String>? = null
    var childList: ArrayList<List<Article>>? = null
    var mAdapter: NavigationAdapter? = null

    var isExpand: Boolean = false
    override fun setupFragmentComponent(appComponent: AppComponent) {
        DaggerNavigationComponent.builder().appComponent(appComponent).navigationModule(NavigationModule(this)).build().inject(this)
    }

    override fun initView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_navigation, null)
    }

    override fun initData(savedInstanceState: Bundle?) {
        initRecyclerView()
    }

    private fun initRecyclerView() {
        parentList = ArrayList()
        //初始化详情
        childList = ArrayList()
        mPresenter!!.getNavigation()
        mAdapter = NavigationAdapter(activity!!.baseContext, parentList!!, childList!!)
        mExpandableListView!!.setOnChildClickListener(this)
        mExpandableListView!!.setAdapter(mAdapter)

    }

    override fun refreshAdapterList(response: NavigationResponse) {
        for (item in response.data) {
            parentList!!.add(item.name)
            childList!!.addAll(listOf(item.articles))
        }
        mAdapter!!.notifyDataSetChanged()
    }

    override fun onChildClick(parent: ExpandableListView?, v: View?, groupPosition: Int, childPosition: Int, id: Long): Boolean {
        LogUtils.debugInfo("---onChildClick---")

        val intent = Intent(activity, WebViewActivity::class.java)
        val bean = childList!![groupPosition][childPosition] as Article
        intent.putExtra("link", bean.link)
        intent.putExtra("title", bean.title)
        launchActivity(intent)

        return true
    }

    override fun setData(data: Any?) {

    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)

    }


    override val fragment: Fragment
        get() = this

    override fun showLoading() {
    }

    override fun launchActivity(intent: Intent) {
        checkNotNull(intent)
        ArmsUtils.startActivity(intent)
    }

    override fun hideLoading() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun killMyself() {

    }

    override fun showMessage(message: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}