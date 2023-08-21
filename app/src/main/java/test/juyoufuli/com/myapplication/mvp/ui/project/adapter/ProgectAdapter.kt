package test.juyoufuli.com.myapplication.mvp.ui.project.adapter

import android.content.Context
import test.juyoufuli.com.myapplication.R
import test.juyoufuli.com.myapplication.app.recyclerview.CommonAdapter
import test.juyoufuli.com.myapplication.app.recyclerview.base.ViewHolder
import test.juyoufuli.com.myapplication.mvp.entity.ProjectData
import javax.inject.Inject

/**
 * Author : ludf
 * Created Time : 2018-09-27  14:57
 * Description:
 */
class ProjectAdapter @Inject
constructor(context: Context, infos: ArrayList<ProjectData>) : CommonAdapter<ProjectData>(
    context, R.layout.item_project_title, infos
) {

    override fun convert(holder: ViewHolder?, t: ProjectData?, position: Int) {
        TODO("Not yet implemented")
    }

}
