package test.juyoufuli.com.myapplication.di.module;

import com.jess.arms.di.scope.FragmentScope;

import dagger.Module;
import dagger.Provides;
import test.juyoufuli.com.myapplication.mvp.contract.ProjectContract;
import test.juyoufuli.com.myapplication.mvp.model.ProjectModel;

/**
 * Author : dongfang
 * Created Time : 2018-10-31  13:41
 * Description:
 */
@Module
public class ProjectModule {
    private ProjectContract.View view;

    public ProjectModule(ProjectContract.View view) {
        this.view = view;
    }


    @FragmentScope
    @Provides
    ProjectContract.View provideSystemView() {
        return this.view;
    }

    @FragmentScope
    @Provides
    ProjectContract.Model provideSystemModel(ProjectModel model) {
        return model;
    }
}
