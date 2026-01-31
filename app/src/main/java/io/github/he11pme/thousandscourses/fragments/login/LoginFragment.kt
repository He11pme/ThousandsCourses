package io.github.he11pme.thousandscourses.fragments.login

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import io.github.he11pme.thousandscourses.R
import io.github.he11pme.thousandscourses.databinding.FragmentLoginBinding
import kotlinx.coroutines.launch

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(layoutInflater, container, false)

        bindToViewModel()

        return binding.root
    }

    private fun bindToViewModel() {
        binding.viewModel = viewModel
        bindState()
        bindAction()
    }

    private fun bindState() {
        viewModel.state.observe(viewLifecycleOwner) {
            binding.loginBtn.isEnabled = it is LoginViewModel.State.CorrectInput
        }
    }

    private fun bindAction() {
        lifecycleScope.launch {
            viewModel.action.collect {
                when (it) {
                    LoginViewModel.Action.Login -> navigateToHomeFragment()
                    LoginViewModel.Action.ToOk -> openOk()
                    LoginViewModel.Action.ToVk -> openVk()
                }
            }
        }
    }

    private fun navigateToHomeFragment() {
        findNavController().navigate(R.id.navigateFromLoginFragmentToHomeFragment)
    }

    private fun openOk() = openLink(OK_URI)

    private fun openVk() = openLink(VK_URI)

    fun openLink(url: String) {
        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse(url)
        }

        requireContext().startActivity(intent)
    }

    companion object {
        private const val VK_URI = "https://vk.com/"
        private const val OK_URI = "https://ok.ru/"
    }
}