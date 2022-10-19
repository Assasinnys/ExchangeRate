package com.dmitryzenevich.exchangerate.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.dmitryzenevich.exchangerate.data.database.ExchangeRatesDatabase
import com.dmitryzenevich.exchangerate.data.database.dao.FavoritesDao
import com.dmitryzenevich.exchangerate.data.database.dao.RatesDao
import com.dmitryzenevich.exchangerate.data.network.BASE_URL
import com.dmitryzenevich.exchangerate.data.network.CONNECTION_DELAY
import com.dmitryzenevich.exchangerate.data.network.ExchangeRateApi
import com.dmitryzenevich.exchangerate.data.network.interceptors.AuthInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(authInterceptor: AuthInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .callTimeout(CONNECTION_DELAY, TimeUnit.SECONDS)
            .connectTimeout(CONNECTION_DELAY, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideRemoteSource(okHttpClient: OkHttpClient): ExchangeRateApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(ExchangeRateApi::class.java)
    }

    @Provides
    @Singleton
    fun provideRoomDatabase(app: Application): ExchangeRatesDatabase {
        return Room
            .databaseBuilder(app, ExchangeRatesDatabase::class.java, "ExchangeRatesDatabase")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideRatesDao(db: ExchangeRatesDatabase): RatesDao = db.getRatesDao()

    @Provides
    @Singleton
    fun provideFavoritesDao(db: ExchangeRatesDatabase): FavoritesDao = db.getFavoritesDao()

    @Provides
    @Singleton
    fun provideSharedPreferences(app: Application): SharedPreferences {
        return app.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
    }
}
