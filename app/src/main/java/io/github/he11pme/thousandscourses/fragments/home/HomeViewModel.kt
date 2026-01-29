package io.github.he11pme.thousandscourses.fragments.home

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
class HomeViewModel @Inject constructor(
    private val coursesRepository: CoursesRepository
) : ViewModel() {

    private val _state = MutableLiveData<State>()
    val state: LiveData<State> get() = _state

    private var ascending = true

    fun loadCourses() {
        _state.value = State.Loading

        viewModelScope.launch {
            coursesRepository.getAllCourses().apply {
                onSuccess { courses ->
                    _state.value = State.Loaded(sortByPublishDate(courses))
                }
                onFailure {
                    _state.value = State.Error(it)
                }
            }
        }

    }

    fun onClickSortBtn() = sort()

    private fun sort() {
        (_state.value as? State.Loaded)?.let {
            _state.value = State.Loaded(sortByPublishDate(it.courses))
        }
    }

    private fun sortByPublishDate(list: List<Course>): List<Course> {
        val sorted = if (ascending) list.sortedBy { it.publishDate }
        else list.sortedByDescending { it.publishDate }

        ascending = !ascending

        return sorted
    }

    sealed interface State {
        data class Loaded(val courses: List<Course>) : State
        object Loading : State
        data class Error(val e: Throwable) : State
    }

}