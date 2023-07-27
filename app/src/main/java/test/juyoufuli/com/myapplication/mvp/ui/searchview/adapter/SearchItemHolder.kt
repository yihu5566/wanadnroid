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
package test.juyoufuli.com.myapplication.mvp.ui.searchview.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView

import com.jess.arms.base.BaseHolder

import test.juyoufuli.com.myapplication.R

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
class SearchItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val mName: TextView
    val mDesc: TextView
    val tvTime: TextView
    val root: RelativeLayout

    init {
        mName = itemView.findViewById(R.id.tv_chapterName)
        mDesc = itemView.findViewById(R.id.tv_desc)
        tvTime = itemView.findViewById(R.id.tv_time)
        root = itemView.findViewById(R.id.root)

    }

}
