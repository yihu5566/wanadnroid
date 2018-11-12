package test.juyoufuli.com.myapplication.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.FragmentScope;

import dagger.Component;
import test.juyoufuli.com.myapplication.di.module.NavigationModule;
import test.juyoufuli.com.myapplication.di.module.ProjectModule;
import test.juyoufuli.com.myapplication.mvp.model.NavigationModel;
import test.juyoufuli.com.myapplication.mvp.ui.navigation.NavigationFragment;
import test.juyoufuli.com.myapplication.mvp.ui.project.ProjectFragment;

/**
 * Author : dongfang
 * Created Time : 2018-10-31  13:38
 * Description:
 */
@FragmentScope
@Component(modules = NavigationModule.class, dependencies = AppComponent.class)
public interface NavigationComponent {
    void inject(NavigationFragment fragment);
}
