package test.juyoufuli.com.myapplication.mvp.ui.project.adapter

import android.view.View
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import com.jess.arms.base.BaseHolder
import com.jess.arms.di.component.AppComponent
import com.jess.arms.http.imageloader.ImageLoader
import com.jess.arms.http.imageloader.glide.ImageConfigImpl
import com.jess.arms.utils.ArmsUtils
import test.juyoufuli.com.myapplication.R
import test.juyoufuli.com.myapplication.mvp.entity.ProjectDatas
import javax.inject.Inject

/**
 * @Author : dongfang
 * @Created Time : 2019-02-28  09:53
 * @Description:
 */
class ProjectDetailsHolder @Inject constructor(view: View, private var mChildClickListener: ChildClickListener?) : BaseHolder<ProjectDatas>(view) {

    @JvmField
    @BindView(R.id.iv_project_details_icon)
    internal var iv_project_details_icon: ImageView? = null
    @JvmField
    @BindView(R.id.tv_project_details_title)
    internal var tv_project_details_title: TextView? = null
    @JvmField
    @BindView(R.id.tv_project_details_content)
    internal var tv_project_details_content: TextView? = null
    @JvmField
    @BindView(R.id.tv_project_details_time)
    internal var tv_project_details_time: TextView? = null
    @JvmField
    @BindView(R.id.tv_project_details_author)
    internal var tv_project_details_author: TextView? = null
    @JvmField
    @BindView(R.id.tv_project_details_collect)
    internal var tv_project_details_collect: CheckBox? = null
    var mAppComponent: AppComponent? = null
    var mImageLoader: ImageLoader? = null//用于加载图片的管理类,默认使用 Glide,使用策略模式,可替换框架

    interface ChildClickListener {
        fun viewClick(viewid: Int, position: Int, data: ProjectDatas)
    }

    init {
        //可以在任何可以拿到 Context 的地方,拿到 AppComponent,从而得到用 Dagger 管理的单例对象
        mAppComponent = ArmsUtils.obtainAppComponentFromContext(itemView.context)
        mImageLoader = mAppComponent!!.imageLoader()
    }

    override fun setData(data: ProjectDatas, position: Int) {
        val configImpl = ImageConfigImpl
                .builder()
                .url(data.envelopePic)
                .imageView(iv_project_details_icon)
                .build()
        mImageLoader!!.loadImage(mAppComponent!!.application(), configImpl)
        tv_project_details_title!!.text = data.title
        tv_project_details_content!!.text = data.desc
        tv_project_details_time!!.text = data.niceDate
        tv_project_details_author!!.text = data.author
        tv_project_details_collect!!.isChecked = data.collect

        tv_project_details_collect!!.setOnClickListener {
            mChildClickListener!!.viewClick(R.id.tv_project_details_collect, position, data)
        }

    }

}