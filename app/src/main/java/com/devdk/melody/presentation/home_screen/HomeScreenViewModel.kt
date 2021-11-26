package com.devdk.melody.presentation.home_screen

import android.content.Context
import android.os.Handler
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devdk.melody.domain.mediaPlayer.MediaPlayer
import com.devdk.melody.domain.modal.Song
import com.devdk.melody.domain.repository.SongRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.ExperimentalTime


@ExperimentalTime
@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val songRepository: SongRepository,
    private val mediaPlayer : MediaPlayer
):ViewModel() {
    private val _states = mutableStateOf(HomeScreenStates())
    val states : State<HomeScreenStates> = _states

    suspend fun getSongs(context: Context) {
        viewModelScope.launch {
            songRepository.getSongs(context).let { Song ->
                _states.value = states.value.copy(
                    songs = Song
                )
            }
        }
    }

    fun play(song : Song, context: Context){
        _states.value = states.value.copy(
            curPlayingSong = song,
            isPlaying = true,
            nextSong = if(states.value.songs.indexOf(song) == states.value.songs.size - 1) states.value.songs[0] else states.value.songs[states.value.songs.indexOf(song) + 1],
            previousSong = if(states.value.songs.indexOf(song) == 0) states.value.songs[states.value.songs.size - 1] else states.value.songs[states.value.songs.indexOf(song) - 1],
        )
        mediaPlayer.play(song , context)
    }

    fun resume(){
        mediaPlayer.resume()
        _states.value = states.value.copy(
            isPlaying = true
        )
    }

    fun pause() {
        mediaPlayer.pause()
        _states.value = states.value.copy(
            isPlaying = false
        )
    }

    fun currentPosition(){
            val handler = Handler()
            handler.postDelayed(object : Runnable {
                override fun run() {
                    _states.value = states.value.copy(
                        currentDuration = mediaPlayer.currentDuration()
                    )
                    handler.postDelayed(this, 50)
                }
            }, 50)
    }
}
