package test.juyoufuli.com.myapplication.mvp.ui.project.adapter

import android.view.View
import com.jess.arms.base.BaseHolder
import com.jess.arms.base.DefaultAdapter
import test.juyoufuli.com.myapplication.R
import test.juyoufuli.com.myapplication.mvp.entity.ProjectDatas
import javax.inject.Inject

/**
 * Author : dongfang
 * Created Time : 2018-11-01  09:37
 * Description:
 */
class ProjectDetailsAdapter @Inject constructor(mList: ArrayList<ProjectDatas>) : DefaultAdapter<ProjectDatas>(mList) {


    private var mChildClickListener: ProjectDetailsHolder.ChildClickListener? = null

    fun setChildClickListener(mChildClickListener: ProjectDetailsHolder.ChildClickListener) {
        this.mChildClickListener = mChildClickListener
    }

    override fun getLayoutId(viewType: Int): Int {
        return R.layout.item_project_details
    }

    override fun getHolder(v: View, viewType: Int): BaseHolder<ProjectDatas> {
        return ProjectDetailsHolder(v, mChildClickListener)
    }


}