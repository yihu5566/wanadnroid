package test.juyoufuli.com.myapplication.mvp.ui.tab.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

/**
 * Author : dongfang
 * Created Time : 2018-10-1215:13
 * Description:
 */
class MyPager2Adapter
    (private val mData: List<Fragment>, activity: FragmentActivity) :
    FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return mData.size
    }

    override fun createFragment(position: Int): Fragment {
        return mData[position]
    }

}