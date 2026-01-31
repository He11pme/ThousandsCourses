package io.github.he11pme.thousandscourses.fragments.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    private val _state = MutableLiveData<State>()
    val state: LiveData<State> get() = _state

    private val _action = MutableSharedFlow<Action>()
    val action: Flow<Action> get() = _action

    init {
        _state.value = State.Input

        observeInput()
    }

    private fun observeInput() {
        email.observeForever { validateInput() }
        password.observeForever { validateInput() }
    }

    private fun validateInput() {
        val emailValue = email.value.orEmpty()
        val passwordValue = password.value.orEmpty()

        val emailValid = isEmailValid(emailValue)
        val passwordValid = isPasswordValid(passwordValue)

        _state.value = if (emailValid && passwordValid) State.CorrectInput
        else State.Input
    }

    private fun isEmailValid(email: String): Boolean {
        return EMAIL_REGEX.matches(email)
    }

    private fun isPasswordValid(password: String): Boolean {
        return password.isNotEmpty()
    }

    fun onClickLoginBtn() = navigateToHome()

    private fun navigateToHome() {
        viewModelScope.launch { _action.emit(Action.Login) }
    }

    fun onClickVkBtn() = navigateToVk()

    private fun navigateToVk() {
        viewModelScope.launch { _action.emit(Action.ToVk) }
    }

    fun onCLickOkBtn() = navigateToOk()

    private fun navigateToOk() {
        viewModelScope.launch { _action.emit(Action.ToOk) }
    }

    sealed interface State {
        object Input : State
        object CorrectInput : State
    }

    sealed interface Action {
        object ToVk : Action
        object ToOk : Action
        object Login : Action
    }

    companion object {
        private val EMAIL_REGEX = Regex("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$")

    }

}