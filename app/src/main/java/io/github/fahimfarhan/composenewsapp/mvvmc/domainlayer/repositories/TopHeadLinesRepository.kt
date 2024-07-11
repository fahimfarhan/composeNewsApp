package io.github.fahimfarhan.composenewsapp.mvvmc.domainlayer.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import io.github.fahimfarhan.composenewsapp.mvvmc.domainlayer.pagingsources.TopHeadersPagingSource
import io.github.fahimfarhan.composenewsapp.mvvmc.domainlayer.pagingsources.TopHeadersPagingSource.Companion.TOP_HEAD_LINES_PAGE_SIZE

class TopHeadLinesRepository {

  val topHeadLinesPager = Pager(
    PagingConfig(pageSize=TOP_HEAD_LINES_PAGE_SIZE)
  ) {
    TopHeadersPagingSource()
  }
}