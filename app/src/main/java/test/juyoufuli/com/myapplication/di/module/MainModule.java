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

import com.jess.arms.di.scope.FragmentScope;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.ArrayList;
import java.util.List;

import dagger.Module;
import dagger.Provides;
import test.juyoufuli.com.myapplication.mvp.entity.ArticleBean;
import test.juyoufuli.com.myapplication.mvp.entity.BannerInfor;
import test.juyoufuli.com.myapplication.mvp.model.MainModel;
import test.juyoufuli.com.myapplication.mvp.model.contract.MainContract;
import test.juyoufuli.com.myapplication.mvp.ui.home.adapter.ArticleAdapter;
import test.juyoufuli.com.myapplication.mvp.ui.home.adapter2.MainRecyclerViewAdapter;

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
public class MainModule {
    private MainContract.View view;

    /**
     * 构建 UserModule 时,将 View 的实现类传进来,这样就可以提供 View 的实现类给 Presenter
     *
     * @param view
     */
    public MainModule(MainContract.View view) {
        this.view = view;
    }

    @FragmentScope
    @Provides
    MainContract.View provideUserView() {
        return this.view;
    }

    @FragmentScope
    @Provides
    MainContract.Model provideUserModel(MainModel model) {
        return model;
    }

    @FragmentScope
    @Provides
    RxPermissions provideRxPermissions() {
        return new RxPermissions(view.getFragment().getActivity());
    }

    @FragmentScope
    @Provides
    RecyclerView.LayoutManager provideLayoutManager() {
        return new LinearLayoutManager(view.getFragment().getContext());
    }

    @FragmentScope
    @Provides
    ArrayList<ArticleBean> provideUserList() {
        return new ArrayList<>();
    }


    @FragmentScope
    @Provides
    MainRecyclerViewAdapter provideUserAdapter(ArrayList<ArticleBean> list) {
        return new MainRecyclerViewAdapter(view.getFragment().getContext(),list);
    }
}
