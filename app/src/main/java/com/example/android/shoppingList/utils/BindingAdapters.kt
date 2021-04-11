package com.example.android.shoppingList.utils

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.shoppingList.R
import com.example.android.shoppingList.activelists.HomeAdapter
import com.example.android.shoppingList.archive.ArchiveAdapter
import com.example.android.shoppingList.database.ListItem
import com.example.android.shoppingList.database.ShoppingList
import com.example.android.shoppingList.listdetails.ListItemAdapter

// Bind active list
@BindingAdapter("activeList")
fun bindRecyclerViewActive(recyclerView: RecyclerView, data: List<ShoppingList>?) {
    val adapter = recyclerView.adapter as HomeAdapter
    adapter.submitList(data)
}

// Bind list of items in a detail view
@BindingAdapter("itemsData")
fun bindRecyclerViewItems(recyclerView: RecyclerView, data: List<ListItem>?) {
    val adapter = recyclerView.adapter as ListItemAdapter
    adapter.submitList(data)
}

// Bind list in archive view
@BindingAdapter("archiveData")
fun bindRecyclerViewArchive(recyclerView: RecyclerView, data: List<ShoppingList>?) {
    val adapter = recyclerView.adapter as ArchiveAdapter
    adapter.submitList(data)
}

// Bind shopping list  icon
@BindingAdapter("listImage")
fun bindShoppingListIcon(imageView: ImageView, item: ShoppingList?) {
    item?.let {
        imageView.setImageResource(when (item.isArchived) {
            true -> R.drawable.ic_full_trolley
            else -> R.drawable.outline_shopping_cart_24
        })
    }
}

// Bind shopping list subtitle that shows a date
@BindingAdapter("listDate")
fun bindSubtitle(textView: TextView, item: ShoppingList?) {
    item?.let {
        textView.text = convertEpochtoString(item.startTimeMilli)
    }
}

// Bind shopping list item icon
@BindingAdapter("itemImage")
fun setItemImage(imageView: ImageView, item: ListItem?) {
    item?.let {
        imageView.setImageResource(when (item.isInBasket) {
            true -> R.drawable.check_circle_outline_on
            else -> R.drawable.check_circle_outline_off
        })
    }
}




