package io.github.he11pme.thousandscourses.data.model

import kotlinx.serialization.Serializable

@Serializable
data class CoursesResponse(
    val courses: List<Course>
)
