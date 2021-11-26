package com.devdk.melody.domain.modal

import android.graphics.Bitmap
import android.net.Uri

data class Song(
    val contentURI : Uri,
    val songId : String ,
    val cover : Bitmap? ,
    val artist : String ,
    val title : String ,
    val duration : Long
)