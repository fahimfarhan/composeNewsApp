package io.github.fahimfarhan.composenewsapp.mvvmc.uilayer.coordinator

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import io.github.fahimfarhan.composenewsapp.mvvmc.uilayer.home.Home
import io.github.fahimfarhan.composenewsapp.mvvmc.uilayer.sources.SourcesList

// common navigation related code

class AppCoordinator(
  private val mNavController: NavHostController
) {

  companion object {
    const val TAG = "AppCoordinator"
  }

  /*private var _mNavController: NavHostController? = null
  val mNavController get() = _mNavController!!

  fun setNavController(navHostController: NavHostController) {
    this._mNavController = navHostController
  }*/

  private val mHome by lazy { Home(mNavController) }
  private val mSourceList by lazy { SourcesList(mNavController) }

  @Composable
  fun NewsNavHost(
    modifier: Modifier = Modifier,
//    navController: NavHostController=this.mNavController,
    startDestination: String=NavigationItem.Home.route
  ) {
    NavHost(modifier = modifier, navController = mNavController, startDestination = startDestination) {
      composable(NavigationItem.Home.route) { mHome.HomeView(modifier) }
      composable(NavigationItem.SourcesList.route) { mSourceList.SourcesListView(modifier) }
      composable(
        route = "${NavigationItem.SourcesList.route}/{country}",
        arguments = listOf(navArgument("country") { type = NavType.StringType } )
      ) { backStackEntry ->
        val mCountry: String = backStackEntry.arguments?.getString("country")?:"us"
        mSourceList.SourcesListView(modifier, mCountry)
      }
    }
  }

}

