package io.github.fahimfarhan.composenewsapp.mvvmc.domainlayer.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.fahimfarhan.composenewsapp.mvvmc.datalayer.models.GenericData
import io.github.fahimfarhan.composenewsapp.mvvmc.datalayer.models.SourceResponse
import io.github.fahimfarhan.composenewsapp.mvvmc.domainlayer.repositories.SourcesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class SourcesListViewModel: ViewModel() {
  companion object {
    const val TAG = "SRC_LIST_VM"
  }

  private val mSrcRepo = SourcesRepository()

  val genericDataFlow: MutableStateFlow<GenericData<SourceResponse>> get() =  mSrcRepo.genericDataFlow

  init {
    loadSourcesListAsync()
  }

  fun loadSourcesListAsync() {
    viewModelScope.launch(Dispatchers.IO) {
      mSrcRepo.loadSourcesListAsync()
    }
  }
}