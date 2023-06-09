package com.sonder.boredapp.activity_list.browse

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.RecyclerView
import com.sonder.boredapp.activity_list.UiState
import com.sonder.boredapp.activity_list.browse.BrowseActivityListViewModel.Companion.CONSECUTIVE_ACTIVITIES_SIZE
import com.sonder.boredapp.activity_list.browse.adapter.BrowseActivityListAdapter
import com.sonder.boredapp.activity_list.filter.FilterActivitiesViewModel
import com.sonder.boredapp.activity_list.filter.showFilterList
import com.sonder.boredapp.feature.activity_list.R
import com.sonder.boredapp.feature.activity_list.databinding.FragmentBrowseActivityListBinding
import com.sonder.boredapp.model.data.ActivityResource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BrowseActivityListFragment : Fragment() {

    private val viewModel: BrowseActivityListViewModel by viewModels()
    private val filterViewModel: FilterActivitiesViewModel by activityViewModels()

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
        setupFilterButton()

        super.onViewCreated(view, savedInstanceState)
    }

    private fun setupFilterButton() {
        binding.activityFilter.setOnClickListener {
            showFilterList()
        }
    }

    private fun setupRecyclerView() {
        binding.activityList.adapter = adapter
        binding.activityList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                if (!binding.activityScrollView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    viewModel.getNetworkActivities(
                        filterViewModel.getCurrentActivityType(),
                        times = CONSECUTIVE_ACTIVITIES_SIZE
                    )
                }
            }
        })
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setupObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {

                launch {
                    viewModel.networkActivityState.collect {
                        when (it) {
                            is UiState.Success -> {
                                adapter.notifyItemInserted(adapter.items.lastIndex)
                                updateProgressBar(loading = false)
                            }
                            is UiState.Error -> {
                                showToast(
                                    it.exception?.message
                                        ?: getString(R.string.activity_state_error)
                                )
                                updateProgressBar(loading = true)
                            }
                            UiState.Loading -> updateProgressBar(loading = true)
                            else -> {}
                        }
                    }
                }

                launch {
                    viewModel.addUserActivitiesState.collect {
                        when (it) {
                            is UiState.Success -> {
                                showToast(getString(R.string.activity_add_state_success))
                                viewModel.setAddActivityInitialState()
                            }
                            is UiState.Error -> showToast(
                                it.exception?.message
                                    ?: getString(R.string.activity_add_state_error)
                            )
                            else -> {}
                        }
                    }
                }

                launch {
                    viewModel.lastFilteredType.collect {
                        when (it) {
                            is UiState.Success -> {
                                adapter.notifyDataSetChanged()
                            }
                            else -> {}
                        }
                    }
                }

                launch {
                    filterViewModel.filterActivitiesState.collect {
                        if (it is UiState.Success) {
                            if (it.value != viewModel.getLastFilteredTypeSuccess()) {
                                viewModel.updateFilter(it.value)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun updateProgressBar(loading: Boolean) {
        binding.activityProgressBar.isVisible = loading
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}
