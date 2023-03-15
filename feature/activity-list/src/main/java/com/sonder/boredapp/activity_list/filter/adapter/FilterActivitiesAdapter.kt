package com.sonder.boredapp.activity_list.filter.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sonder.boredapp.activity_list.getDisplayText
import com.sonder.boredapp.feature.activity_list.databinding.ItemActivityTypeBinding
import com.sonder.boredapp.model.data.ActivityType

class FilterActivitiesAdapter(
    private val items: List<ActivityType>,
    private val onClick: (ActivityType) -> (Unit)
) :
    RecyclerView.Adapter<FilterActivitiesAdapter.ActivityTypeViewHolder>() {

    inner class ActivityTypeViewHolder(private val binding: ItemActivityTypeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(type: ActivityType) {
            with(binding) {
                activityType.text = root.context.getString(type.getDisplayText())
                activityType.setOnClickListener { onClick(type) }
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ActivityTypeViewHolder {
        val itemBinding =
            ItemActivityTypeBinding.inflate(
                LayoutInflater.from(viewGroup.context),
                viewGroup,
                false
            )
        return ActivityTypeViewHolder(itemBinding)
    }

    override fun onBindViewHolder(viewHolder: ActivityTypeViewHolder, position: Int) {
        viewHolder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}
