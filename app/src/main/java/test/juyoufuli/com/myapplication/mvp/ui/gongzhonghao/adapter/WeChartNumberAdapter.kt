package test.juyoufuli.com.myapplication.mvp.ui.gongzhonghao.adapter

import android.view.View
import com.jess.arms.base.BaseHolder
import com.jess.arms.base.DefaultAdapter
import test.juyoufuli.com.myapplication.mvp.entity.WeChatNumberResponse
import javax.inject.Inject

/**
 * @Author : dongfang
 * @Created Time : 2019-03-14  11:32
 * @Description:
 */
class WeChartNumberAdapter @Inject constructor(arrayList: ArrayList<WeChatNumberResponse>) : DefaultAdapter<WeChatNumberResponse>(arrayList) {
    override fun getLayoutId(viewType: Int): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getHolder(v: View?, viewType: Int): BaseHolder<WeChatNumberResponse> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}