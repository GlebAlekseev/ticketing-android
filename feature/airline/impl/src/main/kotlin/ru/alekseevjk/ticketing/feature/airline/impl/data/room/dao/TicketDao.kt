package ru.alekseevjk.ticketing.feature.airline.impl.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.datetime.LocalDateTime
import ru.alekseevjk.ticketing.feature.airline.impl.data.room.model.TicketDbModel

@Dao
interface TicketDao {
    @Query("SELECT * FROM TicketDbModel WHERE cached_at > :dateLimit")
    fun getTicketsCachedInLastThreeDays(dateLimit: LocalDateTime): List<TicketDbModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(tickets: List<TicketDbModel>)
}