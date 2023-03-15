package com.sonder.boredapp.activity_list.user

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
import com.sonder.boredapp.activity_list.UiState
import com.sonder.boredapp.activity_list.filter.FilterActivitiesViewModel
import com.sonder.boredapp.activity_list.filter.showFilterList
import com.sonder.boredapp.activity_list.user.adapter.UserActivityAdapter
import com.sonder.boredapp.activity_list.user.adapter.UserActivityListener
import com.sonder.boredapp.feature.activity_list.R
import com.sonder.boredapp.feature.activity_list.databinding.FragmentUserActivitiesBinding
import com.sonder.boredapp.model.data.ActivityResource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UserActivitiesFragment : Fragment(), UserActivityListener {

    private val viewModel: UserActivitiesViewModel by viewModels()
    private val filterViewModel: FilterActivitiesViewModel by activityViewModels()

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
        _adapter = UserActivityAdapter(this)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupRecyclerView()
        setupObservers()
        setupFilterButton()

        viewModel.getUserActivities(filterViewModel.getCurrentActivityType())

        super.onViewCreated(view, savedInstanceState)
    }

    private fun setupFilterButton() {
        binding.userActivityFilter.setOnClickListener {
            showFilterList()
        }
    }

    private fun setupRecyclerView() {
        binding.userActivitiesList.adapter = adapter
    }

    private fun setupObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {

                launch {
                    viewModel.userActivitiesState.collect {
                        when (it) {
                            is UiState.Success -> {
                                adapter.submitList(it.value)
                                updateProgressBar(loading = false)
                            }
                            is UiState.Error -> {
                                updateProgressBar(loading = false)
                                showToast(
                                    it.exception?.message
                                        ?: getString(R.string.activity_state_error)
                                )
                            }
                            UiState.Loading -> updateProgressBar(loading = true)
                            UiState.Initial -> updateProgressBar(loading = false)
                        }
                    }
                }

                launch {
                    viewModel.updateUserActivityStatus.collect {
                        when (it) {
                            is UiState.Success -> {
                                showToast(getString(R.string.activity_status_update_success))
                                updateProgressBar(loading = false)
                                viewModel.setUpdateActivityInitialState()
                            }
                            is UiState.Error -> {
                                updateProgressBar(loading = false)
                                showToast(
                                    it.exception?.message
                                        ?: getString(R.string.activity_status_update_error)
                                )
                            }
                            UiState.Loading -> updateProgressBar(loading = true)
                            UiState.Initial -> updateProgressBar(loading = false)
                        }
                    }
                }

                launch {
                    filterViewModel.filterActivitiesState.collect {
                        if (it is UiState.Success) {
                            viewModel.getUserActivities(it.value)
                        }
                    }
                }
            }
        }
    }

    private fun updateProgressBar(loading: Boolean) {
        binding.userActivitiesProgressBar.isVisible = loading
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onPlay(activityResource: ActivityResource) {
        viewModel.startActivity(activityResource, filterViewModel.getCurrentActivityType())
    }

    override fun onStop(activityResource: ActivityResource) {
        viewModel.stopActivity(activityResource, filterViewModel.getCurrentActivityType())
    }

    override fun onWithdraw(activityResource: ActivityResource) {
        viewModel.withdrawActivity(activityResource, filterViewModel.getCurrentActivityType())
    }
}
