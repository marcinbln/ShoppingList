package com.example.android.shoppingList.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "list_item_table")
data class ListItem(
        @PrimaryKey(autoGenerate = true)
        var listId: Long = 0L,

        @ColumnInfo(name = "list_key")
        var listKey: Long = 0L,

        @ColumnInfo(name = "item_name")
        var itemName: String = "",

        @ColumnInfo(name = "basket")
        var isInBasket: Boolean = false,

        @ColumnInfo(name = "quantity")
        var quantity: Int = 0

)


