package com.nextint.learncrypto.app.model.source.remote

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.CertificatePinner
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit
import javax.inject.Named

@Module
class NetworkModule(private val url : String) {

    @Provides
    fun provideGson() : Gson = GsonBuilder().create()

    @Provides
    @Named("HttpsClient")
    fun provideHttpClient() : OkHttpClient = okHttpClientFactory()


    private fun okHttpClientFactory(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(applicationInterceptor())
            .addInterceptor(networkInterceptor())
            .addInterceptor(loggingInterceptor())
            .certificatePinner(certifiedPinner())
            .readTimeout(25, TimeUnit.SECONDS)
            .connectTimeout(25, TimeUnit.SECONDS)
            .build()
    }

    private fun retrofitFactory(okHttpClient: OkHttpClient, baseUrl : String) : Retrofit{
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .baseUrl(baseUrl)
            .build()
    }

    @Throws(IOException::class)
    private fun applicationInterceptor() : Interceptor{
        return Interceptor {
            chain ->
            val request = chain.request()
                .newBuilder()
                .addHeader("Content-Type","appliction/json")
                .build()
            return@Interceptor chain.proceed(request)
        }
    }

    @Throws(IOException::class)
    private fun networkInterceptor() : Interceptor{
        return Interceptor {
            chain->
            val request = chain.request()
            val requestUrl = request
                .url
                .newBuilder()
                .addQueryParameter("api_key","")
                .build()

            val requestBuilder = request.newBuilder().url(requestUrl)
                .build()
            return@Interceptor chain.proceed(requestBuilder)
        }
    }

    private fun loggingInterceptor() : HttpLoggingInterceptor{
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        return logging

    }

    private fun certifiedPinner() : CertificatePinner{
        return CertificatePinner.Builder()
            .add("https://api.coinpaprika.com","sha256/FeqysaMUH1po2K+fnMwzI6OlNhulrDr3s/6z2h+mSYU=")
            .build()
    }
}