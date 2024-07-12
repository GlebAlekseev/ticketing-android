package ru.alekseevjk.ticketing.feature.airline.impl.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.datetime.LocalDateTime
import ru.alekseevjk.ticketing.feature.airline.impl.data.room.model.TicketOfferDbModel

@Dao
interface TicketOfferDao {
    @Query("SELECT * FROM TicketOfferDbModel WHERE cached_at > :dateLimit")
    fun getTicketsOffersCachedInLastThreeDays(dateLimit: LocalDateTime): List<TicketOfferDbModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(ticketsOffers: List<TicketOfferDbModel>)
}