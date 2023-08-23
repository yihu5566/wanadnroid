package test.juyoufuli.com.myapplication.mvp.ui.gongzhonghao

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.os.Message
import android.view.LayoutInflater
import android.view.View
import androidx.viewpager.widget.ViewPager
import com.blankj.utilcode.util.LogUtils
import net.lucode.hackware.magicindicator.ViewPagerHelper
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView
import test.juyoufuli.com.myapplication.app.BaseFragment
import test.juyoufuli.com.myapplication.databinding.FragmentWechatnumberBinding
import test.juyoufuli.com.myapplication.mvp.ui.tab.RecyclerViewFragment
import test.juyoufuli.com.myapplication.mvp.ui.tab.adapter.MyPagerAdapter

/**
 * @Author : dongfang
 * @Created Time : 2019-03-14  10:24
 * @Description:
 */
class WeChatNumberFragment : BaseFragment<FragmentWechatnumberBinding>(), View.OnClickListener {

    var tagNameList: ArrayList<String> = arrayListOf()

    var fragmentList: ArrayList<RecyclerViewFragment> = arrayListOf()

    /**
     * 默认的公众号id
     */
    lateinit var cid: String


//    override fun refreshData(response: WeChatNumberResponse) {
//        tagNameList!!.clear()
//        val children = response.data
//        var stringb: StringBuffer
//        for (i in 0 until children.size) {
//            stringb = StringBuffer()
//            stringb.append(children[i].id)
//            stringb.append("*")
//            stringb.append(children[i].name)
//
//            tagNameList!!.add(stringb.toString())
//        }
//
//        initDataList()
//
//    }

    override fun initView(savedInstanceState: Bundle?) {
        //获取公众号列表
//        mPresenter?.requestSystemDataList()

        binding.fabWechatNumber.setOnClickListener(this@WeChatNumberFragment)
    }

    override fun attachBinding(): FragmentWechatnumberBinding {
        return FragmentWechatnumberBinding.inflate(LayoutInflater.from(requireContext()))
    }

    override fun invalidate() {

    }


    override fun onClick(v: View?) {
//        val intent = Intent(activity, WeChatSearchHistoryActivity::class.java)
//        intent.putExtra("cid", cid)
//        launchActivity(intent)
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
                        binding.vpSystemDetailsContent.setCurrentItem(index)
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


        binding.rivSystemDetailsTop.setNavigator(commonNavigator)

        ViewPagerHelper.bind(binding.rivSystemDetailsTop, binding.vpSystemDetailsContent)

        binding.vpSystemDetailsContent.adapter = myPagerAdapter

        binding.vpSystemDetailsContent.addOnPageChangeListener(object :
            ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
//                toolbarTitle!!.setText(tagNameList!!.get(position).split("*").get(1))
                var get = fragmentList?.get(position)
                var value = Message()
                var bundle = Bundle()
                bundle.putString("cid", tagNameList!!.get(position).split("*").get(0))
                value.data = bundle
//                get.setData(value)
                cid = tagNameList!!.get(position).split("*").get(0)
                LogUtils.d("cid=" + cid)
            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })
    }
}