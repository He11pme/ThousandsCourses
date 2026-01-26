package io.github.he11pme.thousandscourses.data.repository

import dagger.hilt.android.scopes.ActivityRetainedScoped
import io.github.he11pme.thousandscourses.data.model.Course
import io.github.he11pme.thousandscourses.data.network.Api
import java.lang.Exception
import javax.inject.Inject

@ActivityRetainedScoped
class CoursesRepository @Inject constructor(
    api: Api
) {
    private val apiService = api.retrofitService
    private var courses: List<Course>? = null

    suspend fun getAllCourses(): Result<List<Course>> {
        return safeApiCall {
            courses ?: run {
                val response = apiService.getCourses().courses
                courses = response
                response
            }
        }
    }

    private suspend fun <T> safeApiCall(onSuccess: suspend () -> T): Result<T> {
        return try {
            Result.success(onSuccess())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}