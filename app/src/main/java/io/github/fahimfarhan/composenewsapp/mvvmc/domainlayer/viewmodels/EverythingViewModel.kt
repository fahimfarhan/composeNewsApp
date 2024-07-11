package io.github.fahimfarhan.composenewsapp.mvvmc.domainlayer.viewmodels

import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingData
import io.github.fahimfarhan.composenewsapp.mvvmc.datalayer.models.Article
import io.github.fahimfarhan.composenewsapp.mvvmc.domainlayer.repositories.EverythingRepository
import io.github.fahimfarhan.composenewsapp.mvvmc.domainlayer.repositories.TopHeadLinesRepository
import kotlinx.coroutines.flow.Flow

class EverythingViewModel: ViewModel() {
  private val repository = EverythingRepository()
  private val everyThingPager: Pager<Int, Article> get() = repository.everythingPager
  val flowOfPagingDataArticle: Flow<PagingData<Article>> get() = everyThingPager.flow
}