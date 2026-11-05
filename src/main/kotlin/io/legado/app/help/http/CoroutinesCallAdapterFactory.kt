// Restored from reader-pro-3.2.14.jar; see docs/PROVENANCE.md.
package io.legado.app.help.http

import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class CoroutinesCallAdapterFactory private constructor() : CallAdapter.Factory() {

    override fun get(
        returnType: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {
        if (Deferred::class.java != getRawType(returnType)) {
            return null
        }
        if (returnType !is ParameterizedType) {
            val message: Any = "Deferred return type must be parameterized as Deferred<Foo> or Deferred<out Foo>"
            throw IllegalStateException(message.toString())
        }
        val responseType = getParameterUpperBound(0, returnType)
        val rawDeferredType = getRawType(responseType)
        return if (rawDeferredType == Response::class.java) {
            if (responseType !is ParameterizedType) {
                val message: Any = "Response must be parameterized as Response<Foo> or Response<out Foo>"
                throw IllegalStateException(message.toString())
            }
            ResponseCallAdapter<Any>(
                getParameterUpperBound(
                    0,
                    responseType
                )
            )
        } else {
            BodyCallAdapter<Any>(responseType)
        }
    }

    companion object {
        fun create(): CoroutinesCallAdapterFactory {
            return CoroutinesCallAdapterFactory()
        }
    }

    private class BodyCallAdapter<T>(private val responseType: Type) :
        CallAdapter<T, Deferred<T>> {

        override fun responseType(): Type {
            return responseType
        }

        override fun adapt(call: Call<T>): Deferred<T> {
            val deferred = CompletableDeferred<T>()
            deferred.invokeOnCompletion {
                if (deferred.isCancelled) {
                    call.cancel()
                }
            }
            call.enqueue(object : Callback<T> {
                override fun onFailure(call: Call<T>, t: Throwable) {
                    deferred.completeExceptionally(t)
                }

                override fun onResponse(call: Call<T>, response: Response<T>) {
                    if (response.isSuccessful) {
                        val body = response.body()
                        checkNotNull(body)
                        deferred.complete(body)
                    } else {
                        deferred.completeExceptionally(HttpException(response))
                    }
                }
            })
            return deferred
        }
    }

    private class ResponseCallAdapter<T>(private val responseType: Type) :
        CallAdapter<T, Deferred<Response<T>>> {

        override fun responseType(): Type {
            return responseType
        }

        override fun adapt(call: Call<T>): Deferred<Response<T>> {
            val deferred = CompletableDeferred<Response<T>>()
            deferred.invokeOnCompletion {
                if (deferred.isCancelled) {
                    call.cancel()
                }
            }
            call.enqueue(object : Callback<T> {
                override fun onFailure(call: Call<T>, t: Throwable) {
                    deferred.completeExceptionally(t)
                }

                override fun onResponse(call: Call<T>, response: Response<T>) {
                    deferred.complete(response)
                }
            })
            return deferred
        }
    }
}
