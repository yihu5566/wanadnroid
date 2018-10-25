package test.juyoufuli.com.myapplication.mvp.ui.account

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import butterknife.BindView
import com.jess.arms.base.BaseActivity
import com.jess.arms.di.component.AppComponent
import com.jess.arms.utils.ArmsUtils
import com.jess.arms.utils.LogUtils
import com.jess.arms.utils.Preconditions
import com.paginate.Paginate
import test.juyoufuli.com.myapplication.R
import test.juyoufuli.com.myapplication.di.component.DaggerCollectArticleComponent
import test.juyoufuli.com.myapplication.di.module.CollectArticleModule
import test.juyoufuli.com.myapplication.mvp.entity.ArticleBean
import test.juyoufuli.com.myapplication.mvp.entity.ArticleResponse
import test.juyoufuli.com.myapplication.mvp.model.CollectArticleModel
import test.juyoufuli.com.myapplication.mvp.model.contract.CollectArticleContract
import test.juyoufuli.com.myapplication.mvp.presenter.CollectArticlePresenter
import test.juyoufuli.com.myapplication.mvp.ui.account.adapter.ArticleCollectAdapter
import test.juyoufuli.com.myapplication.mvp.ui.account.adapter.ArticleCollectItemHolder
import test.juyoufuli.com.myapplication.mvp.ui.home.adapter.ArticleAdapter
import test.juyoufuli.com.myapplication.mvp.ui.home.adapter.ArticleItemHolder
import test.juyoufuli.com.myapplication.mvp.ui.webview.WebViewActivity
import java.util.*
import javax.inject.Inject

/**
 * Author : dongfang
 * Created Time : 2018-10-24  14:51
 * Description: 收藏的文章列表
 */
class CollectArticleActivity : BaseActivity<CollectArticlePresenter>(), CollectArticleContract.View, View.OnClickListener {

    @JvmField
    @BindView(R.id.rlv_collect)
    var mRecyclerView: RecyclerView? = null

    @JvmField
    @BindView(R.id.toolbar_back)
    internal var toolbarBack: RelativeLayout? = null
    @JvmField
    @BindView(R.id.toolbar_title)
    internal var toolbar_title: TextView? = null

    @JvmField
    @Inject
    internal var mLayoutManager: RecyclerView.LayoutManager? = null
    @JvmField
    @Inject
    internal var mAdapter: ArticleCollectAdapter? = null
    @JvmField
    @Inject
    internal var articleList: ArrayList<ArticleBean>? = null

    private var page: Int = 0
    private var totalPage: Int = 0
    private var mPaginate: Paginate? = null
    private var isLoadingMore: Boolean = false
    override fun setupActivityComponent(appComponent: AppComponent) {
        DaggerCollectArticleComponent.builder().appComponent(appComponent).collectArticleModule(CollectArticleModule(this)).build().inject(this)
    }

    override fun initView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_collect
    }

    override fun initData(savedInstanceState: Bundle?) {
        initRecyclerView()
        initPaginate()
        toolbarBack!!.visibility = View.VISIBLE
        toolbarBack!!.setOnClickListener(this)
        mPresenter!!.requestCollectArticleList(page.toString())//打开 App 时自动加载列表
    }

    override fun onResume() {
        super.onResume()
        toolbar_title!!.text = "收藏列表"

    }


    private fun initRecyclerView() {
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
            val intent = Intent(this, WebViewActivity::class.java)
            intent.putExtra("link", (data as ArticleBean).link)
            intent.putExtra("title", data.title)
            launchActivity(intent)
        }
        mAdapter!!.setChildClickListener(object : ArticleCollectItemHolder.ChildClickListener {
            override fun viewClick(viewid: Int, position: Int, data: ArticleBean) {
                if (data.collect) {
                    LogUtils.debugInfo(data.id.toString() + "--true--" + position)
                } else {
                    mPresenter!!.cancelCollectArticle(data.id.toString(), data.originId.toString(), position)
                    LogUtils.debugInfo(data.id.toString() + "--false--" + position)
//                    articleList!![position].originId=-1


                }
            }
        })
        mRecyclerView!!.adapter = mAdapter
    }

    private fun initPaginate() {
        if (mPaginate == null) {
            val callbacks = object : Paginate.Callbacks {
                override fun onLoadMore() {
                    if (totalPage > page) {
                        isLoadingMore = true
                        mPresenter!!.requestCollectArticleList(page.toString())
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

    override fun getActivity(): Activity {
        return this
    }

    override fun getArticleCollectSucceed(response: ArticleResponse) {
        page = response.data.curPage

        articleList!!.addAll(response.data.datas)
        totalPage = response.data.pageCount
        mAdapter!!.notifyDataSetChanged()

        isLoadingMore = false
    }


    override fun cancelArticleCollectSucceed(position: Int) {
        articleList!!.removeAt(position)
        mAdapter!!.notifyItemRemoved(position)
    }

    override fun showLoading() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun launchActivity(intent: Intent) {
        Preconditions.checkNotNull(intent)
        ArmsUtils.startActivity(intent)
    }

    override fun hideLoading() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun killMyself() {
        finish()
    }

    override fun showMessage(message: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onClick(view: View?) {
        when (view!!.id) {
            R.id.toolbar_back -> {
                killMyself()

            }
        }
    }
}