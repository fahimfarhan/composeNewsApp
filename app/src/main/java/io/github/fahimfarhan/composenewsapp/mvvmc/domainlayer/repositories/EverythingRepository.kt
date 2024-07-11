package io.github.fahimfarhan.composenewsapp.mvvmc.domainlayer.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import io.github.fahimfarhan.composenewsapp.mvvmc.domainlayer.pagingsources.EverythingPagingSource
import io.github.fahimfarhan.composenewsapp.mvvmc.domainlayer.pagingsources.EverythingPagingSource.Companion.EVERYTHING_PAGE_SIZE
import io.github.fahimfarhan.composenewsapp.mvvmc.domainlayer.pagingsources.TopHeadersPagingSource

class EverythingRepository {
  val everythingPager = Pager(
    PagingConfig(pageSize= EVERYTHING_PAGE_SIZE)
  ) {
    EverythingPagingSource()
  }
}