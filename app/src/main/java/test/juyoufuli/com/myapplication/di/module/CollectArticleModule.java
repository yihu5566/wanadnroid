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

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.di.scope.FragmentScope;

import java.util.ArrayList;
import java.util.List;

import dagger.Module;
import dagger.Provides;
import test.juyoufuli.com.myapplication.mvp.entity.ArticleBean;
import test.juyoufuli.com.myapplication.mvp.entity.SystemBean;
import test.juyoufuli.com.myapplication.mvp.model.CollectArticleModel;
import test.juyoufuli.com.myapplication.mvp.model.HomeModel;
import test.juyoufuli.com.myapplication.mvp.model.contract.CollectArticleContract;
import test.juyoufuli.com.myapplication.mvp.model.contract.HomeContract;
import test.juyoufuli.com.myapplication.mvp.ui.account.adapter.ArticleCollectAdapter;
import test.juyoufuli.com.myapplication.mvp.ui.home.adapter.ArticleAdapter;
import test.juyoufuli.com.myapplication.mvp.ui.tab.adapter.SystemDataAdapter;

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
public class CollectArticleModule {
    private CollectArticleContract.View view;

    /**
     * 构建 UserModule 时,将 View 的实现类传进来,这样就可以提供 View 的实现类给 Presenter
     *
     * @param view
     */
    public CollectArticleModule(CollectArticleContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    CollectArticleContract.View provideUserView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    CollectArticleContract.Model provideUserModel(CollectArticleModel model) {
        return model;
    }


    @ActivityScope
    @Provides
    RecyclerView.LayoutManager provideLayoutManager() {
        return new LinearLayoutManager(view.getActivity());
    }


    @ActivityScope
    @Provides
    ArrayList<ArticleBean> provideSystemList() {
        return new ArrayList<>();
    }


    @ActivityScope
    @Provides
    ArticleCollectAdapter provideSystemAdapter(ArrayList<ArticleBean> list) {
        return new ArticleCollectAdapter(list);
    }

}
