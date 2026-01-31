package io.github.he11pme.thousandscourses.fragments.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.he11pme.thousandscourses.data.model.Course
import io.github.he11pme.thousandscourses.data.repository.CoursesRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val coursesRepository: CoursesRepository
) : ViewModel() {

    private val _state = MutableLiveData<State>()
    val state: LiveData<State> get() = _state

    fun loadFavoriteCourses() {
        _state.value = State.Loading

        coursesRepository.observeFavoriteIds()
            .observeForever { ids ->
                viewModelScope.launch {
                    val courses = ids.mapNotNull {
                        coursesRepository.getCoursesById(it).getOrNull()
                    }

                    _state.value = when {
                        courses.isEmpty() -> State.Empty
                        else -> State.Loaded(courses)
                    }
                }
            }

    }


    fun onClickFavoriteBtn(id: Int) = toggleFavorite(id)

    private fun toggleFavorite(id: Int) {
        viewModelScope.launch {
            if (!coursesRepository.toggleFavorite(id)) removeFavoriteCourseById(id)
        }
    }

    private fun removeFavoriteCourseById(id: Int) {
        viewModelScope.launch {
            coursesRepository.removeFavoriteById(id)

            (_state.value as? State.Loaded)?.let {
                val updatedList = it.courses.filterNot { course -> course.id == id }
                _state.value = if (updatedList.isEmpty()) State.Empty else State.Loaded(updatedList)
            }

        }
    }

    sealed interface State {
        data class Loaded(val courses: List<Course>) : State
        object Loading : State
        object Empty : State
    }

}