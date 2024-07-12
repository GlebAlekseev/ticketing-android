package ru.alekseevjk.ticketing.di.module

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import ru.alekseevjk.ticketing.core.di.ApplicationContext
import ru.alekseevjk.ticketing.data.room.AppDatabase
import ru.alekseevjk.ticketing.di.scope.AppComponentScope
import ru.alekseevjk.ticketing.feature.airline.impl.data.room.dao.OfferDao
import ru.alekseevjk.ticketing.feature.airline.impl.data.room.dao.TicketDao
import ru.alekseevjk.ticketing.feature.airline.impl.data.room.dao.TicketOfferDao

@Module
interface RoomModule {
    companion object {
        @AppComponentScope
        @Provides
        fun provideAppDataBase(@ApplicationContext context: Context): AppDatabase {
            return Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                AppDatabase.DATABASE_NAME
            ).build()
        }

        @AppComponentScope
        @Provides
        fun provideOfferDao(appDatabase: AppDatabase): OfferDao {
            return appDatabase.offerDao()
        }

        @AppComponentScope
        @Provides
        fun provideTicketDao(appDatabase: AppDatabase): TicketDao {
            return appDatabase.ticketDao()
        }

        @AppComponentScope
        @Provides
        fun provideTicketOfferDao(appDatabase: AppDatabase): TicketOfferDao {
            return appDatabase.ticketOfferDao()
        }
    }
}