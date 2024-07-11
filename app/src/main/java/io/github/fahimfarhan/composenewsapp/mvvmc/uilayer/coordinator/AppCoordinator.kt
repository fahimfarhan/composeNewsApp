package io.github.fahimfarhan.composenewsapp.mvvmc.uilayer.coordinator

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import io.github.fahimfarhan.composenewsapp.mvvmc.uilayer.everything.BasicEveryThingScreen
import io.github.fahimfarhan.composenewsapp.mvvmc.uilayer.everything.EverythingScreen
import io.github.fahimfarhan.composenewsapp.mvvmc.uilayer.everything.GlideEveryThingScreen
import io.github.fahimfarhan.composenewsapp.mvvmc.uilayer.everything.SingleArticleScreen
import io.github.fahimfarhan.composenewsapp.mvvmc.uilayer.home.Home
import io.github.fahimfarhan.composenewsapp.mvvmc.uilayer.sources.SourcesList
import io.github.fahimfarhan.composenewsapp.mvvmc.uilayer.topheadlines.TopHeadLinesScreen

// common navigation related code

class AppCoordinator(
  private val mNavController: NavHostController,
  private val mainModifier: Modifier
) {

  companion object {
    const val TAG = "AppCoordinator"
  }

  /*private var _mNavController: NavHostController? = null
  val mNavController get() = _mNavController!!

  fun setNavController(navHostController: NavHostController) {
    this._mNavController = navHostController
  }*/

  private val mHome by lazy { Home(mNavController, mainModifier) }
  private val mSourceList by lazy { SourcesList(mNavController, mainModifier) }
  private val mTopHeadLinesScreen by lazy { TopHeadLinesScreen(mNavController, mainModifier) }
  private val mBasicEveryThingScreen by lazy { BasicEveryThingScreen(mNavController, mainModifier) }
  private val mGlideEveryThingScreen by lazy { GlideEveryThingScreen(mNavController, mainModifier) }
  private val mSingleArticleScreen by lazy { SingleArticleScreen(mNavController, mainModifier) }

  private val listOfNewsAppNavGraphs: List<NewsAppNavGraph> by lazy {
    arrayListOf( mBasicEveryThingScreen, mGlideEveryThingScreen)
  }

  @Composable
  fun NewsNavHost(
    startDestination: String=NavigationItem.Home.route
  ) {
    NavHost(modifier = mainModifier, navController = mNavController, startDestination = startDestination) {
//      composable(NavigationItem.Home.route) { mHome.HomeView(mainModifier) }
      mHome.createChildNavGraphBuilder().invoke(this)
      mSourceList.createChildNavGraphBuilder().invoke(this)
      mTopHeadLinesScreen.createChildNavGraphBuilder().invoke(this)

      /*for(newsAppNavGraph in listOfNewsAppNavGraphs) {
        newsAppNavGraph.createChildNavGraphBuilder().invoke(this)
      }*/

      mBasicEveryThingScreen.createChildNavGraphBuilder().invoke(this)
      mGlideEveryThingScreen.createChildNavGraphBuilder().invoke(this)
      mSingleArticleScreen.createChildNavGraphBuilder().invoke(this)

      /*
      navigation(startDestination = NavigationItem.BasicEveryThing.route, route = NavigationItem.SingleArticleWithArgs.route) {
        mSingleArticleScreen.createChildNavGraphBuilder().invoke(this)
      }

      navigation(startDestination = NavigationItem.GlideEveryThing.route, route = NavigationItem.SingleArticleWithArgs.route) {
        mSingleArticleScreen.createChildNavGraphBuilder().invoke(this)
      }
      */
    }
  }
}

