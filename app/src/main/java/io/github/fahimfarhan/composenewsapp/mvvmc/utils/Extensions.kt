package io.github.fahimfarhan.composenewsapp.mvvmc.utils

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

}