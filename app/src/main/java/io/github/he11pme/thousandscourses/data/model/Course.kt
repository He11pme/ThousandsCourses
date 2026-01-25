package io.github.he11pme.thousandscourses.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Course(
    val id: Int,
    val title: String,
    @SerialName("text")
    val description: String,
    val price: String,
    val rate: Double,
    val startDate: String,
    val hasLike: Boolean,
    val publishDate: String
)
