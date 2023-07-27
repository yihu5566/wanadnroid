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
package test.juyoufuli.com.myapplication.di.module;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jess.arms.di.scope.FragmentScope;

import java.util.ArrayList;
import java.util.List;

import dagger.Module;
import dagger.Provides;
import test.juyoufuli.com.myapplication.mvp.contract.SystemDataDetailsItemContract;
import test.juyoufuli.com.myapplication.mvp.entity.ArticleBean;
import test.juyoufuli.com.myapplication.mvp.model.SystemDataDetailsItemModel;
import test.juyoufuli.com.myapplication.mvp.ui.searchview.adapter.SearchAdapter;

/**
 * ================================================
 * 展示 Module 的用法
 *
 * @see <a href="https://github.com/JessYanCoding/MVPArms/wiki#2.4.5">Module wiki 官方文档</a>
 * Created by JessYan on 09/04/2016 11:10
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
@Module
public class SystemDataItemModule {
    private SystemDataDetailsItemContract.View view;

    /**
     * 将 View 的实现类传进来,这样就可以提供 View 的实现类给 Presenter
     *
     * @param view
     */
    public SystemDataItemModule(SystemDataDetailsItemContract.View view) {
        this.view = view;
    }

    @FragmentScope
    @Provides
    SystemDataDetailsItemContract.View provideSystemView() {
        return this.view;
    }

    @FragmentScope
    @Provides
    SystemDataDetailsItemContract.Model provideSystemModel(SystemDataDetailsItemModel model) {
        return model;
    }

    @FragmentScope
    @Provides
    RecyclerView.LayoutManager provideLayoutManager() {
        return new LinearLayoutManager(view.getFragment().getContext());
    }

    @FragmentScope
    @Provides
    SearchAdapter provideSystemAdapter() {
        return new SearchAdapter(view.getFragment().getContext());
    }

    @FragmentScope
    @Provides
    List<ArticleBean> provideSystemNameList() {
        return new ArrayList<>();
    }
}
