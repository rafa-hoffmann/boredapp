package com.sonder.boredapp.activity_list

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
import com.sonder.boredapp.activity_list.adapter.ActivityListAdapter
import com.sonder.boredapp.feature.activity_list.R
import com.sonder.boredapp.feature.activity_list.databinding.FragmentActivityListBinding
import com.sonder.boredapp.model.data.ActivityResource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class ActivityListFragment : Fragment() {

    private val viewModel: ActivityListViewModel by viewModels()

    private var _binding: FragmentActivityListBinding? = null
    private val binding get() = _binding!!

    private var _adapter: ActivityListAdapter? = null
    private val adapter get() = _adapter!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentActivityListBinding.inflate(inflater, container, false)
        _adapter = ActivityListAdapter(mutableListOf()) {
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

        viewModel.getNetworkActivities(times = INITIAL_ACTIVITIES_SIZE)

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
            Lifecycle.State.STARTED
        ).onEach {
            when (it) {
                is UiState.Success -> {
                    adapter.items.add(it.value)
                    adapter.notifyItemInserted(adapter.items.lastIndex)
                    updateProgressBar(loading = false)
                }
                UiState.Error -> showToast(getString(R.string.activity_state_error))
                UiState.Loading -> updateProgressBar(loading = true)
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.addUserActivitiesState.flowWithLifecycle(
            lifecycle,
            Lifecycle.State.STARTED
        ).onEach {
            when (it) {
                is UiState.Success -> showToast(getString(R.string.activity_add_state_success))
                UiState.Error -> showToast(getString(R.string.activity_add_state_error))
                UiState.Loading -> updateProgressBar(loading = true)
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
