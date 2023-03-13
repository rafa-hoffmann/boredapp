package com.sonder.boredapp.activity_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sonder.boredapp.data.repository.ActivityRepository
import com.sonder.boredapp.model.data.ActivityResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ActivityListViewModel @Inject constructor(
    private val activityRepository: ActivityRepository
) : ViewModel() {
    private val _activityState: MutableStateFlow<ActivityUiState> =
        MutableStateFlow(ActivityUiState.Loading)

    val activityState: StateFlow<ActivityUiState> = _activityState

    fun getActivity() {
        viewModelScope.launch {

        }
    }
}

sealed interface ActivityUiState {
    object Loading : ActivityUiState
    data class Success(val activities: List<ActivityResource>) : ActivityUiState
    object Error : ActivityUiState
}
