package com.sonder.boredapp.activity_list.user

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
class UserActivitiesViewModel @Inject constructor(
    private val activityRepository: ActivityRepository
) : ViewModel() {
    private val _userActivitiesState: MutableStateFlow<UiState<List<ActivityResource>>> =
        MutableStateFlow(UiState.Loading)

    val userActivitiesState: StateFlow<UiState<List<ActivityResource>>> = _userActivitiesState

    fun getUserActivities(type: ActivityType? = null) {
        viewModelScope.launch {
            activityRepository.getUserActivities(type).asResult().collect { activityResult ->
                _userActivitiesState.update {
                    when (activityResult) {
                        is Result.Success -> UiState.Success(activityResult.data)
                        is Result.Error -> UiState.Error
                        is Result.Loading -> UiState.Loading
                    }
                }
            }
        }
    }
}
