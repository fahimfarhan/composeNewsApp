package io.github.fahimfarhan.composenewsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import io.github.fahimfarhan.composenewsapp.mvvmc.uilayer.home.Home
import io.github.fahimfarhan.composenewsapp.mvvmc.uilayer.sources.SourcesList
import io.github.fahimfarhan.composenewsapp.ui.theme.ComposeNewsAppTheme

class MainActivity : ComponentActivity() {

  private val mHome = Home()
  private val mSourceList = SourcesList()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()

//    val newsApiKey = BuildConfig.NEWS_API_KEY

    setContent {
      ComposeNewsAppTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
          mSourceList.SourcesListView(innerPadding)
//          mHome.HomeView(innerPadding)
        }
      }
    }
  }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
  Text(
    text = "Hello $name!",
    modifier = modifier
  )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
  ComposeNewsAppTheme {
    Greeting("Android")
  }
}