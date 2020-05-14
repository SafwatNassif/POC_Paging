package com.example.pocpaging.di.componet

import android.content.Context
import com.example.pocpaging.POCApplication
import com.example.pocpaging.di.module.*
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [AndroidInjectionModule::class
        , AppModule::class
        , ActivityBuilder::class
        , NetworkModule::class
        , APIServiceModule::class
        , ViewModelModule::class
        , RepositoryModule::class
    ]
)
interface AppComponent {

    @Component.Builder
    interface AppBuilder {

        @BindsInstance
        fun application(pocApp: POCApplication): AppBuilder

        @BindsInstance
        fun context(context: Context): AppBuilder

        fun build(): AppComponent
    }


    fun inject(pocApp: POCApplication)
}