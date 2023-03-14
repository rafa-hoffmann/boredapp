package com.sonder.boredapp.activity_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sonder.boredapp.common.result.asResult
import com.sonder.boredapp.common.result.Result
import com.sonder.boredapp.data.repository.ActivityRepository
import com.sonder.boredapp.model.data.ActivityResource
import com.sonder.boredapp.model.data.ActivityType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

const val INITIAL_ACTIVITIES_SIZE = 30
const val CONSECUTIVE_ACTIVITIES_SIZE = 10

@HiltViewModel
class ActivityListViewModel @Inject constructor(
    private val activityRepository: ActivityRepository
) : ViewModel() {
    private val _activityState: MutableStateFlow<ActivityUiState> =
        MutableStateFlow(ActivityUiState.Loading)

    val activityState: StateFlow<ActivityUiState> = _activityState

    fun getActivities(type: ActivityType? = null, times: Int) {
        viewModelScope.launch {
            repeat(times) {
                activityRepository.getActivity(type).asResult().collect { activityResult ->
                    _activityState.update {
                        when (activityResult) {
                            is Result.Success -> ActivityUiState.Success(activityResult.data)
                            is Result.Error -> ActivityUiState.Error
                            is Result.Loading -> ActivityUiState.Loading
                        }
                    }
                }
            }
        }
    }
}

sealed interface ActivityUiState {
    object Loading : ActivityUiState
    data class Success(val activity: ActivityResource) : ActivityUiState
    object Error : ActivityUiState
}
