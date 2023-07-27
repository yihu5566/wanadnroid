/*
 * Copyright 2017 JessYan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package test.juyoufuli.com.myapplication.mvp.ui.home.adapter

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.ImageView
import android.widget.TextView

import com.jess.arms.base.BaseHolder
import com.jess.arms.base.DefaultAdapter
import com.jess.arms.di.component.AppComponent
import com.jess.arms.http.imageloader.ImageLoader
import com.jess.arms.utils.ArmsUtils

import butterknife.BindView
import io.reactivex.Observable
import test.juyoufuli.com.myapplication.R
import test.juyoufuli.com.myapplication.mvp.entity.ArticleBean

/**
 * ================================================
 * 展示 [BaseHolder] 的用法
 *
 *
 * Created by JessYan on 9/4/16 12:56
 * [Contact me](mailto:jess.yan.effort@gmail.com)
 * [Follow me](https://github.com/JessYanCoding)
 * ================================================
 */
class ArticleItemHolder : BaseHolder<ArticleBean> {
    @JvmField
    @BindView(R.id.tv_chapterName)
    internal var mName: TextView? = null
    @JvmField
    @BindView(R.id.tv_desc)
    internal var mDesc: TextView? = null
    @JvmField
    @BindView(R.id.tv_time)
    internal var tvTime: TextView? = null
    @JvmField
    @BindView(R.id.iv_favorite_article)
    internal var ivFavoriteArticle: CheckBox? = null
    private var mAppComponent: AppComponent? = null
    private var mImageLoader: ImageLoader? = null//用于加载图片的管理类,默认使用 Glide,使用策略模式,可替换框架
    private var mChildClickListener: ChildClickListener? = null

    constructor(itemView: View) : super(itemView) {
        //可以在任何可以拿到 Context 的地方,拿到 AppComponent,从而得到用 Dagger 管理的单例对象
        mAppComponent = ArmsUtils.obtainAppComponentFromContext(itemView.context)
        mImageLoader = mAppComponent!!.imageLoader()
    }

    constructor(itemView: View, mChildClickListener: ChildClickListener) : super(itemView) {
        //可以在任何可以拿到 Context 的地方,拿到 AppComponent,从而得到用 Dagger 管理的单例对象
        mAppComponent = ArmsUtils.obtainAppComponentFromContext(itemView.context)
        mImageLoader = mAppComponent!!.imageLoader()
        this.mChildClickListener = mChildClickListener
    }

    @SuppressLint("CheckResult")
    override fun setData(data: ArticleBean, position: Int) {
        Observable.just(data.author)
                .subscribe { s -> mName!!.text = s }
        Observable.just(data.title)
                .subscribe { s -> mDesc!!.text = s }
        Observable.just(data.niceDate)
                .subscribe { s -> tvTime!!.text = s }
        ivFavoriteArticle!!.isChecked = data.collect

        ivFavoriteArticle!!.setOnClickListener {
            mChildClickListener!!.viewClick(R.id.iv_favorite_article, position, data)
        }
    }

    interface ChildClickListener {
        fun viewClick(viewid: Int, position: Int, data: ArticleBean)
    }


    /**
     * 在 Activity 的 onDestroy 中使用 [DefaultAdapter.releaseAllHolder] 方法 (super.onDestroy() 之前)
     * [BaseHolder.onRelease] 才会被调用, 可以在此方法中释放一些资源
     */
    override fun onRelease() {
        //只要传入的 Context 为 Activity, Glide 就会自己做好生命周期的管理, 其实在上面的代码中传入的 Context 就是 Activity
        //所以在 onRelease 方法中不做 clear 也是可以的, 但是在这里想展示一下 clear 的用法
        this.mDesc = null
        this.mName = null
        this.mAppComponent = null
        this.mImageLoader = null
    }
}
