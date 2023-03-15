package com.sonder.boredapp.activity_list.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sonder.boredapp.activity_list.UiState
import com.sonder.boredapp.common.result.Result
import com.sonder.boredapp.common.result.asResult
import com.sonder.boredapp.data.repository.ActivityRepository
import com.sonder.boredapp.model.data.ActivityResource
import com.sonder.boredapp.model.data.ActivityStatus
import com.sonder.boredapp.model.data.ActivityType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import javax.inject.Inject

@HiltViewModel
class UserActivitiesViewModel @Inject constructor(
    private val activityRepository: ActivityRepository
) : ViewModel() {
    private val _userActivitiesState: MutableStateFlow<UiState<List<ActivityResource>>> =
        MutableStateFlow(UiState.Initial)

    val userActivitiesState: StateFlow<UiState<List<ActivityResource>>> = _userActivitiesState

    private val _updateUserActivityStatus: MutableStateFlow<UiState<Unit>> =
        MutableStateFlow(UiState.Initial)

    val updateUserActivityStatus: StateFlow<UiState<Unit>> = _updateUserActivityStatus

    fun getUserActivities(type: ActivityType?) {
        viewModelScope.launch {
            activityRepository.getUserActivities(type).asResult().collect { activityResult ->
                _userActivitiesState.update {
                    when (activityResult) {
                        is Result.Success -> UiState.Success(activityResult.data)
                        is Result.Error -> UiState.Error(activityResult.exception)
                        is Result.Loading -> UiState.Loading
                    }
                }
            }
        }
    }

    private fun updateActivityStatus(activityResource: ActivityResource, type: ActivityType?) {
        viewModelScope.launch {
            activityRepository.updateActivityStatus(activityResource).asResult()
                .collect { activityResult ->
                    _updateUserActivityStatus.update {
                        when (activityResult) {
                            is Result.Success -> {
                                getUserActivities(type)
                                UiState.Success(Unit)
                            }
                            is Result.Error -> UiState.Error(activityResult.exception)
                            is Result.Loading -> UiState.Loading
                        }
                    }
                }
        }
    }

    fun setUpdateActivityInitialState() {
        _updateUserActivityStatus.value = UiState.Initial
    }

    fun startActivity(activityResource: ActivityResource, type: ActivityType?) {
        val newActivity = activityResource.copy(
            status = ActivityStatus.InProgress(
                startTime = Clock.System.now()
            )
        )
        updateActivityStatus(newActivity, type)
    }

    fun stopActivity(activityResource: ActivityResource, type: ActivityType?) {
        val startTime = (activityResource.status as ActivityStatus.InProgress).startTime
        val newActivity = activityResource.copy(
            status = ActivityStatus.Finished(
                startTime = startTime,
                finishTime = Clock.System.now()
            )
        )
        updateActivityStatus(newActivity, type)
    }

    fun withdrawActivity(activityResource: ActivityResource, type: ActivityType?) {
        val newActivity = activityResource.copy(status = ActivityStatus.Withdrawal)
        updateActivityStatus(newActivity, type)
    }
}
