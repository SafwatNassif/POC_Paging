package com.example.pocpaging.di.module

import android.content.Context
import com.example.pocpaging.data.local.SharedPrefUtils
import com.example.pocpaging.data.local.SharedPrefUtils.Companion.PREF_FILE
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by Safwat Nassif on 7/24/2019.
 */

@Module
class AppModule {

    @Singleton
    @Provides
    fun provideSharedPrefUtils(context: Context) =
        SharedPrefUtils(context.getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE))

    @Provides
    fun provideApplicationContext(context: Context) = context

}