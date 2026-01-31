package io.github.he11pme.thousandscourses.fragments.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import io.github.he11pme.thousandscourses.databinding.FragmentFavoritesBinding
import io.github.he11pme.thousandscourses.utils.extensions.rv.adapters.CourseAdapter
import io.github.he11pme.thousandscourses.utils.extensions.rv.decorations.CourseOffsetsDecoration

@AndroidEntryPoint
class FavoritesFragment : Fragment() {

    private lateinit var binding: FragmentFavoritesBinding
    private val viewModel: FavoritesViewModel by viewModels()

    private val adapter = CourseAdapter(::toMoreDetails, ::onClickFavoriteBtn)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.loadFavoriteCourses()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoritesBinding.inflate(layoutInflater, container, false)

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

    private fun handleState(state: FavoritesViewModel.State) {
        adapter.submitList(if (state is FavoritesViewModel.State.Loaded) state.courses else emptyList())
    }

    private fun setUpViews() {
        setUpRv()
    }

    private fun setUpRv() {
        binding.rvFavorites.apply {
            this@apply.adapter = this@FavoritesFragment.adapter
            addItemDecoration(CourseOffsetsDecoration())
        }
    }

    private fun toMoreDetails(id: Int) {

    }

    private fun onClickFavoriteBtn(id: Int) = viewModel.onClickFavoriteBtn(id)
}