package com.nextint.learncrypto.app.core.source.remote.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.nextint.learncrypto.app.core.source.remote.*
import com.nextint.learncrypto.app.util.BASE_URL
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
class NetworkModule {

    @Provides
    fun provideGson() : Gson = GsonBuilder().create()

    @Provides
    @Named("HttpsClient")
    fun provideHttpClient() : OkHttpClient = okHttpClientFactory()

    @Provides
    @Named("NetworkService")
    fun provideRetrofit(@Named("HttpsClient") okHttpClient: OkHttpClient) : Retrofit{
        return retrofitFactory(okHttpClient)
    }

    @Provides
    fun provideCoins(@Named("NetworkService")retrofit: Retrofit) : Coins {
        return retrofit.create(Coins::class.java)
    }

    @Provides
    fun provideCryptoExchange(@Named("NetworkService") retrofit: Retrofit) : CryptoExchange {
        return retrofit.create(CryptoExchange::class.java)
    }

    @Provides
    fun providePeople(@Named("NetworkService") retrofit: Retrofit) : People {
        return retrofit.create(People::class.java)
    }

    @Provides
    fun provideSearch(@Named("NetworkService") retrofit: Retrofit) : Search{
        return retrofit.create(Search::class.java)
    }

    @Provides
    fun provideVocabulary(@Named("NetworkServices") retrofit: Retrofit) : Vocabulary{
        return retrofit.create(Vocabulary::class.java)
    }

    private fun okHttpClientFactory(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(applicationInterceptor())
            .addInterceptor(networkInterceptor())
            .addInterceptor(loggingInterceptor())
            //.certificatePinner(certifiedPinner())
            .readTimeout(25, TimeUnit.SECONDS)
            .connectTimeout(25, TimeUnit.SECONDS)
            .build()
    }

    private fun retrofitFactory(okHttpClient: OkHttpClient) : Retrofit{
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .build()
    }

    @Throws(IOException::class)
    private fun applicationInterceptor() : Interceptor {
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

    private fun loggingInterceptor() : HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        return logging

    }
//
//    private fun certifiedPinner() : CertificatePinner {
//        return CertificatePinner.Builder()
//            .add("https://api.coinpaprika.com","sha256/FeqysaMUH1po2K+fnMwzI6OlNhulrDr3s/6z2h+mSYU=")
//            .build()
//    }

}