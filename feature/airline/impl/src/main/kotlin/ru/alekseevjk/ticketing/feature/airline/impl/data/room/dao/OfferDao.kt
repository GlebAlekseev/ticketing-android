package ru.alekseevjk.ticketing.feature.airline.impl.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.datetime.LocalDateTime
import ru.alekseevjk.ticketing.feature.airline.impl.data.room.model.OfferDbModel

@Dao
interface OfferDao {
    @Query("SELECT * FROM OfferDbModel WHERE cached_at > :dateLimit")
    fun getOffersCachedInLastThreeDays(dateLimit: LocalDateTime): List<OfferDbModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(offers: List<OfferDbModel>)
}