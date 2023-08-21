package test.juyoufuli.com.myapplication.mvp.ui.project.adapter

import android.content.Context
import test.juyoufuli.com.myapplication.R
import test.juyoufuli.com.myapplication.app.recyclerview.CommonAdapter
import test.juyoufuli.com.myapplication.app.recyclerview.base.ViewHolder
import test.juyoufuli.com.myapplication.mvp.entity.ProjectDatas
import javax.inject.Inject

/**
 * Author : dongfang
 * Created Time : 2018-11-01  09:37
 * Description:
 */
class ProjectDetailsAdapter @Inject constructor(
    context: Context, mList: ArrayList<ProjectDatas>
) : CommonAdapter<ProjectDatas>(context, R.layout.item_project_details, mList) {

    private var mChildClickListener: ProjectDetailsHolder.ChildClickListener? = null

    fun setChildClickListener(mChildClickListener: ProjectDetailsHolder.ChildClickListener) {
        this.mChildClickListener = mChildClickListener
    }

    override fun convert(holder: ViewHolder?, t: ProjectDatas?, position: Int) {
        TODO("Not yet implemented")
    }
}