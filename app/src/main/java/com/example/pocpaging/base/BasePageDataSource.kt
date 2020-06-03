package com.example.pocpaging.base

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.blankj.utilcode.util.NetworkUtils
import com.example.pocpaging.data.errorHandler.RetrofitException
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import java.io.IOException

abstract class BasePageDataSource<T>(private val state: MutableLiveData<ViewState>) :
    PageKeyedDataSource<Int, T>() {
    private val compositeDisposable = CompositeDisposable()

    fun <T> subscribe(
        request: Single<T>,
        success: Consumer<T>,
        error: Consumer<Throwable>,
        subscribeScheduler: Scheduler = Schedulers.io(),
        observeOnMainThread: Boolean = true
    ) {

        val observerScheduler =
            if (observeOnMainThread) AndroidSchedulers.mainThread()
            else subscribeScheduler

        compositeDisposable.add(
            request.subscribeOn(subscribeScheduler)
                .observeOn(observerScheduler)
                .compose {
                    composeSingle(it)
                }
                .subscribe(success, error)
        )

    }


    private fun <T> composeSingle(
        single: Single<T>
    ): Single<T> {
        return single
            .doOnSubscribe {
                if (!NetworkUtils.isConnected()) {
                    throw RetrofitException.networkError(IOException("no Internet connection"))
                } else {
                    state.postValue(ViewState.LOADING)
                }
            }
            .doOnTerminate {
                state.postValue(ViewState.LOADED)
            }
    }


    fun onCleared() {
        if (compositeDisposable.isDisposed.not()) compositeDisposable.dispose()
    }


}