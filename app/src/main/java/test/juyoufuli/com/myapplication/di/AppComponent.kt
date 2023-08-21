package test.juyoufuli.com.myapplication.di

import com.airbnb.mvrx.MavericksViewModel
import dagger.Component

@Component(modules = [AppModule::class])
interface AppComponent {
    fun viewModelFactories(): Map<Class<out MavericksViewModel<*>>, AssistedViewModelFactory<*, *>>
}
