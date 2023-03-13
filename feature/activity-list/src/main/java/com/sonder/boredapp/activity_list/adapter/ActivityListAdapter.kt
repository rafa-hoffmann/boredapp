package com.sonder.boredapp.activity_list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sonder.boredapp.model.data.ActivityResource
import com.sonder.boredapp.feature.activity_list.databinding.ItemActivityBinding

class ActivityListAdapter :
    ListAdapter<ActivityResource, ActivityListAdapter.ViewHolder>(ActivityListAdapter) {

    inner class ViewHolder(private val binding: ItemActivityBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(activity: ActivityResource) {
            with(binding) {

            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding =
            ItemActivityBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(getItem(position))
    }

    private companion object : DiffUtil.ItemCallback<ActivityResource>() {
        override fun areItemsTheSame(oldItem: ActivityResource, newItem: ActivityResource) =
            oldItem.key == newItem.key

        override fun areContentsTheSame(
            oldItem: ActivityResource,
            newItem: ActivityResource
        ) = oldItem.activity == newItem.activity &&
                oldItem.price == newItem.price &&
                oldItem.participants == newItem.participants &&
                oldItem.accessibility == newItem.accessibility
    }
}
