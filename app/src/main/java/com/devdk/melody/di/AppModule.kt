package com.devdk.melody.di

import com.devdk.melody.data.local_data.SongData
import com.devdk.melody.data.repository.SongRepositoryImpl
import com.devdk.melody.domain.mediaPlayer.MediaPlayer
import com.devdk.melody.domain.repository.SongRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.scopes.ServiceScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import kotlin.time.ExperimentalTime

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideSongData() : SongData{
        return SongData()
    }

    @Provides
    @Singleton
    fun provideSongRepository(songData: SongData) : SongRepository{
        return SongRepositoryImpl(songData)
    }

    @OptIn(ExperimentalTime::class)
    @Provides
    @Singleton
    fun provideMediaPlayer() : MediaPlayer{
        return MediaPlayer()
    }
}