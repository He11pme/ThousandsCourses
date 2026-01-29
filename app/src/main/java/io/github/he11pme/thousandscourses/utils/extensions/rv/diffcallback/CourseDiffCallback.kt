package io.github.he11pme.thousandscourses.utils.extensions.rv.diffcallback

import androidx.recyclerview.widget.DiffUtil
import io.github.he11pme.thousandscourses.data.model.Course

class CourseDiffCallback : DiffUtil.ItemCallback<Course>() {
    override fun areItemsTheSame(
        oldItem: Course,
        newItem: Course
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: Course,
        newItem: Course
    ): Boolean {
        return oldItem == newItem
    }

}