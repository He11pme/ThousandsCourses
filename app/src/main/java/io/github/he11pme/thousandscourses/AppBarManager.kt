package io.github.he11pme.thousandscourses

import android.view.View
import io.github.he11pme.thousandscourses.utils.extensions.dp

class AppBarManager {

    val searchBar: AppBarState get() = AppBarState()
    val defaultBar: AppBarState
        get() = AppBarState(
            visibilitySearchBar = View.GONE,
            paddingLeft = 16.dp
        )

    data class AppBarState(
        val visibilitySearchBar: Int = View.VISIBLE,
        val paddingLeft: Int = 0.dp
    )
}

