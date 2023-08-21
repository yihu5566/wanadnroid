package test.juyoufuli.com.myapplication.mvp.ui.tab.adapter

import android.content.Context
import test.juyoufuli.com.myapplication.R
import test.juyoufuli.com.myapplication.app.recyclerview.CommonAdapter
import test.juyoufuli.com.myapplication.app.recyclerview.base.ViewHolder
import test.juyoufuli.com.myapplication.mvp.entity.SystemBean

/**
 * Author : ludf
 * Created Time : 2018-09-27  14:57
 * Description:
 */
class SystemDataAdapter(var context: Context, infos: List<SystemBean>) :
    CommonAdapter<SystemBean>(context, R.layout.article_system_item, infos) {

//    override fun getHolder(v: View, viewType: Int): BaseHolder<SystemBean> {
//        return SystemDataItemHolder(v)
//    }


    override fun convert(holder: ViewHolder?, t: SystemBean?, position: Int) {
        TODO("Not yet implemented")
    }
}
