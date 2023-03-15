package com.sonder.boredapp.activity_list.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.sonder.boredapp.activity_list.UiState
import com.sonder.boredapp.activity_list.user.adapter.UserActivityAdapter
import com.sonder.boredapp.feature.activity_list.R
import com.sonder.boredapp.feature.activity_list.databinding.FragmentUserActivitiesBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class UserActivitiesFragment : Fragment() {
    private val viewModel: UserActivitiesViewModel by viewModels()

    private var _binding: FragmentUserActivitiesBinding? = null
    private val binding get() = _binding!!

    private var _adapter: UserActivityAdapter? = null
    private val adapter get() = _adapter!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserActivitiesBinding.inflate(inflater, container, false)
        _adapter = UserActivityAdapter()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupRecyclerView()
        setupObservers()

        viewModel.getUserActivities()

        super.onViewCreated(view, savedInstanceState)
    }

    private fun setupRecyclerView() {
        binding.userActivitiesList.adapter = adapter
    }

    private fun setupObservers() {
        viewModel.userActivitiesState.flowWithLifecycle(
            lifecycle,
            Lifecycle.State.STARTED
        ).onEach {
            when (it) {
                is UiState.Success -> {
                    adapter.submitList(it.value)
                    updateProgressBar(loading = false)
                }
                UiState.Error -> showToast(getString(R.string.activity_state_error))
                UiState.Loading -> updateProgressBar(loading = true)
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun updateProgressBar(loading: Boolean) {
        binding.userActivitiesProgressBar.isVisible = loading
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}
