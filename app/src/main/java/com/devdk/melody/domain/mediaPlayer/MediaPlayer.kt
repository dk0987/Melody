package com.devdk.melody.domain.mediaPlayer

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import androidx.compose.material.ExperimentalMaterialApi
import com.devdk.melody.domain.modal.Song
import dagger.hilt.processor.internal.definecomponent.codegen._dagger_hilt_android_components_ServiceComponent
import kotlin.time.ExperimentalTime
import kotlin.time.milliseconds

@ExperimentalTime
class MediaPlayer {

    private val mediaPlayer : MediaPlayer = MediaPlayer()

    @OptIn(ExperimentalMaterialApi::class,
        androidx.compose.foundation.ExperimentalFoundationApi::class
    )
    fun play(song : Song, context: Context ){
        mediaPlayer.setOnCompletionListener{
            MediaPlayer.OnCompletionListener{
                it.isLooping = true
            }.onCompletion(mediaPlayer)
        }
        mediaPlayer.reset()
        mediaPlayer.setDataSource(context, song.contentURI)
        mediaPlayer.prepare()
        mediaPlayer.start()
    }
    fun pause(){
        mediaPlayer.pause()
    }

    fun resume(){
        mediaPlayer.start()
    }

    fun currentDuration() : Float{
        return mediaPlayer.currentPosition.toFloat() / mediaPlayer.duration.toFloat()
    }


}