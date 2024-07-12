package ru.alekseevjk.ticketing.core.di

import dagger.MapKey
import java.lang.annotation.Inherited
import javax.inject.Qualifier
import kotlin.reflect.KClass


@Inherited
@Qualifier
annotation class ApplicationContext

@MapKey
annotation class DependenciesKey(val value: KClass<out Dependencies>)
