package test.juyoufuli.com.myapplication.di.module;

import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.jess.arms.di.scope.ActivityScope;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.ArrayList;
import java.util.List;

import dagger.Module;
import dagger.Provides;
import test.juyoufuli.com.myapplication.mvp.entity.ArticleBean;
import test.juyoufuli.com.myapplication.mvp.model.SearchViewModel;
import test.juyoufuli.com.myapplication.mvp.model.contract.SearchContract;
import test.juyoufuli.com.myapplication.mvp.ui.searchview.adapter.SearchAdapter;

/**
 * Author : dongfang
 * Created Time : 2018-10-0911:38
 * Description:
 */
@Module
public class SearchModule {
    private SearchContract.View view;

    /**
     * 将 View 的实现类传进来,这样就可以提供 View 的实现类给 Presenter
     *
     * @param view
     */
    public SearchModule(SearchContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    SearchContract.View provideSystemView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    SearchContract.Model provideSystemModel(SearchViewModel model) {
        return model;
    }

    @ActivityScope
    @Provides
    RxPermissions provideRxPermissions() {
        return new RxPermissions((FragmentActivity) view.getActivity());
    }

    @ActivityScope
    @Provides
    RecyclerView.LayoutManager provideLayoutManager() {
        return new LinearLayoutManager(view.getActivity());
    }

    @ActivityScope
    @Provides
    List<ArticleBean> provideSystemList() {
        return new ArrayList<>();
    }

    @ActivityScope
    @Provides
    SearchAdapter provideSystemAdapter(){
        return new SearchAdapter(view.getActivity());
    }
}
