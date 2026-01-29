package io.github.he11pme.thousandscourses.fragments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import io.github.he11pme.thousandscourses.databinding.FragmentHomeBinding
import io.github.he11pme.thousandscourses.utils.extensions.rv.adapters.CourseAdapter
import io.github.he11pme.thousandscourses.utils.extensions.rv.decorations.CourseOffsetsDecoration

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModels()
    private val adapter = CourseAdapter(::toMoreDetails, ::onClickFavoriteBtn)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.loadCourses()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)

        bindToViewModel()
        setUpViews()

        return binding.root
    }

    private fun bindToViewModel() {
        bindState()
    }

    private fun bindState() {
        viewModel.state.observe(viewLifecycleOwner, ::handleState)
    }

    private fun handleState(state: HomeViewModel.State) {
        if (state is HomeViewModel.State.Loaded) adapter.submitList(state.courses)
    }

    private fun setUpViews() {
        setUpRv()
    }

    private fun setUpRv() {
        binding.rvHome.apply {
            this@apply.adapter = this@HomeFragment.adapter
            addItemDecoration(CourseOffsetsDecoration())
        }
    }

    private fun toMoreDetails(id: Int) {

    }

    private fun onClickFavoriteBtn(id: Int) {

    }
}