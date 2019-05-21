package test.juyoufuli.com.myapplication.mvp.ui.tab

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Message
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import com.jess.arms.base.BaseActivity
import com.jess.arms.base.BaseFragment
import com.jess.arms.di.component.AppComponent
import com.jess.arms.utils.ArmsUtils
import com.tbruyelle.rxpermissions2.RxPermissions
import net.lucode.hackware.magicindicator.MagicIndicator
import net.lucode.hackware.magicindicator.ViewPagerHelper
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView
import test.juyoufuli.com.myapplication.R
import test.juyoufuli.com.myapplication.app.EventBusTags
import test.juyoufuli.com.myapplication.di.component.DaggerSystemDataDetailsComponent
import test.juyoufuli.com.myapplication.di.module.SystemDataDetailsModule
import test.juyoufuli.com.myapplication.mvp.model.contract.SystemDataDetailsContract
import test.juyoufuli.com.myapplication.mvp.presenter.SystemDataDetailsPresenter
import test.juyoufuli.com.myapplication.mvp.ui.tab.adapter.MyPagerAdapter
import javax.inject.Inject


/**
 * Author : dongfang
 * Created Time : 2018-10-1211:21
 * Description:
 */
class SystemDataDetailsActivity : BaseActivity<SystemDataDetailsPresenter>(), SystemDataDetailsContract.View, View.OnClickListener {

    var tagNameList: ArrayList<String>? = null
    var fragmentList: ArrayList<RecyclerViewFragment>? = null

    var toolbarBack: RelativeLayout? = null
    var toolbarTitle: TextView? = null
    var mViewPager: ViewPager? = null
    var magicIndicator: MagicIndicator? = null

    override fun setupActivityComponent(appComponent: AppComponent) {
        DaggerSystemDataDetailsComponent.builder().appComponent(appComponent).systemDataDetailsModule(SystemDataDetailsModule(this)).build().inject(this)
    }

    override fun initView(savedInstanceState: Bundle?): Int {
        return R.layout.system_data_details_activity
    }

    override fun initData(savedInstanceState: Bundle?) {
        toolbarTitle = findViewById(R.id.toolbar_title)
        toolbarBack = findViewById(R.id.toolbar_back)
        mViewPager = findViewById(R.id.vp_system_details_content)
        magicIndicator = findViewById(R.id.riv_system_details_top)

        toolbarBack!!.setVisibility(View.VISIBLE)
        toolbarBack!!.setOnClickListener(this)
        tagNameList = intent.getStringArrayListExtra("tagName")
        fragmentList = ArrayList<RecyclerViewFragment>()

        for (ddd in (tagNameList)!!) {
            var recyclerViewFragment = RecyclerViewFragment()
            val bundle = Bundle()
            bundle.putString("cid", ddd.split("*").get(0))

            recyclerViewFragment.arguments = bundle

            fragmentList!!.add(recyclerViewFragment)
        }

        initFirst()

        val supportFragmentManager = supportFragmentManager

        val myPagerAdapter = MyPagerAdapter(fragmentList!!, supportFragmentManager)
        val commonNavigator = CommonNavigator(this)

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
                toolbarTitle!!.setText(tagNameList!!.get(position).split("*").get(1))
                var get = fragmentList!!.get(position)
                var value = Message()
                var bundle = Bundle()
                bundle.putString("cid", tagNameList!!.get(position).split("*").get(0))
                value.data = bundle
                get.setData(value)

            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })
    }

    private fun initFirst() {
        toolbarTitle!!.setText(tagNameList!!.get(0).split("*").get(1))
//        var get = fragmentList!!.get(0)
//        var value = Message()
//        var bundle = Bundle()
//        bundle.putString("cid", tagNameList!!.get(0).split("*").get(0))
//        value.data = bundle
//        get.setData(value)
    }

    override fun onClick(p0: View?) {
        killMyself()
    }

    override val activity: Activity
        get() = this

    override fun showLoading() {

    }

    override fun launchActivity(intent: Intent) {
        checkNotNull(intent)
        ArmsUtils.startActivity(intent)
    }

    override fun hideLoading() {

    }

    override fun killMyself() {
        finish()
    }

    override fun showMessage(message: String) {

    }


}