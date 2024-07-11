package io.github.fahimfarhan.composenewsapp.mvvmc.domainlayer.pagingsources

import androidx.paging.PagingSource
import androidx.paging.PagingState
import io.github.fahimfarhan.composenewsapp.mvvmc.datalayer.models.Article
import io.github.fahimfarhan.composenewsapp.mvvmc.datalayer.models.getApiError
import io.github.fahimfarhan.composenewsapp.mvvmc.datalayer.networking.NewsApiService
import io.github.fahimfarhan.composenewsapp.mvvmc.datalayer.networking.RetrofitSingletonInstance

class EverythingPagingSource(
  private val newsApiService: NewsApiService = RetrofitSingletonInstance.getNewsApiInstance()
): PagingSource<Int, Article>() {


  companion object {
    const val TAG= "EVERYTHING_PS"
    const val EVERYTHING_PAGE_SIZE = 10
  }

  override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
    return ((state.anchorPosition ?: 0) - state.config.initialLoadSize / 2)
      .coerceAtLeast(0)
  }

  override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
    val currentPageNumber = params.key?:1
    val prevKey = if(currentPageNumber == 1) null else currentPageNumber-1
    val nextKey = currentPageNumber+1

    val response = newsApiService.everyThing(q = "news", page = currentPageNumber, pageSize = params.loadSize)
    return if (response.isSuccessful && response.body()!=null) {
      LoadResult.Page(
        data = response.body()?.articles?: emptyList(),
        prevKey = prevKey,
        nextKey = nextKey
      )
    } else {
      LoadResult.Error(Exception(response.errorBody()?.getApiError()?.message))
    }
  }
}