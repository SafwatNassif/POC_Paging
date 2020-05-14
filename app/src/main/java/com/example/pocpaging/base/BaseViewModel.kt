package com.example.pocpaging.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.blankj.utilcode.util.NetworkUtils
import com.example.pocpaging.R
import com.example.pocpaging.data.errorHandler.RetrofitException
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import java.io.IOException

abstract class BaseViewModel : ViewModel() {
     val compositeDisposable = CompositeDisposable()
    val internalState = MutableLiveData<ViewState>()
    val state: LiveData<ViewState> = internalState


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
                    internalState.postValue(ViewState.LOADING)
                }
            }
            .doOnTerminate {
                internalState.postValue(ViewState.LOADED)
            }
    }


    fun handelError(exception: Throwable?) {
        if (exception is RetrofitException) {
            when (exception.getKind()) {
                RetrofitException.Kind.NETWORK -> {
                    internalState.value = ViewState.Error(localError = R.string.no_internet)
                }
                RetrofitException.Kind.HTTP -> {
                    internalState.value = ViewState.Error(exception.message ?: "")
                }
                RetrofitException.Kind.UNEXPECTED -> {
                    internalState.value = ViewState.Error(exception.message ?: "")
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        if (compositeDisposable.isDisposed.not()) compositeDisposable.clear()
    }

}