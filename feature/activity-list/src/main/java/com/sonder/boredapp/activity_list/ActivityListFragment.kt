package com.sonder.boredapp.activity_list

import android.os.Bundle
import android.util.Log
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
        _adapter = ActivityListAdapter(mutableListOf())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupRecyclerView()
        setupObservers()

        viewModel.getActivities(times = INITIAL_ACTIVITIES_SIZE)

        super.onViewCreated(view, savedInstanceState)
    }

    private fun setupRecyclerView() {
        binding.activityList.adapter = adapter
        binding.activityList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                if (!binding.activityScrollView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    viewModel.getActivities(times = CONSECUTIVE_ACTIVITIES_SIZE)
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
                is ActivityUiState.Success -> {
                    adapter.items.add(it.activity)
                    adapter.notifyItemInserted(adapter.items.lastIndex)
                    updateProgressBar(loading = false)
                }
                ActivityUiState.Error -> showErrorToast(getString(R.string.activity_state_error))
                ActivityUiState.Loading -> updateProgressBar(loading = true)
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun updateProgressBar(loading: Boolean) {
        binding.activityProgressBar.isVisible = loading
    }

    private fun showErrorToast(errorMessage: String) {
        Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
    }
}
