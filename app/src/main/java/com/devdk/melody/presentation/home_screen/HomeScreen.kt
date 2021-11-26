package com.devdk.melody.presentation.home_screen

import android.hardware.camera2.params.BlackLevelPattern
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.util.toRange
import androidx.hilt.navigation.compose.hiltViewModel
import com.devdk.melody.R
import com.devdk.melody.domain.modal.Song
import com.devdk.melody.presentation.ui.theme.bgBlue
import com.devdk.melody.presentation.ui.theme.slider
import com.devdk.melody.presentation.ui.theme.textColor
import com.devdk.melody.presentation.ui.theme.thumb
import com.devdk.melody.presentation.util.SongCard
import com.devdk.melody.util.LongToMin
import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.ExperimentalTime
import kotlin.time.toDuration

@ExperimentalTime
@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun HomeScreen(
 homeScreenViewModel: HomeScreenViewModel = hiltViewModel()
) {
     val context = LocalContext.current
     val state = homeScreenViewModel.states.value

    LaunchedEffect(key1 = true){
       homeScreenViewModel.getSongs(context)
       homeScreenViewModel.currentPosition()
    }
    Box(modifier = Modifier
        .fillMaxSize()
        .background(bgBlue),
        contentAlignment = Alignment.Center
    ){
        Box(modifier = Modifier.fillMaxSize()){
            Column(modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
                .padding(20.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.background),
                    contentDescription = "Song",
                    modifier = Modifier
                        .size(150.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(15.dp))
                Text(
                    text = state.curPlayingSong?.title ?: "Select a Song..." ,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 25.sp,
                    textAlign = TextAlign.Center ,
                    maxLines = 1 ,
                    overflow = TextOverflow.Clip,
                    color = textColor
                )
                Spacer(modifier = Modifier.height(7.dp))
                Text(
                    text = state.curPlayingSong?.artist ?: "" ,
                    fontWeight = FontWeight.Thin,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center ,
                    maxLines = 1 ,
                    overflow = TextOverflow.Clip ,
                    fontStyle = FontStyle.Italic,
                    color = textColor
                )
                Spacer(modifier = Modifier.height(50.dp))
                Slider(
                    value = state.currentDuration,
                    onValueChange = { state.currentDuration = it},
                    colors = SliderDefaults.colors(
                        thumb,
                        activeTrackColor = slider,
                        inactiveTrackColor = Color.Black
                    ),
                )
                Text(
                    text = state.curPlayingSong?.duration?.let { LongToMin.longToMin(it) }.toString(),
                    color = textColor,
                    textAlign = TextAlign.End,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(20.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = { state.previousSong?.let { state.nextSong?.let { it1 ->
                        homeScreenViewModel.play(it, context)
                    } } }, Modifier.size(50.dp)) {
                        Image(
                            painter = painterResource(id = R.drawable.previous),
                            contentDescription = "previous",
                        )
                    }
                    Spacer(modifier = Modifier.width(25.dp))
                    IconButton(onClick = {
                                         if (state.isPlaying){
                                             homeScreenViewModel.pause()
                                         }
                                         else{
                                             homeScreenViewModel.resume()
                                         }
                                         }, Modifier.size(70.dp)) {
                        Image(
                            painter = painterResource(id = if(state.isPlaying)R.drawable.stop else R.drawable.play),
                            contentDescription = "play"
                        )
                    }
                    Spacer(modifier = Modifier.width(25.dp))
                    IconButton(onClick = { state.nextSong?.let { homeScreenViewModel.play(it, context) } }, Modifier.size(50.dp)) {
                        Image(
                            painter = painterResource(id = R.drawable.forward),
                            contentDescription = "forward",
                        )
                    }
                }
                Spacer(modifier = Modifier.height(40.dp))
                LazyVerticalGrid(cells = GridCells.Fixed(2) ){
                    items(state.songs){song ->
                        SongCard(song = song , homeScreenViewModel , context)
                    }
                }
            }

        }

    }

}