package io.github.fahimfarhan.composenewsapp.mvvmc.domainlayer.viewmodels

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import io.github.fahimfarhan.composenewsapp.mvvmc.datalayer.models.Article
import io.github.fahimfarhan.composenewsapp.mvvmc.domainlayer.repositories.TopHeadLinesRepository
import kotlinx.coroutines.flow.Flow

class TopHeadLinesViewModel: ViewModel() {
  private val repository = TopHeadLinesRepository()
  private val topHeadLinesPager: Pager<Int, Article> get() = repository.topHeadLinesPager
  val flowOfPagingDataArticle: Flow<PagingData<Article>> get() = topHeadLinesPager.flow
//  val lazyPagingArticles: LazyPagingItems<Article> @Composable get() = flowOfPagingDataArticle.collectAsLazyPagingItems()
}