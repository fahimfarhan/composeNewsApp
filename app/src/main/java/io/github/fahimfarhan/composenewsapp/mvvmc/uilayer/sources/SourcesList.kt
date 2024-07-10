package io.github.fahimfarhan.composenewsapp.mvvmc.uilayer.sources

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import io.github.fahimfarhan.composenewsapp.mvvmc.datalayer.models.GenericData
import io.github.fahimfarhan.composenewsapp.mvvmc.datalayer.models.Source
import io.github.fahimfarhan.composenewsapp.mvvmc.datalayer.models.SourceResponse
import io.github.fahimfarhan.composenewsapp.mvvmc.domainlayer.viewmodels.SourcesListViewModel
import java.util.UUID

class SourcesList(mNavController: NavHostController) {
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
    modifier: Modifier=Modifier,
    mCountry: String = "us"
  ) {
    Log.d(TAG, "SourceListView->mCountry: $mCountry")
    val mViewModel: SourcesListViewModel = viewModel(factory = SourcesListViewModel.Factory(mCountry))

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
        LazyColumn(modifier = modifier.fillMaxSize(),
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
    SourcesListView(mCountry = "us")
  }
  
  
}