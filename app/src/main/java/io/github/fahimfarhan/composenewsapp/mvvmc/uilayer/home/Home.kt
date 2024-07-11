package io.github.fahimfarhan.composenewsapp.mvvmc.uilayer.home

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import io.github.fahimfarhan.composenewsapp.mvvmc.uilayer.coordinator.NavigationItem
import io.github.fahimfarhan.composenewsapp.mvvmc.uilayer.coordinator.NewsAppNavGraph
import io.github.fahimfarhan.composenewsapp.ui.theme.ComposeNewsAppTheme

class Home(
  private val mNavController: NavHostController,
  private val mainModifier: Modifier
): NewsAppNavGraph {
  companion object {
    const val TAG = "HOME"
  }

  @Composable
  private fun SourceView() {
    Text(text = "Tap to load us news sources", textAlign = TextAlign.Center, modifier = Modifier
      .fillMaxWidth(1f)
      .padding(16.dp)
      .clickable {
        Log.d(TAG, "user taps on source!")
        mNavController.navigate(NavigationItem.SourcesList.route + "/us")
      })


  }

  @Composable
  private fun SourceUkView() {
    Text(text = "Tap to load uk news sources", textAlign = TextAlign.Center, modifier = Modifier
      .fillMaxWidth(1f)
      .padding(16.dp)
      .clickable {
        Log.d(TAG, "user taps on source gb!")
        mNavController.navigate(NavigationItem.SourcesList.route + "/gb")
      })
  }

  @Composable
  private fun TopHeadLinesView() {
    Text(text = "this is for top headlines", textAlign = TextAlign.Center, modifier = Modifier
      .fillMaxWidth(1f)
      .padding(16.dp)
      .clickable {
        Log.d(TAG, "user taps on top headlines!")
        mNavController.navigate(NavigationItem.TopHeadLines.route)
      })
  }

  @Composable
  private fun ArticlesListView() {
    Text(text = "this is for news list",textAlign = TextAlign.Center, modifier = Modifier
      .fillMaxWidth(1f)
      .padding(16.dp)
      .clickable {
        Log.d(TAG, "user taps articles list view!")
      })
  }



  @Composable
  fun HomeView(modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {
      // src
      Row(horizontalArrangement = Arrangement.Center) {
        SourceView()
      }

      Row(horizontalArrangement = Arrangement.Center) {
        SourceUkView()
      }

      // headlines
      Row(horizontalArrangement = Arrangement.Center) {
        TopHeadLinesView()
      }

      // articles
      Row(horizontalArrangement = Arrangement.Center) {
        ArticlesListView()
      }
    }
  }


  @Composable
  @Preview
  fun HomePreview() {
    ComposeNewsAppTheme {
      HomeView()
    }
  }

  override fun createChildNavGraphBuilder(): NavGraphBuilder.() -> Unit {
    val output: NavGraphBuilder.() -> Unit = { composable(NavigationItem.Home.route) { HomeView(modifier=mainModifier) } }
    return output
  }
}