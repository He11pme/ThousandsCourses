package io.github.he11pme.thousandscourses

import android.view.View
import io.github.he11pme.thousandscourses.utils.extensions.dp

class AppBarManager {

    val defaultBar: AppBarState = AppBarState()
    val searchBar: AppBarState
        get() = AppBarState(
            visibilitySearchBar = View.VISIBLE,
            paddingLeft = 0.dp
        )
    val emptyBar: AppBarState
        get() = AppBarState(
            visibilityAppBar = View.GONE,
            visibilityBottomNav = View.GONE,
        )

    data class AppBarState(
        val visibilityAppBar: Int = View.VISIBLE,
        val visibilityBottomNav: Int = View.VISIBLE,
        val visibilitySearchBar: Int = View.GONE,
        val paddingLeft: Int = 16.dp
    )
}

