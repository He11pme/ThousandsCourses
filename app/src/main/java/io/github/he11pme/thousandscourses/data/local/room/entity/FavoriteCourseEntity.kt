package io.github.he11pme.thousandscourses.data.local.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_courses")
data class FavoriteCourseEntity (
    @PrimaryKey val id: Int
)