package io.github.fahimfarhan.composenewsapp
import io.github.fahimfarhan.composenewsapp.mvvmc.datalayer.models.getApiError
import io.github.fahimfarhan.composenewsapp.mvvmc.datalayer.networking.RetrofitSingletonInstance
import kotlinx.coroutines.runBlocking
import org.junit.Test

import org.junit.Assert.*

class ApiUnitTest {

  @Test
  fun assert_failure() = runBlocking {
    val newsApiService = RetrofitSingletonInstance.getNewsApiInstance()
    val response = newsApiService.everyThing(q="NEWS", from = "1900-01-01", to="1800-01-01")
    assert(!response.isSuccessful)
    val newsApiError = response.errorBody()?.getApiError()
    assertNotNull(newsApiError!!)
    println(newsApiError)
  }

  @Test
  fun assert_Everything() = runBlocking {
    val newsApiService = RetrofitSingletonInstance.getNewsApiInstance()

    val response = newsApiService.everyThing(q="NEWS")
    assert(response.isSuccessful)
    val mNewsResponse = response.body()
    assertNotNull(mNewsResponse!!)
    assertEquals(mNewsResponse.status, "ok")

    println(mNewsResponse)

  }

  @Test
  fun assert_headlines() = runBlocking {
    val newsApiService = RetrofitSingletonInstance.getNewsApiInstance()
    val topHeadLinesResponse = newsApiService.topHeadLines(q="news")
    assert(topHeadLinesResponse.isSuccessful)
    val newsResponse = topHeadLinesResponse.body()
    assertNotNull(newsResponse!!)
    println(newsResponse)
  }

  @Test
  fun assert_sources() = runBlocking {
    val newsApiService = RetrofitSingletonInstance.getNewsApiInstance()
    val response = newsApiService.sources()
    assert(response.isSuccessful)
    val sourceResponse = response.body()
    assertNotNull(sourceResponse!!)
    println(sourceResponse)
  }
}