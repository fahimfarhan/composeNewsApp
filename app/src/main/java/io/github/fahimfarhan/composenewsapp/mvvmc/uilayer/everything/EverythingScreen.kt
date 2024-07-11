package io.github.fahimfarhan.composenewsapp.mvvmc.uilayer.everything

import androidx.compose.runtime.Composable
import io.github.fahimfarhan.composenewsapp.mvvmc.domainlayer.viewmodels.EverythingViewModel

interface EverythingScreen {
  @Composable
  fun EverythingView(sharedViewModel: EverythingViewModel)
}