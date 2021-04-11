package com.example.android.shoppingList.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shopping_lists_table")
data class ShoppingList(
        @PrimaryKey(autoGenerate = true)
        var listId: Long = 0L,

        @ColumnInfo(name = "start_time_milli")
        val startTimeMilli: Long = System.currentTimeMillis(),

        @ColumnInfo(name = "list_name")
        var listName: String = "",

        @ColumnInfo(name = "archived")
        var isArchived: Boolean = false
)
