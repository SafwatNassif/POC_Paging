package com.example.pocpaging.di.helper

import android.app.Activity
import android.app.Application
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.example.pocpaging.POCApplication
import com.example.pocpaging.di.componet.AppComponent
import com.example.pocpaging.di.componet.DaggerAppComponent
import dagger.android.AndroidInjection
import dagger.android.support.AndroidSupportInjection

object AppInjector {

    lateinit var appComponent: AppComponent

    fun init(pocApplication: POCApplication) {
        appComponent = DaggerAppComponent.builder()
            .application(pocApplication)
            .context(pocApplication.baseContext)
            .build()

        appComponent.inject(pocApplication)

        pocApplication.registerActivityLifecycleCallbacks(object :
            Application.ActivityLifecycleCallbacks {
            override fun onActivityPaused(activity: Activity?) {
                //Pass
            }

            override fun onActivityResumed(activity: Activity?) {
                //Pass
            }

            override fun onActivityStarted(activity: Activity?) {
                //Pass
            }

            override fun onActivityDestroyed(activity: Activity?) {
                //Pass
            }

            override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {
                //Pass
            }

            override fun onActivityStopped(activity: Activity?) {
                //Pass
            }

            override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
                injectActivity(activity)
            }

        })
    }

    private fun injectActivity(activity: Activity?) {
        AndroidInjection.inject(activity)

        if (activity is FragmentActivity) {
            activity.supportFragmentManager.registerFragmentLifecycleCallbacks(
                object : FragmentManager.FragmentLifecycleCallbacks() {
                    override fun onFragmentCreated(
                        fm: FragmentManager
                        , fragment: Fragment
                        , savedInstanceState: Bundle?
                    ) {
                        if (fragment is Injectable) {
                            AndroidSupportInjection.inject(fragment)
                        }
                    }
                }, true
            )
        }

    }
}