package io.github.fahimfarhan.composenewsapp.mvvmc.datalayer.networking

import io.github.fahimfarhan.composenewsapp.BuildConfig
import io.github.fahimfarhan.composenewsapp.mvvmc.datalayer.models.NewsResponse
import io.github.fahimfarhan.composenewsapp.mvvmc.datalayer.models.SourceResponse
import io.github.fahimfarhan.composenewsapp.mvvmc.domainlayer.pagingsources.TopHeadersPagingSource
import io.github.fahimfarhan.composenewsapp.mvvmc.domainlayer.repositories.TopHeadLinesRepository
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {

  @GET("/v2/everything")
  suspend fun everyThing(
    @Query(value = "apiKey") apiKey: String= BuildConfig.NEWS_API_KEY,
    @Query(value = "q") q: String,
    @Query(value = "searchIn") searchIn: String="title,description,content", // title,description,content  one or more comma separated
    // todo: Implement these filters
    @Query(value = "sources") sources: String?=null, // comma-separated strings from sources() api. max 20
    @Query(value = "domains") domains: String?=null, // bbc.co.uk, etc comma-separated
    @Query(value = "excludedDomains") excludedDomains: String?=null, // blacklist domains
    @Query(value = "from") from: String?= null, //"2024-01-01", // yyyy-mm-dd
    @Query(value = "to") to: String?=null, // "2020-01-01", // yyyy-mm-dd
    @Query(value = "language") language: String="en", //
    @Query(value = "sortBy") sortBy: String="publishedAt", // relevancy, popularity, publishedAt
    @Query(value = "pageSize") pageSize: Int=50,
    @Query(value = "page") page: Int=1
  ): Response<NewsResponse>

  @GET("/v2/top-headlines")
  suspend fun topHeadLines(
    @Query(value = "apiKey") apiKey: String= BuildConfig.NEWS_API_KEY,
    @Query(value = "country") country: String?=null,
    @Query(value = "category") category: String?=null,
    @Query(value = "sources") sources: String?=null,
    @Query(value = "q") q: String?="news",
    @Query(value = "pageSize") pageSize: Int=TopHeadersPagingSource.TOP_HEAD_LINES_PAGE_SIZE,
    @Query(value = "page") page: Int=1
  ): Response<NewsResponse>

  @GET("/v2/top-headlines/sources")
  suspend fun sources(
    @Query(value = "apiKey") apiKey: String= BuildConfig.NEWS_API_KEY,
    @Query(value = "category") category: String?=null,
    @Query(value = "language") language: String="en",
    @Query(value = "country") country: String="us",
  ): Response<SourceResponse>
}