package ru.alekseevjk.ticketing

import android.app.Application
import android.content.Context
import com.jakewharton.threetenabp.AndroidThreeTen
import ru.alekseevjk.ticketing.core.di.DependenciesMap
import ru.alekseevjk.ticketing.core.di.HasDependencies
import ru.alekseevjk.ticketing.di.AppComponent
import ru.alekseevjk.ticketing.di.DaggerAppComponent
import javax.inject.Inject

class App : Application(), HasDependencies {
    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(this)
    }
    @Inject
    override lateinit var dependenciesMap: DependenciesMap

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this);
        appComponent.inject(this)
    }
}

val Context.appComponent: AppComponent
    get() = when (this) {
        is App -> appComponent
        else -> this.applicationContext.appComponent
    }