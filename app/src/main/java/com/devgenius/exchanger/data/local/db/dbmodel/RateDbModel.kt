package com.devgenius.exchanger.data.local.db.dbmodel

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

/**
 * Модель обектов для базы данных
 *
 * @param currency Наименование валюты
 * @param value Цена валюты
 * @param convertedCurrency Валюта по отношению к которой конвертируем
 */
@Entity(tableName = "rates_table")
@Parcelize
data class RateDbModel(
    @PrimaryKey()
    val currency: String,
    val value: Double,
    val convertedCurrency: String
) : Parcelable