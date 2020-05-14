package com.example.pocpaging.di.module

import com.example.pocpaging.data.remote.HomeService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class APIServiceModule {
    @Provides
    fun provideHomeService(retrofit: Retrofit) = retrofit.create(HomeService::class.java)

}