package ru.alekseevjk.ticketing.di.module

import android.content.Context
import android.util.Log
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.alekseevjk.ticketing.api.Constants
import ru.alekseevjk.ticketing.core.di.ApplicationContext
import ru.alekseevjk.ticketing.core.network.NoConnectionInterceptor
import ru.alekseevjk.ticketing.di.qualifier.GoogleDriveQualifier
import ru.alekseevjk.ticketing.di.scope.AppComponentScope
import ru.alekseevjk.ticketing.feature.airline.impl.data.retrofit.GoogleDriveService
import java.util.concurrent.TimeUnit

@Module
interface NetworkModule {
    companion object {
        @Provides
        fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
            return HttpLoggingInterceptor {
                Log.d("Network", it)
            }
                .setLevel(HttpLoggingInterceptor.Level.BODY)
        }

        @Provides
        fun provideNoConnectionInterceptor(
            @ApplicationContext context: Context
        ): NoConnectionInterceptor {
            return NoConnectionInterceptor(context)
        }

        @AppComponentScope
        @Provides
        fun provideOkHttpClient(
            loggingInterceptor: HttpLoggingInterceptor,
            noConnectionInterceptor: NoConnectionInterceptor
        ): OkHttpClient {
            return OkHttpClient.Builder()
                .connectTimeout(Constants.CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(Constants.READ_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(Constants.WRITE_TIMEOUT, TimeUnit.SECONDS)
                .addNetworkInterceptor(loggingInterceptor)
                .addNetworkInterceptor(noConnectionInterceptor)
                .build()
        }


        @GoogleDriveQualifier
        @AppComponentScope
        @Provides
        fun provideGoogleDriveRetrofitBuilder(): Retrofit.Builder {
            return Retrofit.Builder()
                .baseUrl(Constants.GOOGLE_DRIVE_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
        }

        @AppComponentScope
        @Provides
        fun provideGoogleDriveService(
            @GoogleDriveQualifier retrofitBuilder: Retrofit.Builder,
            okHttpClient: OkHttpClient
        ): GoogleDriveService {
            return retrofitBuilder
                .client(okHttpClient)
                .build()
                .create(GoogleDriveService::class.java)
        }
    }
}
