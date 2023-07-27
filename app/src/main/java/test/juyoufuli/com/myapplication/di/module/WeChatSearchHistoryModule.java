package test.juyoufuli.com.myapplication.di.module;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jess.arms.di.scope.ActivityScope;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.ArrayList;
import java.util.List;

import dagger.Module;
import dagger.Provides;
import test.juyoufuli.com.myapplication.mvp.entity.ArticleBean;
import test.juyoufuli.com.myapplication.mvp.model.WeChatSearchViewModel;
import test.juyoufuli.com.myapplication.mvp.model.contract.WeChatSearchContract;
import test.juyoufuli.com.myapplication.mvp.ui.searchview.adapter.SearchAdapter;

/**
 * Author : dongfang
 * Created Time : 2018-10-0911:38
 * Description:
 */
@Module
public class WeChatSearchHistoryModule {
    private WeChatSearchContract.View view;

    /**
     * 将 View 的实现类传进来,这样就可以提供 View 的实现类给 Presenter
     *
     * @param view
     */
    public WeChatSearchHistoryModule(WeChatSearchContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    WeChatSearchContract.View provideSystemView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    WeChatSearchContract.Model provideSystemModel(WeChatSearchViewModel model) {
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
