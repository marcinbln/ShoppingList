package com.example.android.shoppingList.listdetails

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.shoppingList.database.ListItem
import com.example.android.shoppingList.databinding.ListItemBinding

class ListItemAdapter(val archive: Boolean, val clickListener: ListItemListener) : ListAdapter<ListItem,
        ListItemAdapter.ViewHolder>(ListItemDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)

        holder.bind(archive, clickListener, item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: ListItemBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(archive: Boolean, clickListener: ListItemListener, item: ListItem) {
            binding.archive = archive
            binding.item = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}

// Callback to calculate the difference between new and old list when 'submitList' executed
class ListItemDiffCallback : DiffUtil.ItemCallback<ListItem>() {
    override fun areItemsTheSame(oldItem: ListItem, newItem: ListItem): Boolean {
        return oldItem.listId == newItem.listId
    }

    override fun areContentsTheSame(oldItem: ListItem, newItem: ListItem): Boolean {
        return oldItem == newItem
    }
}

class ListItemListener(val clickListener: (view: View, itemID: Long) -> Unit) {
    fun onClick(view: View, listItem: ListItem) = clickListener(view, listItem.listId)
    fun onClickDelete(view: View, listItem: ListItem) = clickListener(view, listItem.listId)
}