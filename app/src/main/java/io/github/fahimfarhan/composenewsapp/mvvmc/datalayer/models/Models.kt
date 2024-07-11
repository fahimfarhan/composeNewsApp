package io.github.fahimfarhan.composenewsapp.mvvmc.datalayer.models

import com.google.gson.Gson
import okhttp3.ResponseBody

data class NewsResponse (
  val status: String,
  val totalResults: Int,
  val articles: List<Article>
)

data class Article(
  val source: Source,
  val author: String?,
  val title: String?,
  val description: String?,
  val url: String?,
  val urlToImage: String?,
  val publishedAt: String?,
  val  content: String?
) {
  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as Article

    if (author != other.author) return false
    if (title != other.title) return false
    if (description != other.description) return false
    if (url != other.url) return false
    if (urlToImage != other.urlToImage) return false
    if (publishedAt != other.publishedAt) return false
    if (content != other.content) return false

    return true
  }

  override fun hashCode(): Int {
    var result = author?.hashCode() ?: 0
    result = 31 * result + (title?.hashCode() ?: 0)
    result = 31 * result + (description?.hashCode() ?: 0)
    result = 31 * result + (url?.hashCode() ?: 0)
    result = 31 * result + (urlToImage?.hashCode() ?: 0)
    result = 31 * result + (publishedAt?.hashCode() ?: 0)
    result = 31 * result + (content?.hashCode() ?: 0)
    return result
  }
}

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