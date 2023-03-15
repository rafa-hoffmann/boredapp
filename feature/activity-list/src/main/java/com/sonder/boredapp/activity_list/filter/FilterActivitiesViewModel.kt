package com.sonder.boredapp.activity_list.filter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sonder.boredapp.activity_list.UiState
import com.sonder.boredapp.model.data.ActivityType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FilterActivitiesViewModel : ViewModel() {
    private val _filterActivitiesState: MutableStateFlow<UiState<ActivityType?>> =
        MutableStateFlow(UiState.Initial)

    val filterActivitiesState: StateFlow<UiState<ActivityType?>> = _filterActivitiesState

    fun updateActivitiesFilter(type: ActivityType? = null) {
        viewModelScope.launch {
            _filterActivitiesState.value = UiState.Success(type)
        }
    }

    fun getCurrentActivityType() =
        (_filterActivitiesState.value as? UiState.Success<ActivityType?>)?.value
}
