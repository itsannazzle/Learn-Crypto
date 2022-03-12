package com.nextint.learncrypto.app.core.di.module


import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.nextint.learncrypto.app.BuildConfig
import com.nextint.learncrypto.app.core.source.remote.service.*
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit
import javax.inject.Named

@Module
class NetworkModule
{

    @Provides
    fun provideGson() : Gson = GsonBuilder().create()

    @Provides
    @Named("HttpsClient")
    fun provideHttpClient() : OkHttpClient = okHttpClientFactory()

    @Provides
    @Named("NetworkService")
    fun provideRetrofit(@Named("HttpsClient") okHttpClient: OkHttpClient) : Retrofit
    {
        return retrofitFactory(okHttpClient)
    }

    @Provides
    fun provideCoins(@Named("NetworkService")retrofit: Retrofit) : CoinsService
    {
        return retrofit.create(CoinsService::class.java)
    }

    @Provides
    fun provideCryptoExchange(@Named("NetworkService") retrofit: Retrofit) : CryptoExchangeService
    {
        return retrofit.create(CryptoExchangeService::class.java)
    }

    @Provides
    fun providePeople(@Named("NetworkService") retrofit: Retrofit) : PeopleService
    {
        return retrofit.create(PeopleService::class.java)
    }

    @Provides
    fun provideSearch(@Named("NetworkService") retrofit: Retrofit) : SearchService
    {
        return retrofit.create(SearchService::class.java)
    }

    @Provides
    fun provideTagService(@Named("NetworkService") retrofit: Retrofit) : TagsService
    {
        return retrofit.create(TagsService::class.java)
    }

    @Provides
    fun provideOverview(@Named("NetworkService") retrofit: Retrofit) : GlobalOverviewService
    {
        return retrofit.create(GlobalOverviewService::class.java)
    }

    @Provides
    fun provideMarket(@Named("NetworkService") retrofit : Retrofit) : MarketService
    {
        return retrofit.create(MarketService::class.java)
    }

    private fun okHttpClientFactory(): OkHttpClient
    {
        return OkHttpClient.Builder()
            .addInterceptor(applicationInterceptor())
            .addInterceptor(networkInterceptor())
            .addInterceptor(loggingInterceptor())
            //.certificatePinner(certifiedPinner())
            .readTimeout(25, TimeUnit.SECONDS)
            .connectTimeout(25, TimeUnit.SECONDS)
            .build()
    }

    private fun retrofitFactory(okHttpClient: OkHttpClient) : Retrofit
    {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .baseUrl(BuildConfig.BASE_URL)
            .build()
    }

    @Throws(IOException::class)
    private fun applicationInterceptor() : Interceptor
    {
        return Interceptor {
            chain ->
            val request = chain.request()
                .newBuilder()
                .addHeader("Content-Type","application/json")
                .build()
            return@Interceptor chain.proceed(request)
        }
    }

    @Throws(IOException::class)
    private fun networkInterceptor() : Interceptor
    {
        return Interceptor()
        {
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

    private fun loggingInterceptor() : HttpLoggingInterceptor
    {
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