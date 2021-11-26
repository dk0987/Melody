package com.devdk.melody.domain.repository

import android.content.Context
import com.devdk.melody.domain.modal.Song

interface SongRepository {
    suspend fun getSongs(context: Context) : List<Song>
}