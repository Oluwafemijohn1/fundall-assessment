package com.example.fundallassessment.di

import android.content.Context
import android.util.Log
import com.example.fundallassessment.local.DataStoreRepository
import com.example.fundallassessment.local.DataStoreRepositoryImpl
import com.example.fundallassessment.remote.ApiService
import com.example.fundallassessment.utils.Constants.TOKEN
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideAppContext(@ApplicationContext app: Context): Context = app

    @Singleton
    @Provides
    fun provideUserDataStoreRepository(
        @ApplicationContext app: Context
    ): DataStoreRepository = DataStoreRepositoryImpl(app)

    @Provides
    @Singleton
    fun provideLogger(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Singleton
    @Provides
    fun providesAppScope(): CoroutineScope = CoroutineScope(SupervisorJob())

    /*Add authorization token to the header interceptor*/


    @Provides
    @Singleton
    fun provideHeaderInterceptor(dataStoreRepository: DataStoreRepository, coroutineScope: CoroutineScope): Interceptor {
        return Interceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")

            coroutineScope.launch {
                dataStoreRepository.getString(TOKEN)?.let {
                    Log.d("provideHeaderInterceptor", "provideHeaderInterceptor: $it")
                    request.addHeader("Authorization", "Bearer $it")
                }
            }
            chain.proceed(request.build())
        }
    }

    @Provides
    @Singleton
    fun provideClient(
        headerAuthorization: Interceptor,
        logger: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(30L, TimeUnit.SECONDS)
            .readTimeout(30L, TimeUnit.SECONDS)
            .writeTimeout(30L, TimeUnit.SECONDS)
            .addInterceptor(headerAuthorization)
            .addInterceptor(logger)
            .build()
    }

    @Provides
    @Singleton
    fun provideConverterFactory(): Converter.Factory {
        return MoshiConverterFactory.create()
    }

    @Provides
    @Singleton
    fun provideRetrofitService(
        client: OkHttpClient,
        converterFactory: Converter.Factory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://expense-api.fundall.io/api/v1/")
            .addConverterFactory(converterFactory)
            .client(client)
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

}