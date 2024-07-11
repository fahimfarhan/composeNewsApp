package io.github.fahimfarhan.composenewsapp.mvvmc.domainlayer.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.compose.collectAsLazyPagingItems
import io.github.fahimfarhan.composenewsapp.mvvmc.datalayer.models.Article
import io.github.fahimfarhan.composenewsapp.mvvmc.domainlayer.repositories.EverythingRepository
import io.github.fahimfarhan.composenewsapp.mvvmc.domainlayer.repositories.TopHeadLinesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch

class EverythingViewModel: ViewModel() {
  companion object {
    const val TAG = "EveryThingVm"
    var INSTANCE_COUNT = 0
  }

  init {
    INSTANCE_COUNT++
    Log.d(TAG, "instanceCount: $INSTANCE_COUNT")
  }

  private val repository = EverythingRepository()
  private val everyThingPager: Pager<Int, Article> get() = repository.everythingPager
  val flowOfPagingDataArticle: Flow<PagingData<Article>> get() = everyThingPager.flow // .cachedIn(viewModelScope)


  // it looks like the paging library does not want us to touch the items(articles in this case) directly.
  // for now I am doing this hacky thing. But in future, add a room database to load the articles...
  private var snapShotArticleList: List<Article> = ArrayList<Article>()

  fun setSnapShot(mList: List<Article>) {
    this.snapShotArticleList = (mList)
    Log.d(TAG, "snapShotArticleList.size = ${snapShotArticleList.size}")
  }

  fun getImmutableSnapShotArticleList(): List<Article> = snapShotArticleList
}