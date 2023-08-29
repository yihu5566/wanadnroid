package test.juyoufuli.com.myapplication.mvp.ui.system.adapter

import android.content.Context
import test.juyoufuli.com.myapplication.R
import test.juyoufuli.com.myapplication.app.recyclerview.CommonAdapter
import test.juyoufuli.com.myapplication.app.recyclerview.base.ViewHolder
import test.juyoufuli.com.myapplication.databinding.ArticleSystemItemBinding
import test.juyoufuli.com.myapplication.mvp.entity.SystemBean

/**
 * Author : ludf
 * Created Time : 2018-09-27  14:57
 * Description:
 */
class SystemDataAdapter(var context: Context, infos: List<SystemBean>) :
    CommonAdapter<SystemBean>(context, R.layout.article_system_item, infos) {


    override fun convert(holder: ViewHolder, t: SystemBean, position: Int) {
        val bind = ArticleSystemItemBinding.bind(holder.itemView)
        bind.tvChapterName.text = t.name
        var stringName = ""
        t.children.forEach {
            stringName += it.name + ","
        }
        bind.tvDesc.text = stringName
    }
}
