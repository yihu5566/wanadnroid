package test.juyoufuli.com.myapplication.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;
import test.juyoufuli.com.myapplication.mvp.model.SystemDataDetailsModel;
import test.juyoufuli.com.myapplication.mvp.model.contract.SystemDataDetailsContract;

/**
 * Author : dongfang
 * Created Time : 2018-10-0911:38
 * Description:
 */
@Module
public class SystemDataDetailsModule {
    private SystemDataDetailsContract.View view;

    /**
     * 将 View 的实现类传进来,这样就可以提供 View 的实现类给 Presenter
     *
     * @param view
     */
    public SystemDataDetailsModule(SystemDataDetailsContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    SystemDataDetailsContract.View provideSystemView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    SystemDataDetailsContract.Model provideSystemModel(SystemDataDetailsModel model) {
        return model;
    }


}
