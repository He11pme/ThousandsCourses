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

    fun loadCourses() {
        _state.value = State.Loading

        viewModelScope.launch {
            coursesRepository.getAllCourses().apply {
                onSuccess {
                    _state.value = State.Loaded(it)
                }
                onFailure {
                    _state.value = State.Error(it)
                }
            }
        }

    }

    sealed interface State {
        data class Loaded(val courses: List<Course>) : State
        object Loading : State
        data class Error(val e: Throwable) : State
    }

}