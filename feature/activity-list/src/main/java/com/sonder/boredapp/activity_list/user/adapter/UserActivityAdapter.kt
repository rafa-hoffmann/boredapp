package com.sonder.boredapp.activity_list.user.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.sonder.boredapp.activity_list.getDisplayText
import com.sonder.boredapp.feature.activity_list.R
import com.sonder.boredapp.feature.activity_list.databinding.ItemActivityBinding
import com.sonder.boredapp.model.data.ActivityResource

class UserActivityAdapter() :
    ListAdapter<ActivityResource, UserActivityAdapter.UserActivityViewHolder>(UserActivityAdapter) {

    inner class UserActivityViewHolder(private val binding: ItemActivityBinding) :
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

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): UserActivityViewHolder {
        val itemBinding =
            ItemActivityBinding.inflate(
                LayoutInflater.from(viewGroup.context),
                viewGroup,
                false
            )
        return UserActivityViewHolder(itemBinding)
    }

    override fun onBindViewHolder(viewHolder: UserActivityViewHolder, position: Int) {
        viewHolder.bind(getItem(position))
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
