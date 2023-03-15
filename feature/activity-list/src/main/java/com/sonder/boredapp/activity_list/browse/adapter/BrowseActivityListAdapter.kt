package com.sonder.boredapp.activity_list.browse.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.sonder.boredapp.activity_list.setup
import com.sonder.boredapp.feature.activity_list.databinding.ItemActivityBrowseBinding
import com.sonder.boredapp.model.data.ActivityResource

class BrowseActivityListAdapter(
    val items: MutableList<ActivityResource>,
    private val onItemAdd: (ActivityResource) -> (Unit)
) :
    RecyclerView.Adapter<BrowseActivityListAdapter.ActivityViewHolder>() {
    inner class ActivityViewHolder(private val binding: ItemActivityBrowseBinding) :
        ViewHolder(binding.root) {

        fun bind(activity: ActivityResource) {
            with(binding) {
                itemActivity.setup(activity)

                activityAdd.setOnClickListener {
                    onItemAdd(activity)
                }
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ActivityViewHolder {
        val itemBinding =
            ItemActivityBrowseBinding.inflate(
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
}
