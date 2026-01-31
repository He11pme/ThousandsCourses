package io.github.he11pme.thousandscourses.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import io.github.he11pme.thousandscourses.data.local.room.dao.FavoriteCoursesDao
import io.github.he11pme.thousandscourses.data.local.room.entity.FavoriteCourseEntity

@Database(
    entities = [FavoriteCourseEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract val favoriteCoursesDao: FavoriteCoursesDao
}