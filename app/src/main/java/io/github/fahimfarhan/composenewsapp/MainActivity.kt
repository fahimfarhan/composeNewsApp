package io.github.fahimfarhan.composenewsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import io.github.fahimfarhan.composenewsapp.mvvmc.uilayer.coordinator.AppCoordinator
import io.github.fahimfarhan.composenewsapp.mvvmc.uilayer.coordinator.NavigationItem
import io.github.fahimfarhan.composenewsapp.mvvmc.uilayer.home.Home
import io.github.fahimfarhan.composenewsapp.mvvmc.uilayer.sources.SourcesList
import io.github.fahimfarhan.composenewsapp.ui.theme.ComposeNewsAppTheme

class MainActivity : ComponentActivity() {

  companion object {
    const val TAG = "MainActivity"
  }

  private var _appCoordinator: AppCoordinator? = null
  private val appCoordinator get() = _appCoordinator!!
//  private val mHome = Home()
//  private val mSourceList = SourcesList()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()

//    val newsApiKey = BuildConfig.NEWS_API_KEY

    setContent {
      BaseView()
    }
  }

  @Composable
  private fun BaseView() {
    val mRememberNavHostController: NavHostController = rememberNavController()
    _appCoordinator = AppCoordinator(mRememberNavHostController)

    ComposeNewsAppTheme {
      Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        appCoordinator.NewsNavHost(modifier = Modifier.padding(innerPadding))
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