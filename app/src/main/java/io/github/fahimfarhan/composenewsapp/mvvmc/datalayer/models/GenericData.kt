package io.github.fahimfarhan.composenewsapp.mvvmc.datalayer.models

sealed class GenericData<T> {
  class Loading<T>: GenericData<T>()
  class Normal<T>: GenericData<T>()
  class Success<T>(val responseBody: T): GenericData<T>()
  class Error<T>(val error: NewsApiError?): GenericData<T>()
}