package com.devdk.melody.presentation.home_screen

import com.devdk.melody.domain.modal.Song

data class HomeScreenStates(
    val songs : List<Song> = emptyList(),
    var curPlayingSong : Song? = null,
    var isPlaying : Boolean = false,
    val nextSong : Song? = null,
    val previousSong : Song? = null,
    var currentDuration : Float = 0f
)
