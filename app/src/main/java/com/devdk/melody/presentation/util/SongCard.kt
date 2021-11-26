package com.devdk.melody.presentation.util

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.devdk.melody.R
import com.devdk.melody.domain.modal.Song
import com.devdk.melody.presentation.home_screen.HomeScreenViewModel
import com.devdk.melody.presentation.ui.theme.textColor
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
@ExperimentalMaterialApi
@Composable
fun SongCard(
    song: Song,
    viewModel: HomeScreenViewModel,
    context : Context
) {
    Card(
        onClick = {
            viewModel.play(song , context) } ,
        modifier = Modifier
            .padding(10.dp)
            .fillMaxSize()
            .size(150.dp),
        shape = RoundedCornerShape(15.dp),
        elevation = 10.dp,
    ) {
        Box(modifier = Modifier.fillMaxSize()){
            Image(
                painter = painterResource(id = R.drawable.background),
                contentDescription = " ,",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            Text(
                text = song.title,
                Modifier
                    .background(
                    brush = Brush.verticalGradient(
                        listOf(
                            Color.Transparent ,
                            Color.Black
                        ),
                        startY = 50f
                    )
                ).align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .padding(5.dp),
                color = textColor,
                overflow = TextOverflow.Ellipsis,
                maxLines = 2
            )
        }
    }
}