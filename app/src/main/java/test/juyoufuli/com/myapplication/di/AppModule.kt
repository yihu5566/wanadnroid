package test.juyoufuli.com.myapplication.di

import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import test.juyoufuli.com.myapplication.mvp.ui.viewmodel.HomeViewModel

@Module
interface AppModule {

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    fun helloViewModelFactory(factory: HomeViewModel.Factory): AssistedViewModelFactory<*, *>
}
