package test.juyoufuli.com.myapplication.mvp.ui.tab.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.view.ViewGroup

/**
 * Author : dongfang
 * Created Time : 2018-10-1215:13
 * Description:
 */
class MyPagerAdapter
(private val mData: List<Fragment>, fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {
    override fun getItem(position: Int): Fragment {
        return mData.get(position)
    }


    override fun getCount(): Int {
        return mData.size
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {


    }
}