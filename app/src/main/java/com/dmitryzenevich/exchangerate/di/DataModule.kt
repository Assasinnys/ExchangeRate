package com.dmitryzenevich.exchangerate.di

import com.dmitryzenevich.exchangerate.data.network.interceptors.AuthInterceptor
import com.dmitryzenevich.exchangerate.data.network.repository.ExchangeRatesRepositoryImpl
import com.dmitryzenevich.exchangerate.domain.repository.ExchangeRatesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    @Singleton
    abstract fun bindAuthInterceptor(authInterceptor: AuthInterceptor): Interceptor

    @Binds
    @Singleton
    abstract fun bindRatesRepository(exchangeRatesRepositoryImpl: ExchangeRatesRepositoryImpl): ExchangeRatesRepository
}