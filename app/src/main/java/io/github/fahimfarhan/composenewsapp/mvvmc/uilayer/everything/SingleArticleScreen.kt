package io.github.fahimfarhan.composenewsapp.mvvmc.uilayer.everything

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import io.github.fahimfarhan.composenewsapp.mvvmc.datalayer.models.Article
import io.github.fahimfarhan.composenewsapp.mvvmc.domainlayer.viewmodels.EverythingViewModel
import io.github.fahimfarhan.composenewsapp.mvvmc.uilayer.coordinator.NavigationItem
import io.github.fahimfarhan.composenewsapp.mvvmc.uilayer.coordinator.NewsAppNavGraph
import io.github.fahimfarhan.composenewsapp.mvvmc.utils.Extensions.sharedViewModel

class SingleArticleScreen(
  private val mNavController: NavHostController,
  private val mainModifier: Modifier
): NewsAppNavGraph {

  companion object {
    const val TAG = "SAS"
  }

  @Composable
  fun SingleArticleView(sharedViewModel: EverythingViewModel, articleIdx: Int) {
    Log.d(TAG, "sharedVm.instanceCount: ${EverythingViewModel.INSTANCE_COUNT}")
    val mArticle = sharedViewModel.getImmutableSnapShotArticleList().getOrNull(articleIdx)
    if (mArticle != null) {
      EverythingRow(article = mArticle)
    } else {
      Text(text = "Something went wrong!", Modifier.fillMaxSize())
    }
  }

  @Composable
  private fun EverythingRow(article: Article) {
    Row {
      Column {
        Text(text = "${article.title}")
        Text(text = "Author ${article.author}")
        AsyncImage(model = article.urlToImage, contentDescription = article.urlToImage)
        Text(text = "${article.content}")
        Text(text = "Source url: ${article.url}")
        Text(text = "Published at: ${article.publishedAt}")
        HorizontalDivider(thickness = 2.dp)
      }
    }
  }

  override fun createChildNavGraphBuilder(): NavGraphBuilder.() -> Unit {
    return {
      composable(
        route = "${NavigationItem.SingleArticleWithArgs.route}/{articleIdx}",
        arguments = listOf(navArgument("articleIdx") { type = NavType.IntType } )
      ) { backStackEntry: NavBackStackEntry ->
        val targetBackStackEntry: NavBackStackEntry = remember(key1 = mNavController.currentBackStackEntry) {
          mNavController.getBackStackEntry(NavigationItem.BasicEveryThing.route)
        }
        val sharedViewModel: EverythingViewModel = viewModel(targetBackStackEntry) // viewModel(mNavController.previousBackStackEntry!!) // backStackEntry.sharedViewModel<EverythingViewModel>(mNavController)

        val articleIdx: Int = backStackEntry.arguments?.getInt("articleIdx", -1)?:-1
        SingleArticleView(
          sharedViewModel,
          articleIdx
        )
      }
    }

  }
}