package test.juyoufuli.com.myapplication.di.module;

import com.jess.arms.di.scope.FragmentScope;

import dagger.Module;
import dagger.Provides;
import test.juyoufuli.com.myapplication.mvp.model.NavigationModel;
import test.juyoufuli.com.myapplication.mvp.contract.NavigationContract;

/**
 * Author : dongfang
 * Created Time : 2018-10-31  13:41
 * Description:
 */
@Module
public class NavigationModule {
    private NavigationContract.View view;

    public NavigationModule(NavigationContract.View view) {
        this.view = view;
    }


    @FragmentScope
    @Provides
    NavigationContract.View provideSystemView() {
        return this.view;
    }

    @FragmentScope
    @Provides
    NavigationContract.Model provideSystemModel(NavigationModel model) {
        return model;
    }
}
