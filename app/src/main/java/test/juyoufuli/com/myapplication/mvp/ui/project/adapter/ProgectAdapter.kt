package test.juyoufuli.com.myapplication.mvp.ui.home.adapter

import android.view.View
import com.jess.arms.base.BaseHolder
import com.jess.arms.base.DefaultAdapter
import com.jess.arms.utils.LogUtils
import test.juyoufuli.com.myapplication.R
import test.juyoufuli.com.myapplication.mvp.entity.ProjectData
import test.juyoufuli.com.myapplication.mvp.ui.project.adapter.ProjectItemHolder
import javax.inject.Inject

/**
 * Author : ludf
 * Created Time : 2018-09-27  14:57
 * Description:
 */
class ProjectAdapter @Inject
constructor(infos: ArrayList<ProjectData>) : DefaultAdapter<ProjectData>(infos) {

    override fun getHolder(v: View, viewType: Int): BaseHolder<ProjectData> {
        LogUtils.debugInfo("ProjectAdapter")
        return ProjectItemHolder(v)
    }

    override fun getLayoutId(viewType: Int): Int {
        return R.layout.item_project_title
    }

}
