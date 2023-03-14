package com.sonder.boredapp.activity_list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.sonder.boredapp.activity_list.getDisplayText
import com.sonder.boredapp.feature.activity_list.R
import com.sonder.boredapp.model.data.ActivityResource
import com.sonder.boredapp.feature.activity_list.databinding.ItemActivityBinding

class ActivityListAdapter(val items: MutableList<ActivityResource>) :
    RecyclerView.Adapter<ActivityListAdapter.ActivityViewHolder>() {
    inner class ActivityViewHolder(private val binding: ItemActivityBinding) :
        ViewHolder(binding.root) {

        fun bind(activity: ActivityResource) {
            with(binding) {
                activityName.text = activity.activity
                activityType.text = root.context.getString(activity.type.getDisplayText())

                activityPrice.text = root.context.getString(R.string.activity_price, activity.price)
                activityAccessibility.text =
                    root.context.getString(R.string.activity_accessibility, activity.accessibility)
                activityParticipants.text =
                    root.context.getString(R.string.activity_participants, activity.participants)
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ActivityViewHolder {
        val itemBinding =
            ItemActivityBinding.inflate(
                LayoutInflater.from(viewGroup.context),
                viewGroup,
                false
            )
        return ActivityViewHolder(itemBinding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(viewHolder: ActivityViewHolder, position: Int) {
        viewHolder.bind(items[position])
    }

    private companion object : DiffUtil.ItemCallback<ActivityResource>() {
        override fun areItemsTheSame(oldItem: ActivityResource, newItem: ActivityResource) =
            oldItem.key == newItem.key

        override fun areContentsTheSame(
            oldItem: ActivityResource,
            newItem: ActivityResource
        ) = oldItem == newItem
    }
}
