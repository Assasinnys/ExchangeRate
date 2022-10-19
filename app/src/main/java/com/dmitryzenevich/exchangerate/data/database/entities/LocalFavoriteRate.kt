package com.dmitryzenevich.exchangerate.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites")
data class LocalFavoriteRate(
    @PrimaryKey
    val rateName: String,
    val rateValue: String
)