package io.github.fahimfarhan.composenewsapp.mvvmc.utils

object Extensions {
  fun listOfParamsToString(listOfParams: List<Any>) : String {
    var output = ""
    for(param in listOfParams) {
      output += "/${param}"
    }
    return output
  }
}