package com.devdk.melody.data.local_data

import android.content.Context
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.core.net.toUri
import com.devdk.melody.domain.modal.Song
import kotlinx.coroutines.*

class SongData{
     suspend fun getSongs(context: Context) : List<Song>{
        val songs = arrayListOf<Song>()
        withContext(Dispatchers.IO){
            val collection = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    MediaStore.Audio.Media.getContentUri(MediaStore.VOLUME_EXTERNAL)
                } else {
                    MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
                }
            val projection = arrayOf(
                MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.IS_MUSIC
            )
            context.contentResolver.query(
                collection ,
                projection ,
                null ,
                null ,
                MediaStore.Audio.Media.DATE_ADDED
            )?.use { cursor ->
                val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID)
                val durationColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION)
                val titleColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE)
                val artistColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST)
                val contentUriColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)

                while (cursor.moveToNext()) {
                    val id = cursor.getString(idColumn)
                    val duration = cursor.getLong(durationColumn)
                    val title = cursor.getString(titleColumn)
                    val artist = cursor.getString(artistColumn)
                    val contentUri: Uri = cursor.getString(contentUriColumn).toUri()
                    songs.add(Song(contentUri , id , null , artist , title , duration))
                }
            }
        }
        return songs
    }

}