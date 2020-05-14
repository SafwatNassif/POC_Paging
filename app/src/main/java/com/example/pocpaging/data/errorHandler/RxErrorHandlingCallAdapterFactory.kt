package com.example.pocpaging.data.errorHandler

import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import java.io.IOException
import java.lang.reflect.Type

class RxErrorHandlingCallAdapterFactory : CallAdapter.Factory() {

    private val _original by lazy {
        RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io())
    }



    companion object {
        fun create() = RxErrorHandlingCallAdapterFactory()
    }


    override fun get(returnType: Type, annotations: Array<Annotation>, retrofit: Retrofit): CallAdapter<*, *>? {
        val wrapped = _original.get(returnType, annotations, retrofit) as CallAdapter<out Any, *>
        return RxCallAdapterWrapper(retrofit, wrapped)
    }

    private class RxCallAdapterWrapper<R>(
        val _retrofit: Retrofit,
        val _wrappedCallAdapter: CallAdapter<R, *>
    ) : CallAdapter<R, Any> {


        override fun responseType() = _wrappedCallAdapter.responseType()

        override fun adapt(call: Call<R>): Any {
            val adapted = (_wrappedCallAdapter.adapt(call) as Single<R>)
            return adapted.onErrorResumeNext { throwable: Throwable ->
                //need return here
                Single.error(asRetrofitException(throwable))
            }
        }

        private fun asRetrofitException(throwable: Throwable): RetrofitException {
            if (throwable is HttpException) {
                val response = throwable.response()
                return RetrofitException.httpError(
                    response!!.raw().request.url.toString(),
                    response,
                    _retrofit,
                    throwable
                )
            }

            if (throwable is IOException) {
                return RetrofitException.networkError(throwable)
            }

            return RetrofitException.unexpectedError(throwable)
        }


    }
}