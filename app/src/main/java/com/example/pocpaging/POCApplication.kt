package com.example.pocpaging

import android.app.Activity
import android.app.Application
import com.example.pocpaging.di.helper.AppInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

class POCApplication : Application(), HasActivityInjector {


    @Inject
    lateinit var activityInjector: DispatchingAndroidInjector<Activity>

    override fun activityInjector() = activityInjector


    override fun onCreate() {
        super.onCreate()
        AppInjector.init(this)
    }

}