package io.github.fahimfarhan.composenewsapp.mvvmc.uilayer.coordinator

import androidx.navigation.NavGraphBuilder

interface NewsAppNavGraph {
  fun createChildNavGraphBuilder(): (NavGraphBuilder.() -> Unit)
}