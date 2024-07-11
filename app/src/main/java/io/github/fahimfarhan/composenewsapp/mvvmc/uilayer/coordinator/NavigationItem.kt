package io.github.fahimfarhan.composenewsapp.mvvmc.uilayer.coordinator


enum class Screen {
  HOME,
  SOURCES_LIST,
  TOP_HEADLINES
}

sealed class NavigationItem(val route: String) {
  data object Home: NavigationItem(Screen.HOME.name)
  data object SourcesList: NavigationItem(Screen.SOURCES_LIST.name)
  data object TopHeadLines: NavigationItem(Screen.TOP_HEADLINES.name)
}
