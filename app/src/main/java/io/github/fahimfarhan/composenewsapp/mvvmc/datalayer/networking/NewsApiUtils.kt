package io.github.fahimfarhan.composenewsapp.mvvmc.datalayer.networking

import io.github.fahimfarhan.composenewsapp.mvvmc.datalayer.models.GenericData
import io.github.fahimfarhan.composenewsapp.mvvmc.datalayer.models.getApiError
import retrofit2.Response


fun <T>apiCallGenericWrapper(response: Response<T>): GenericData<T> {
  return if(response.isSuccessful && response.body()!=null) {
    GenericData.Success(response.body()!!)
  } else {
    GenericData.Error(response.errorBody()?.getApiError())
  }
}