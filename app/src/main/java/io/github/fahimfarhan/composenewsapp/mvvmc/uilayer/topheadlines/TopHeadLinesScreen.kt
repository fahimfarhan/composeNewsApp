package io.github.fahimfarhan.composenewsapp.mvvmc.uilayer.topheadlines

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import io.github.fahimfarhan.composenewsapp.mvvmc.datalayer.models.Article
import io.github.fahimfarhan.composenewsapp.mvvmc.domainlayer.viewmodels.TopHeadLinesViewModel
import io.github.fahimfarhan.composenewsapp.mvvmc.uilayer.coordinator.NavigationItem
import io.github.fahimfarhan.composenewsapp.mvvmc.uilayer.coordinator.NewsAppNavGraph
import io.github.fahimfarhan.composenewsapp.ui.theme.Pink40

class TopHeadLinesScreen(
  private val mNavController: NavHostController,
  private val mainModifier: Modifier
): NewsAppNavGraph {
  companion object {
    const val TAG = "SOURCES_LIST"
  }

  @Composable
  fun TopHeadLinesView() {
    val vm: TopHeadLinesViewModel = viewModel()
    val lazyPagingArticles: LazyPagingItems<Article> = vm.flowOfPagingDataArticle.collectAsLazyPagingItems()

    MainScreen(lazyPagingArticles = lazyPagingArticles, modifier = mainModifier)

  }

  @Composable
  private fun MainScreen(lazyPagingArticles: LazyPagingItems<Article>, modifier: Modifier) {
    when(lazyPagingArticles.loadState.refresh) {
      is LoadState.Error -> {
        ErrorScreen(loadStateError = lazyPagingArticles.loadState.refresh as LoadState.Error)
      }
      LoadState.Loading -> {
        LoadingScreen()
      }
      is LoadState.NotLoading -> {
        PagedRecyclerView(lazyPagingArticles, modifier)
      }
    }
  }

  @Composable
  private fun ErrorScreen(loadStateError: LoadState.Error) {
    val errorMsg = loadStateError.error.message
    Text(text = "$errorMsg", modifier = Modifier.background(Pink40))
  }

  @Composable
  private fun LoadingScreen() {
    val strokeWidth = 5.dp

    CircularProgressIndicator(
      modifier = Modifier.drawBehind {
        drawCircle(
          Color.Red,
          radius = size.width / 2 - strokeWidth.toPx() / 2,
          style = Stroke(strokeWidth.toPx())
        )
      },
      color = Color.LightGray,
      strokeWidth = strokeWidth
    )
  }

  @Composable
  private fun PagedRecyclerView(lazyPagingArticles: LazyPagingItems<Article>, modifier: Modifier) {
    LazyColumn(modifier = modifier,
      verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
      items(
        lazyPagingArticles.itemCount  // todo: tutorial e direct lazyPagingArticles use korse, but amake error dichchilo. itemSnapShotList dile kaj kore :/
      ) { idx: Int ->
        val item = lazyPagingArticles[idx]
        if(item!=null) {
          TopHeadLineRow(article = item)
          /*try {
            TopHeadLineRow(article = item)
          } catch (x: Exception) {
            NullArticleRow(x.message?:"try catch!")
          }*/
        } else {
          NullArticleRow()
        }
      }
    }
  }

  @Composable
  private fun TopHeadLineRow(article: Article) {
    Row {
      Column {
        article.title?.let {
          Text(text = it)
        }
        Text(text = "Author ${article.author}")
        AsyncImage(model = article.urlToImage, contentDescription = article.urlToImage)
        Text(text = "${article.content}")
        Text(text = "Source url: ${article.url}")
        Text(text = "Published at: ${article.publishedAt}")
        HorizontalDivider(thickness = 2.dp)
      }
    }
  }

  @Composable
  private fun NullArticleRow(msg: String = "default msg") {
    Text(text = "An article was null! msg = $msg", modifier = Modifier.background(Color.Yellow))
  }

  @Preview
  @Composable
  fun TopHeadLinesPreView() {}

  override fun createChildNavGraphBuilder(): NavGraphBuilder.() -> Unit {
    return {
      composable(NavigationItem.TopHeadLines.route) { TopHeadLinesView() }
    }

  }

}