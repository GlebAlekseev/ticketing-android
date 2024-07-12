package ru.alekseevjk.ticketing.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.alekseevjk.ticketing.data.room.converter.StringListConverter
import ru.alekseevjk.ticketing.feature.airline.impl.data.room.converter.LocalDateTime2Converter
import ru.alekseevjk.ticketing.feature.airline.impl.data.room.converter.LocalDateTimeConverter
import ru.alekseevjk.ticketing.feature.airline.impl.data.room.dao.OfferDao
import ru.alekseevjk.ticketing.feature.airline.impl.data.room.dao.TicketDao
import ru.alekseevjk.ticketing.feature.airline.impl.data.room.dao.TicketOfferDao
import ru.alekseevjk.ticketing.feature.airline.impl.data.room.model.OfferDbModel
import ru.alekseevjk.ticketing.feature.airline.impl.data.room.model.TicketDbModel
import ru.alekseevjk.ticketing.feature.airline.impl.data.room.model.TicketOfferDbModel

@Database(
    entities = [
        OfferDbModel::class,
        TicketDbModel::class,
        TicketOfferDbModel::class,
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(
    StringListConverter::class,
    LocalDateTimeConverter::class,
    LocalDateTime2Converter::class
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun offerDao(): OfferDao
    abstract fun ticketDao(): TicketDao
    abstract fun ticketOfferDao(): TicketOfferDao

    companion object {
        const val DATABASE_NAME = "ticketing-database"
    }
}