package com.example.fundallassessment.di

import com.example.fundallassessment.remote.ApiService
import com.example.fundallassessment.remote.SignUpRepository
import com.example.fundallassessment.remote.SignUpRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Singleton
    @Provides
    fun provideSignUp(apiService: ApiService): SignUpRepository = SignUpRepositoryImpl(apiService)

}