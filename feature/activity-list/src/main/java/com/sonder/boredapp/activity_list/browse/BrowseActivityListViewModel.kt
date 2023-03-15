package com.sonder.boredapp.activity_list.browse

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sonder.boredapp.activity_list.UiState
import com.sonder.boredapp.common.result.Result
import com.sonder.boredapp.common.result.asResult
import com.sonder.boredapp.data.repository.ActivityRepository
import com.sonder.boredapp.model.data.ActivityResource
import com.sonder.boredapp.model.data.ActivityType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BrowseActivityListViewModel @Inject constructor(
    private val activityRepository: ActivityRepository
) : ViewModel() {
    private val _activityState: MutableStateFlow<UiState<ActivityResource>> =
        MutableStateFlow(UiState.Initial)

    val activityState: StateFlow<UiState<ActivityResource>> = _activityState

    private val _addUserActivityState: MutableStateFlow<UiState<Unit>> =
        MutableStateFlow(UiState.Initial)

    val addUserActivitiesState: StateFlow<UiState<Unit>> = _addUserActivityState

    val activityList: MutableList<ActivityResource> = mutableListOf()

    init {
        getNetworkActivities(times = INITIAL_ACTIVITIES_SIZE)
    }

    fun getNetworkActivities(type: ActivityType? = null, times: Int) {
        viewModelScope.launch {
            repeat(times) {
                activityRepository.getActivity(type).asResult().collect { activityResult ->
                    _activityState.update {
                        when (activityResult) {
                            is Result.Success -> {
                                activityList.add(activityResult.data)
                                UiState.Success(activityResult.data)
                            }
                            is Result.Error -> UiState.Error
                            is Result.Loading -> UiState.Loading
                        }
                    }
                }
            }
        }
    }

    fun addToUserActivities(activity: ActivityResource) {
        viewModelScope.launch {
            activityRepository.addUserActivity(activity).asResult().collect { addActivityResult ->
                _addUserActivityState.update {
                    when (addActivityResult) {
                        is Result.Success -> UiState.Success(addActivityResult.data)
                        is Result.Error -> UiState.Error
                        is Result.Loading -> UiState.Loading
                    }
                }
            }
        }
    }

    fun setAddActivityInitialState() {
        viewModelScope.launch {
            _addUserActivityState.value = UiState.Initial
        }
    }

    companion object {
        private const val INITIAL_ACTIVITIES_SIZE = 15
        const val CONSECUTIVE_ACTIVITIES_SIZE = 5
    }
}
