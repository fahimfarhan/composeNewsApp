package io.github.fahimfarhan.composenewsapp.mvvmc.uilayer.sources

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import io.github.fahimfarhan.composenewsapp.mvvmc.datalayer.models.GenericData
import io.github.fahimfarhan.composenewsapp.mvvmc.datalayer.models.Source
import io.github.fahimfarhan.composenewsapp.mvvmc.datalayer.models.SourceResponse
import io.github.fahimfarhan.composenewsapp.mvvmc.domainlayer.viewmodels.SourcesListViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import java.util.UUID

class SourcesList {
  companion object {
    const val TAG = "SOURCES_LIST"
  }

  @Composable
  fun SourceRow(source: Source) {
    Row(modifier = Modifier.background(Color.Blue)) {
      Column {
        Text(text = source.id)
        Text(text = source.name)
        source.description?.let {
          Text(text = it)
        }
        source.url?.let {
          Text(text = it)
        }
        source.category?.let {
          Text(text = it)
        }
        source.language?.let {
          Text(text = it)
        }
        source.country?.let {
          Text(text = it)
        }
      }
    }
  }

  @Preview
  @Composable
  fun SourceRowPreview() {
    SourceRow(
      Source(id="demoId", name="DemoName", description = "DemoDescription", url="www.newsapi.org", category = "news", language = "en", country = "us")
    )
  }


  @Composable
  fun SourcesListView(
    innerPadding: PaddingValues,
    mViewModel: SourcesListViewModel = viewModel(modelClass = SourcesListViewModel::class.java)
  ) {

    val mGenericData by mViewModel.genericDataFlow.collectAsState(initial = GenericData.Normal())

    when (mGenericData) {
      is GenericData.Error -> {
        val mError = (mGenericData as GenericData.Error)
        Text(text =  mError.error.toString())
      }

      is GenericData.Loading -> {
        Text(text =  "Loading...")
      }

      is GenericData.Normal -> {
        Text(text =  "Initial / Normal state")
      }

      is GenericData.Success -> {
        val mSuccess = mGenericData as GenericData.Success<SourceResponse>
        LazyColumn(modifier = Modifier.fillMaxSize().padding(innerPadding),
           verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
          items(
            mSuccess.responseBody.sources,
            key = { ignore -> UUID.randomUUID() }
            ) { someSource ->
            SourceRow(source = someSource)
          }
        }
      }
    }
  }
  
  @Preview
  @Composable
  fun SourcesListPreview() {
    SourcesListView(PaddingValues(8.dp))
  }
  
  
}