package io.github.he11pme.thousandscourses.data.repository

import dagger.hilt.android.scopes.ActivityRetainedScoped
import io.github.he11pme.thousandscourses.data.local.room.dao.FavoriteCoursesDao
import io.github.he11pme.thousandscourses.data.local.room.entity.FavoriteCourseEntity
import io.github.he11pme.thousandscourses.data.model.Course
import io.github.he11pme.thousandscourses.data.network.Api
import java.lang.Exception
import javax.inject.Inject

@ActivityRetainedScoped
class CoursesRepository @Inject constructor(
    api: Api,
    private val favoriteCoursesDao: FavoriteCoursesDao
) {
    private val apiService = api.retrofitService
    private var courses: List<Course>? = null

    suspend fun getAllCourses(): Result<List<Course>> {
        return safeApiCall {
            val courses = courses ?: run {
                val response = apiService.getCourses().courses
                courses = response
                response
            }
            courses.map { it.copy(isFavorite = favoriteCoursesDao.isFavorite(it.id)) }
        }
    }

    suspend fun getCoursesById(id: Int): Result<Course> {
        val allCourses = getAllCourses().getOrElse { return Result.failure(it) }

        val course = allCourses.find { it.id == id }
            ?: return Result.failure(RuntimeException("Course not found in the list"))

        return Result.success(course)
    }

    private suspend fun <T> safeApiCall(onSuccess: suspend () -> T): Result<T> {
        return try {
            Result.success(onSuccess())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Toggles the favorite status of a course with the given [id]
     *
     * If the course is already in the favorites table, it will be removed.
     * If the course is not in favorites, it will be added.
     *
     * @return `true` if the course is now marked as favorite after the call
     *         `false` if the course was removed from favorites
     */
    suspend fun toggleFavorite(id: Int): Boolean {
        val favorite = FavoriteCourseEntity(id)
        if (favoriteCoursesDao.isFavorite(id)) {
            favoriteCoursesDao.removeFavorite(favorite)
            return false
        } else {
            favoriteCoursesDao.addFavorite(favorite)
            return true
        }
    }

    suspend fun getAllFavorites(): List<Result<Course>> {
        return favoriteCoursesDao.getAllFavorites().map { getCoursesById(it.id) }
    }

    suspend fun removeFavoriteById(movieId: Int) {
        favoriteCoursesDao.removeFavorite(FavoriteCourseEntity(movieId))
    }

}