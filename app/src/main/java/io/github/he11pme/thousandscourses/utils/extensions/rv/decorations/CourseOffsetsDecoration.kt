package io.github.he11pme.thousandscourses.utils.extensions.rv.decorations

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import io.github.he11pme.thousandscourses.utils.extensions.dp

class CourseOffsetsDecoration : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        val defaultOffset = 16.dp
        outRect.set(
            0,
            0,
            0,
            defaultOffset
        )
    }

}