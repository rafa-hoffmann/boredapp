package com.sonder.boredapp.activity_list.browse

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
import androidx.recyclerview.widget.RecyclerView
import com.sonder.boredapp.activity_list.UiState
import com.sonder.boredapp.activity_list.browse.BrowseActivityListViewModel.Companion.CONSECUTIVE_ACTIVITIES_SIZE
import com.sonder.boredapp.activity_list.browse.adapter.BrowseActivityListAdapter
import com.sonder.boredapp.feature.activity_list.R
import com.sonder.boredapp.feature.activity_list.databinding.FragmentBrowseActivityListBinding
import com.sonder.boredapp.model.data.ActivityResource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class BrowseActivityListFragment : Fragment() {

    private val viewModel: BrowseActivityListViewModel by viewModels()

    private var _binding: FragmentBrowseActivityListBinding? = null
    private val binding get() = _binding!!

    private var _adapter: BrowseActivityListAdapter? = null
    private val adapter get() = _adapter!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBrowseActivityListBinding.inflate(inflater, container, false)
        _adapter = BrowseActivityListAdapter(viewModel.activityList) {
            onItemAdd(it)
        }
        return binding.root
    }

    private fun onItemAdd(activity: ActivityResource) {
        viewModel.addToUserActivities(activity)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupRecyclerView()
        setupObservers()

        super.onViewCreated(view, savedInstanceState)
    }

    private fun setupRecyclerView() {
        binding.activityList.adapter = adapter
        binding.activityList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                if (!binding.activityScrollView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    viewModel.getNetworkActivities(times = CONSECUTIVE_ACTIVITIES_SIZE)
                }
            }
        })
    }

    private fun setupObservers() {
        viewModel.activityState.flowWithLifecycle(
            lifecycle,
            Lifecycle.State.CREATED
        ).onEach {
            when (it) {
                is UiState.Success -> {
                    adapter.notifyItemInserted(adapter.items.lastIndex)
                    updateProgressBar(loading = false)
                }
                UiState.Error -> showToast(getString(R.string.activity_state_error))
                UiState.Loading -> updateProgressBar(loading = true)
                UiState.Initial -> {}
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.addUserActivitiesState.flowWithLifecycle(
            lifecycle,
            Lifecycle.State.CREATED
        ).onEach {
            when (it) {
                is UiState.Success -> {
                    showToast(getString(R.string.activity_add_state_success))
                    viewModel.setAddActivityInitialState()
                }
                UiState.Error -> showToast(getString(R.string.activity_add_state_error))
                UiState.Loading -> {}
                UiState.Initial -> {}
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun updateProgressBar(loading: Boolean) {
        binding.activityProgressBar.isVisible = loading
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}
