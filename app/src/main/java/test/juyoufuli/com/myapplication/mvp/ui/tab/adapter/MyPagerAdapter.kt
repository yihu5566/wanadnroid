package test.juyoufuli.com.myapplication.mvp.ui.tab.adapter

import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

/**
 * Author : dongfang
 * Created Time : 2018-10-1215:13
 * Description:
 */
class MyPagerAdapter
(private val mData: List<Fragment>, fragmentManager: FragmentManager) : FragmentStatePagerAdapter(fragmentManager) {
    override fun getItem(position: Int): Fragment {
        return mData.get(position)
    }


    override fun getCount(): Int {
        return mData.size
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {


    }
}