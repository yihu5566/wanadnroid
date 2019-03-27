package test.juyoufuli.com.myapplication.mvp.ui.gongzhonghao

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Message
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import butterknife.BindView
import com.jess.arms.base.BaseFragment
import com.jess.arms.di.component.AppComponent
import com.jess.arms.utils.ArmsUtils
import com.jess.arms.utils.LogUtils
import com.jess.arms.utils.Preconditions
import net.lucode.hackware.magicindicator.MagicIndicator
import net.lucode.hackware.magicindicator.ViewPagerHelper
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView
import test.juyoufuli.com.myapplication.R
import test.juyoufuli.com.myapplication.di.component.DaggerWeChatNumberComponent
import test.juyoufuli.com.myapplication.di.module.WeChatNumberModule
import test.juyoufuli.com.myapplication.mvp.contract.WeChatNumberContract
import test.juyoufuli.com.myapplication.mvp.entity.WeChatNumberResponse
import test.juyoufuli.com.myapplication.mvp.presenter.WeChatNumberPresenter
import test.juyoufuli.com.myapplication.mvp.ui.tab.RecyclerViewFragment
import test.juyoufuli.com.myapplication.mvp.ui.tab.adapter.MyPagerAdapter
import javax.inject.Inject

/**
 * @Author : dongfang
 * @Created Time : 2019-03-14  10:24
 * @Description:
 */
class WeChatNumberFragment : BaseFragment<WeChatNumberPresenter>(), WeChatNumberContract.View, View.OnClickListener {


    @JvmField
    @Inject
    var tagNameList: ArrayList<String>? = null
    @JvmField
    @Inject
    var fragmentList: ArrayList<RecyclerViewFragment>? = null
    @JvmField
    @BindView(R.id.toolbar_title)
    var toolbarTitle: TextView? = null
    @JvmField
    @BindView(R.id.vp_system_details_content)
    var mViewPager: ViewPager? = null
    @JvmField
    @BindView(R.id.riv_system_details_top)
    var magicIndicator: MagicIndicator? = null
    @JvmField
    @BindView(R.id.fab_wechat_number)
    var mFloatingActionButton: FloatingActionButton? = null
    @JvmField
    @BindView(R.id.cdl_wechat)
    var mCoordinatorLayout: CoordinatorLayout? = null
    /**
     * 默认的公众号id
     */
    lateinit var cid: String

    override val fragment: Fragment
        get() = this

    override fun refreshData(response: WeChatNumberResponse) {
        tagNameList!!.clear()
        val children = response.data
        var stringb: StringBuffer
        for (i in 0 until children.size) {
            stringb = StringBuffer()
            stringb.append(children[i].id)
            stringb.append("*")
            stringb.append(children[i].name)

            tagNameList!!.add(stringb.toString())
        }

        initDataList()

    }

    override fun setupFragmentComponent(appComponent: AppComponent) {
        DaggerWeChatNumberComponent.builder().appComponent(appComponent).weChatNumberModule(WeChatNumberModule(this)).build().inject(this)
    }


    override fun showMessage(message: String) {
    }


    override fun initData(savedInstanceState: Bundle?) {
        //获取公众号列表
        mPresenter!!.requestSystemDataList()

        mFloatingActionButton!!.setOnClickListener(this@WeChatNumberFragment)
    }


    override fun onClick(v: View?) {
//        Snackbar.make(mCoordinatorLayout!!, "已删除一个会话", Snackbar.LENGTH_SHORT)
//                .setAction("撤销") { view ->
//                    Toast.makeText(activity, "撤销了删除", Toast.LENGTH_SHORT).show()
//
//                }.show()
        val intent = Intent(activity, WeChatSearchHistoryActivity::class.java)
        intent.putExtra("cid", cid)
        launchActivity(intent)
    }

    private fun initDataList() {
        for (ddd in (tagNameList)!!) {
            var recyclerViewFragment = RecyclerViewFragment()
            val bundle = Bundle()
            bundle.putString("cid", ddd.split("*").get(0))

            recyclerViewFragment.arguments = bundle

            fragmentList!!.add(recyclerViewFragment)
        }
        //初始化一下
        cid = tagNameList!!.get(0).split("*").get(0)


        val supportFragmentManager = getChildFragmentManager()

        val myPagerAdapter = MyPagerAdapter(fragmentList!!, supportFragmentManager)
        val commonNavigator = CommonNavigator(activity)

        commonNavigator.adapter = object : CommonNavigatorAdapter() {

            override fun getCount(): Int {
                return if (tagNameList == null) 0 else tagNameList!!.size
            }

            override fun getTitleView(context: Context, index: Int): IPagerTitleView {
                val colorTransitionPagerTitleView = ColorTransitionPagerTitleView(context)
                colorTransitionPagerTitleView.normalColor = Color.GRAY
                colorTransitionPagerTitleView.selectedColor = Color.BLACK
                colorTransitionPagerTitleView.setText(tagNameList!!.get(index).split("*").get(1))
                colorTransitionPagerTitleView.setOnClickListener(object : View.OnClickListener {
                    override fun onClick(view: View) {
                        mViewPager!!.setCurrentItem(index)
                    }
                })

                return colorTransitionPagerTitleView
            }

            override fun getIndicator(context: Context): IPagerIndicator {
                val indicator = LinePagerIndicator(context)
                indicator.mode = LinePagerIndicator.MODE_WRAP_CONTENT
                return indicator
            }
        }


        magicIndicator!!.setNavigator(commonNavigator)

        ViewPagerHelper.bind(magicIndicator, mViewPager)

        mViewPager!!.adapter = myPagerAdapter

        mViewPager!!.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
//                toolbarTitle!!.setText(tagNameList!!.get(position).split("*").get(1))
                var get = fragmentList!!.get(position)
                var value = Message()
                var bundle = Bundle()
                bundle.putString("cid", tagNameList!!.get(position).split("*").get(0))
                value.data = bundle
                get.setData(value)
                cid = tagNameList!!.get(position).split("*").get(0)
                LogUtils.debugInfo("cid=" + cid)
            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })

    }


    override fun setData(data: Any?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun initView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_wechatnumber, null)
    }

    override fun launchActivity(intent: Intent) {
        Preconditions.checkNotNull(intent)
        ArmsUtils.startActivity(intent)
    }
}