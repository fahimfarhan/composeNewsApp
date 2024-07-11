package io.github.fahimfarhan.composenewsapp.mvvmc.utils

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import com.bumptech.glide.signature.EmptySignature
import com.bumptech.glide.signature.MediaStoreSignature
import com.bumptech.glide.signature.ObjectKey
import io.github.fahimfarhan.composenewsapp.mvvmc.datalayer.models.Article

object Extensions {
  fun listOfParamsToString(listOfParams: List<Any>) : String {
    var output = ""
    for(param in listOfParams) {
      output += "/${param}"
    }
    return output
  }

  fun Article?.signature() = if(this != null ) {
    ObjectKey(this)
  } else {
    EmptySignature.obtain()
  }

  @Composable
  inline fun <reified  T: ViewModel> NavBackStackEntry.sharedViewModel(
    navController: NavController
  ): T {
    Log.d("Extensions", "destParent: ${destination.parent}, destination: $destination")
    val navGraphRoute = destination.parent?.route ?: return viewModel()
    val parentEntry = remember(this) {
      navController.getBackStackEntry(navGraphRoute)
    }
    return viewModel(parentEntry)
  }

}