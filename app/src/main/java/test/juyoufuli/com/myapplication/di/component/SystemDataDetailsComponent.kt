package test.juyoufuli.com.myapplication.di.component

import android.support.v7.app.AppCompatActivity
import com.jess.arms.di.component.AppComponent
import com.jess.arms.di.scope.ActivityScope
import dagger.Component
import test.juyoufuli.com.myapplication.di.module.SystemDataDetailsModel
import test.juyoufuli.com.myapplication.mvp.ui.tab.SystemDataDetailsActivity

/**
 * Author : dongfang
 * Created Time : 2018-10-1213:40
 * Description:
 */

@ActivityScope
@Component(modules = arrayOf(SystemDataDetailsModel::class), dependencies = arrayOf(AppComponent::class))
interface SystemDataDetailsComponent {
    fun inject(activity: SystemDataDetailsActivity)

}