package com.example.pocpaging.data.errorHandler

import com.google.gson.Gson
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.HttpException
import retrofit2.Response
import retrofit2.Retrofit
import java.io.IOException

class RetrofitException(
    private val _code: String?,
    private val _message: String?,
    private val _url: String?,
    private val _response: Response<*>?,
    private val _kind: Kind,
    private val _exception: Throwable?,
    private val _retrofit: Retrofit?
) : RuntimeException(_message, _exception) {

    companion object {

        fun httpError(
            url: String,
            response: Response<*>,
            retrofit: Retrofit,
            throwable: HttpException
        ): RetrofitException {
            val message: String =
                Gson().fromJson(throwable.response()!!.errorBody()?.string(), ErrorBody::class.java)
                    .statusMessage.toString()
            return RetrofitException(response.code().toString(), message, url, response, Kind.HTTP, null, retrofit)
        }

        fun networkError(exception: IOException): RetrofitException {
            return RetrofitException(null, exception.message, null, null, Kind.NETWORK, exception, null)
        }

        fun unexpectedError(unexpectedError: Throwable): RetrofitException {
            return RetrofitException(null, unexpectedError.message, null, null, Kind.UNEXPECTED, unexpectedError, null)

        }

    }


    fun getUrl() = _url
    fun getResponse() = _response
    fun getKind() = _kind
    fun getRetrofit() = _retrofit


    @Throws(IOException::class)
    fun <T> getErrorBodyAs(type: Class<T>): T? {
        if (_response == null || _response.errorBody() == null || _retrofit == null) {
            return null
        }
        val converter: Converter<ResponseBody, T> =
            _retrofit.responseBodyConverter(type, arrayOfNulls<Annotation>(0))
        return converter.convert(_response.errorBody()!!)
    }


    enum class Kind {
        HTTP,
        NETWORK,
        UNEXPECTED
    }
}