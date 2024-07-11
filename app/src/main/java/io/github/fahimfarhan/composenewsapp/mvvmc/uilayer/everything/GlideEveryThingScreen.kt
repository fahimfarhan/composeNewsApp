package io.github.fahimfarhan.composenewsapp.mvvmc.uilayer.everything

import android.graphics.drawable.Drawable
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.GlidePreloadingData
import com.bumptech.glide.integration.compose.rememberGlidePreloadingData
import io.github.fahimfarhan.composenewsapp.mvvmc.datalayer.models.Article
import io.github.fahimfarhan.composenewsapp.mvvmc.domainlayer.viewmodels.EverythingViewModel
import io.github.fahimfarhan.composenewsapp.mvvmc.uilayer.coordinator.NavigationItem
import io.github.fahimfarhan.composenewsapp.mvvmc.uilayer.coordinator.NewsAppNavGraph
import io.github.fahimfarhan.composenewsapp.mvvmc.utils.Extensions.sharedViewModel
import io.github.fahimfarhan.composenewsapp.mvvmc.utils.Extensions.signature
import io.github.fahimfarhan.composenewsapp.ui.theme.Pink40

open class GlideEveryThingScreen(
  private val mNavController: NavHostController,
  private val mainModifier: Modifier
): EverythingScreen, NewsAppNavGraph {

  companion object {
    const val TAG = "BASIC_EVERYTHING_SCREEN"

    private const val THUMBNAIL_DIMENSION = 50
    private val THUMBNAIL_SIZE = Size(THUMBNAIL_DIMENSION.toFloat(), THUMBNAIL_DIMENSION.toFloat())
  }

  // private methods

  // override methods / public apis
  override fun createChildNavGraphBuilder(): NavGraphBuilder.() -> Unit {
    return {
      composable(NavigationItem.GlideEveryThing.route) { entry ->
        val viewModel: EverythingViewModel = viewModel() // entry.sharedViewModel<EverythingViewModel>(mNavController)
        EverythingView(viewModel) }
    }
  }

  @Composable
  override fun EverythingView(
    sharedViewModel: EverythingViewModel
  ) {
    val vm: EverythingViewModel = viewModel()
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
    val requestBuilderTransform = { item: Article?, requestBuilder: RequestBuilder<Drawable> ->
      requestBuilder.load(item?.urlToImage?:"https://picsum.photos/200").signature(item.signature())
    }

    val snapShotForGlide: List<Article> = lazyPagingArticles.itemSnapshotList.filterNotNull().toList()

    val preloadingData = rememberGlidePreloadingData<Article>(
      snapShotForGlide,
      THUMBNAIL_SIZE,
      requestBuilderTransform = requestBuilderTransform,
    )

    LazyColumn(modifier = modifier,
      verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
      items(
        lazyPagingArticles.itemCount
      ) { idx: Int ->
        val item = lazyPagingArticles[idx]
        if(item!=null) {
          EverythingRow(idx, article = item, preloadingData)
        } else {
          NullArticleRow()
        }
      }
    }
  }

  @OptIn(ExperimentalGlideComposeApi::class)
  @Composable
  private fun EverythingRow(idx: Int, article: Article, preloadingData: GlidePreloadingData<Article>) {
    Row(modifier = Modifier.clickable {
      mNavController.navigate(NavigationItem.SingleArticleWithArgs.route + "/${idx}")
    }) {
      Column {
        Text(text = "${article.title}")
        Text(text = "Author ${article.author}")
//        GlideImage(model = article.urlToImage, contentDescription = article.urlToImage)  // works ok
        val (someArticle, preloadRequestBuilder) = preloadingData.get(index = idx)
        MediaStoreView(item = someArticle, preloadRequestBuilder , modifier = Modifier.fillMaxSize(1f))
        Text(text = "${article.content}")
        Text(text = "Source url: ${article.url}")
        Text(text = "Published at: ${article.publishedAt}")
        HorizontalDivider(thickness = 2.dp)
      }
    }
  }

  @OptIn(ExperimentalGlideComposeApi::class)
  @Composable
  private fun MediaStoreView(item: Article?, requestBuilder: RequestBuilder<Drawable>, modifier: Modifier) {

    val signature = item.signature()
    GlideImage(
      model = item?.urlToImage?:"https://picsum.photos/200",
      contentDescription = item?.title?:"falied to get image, fall back to picsum.photos",
      modifier = modifier,
    ) {
      it
        // This thumbnail request exactly matches the request in GlideLazyListPreloader
        // so that the preloaded image can be used here and display more quickly than
        // the primary request.
        .thumbnail(
          requestBuilder
//            .asDrawable()
            .load(item?.urlToImage?:"https://picsum.photos/200/300")
            .signature(item.signature())
            .override(THUMBNAIL_DIMENSION)
        )
        .signature(signature)
    }
  }

  @Composable
  private fun NullArticleRow(msg: String = "default msg") {
    Text(text = "An article was null! msg = $msg", modifier = Modifier.background(Color.Yellow))
  }
}