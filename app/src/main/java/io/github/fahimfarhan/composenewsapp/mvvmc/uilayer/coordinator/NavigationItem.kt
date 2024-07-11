package io.github.fahimfarhan.composenewsapp.mvvmc.uilayer.coordinator


enum class Screen {
  HOME,
  SOURCES_LIST,
  TOP_HEADLINES,
  BASIC_EVERYTHING,
  GLIDE_EVERYTHING
}

sealed class NavigationItem(val route: String) {
  data object Home: NavigationItem(Screen.HOME.name)
  data object SourcesList: NavigationItem(Screen.SOURCES_LIST.name)
  data object TopHeadLines: NavigationItem(Screen.TOP_HEADLINES.name)
  data object BasicEveryThing: NavigationItem(Screen.BASIC_EVERYTHING.name)
  data object GlideEveryThing: NavigationItem(Screen.GLIDE_EVERYTHING.name)
}
