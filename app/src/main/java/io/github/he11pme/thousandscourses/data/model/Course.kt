package io.github.he11pme.thousandscourses.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

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
) {
    val formattedPublishDate: String = formateDate(publishDate)

    val formattedStartDate: String = formateDate(startDate)

    private fun formateDate(date: String): String {
        return runCatching {
            LocalDate.parse(date, DateTimeFormatter.ISO_DATE)
                .format(DateTimeFormatter.ofPattern("d MMMM yyyy", Locale.getDefault()))
        }.getOrDefault("")
    }
}
