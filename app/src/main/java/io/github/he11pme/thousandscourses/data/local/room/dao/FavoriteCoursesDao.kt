package io.github.he11pme.thousandscourses.data.local.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import io.github.he11pme.thousandscourses.data.local.room.entity.FavoriteCourseEntity

@Dao
interface FavoriteCoursesDao {
    @Insert
    suspend fun addFavorite(favorite: FavoriteCourseEntity)

    @Delete
    suspend fun removeFavorite(favorite: FavoriteCourseEntity)

    @Query("SELECT EXISTS (SELECT 1 FROM favorite_courses WHERE id = :id)")
    suspend fun isFavorite(id: Int): Boolean

    @Query("SELECT * FROM favorite_courses")
    suspend fun getAllFavorites(): List<FavoriteCourseEntity>
}