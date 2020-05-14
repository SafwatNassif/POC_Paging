package com.example.pocpaging.di.module

import com.example.pocpaging.ui.MainRepository
import com.example.pocpaging.base.BaseRepository
import dagger.Binds
import dagger.Module

@Module
abstract class RepositoryModule {
    @Binds
    abstract fun bindMainRepository(mainRepository: MainRepository): BaseRepository

}