package test.juyoufuli.com.myapplication.mvp.ui.tab.adapter

import android.view.View

import com.jess.arms.base.BaseHolder
import com.jess.arms.base.DefaultAdapter

import test.juyoufuli.com.myapplication.R
import test.juyoufuli.com.myapplication.mvp.entity.SystemBean

/**
 * Author : ludf
 * Created Time : 2018-09-27  14:57
 * Description:
 */
class SystemDataAdapter(infos: List<SystemBean>) : DefaultAdapter<SystemBean>(infos) {

    override fun getHolder(v: View, viewType: Int): BaseHolder<SystemBean> {
        return SystemDataItemHolder(v)
    }

    override fun getLayoutId(viewType: Int): Int {
        return R.layout.article_system_item
    }
}
