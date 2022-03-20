package com.devgenius.exchanger.data.local.db.dbmodel

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

/**
 * Модель обектов для базы данных
 *
 * @param id id
 * @param currency Наименование валюты
 * @param value Цена валюты
 */
@Entity(tableName = "rates_table")
@Parcelize
data class RateDbModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    val currency: String,
    val value: Double
) : Parcelable