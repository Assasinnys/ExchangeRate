package com.dmitryzenevich.exchangerate.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dmitryzenevich.exchangerate.data.database.dao.FavoritesDao
import com.dmitryzenevich.exchangerate.data.database.dao.RatesDao
import com.dmitryzenevich.exchangerate.data.database.entities.LocalFavoriteRate
import com.dmitryzenevich.exchangerate.data.database.entities.LocalRate

@Database(entities = [LocalRate::class, LocalFavoriteRate::class], version = 1)
abstract class ExchangeRatesDatabase : RoomDatabase() {
    abstract fun getRatesDao(): RatesDao
    abstract fun getFavoritesDao(): FavoritesDao
}
