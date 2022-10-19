package com.dmitryzenevich.exchangerate.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import androidx.room.Update
import com.dmitryzenevich.exchangerate.data.database.entities.LocalRate
import kotlinx.coroutines.flow.Flow

@Dao
interface RatesDao {

    @Query("SELECT * FROM Rates")
    fun getRates(): Flow<List<LocalRate>>

    @Insert(onConflict = REPLACE)
    suspend fun insertAll(rates: List<LocalRate>)

    @Delete
    suspend fun delete(rate: LocalRate)
}
