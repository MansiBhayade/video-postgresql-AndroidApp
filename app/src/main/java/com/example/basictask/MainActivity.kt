package com.example.basictask



import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import com.example.basictask.ui.theme.BasictaskTheme
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import androidx.compose.material3.ListItem
import kotlinx.serialization.Serializable


val supabase = createSupabaseClient(
    supabaseUrl = "https://swrzxmhocwsaaovbhcoh.supabase.co",
    supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InN3cnp4bWhvY3dzYWFvdmJoY29oIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MTQ5ODYyODksImV4cCI6MjAzMDU2MjI4OX0.giHJU4tK3fqrqMBr0mLHlx68LT6N4iGxXutZieg5pgY"
) {
    install(Postgrest)
}


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            BasictaskTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Videos()

                }
            }


        }
    }
}

@Serializable
data class Video (
    val id:Int,
    val Title: String,
    val channel_name: String,
    val Description: String,
    val likes: Int,
    val dislikes: Int

)



@Composable
fun Videos(){
    val videoList = remember { mutableListOf<Video>() }
//    val titleList = remember { mutableStateListOf<String>() }


    LaunchedEffect(Unit) {
        withContext(Dispatchers.IO){
                val results = supabase.from("Vediolist").select().decodeList<Video>()
                videoList.addAll(results)
                Log.d("Videos", "Video list: $videoList")
            for (video in videoList) {
                val title = video.Title
                val channelName = video.channel_name
                val description = video.Description
                val likes = video.likes
                val dislikes = video.dislikes

                Log.d("Video", "Title: $title")
                Log.d("Video", "Channel Name: $channelName")
                Log.d("Video", "Description: $description")
                Log.d("Video", "Likes: $likes")
                Log.d("Video", "Dislikes: $dislikes")
            }




        }
    }
    LazyColumn{
        items(videoList) {
             Video -> ListItem(headlineContent= { Text(text = Video.Title)})
             }
        }
}

/*
@Composable
fun DisplayTitles(titleList: List<String>) {
    LazyColumn {
        items(titleList) { item ->
            ListItem(text = item)
        }
    }
}



@Composable
fun ListItem(text: String){
    Text(
        text,
        color = Color.White,
        fontWeight = FontWeight.SemiBold
    )
}
*/







