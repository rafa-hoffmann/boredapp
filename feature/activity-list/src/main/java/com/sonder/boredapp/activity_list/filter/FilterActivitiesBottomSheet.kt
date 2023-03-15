package com.sonder.boredapp.activity_list.filter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.sonder.boredapp.activity_list.filter.adapter.FilterActivitiesAdapter
import com.sonder.boredapp.feature.activity_list.databinding.BottomSheetFilterActivitiesBinding
import com.sonder.boredapp.model.data.ActivityType

class FilterActivitiesBottomSheet : BottomSheetDialogFragment() {
    private val filterViewModel: FilterActivitiesViewModel by activityViewModels()

    private var _binding: BottomSheetFilterActivitiesBinding? = null
    private val binding get() = _binding!!

    private var _adapter: FilterActivitiesAdapter? = null
    private val adapter get() = _adapter!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomSheetFilterActivitiesBinding.inflate(inflater, container, false)
        _adapter =
            FilterActivitiesAdapter(ActivityType.values().toList()) { onActivityTypeClicked(it) }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupRecyclerView()
        setupButtons()

        super.onViewCreated(view, savedInstanceState)
    }

    private fun setupRecyclerView() {
        binding.activityTypeList.adapter = adapter
    }

    private fun setupButtons() {
        binding.activityTypeClear.setOnClickListener {
            onActivityTypeClicked(null)
        }
    }

    private fun onActivityTypeClicked(activityType: ActivityType?) {
        filterViewModel.updateActivitiesFilter(activityType)
        dismiss()
    }

    companion object {
        const val TAG = "FilterActivitiesBottomSheet"
    }
}
