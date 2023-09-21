package test.juyoufuli.com.myapplication.app.ext

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import test.juyoufuli.com.myapplication.mvp.ui.gongzhonghao.WeChatNumberFragment
import test.juyoufuli.com.myapplication.mvp.ui.home.HomeFragment
import test.juyoufuli.com.myapplication.mvp.ui.navigation.NavigationFragment
import test.juyoufuli.com.myapplication.mvp.ui.project.ProjectFragment
import test.juyoufuli.com.myapplication.mvp.ui.system.SystemDataFragment

/**
 * @Author : dongfang
 * @Created Time : 2023-09-21  09:06
 * @Description:
 */
/**
 * 首页切换tab
 */
fun ViewPager2.initMain(fragment: Fragment): ViewPager2 {
    //是否可滑动
    this.isUserInputEnabled = false
    this.offscreenPageLimit = 5
    //设置适配器
    adapter = object : FragmentStateAdapter(fragment) {
        override fun createFragment(position: Int): Fragment {
            when (position) {
                0 -> {
                    return HomeFragment()
                }

                1 -> {
                    return SystemDataFragment()
                }

                2 -> {
                    return ProjectFragment()
                }

                3 -> {
                    return NavigationFragment()
                }

                4 -> {
                    return WeChatNumberFragment()
                }

                else -> {
                    return HomeFragment()
                }
            }
        }

        override fun getItemCount() = 5
    }
    return this
}

