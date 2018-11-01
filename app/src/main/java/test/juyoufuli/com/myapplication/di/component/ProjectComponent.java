package test.juyoufuli.com.myapplication.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.FragmentScope;

import dagger.Component;
import dagger.Module;
import test.juyoufuli.com.myapplication.di.module.ProjectModule;
import test.juyoufuli.com.myapplication.mvp.ui.project.ProjectFragment;

/**
 * Author : dongfang
 * Created Time : 2018-10-31  13:38
 * Description:
 */
@FragmentScope
@Component(modules = ProjectModule.class, dependencies = AppComponent.class)
public interface ProjectComponent {
    void inject(ProjectFragment fragment);
}
