package ru.alekseevjk.ticketing.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import ru.alekseevjk.ticketing.App
import ru.alekseevjk.ticketing.core.di.ApplicationContext
import ru.alekseevjk.ticketing.di.module.AirlineDependenciesModule
import ru.alekseevjk.ticketing.di.module.NetworkModule
import ru.alekseevjk.ticketing.di.module.RoomModule
import ru.alekseevjk.ticketing.di.scope.AppComponentScope
import ru.alekseevjk.ticketing.feature.airline.impl.AirlineDependencies
import ru.alekseevjk.ticketing.presentation.MainActivity
import ru.alekseevjk.ticketing.presentation.withMenu.WithMenuFragment

@AppComponentScope
@Component(
    modules = [
        RoomModule::class,
        NetworkModule::class,
        AirlineDependenciesModule::class
    ]
)
interface AppComponent : AirlineDependencies {
    fun inject(app: App)
    fun inject(app: WithMenuFragment)
    fun inject(app: MainActivity)

    @Component.Factory
    interface Factory {
        fun create(
            @[BindsInstance ApplicationContext] context: Context
        ): AppComponent
    }
}