package test.juyoufuli.com.myapplication.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;
import test.juyoufuli.com.myapplication.di.module.SystemDataDetailsModule;
import test.juyoufuli.com.myapplication.mvp.ui.tab.SystemDataDetailsActivity;

/**
 * Author : dongfang
 * Created Time : 2018-10-23  17:12
 * Description:
 */
@ActivityScope
@Component(modules = SystemDataDetailsModule.class, dependencies = AppComponent.class)
public interface SystemDataDetailsComponent {
    void inject(SystemDataDetailsActivity activity);
}
