package io.github.he11pme.thousandscourses.utils.extensions.rv.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import io.github.he11pme.thousandscourses.data.model.Course
import io.github.he11pme.thousandscourses.databinding.ItemCourseBinding
import io.github.he11pme.thousandscourses.utils.extensions.rv.diffcallback.CourseDiffCallback

class CourseAdapter(
    private val openCourseDetail: (id: Int) -> Unit,
    private val onClickFavorite: (id: Int) -> Unit
) :
    ListAdapter<Course, CourseAdapter.ViewHolder>(CourseDiffCallback()) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {

        return ViewHolder(
            ItemCourseBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val binding: ItemCourseBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(course: Course) {
            binding.course = course

            binding.rateBlurView.setupWith(binding.coverBlurTarget)
                .setBlurRadius(16f)

            binding.dateBlurView.setupWith(binding.coverBlurTarget)
                .setBlurRadius(16f)

            binding.moreDetailsInfo.setOnClickListener { openCourseDetail(course.id) }

            binding.favoriteBtn.setOnClickListener { onClickFavorite(course.id) }
        }

    }
}