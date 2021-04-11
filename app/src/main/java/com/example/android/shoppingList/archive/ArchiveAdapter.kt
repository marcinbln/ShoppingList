package com.example.android.shoppingList.archive

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.shoppingList.database.ShoppingList
import com.example.android.shoppingList.databinding.ListItemArchiveBinding

class ArchiveAdapter(val clickListener: ArchiveListener) : ListAdapter<ShoppingList,
        ArchiveAdapter.ViewHolder>(ArchiveDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(clickListener, item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: ListItemArchiveBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(clickListener: ArchiveListener, item: ShoppingList) {
            binding.list = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemArchiveBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}

// Callback to calculate the difference between new and old list when 'submitList' executed
class ArchiveDiffCallback : DiffUtil.ItemCallback<ShoppingList>() {
    override fun areItemsTheSame(oldItem: ShoppingList, newItem: ShoppingList): Boolean {
        return oldItem.listId == newItem.listId
    }

    override fun areContentsTheSame(oldItem: ShoppingList, newItem: ShoppingList): Boolean {
        return oldItem == newItem
    }
}

class ArchiveListener(val clickListener: (listID: Long) -> Unit) {
    fun onClick(shoppingList: ShoppingList) = clickListener(shoppingList.listId)
}