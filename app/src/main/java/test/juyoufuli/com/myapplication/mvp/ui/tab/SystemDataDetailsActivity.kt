package test.juyoufuli.com.myapplication.mvp.ui.tab

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import butterknife.BindView
import com.jess.arms.base.BaseActivity
import com.jess.arms.di.component.AppComponent
import com.jess.arms.utils.ArmsUtils
import com.tbruyelle.rxpermissions2.RxPermissions
import kotlinx.android.synthetic.main.system_data_details_activity.*
import net.lucode.hackware.magicindicator.ViewPagerHelper
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView
import test.juyoufuli.com.myapplication.R
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


    @Inject
    var mRxPermissions: RxPermissions? = null
//    @Inject
//    var mErrorHandler: RxErrorHandler? = null

    var tagNameList: ArrayList<String>? = null
    var fragmentList: ArrayList<Fragment>? = null

    var toolbarBack: RelativeLayout? = null
    var toolbarTitle: TextView? = null

    override fun setupActivityComponent(appComponent: AppComponent) {

    }

    override fun initView(savedInstanceState: Bundle?): Int {
        return R.layout.system_data_details_activity
    }

    override fun initData(savedInstanceState: Bundle?) {
        toolbarTitle = findViewById<TextView>(R.id.toolbar_title)
        toolbarBack = findViewById<RelativeLayout>(R.id.toolbar_back)

        toolbarBack!!.setVisibility(View.VISIBLE)
        toolbarBack!!.setOnClickListener(this)
        tagNameList = intent.getStringArrayListExtra("tagName")

        toolbarTitle!!.setText(tagNameList!!.get(0).split("*").get(1))
        fragmentList = ArrayList<Fragment>()

        for (ddd in (tagNameList)!!) {
            var recyclerViewFragment = RecyclerViewFragment()
            val bundle = Bundle()
            bundle.putString("cid", ddd.split("*").get(0))

            recyclerViewFragment.arguments = bundle

            fragmentList!!.add(recyclerViewFragment)
        }

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
                        vp_system_details_content.setCurrentItem(index)
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


        riv_system_details_top.setNavigator(commonNavigator)

        ViewPagerHelper.bind(riv_system_details_top, vp_system_details_content)

        vp_system_details_content.adapter = myPagerAdapter

        vp_system_details_content.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                toolbarTitle!!.setText(tagNameList!!.get(position).split("*").get(1))

            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })
    }

    override fun onClick(p0: View?) {
        killMyself()
    }

    override val activity: Activity
        get() = this
    override val rxPermissions: RxPermissions
        get() = this.mRxPermissions!!

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