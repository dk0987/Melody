package com.devdk.melody.data.repository

import android.content.Context
import com.devdk.melody.data.local_data.SongData
import com.devdk.melody.domain.modal.Song
import com.devdk.melody.domain.repository.SongRepository

class SongRepositoryImpl(
    private val songData: SongData
) : SongRepository {
    override suspend fun getSongs(context: Context): List<Song> {
        return songData.getSongs(context)
    }
}