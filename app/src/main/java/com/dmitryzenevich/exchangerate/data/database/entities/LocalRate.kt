package com.dmitryzenevich.exchangerate.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Rates")
data class LocalRate(
    @PrimaryKey
    val rateName: String,
    val rateValue: String
)