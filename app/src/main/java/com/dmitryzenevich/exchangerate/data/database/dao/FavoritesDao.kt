package com.dmitryzenevich.exchangerate.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import androidx.room.Transaction
import com.dmitryzenevich.exchangerate.data.database.entities.LocalFavoriteRate
import kotlinx.coroutines.flow.Flow
import retrofit2.http.DELETE

@Dao
interface FavoritesDao {

    @Query("SELECT * FROM favorites")
    suspend fun getFavoriteRates(): List<LocalFavoriteRate>

    @Query("SELECT * FROM favorites")
    fun getFavoriteRatesFlow(): Flow<List<LocalFavoriteRate>>

    @Query("SELECT * FROM favorites WHERE rateName = :name")
    fun getFavoriteRateByName(name: String): Flow<LocalFavoriteRate?>

    @Insert(onConflict = REPLACE)
    suspend fun insertFavoriteRate(rate: LocalFavoriteRate)

    @Insert(onConflict = REPLACE)
    suspend fun insertAllFavorites(rates: List<LocalFavoriteRate>)

    @Delete
    suspend fun deleteFavoriteRate(rate: LocalFavoriteRate)

    @Query("DELETE FROM favorites")
    suspend fun clearTable()

    @Transaction
    suspend fun refreshFavoriteRates(newFavorites: List<LocalFavoriteRate>) {
        clearTable()
        insertAllFavorites(newFavorites)
    }
}