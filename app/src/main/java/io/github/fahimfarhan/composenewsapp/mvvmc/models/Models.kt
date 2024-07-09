package io.github.fahimfarhan.composenewsapp.mvvmc.models

import com.google.gson.Gson
import okhttp3.ResponseBody

data class NewsResponse (
  val status: String,
  val totalResults: Int,
  val articles: List<Article>
)

data class Article(
  val source: Source,
  val author: String,
  val title: String,
  val description: String,
  val url: String,
  val urlToImage: String,
  val publishedAt: String,
  val  content: String
)

data class Source(
  val id: String,
  val name: String,
  val description: String?=null,
  val url: String?=null,
  val category: String?=null,
  val language: String?=null,
  val country: String?=null
)

data class SourceResponse(
  val status: String,
  val sources: List<Source>
)

data class NewsApiError(
  val status: String,
  val code: String,
  val message: String
)

fun ResponseBody.getApiError(): NewsApiError? {
  return Gson().fromJson<NewsApiError>(string(), NewsApiError::class.java)
}