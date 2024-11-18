package com.example.podcastplayer

import android.content.Context
import android.media.MediaPlayer
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.example.podcastplayer.ui.theme.PodcastPlayerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var backgroundColor by remember { mutableStateOf(Color.Transparent) }
            var selectedPlaylist by remember { mutableStateOf<PodcastPlaylist?>(null) }

            PodcastPlayerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = backgroundColor // Use transparent background
                ) {
                    playAudio(this, selectedPlaylist, { newColor ->
                        backgroundColor = newColor
                    }) { playlist ->
                        selectedPlaylist = playlist
                    }
                }
            }
        }
    }
}

data class PodcastPlaylist(
    val title: String,
    val items: List<PodcastItem>,
    val imageResId: Int,
    val backgroundColor: Color
)

data class PodcastItem(
    val title: String,
    val audioResId: Int
)

@Composable
fun playAudio(
    context: Context,
    selectedPlaylist: PodcastPlaylist?,
    onCardClick: (Color) -> Unit,
    onPlaylistSelect: (PodcastPlaylist) -> Unit
) {
    val playlists = listOf(
        PodcastPlaylist(
            title = "Alan Walker Playlist",
            items = listOf(
                PodcastItem("Track 1", R.raw.audio),
                PodcastItem("Track 2", R.raw.a1),
                PodcastItem("Track 3", R.raw.a2),
                PodcastItem("Track 4", R.raw.a3),
                PodcastItem("Track 5", R.raw.a4)
            ),
            imageResId = R.drawable.tamil,
            backgroundColor = Color(0xFF08364B)
        ),
        PodcastPlaylist(
            title = "Tamil Album",
            items = listOf(
                PodcastItem("Track 1", R.raw.e1),
                PodcastItem("Track 2", R.raw.e2),
                PodcastItem("Track 3", R.raw.e3),
                PodcastItem("Track 4", R.raw.e4),
                PodcastItem("Track 5", R.raw.audio_1)
            ),
            imageResId = R.drawable.tamil,
            backgroundColor = Color(0xFF89F561)
        ),
        PodcastPlaylist(
            title = "Hindi",
            items = listOf(
                PodcastItem("Song 1", R.raw.hindi),
                PodcastItem("Song 2", R.raw.h2),
                PodcastItem("Song 3", R.raw.h3),
                PodcastItem("Song 4", R.raw.h4),
                PodcastItem("Song 5", R.raw.h1)
            ),
            imageResId = R.drawable.hindi,
            backgroundColor = Color(0xFFFFA500)
        ),
        PodcastPlaylist(
            title = "Classical Instrumentals",
            items = listOf(
                PodcastItem("Instrumental 1", R.raw.audio_4),
                PodcastItem("Instrumental 2", R.raw.t2),
                PodcastItem("Instrumental 3", R.raw.t3),
                PodcastItem("Instrumental 4", R.raw.t4),
                PodcastItem("Instrumental 5", R.raw.t1)
            ),
            imageResId = R.drawable.englishapeech,
            backgroundColor = Color(0xFF6A5ACD)
        ),
        PodcastPlaylist(
            title = "Story",
            items = listOf(
                PodcastItem("Story 1", R.raw.s1),
                PodcastItem("Story 2", R.raw.s2),
                PodcastItem("Story 3", R.raw.s3),
                PodcastItem("Story 4", R.raw.s4),
                PodcastItem("Story 5", R.raw.audio_1)
            ),
            imageResId = R.drawable.img_5,
            backgroundColor = Color(0xFFD32F2F)
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black) // Transparent background
    ) {
        Text(
            text = "PODCAST",
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            textAlign = TextAlign.Center,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            fontSize = 36.sp,
            letterSpacing = 0.1.em
        )

        if (selectedPlaylist == null) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                playlists.forEach { playlist ->
                    PodcastCard(
                        context = context,
                        playlist = playlist,
                        onClick = { onPlaylistSelect(playlist) }
                    )
                }
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent, // Transparent gradient start
                                selectedPlaylist.backgroundColor // Gradient to the playlist's color
                            )
                        )
                    )
                    .padding(16.dp)
            ) {
                Text(
                    text = selectedPlaylist.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 28.sp,
                    color = Color.White,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                selectedPlaylist.items.forEachIndexed { index, item ->
                    PlayPauseRow(
                        context = context,
                        track = item,
                        trackIndex = index
                    )
                }
            }
        }
    }
}

@Composable
fun PodcastCard(context: Context, playlist: PodcastPlaylist, onClick: () -> Unit) {
    Card(
        elevation = 12.dp,
        border = BorderStroke(1.dp, Color.White),
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .height(250.dp)
            .clickable { onClick() }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent, // Transparent gradient start
                            playlist.backgroundColor.copy(alpha = 0.8f)
                        )
                    )
                )
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = playlist.imageResId),
                    contentDescription = playlist.title,
                    modifier = Modifier
                        .height(150.dp)
                        .width(200.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = playlist.title,
                    textAlign = TextAlign.Center,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
        }
    }
}

@Composable
fun PlayPauseRow(context: Context, track: PodcastItem, trackIndex: Int) {
    var isPlaying by remember { mutableStateOf(false) }
    var mediaPlayer by remember { mutableStateOf<MediaPlayer?>(null) }

    val handlePlayPause = {
        if (isPlaying) {
            mediaPlayer?.pause()
        } else {
            mediaPlayer?.release()
            mediaPlayer = MediaPlayer.create(context, track.audioResId).apply {
                start()
            }
        }
        isPlaying = !isPlaying
    }

    DisposableEffect(trackIndex) {
        onDispose {
            mediaPlayer?.release()
        }
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .background(Color.Transparent) // Transparent background for row
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = track.title,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.weight(1f).padding(end = 16.dp)
        )

        IconButton(
            onClick = handlePlayPause,
            modifier = Modifier.size(40.dp)
        ) {
            Icon(
                painter = painterResource(
                    id = if (isPlaying) R.drawable.pause else R.drawable.play
                ),
                contentDescription = if (isPlaying) "Pause" else "Play",
                tint = Color.White
            )
        }
    }
}