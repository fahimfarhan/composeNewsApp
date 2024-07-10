package io.github.fahimfarhan.composenewsapp.mvvmc.domainlayer.repositories

import android.icu.text.UnicodeSetSpanner.CountMethod
import io.github.fahimfarhan.composenewsapp.mvvmc.datalayer.models.SourceResponse
import io.github.fahimfarhan.composenewsapp.mvvmc.datalayer.models.GenericData
import io.github.fahimfarhan.composenewsapp.mvvmc.datalayer.networking.RetrofitSingletonInstance
import io.github.fahimfarhan.composenewsapp.mvvmc.datalayer.networking.apiCallGenericWrapper
import kotlinx.coroutines.flow.MutableStateFlow

class SourcesRepository {
  val genericDataFlow: MutableStateFlow<GenericData<SourceResponse>> = MutableStateFlow(GenericData.Normal())


  suspend fun loadSourcesListAsync(country: String) {
    val newsApiService = RetrofitSingletonInstance.getNewsApiInstance()
    genericDataFlow.emit(GenericData.Loading())
    val response = newsApiService.sources(country = country)
    genericDataFlow.emit(apiCallGenericWrapper(response))

  }

}