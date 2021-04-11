package com.example.android.shoppingList.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ListItemDatabaseDao {

    @Insert
    suspend fun insert(item: ListItem)

    @Query("SELECT * from list_item_table  WHERE list_key = :key ORDER BY listId DESC")
    fun getItemsByListKey(key: Long): LiveData<List<ListItem>>

    @Query("SELECT basket from list_item_table WHERE listId = :key")
    suspend fun checkInBasket(key: Long): Boolean

    @Query("DELETE from shopping_lists_table WHERE listId = :key")
    suspend fun deleteListWithID(key: Long)

    @Query("DELETE from list_item_table WHERE listId = :key")
    suspend fun deleteItemWithID(key: Long)

    @Query("SELECT * from shopping_lists_table WHERE listId = :key")
    suspend fun getCurrentList(key: Long): ShoppingList

    @Query("UPDATE shopping_lists_table SET archived =:isArchived WHERE listId =:listID")
    suspend fun updateRow(listID: Long?, isArchived: Boolean?): Int

    @Query("UPDATE list_item_table SET basket =:isInBasket WHERE listId =:listID")
    suspend fun updateItem(listID: Long?, isInBasket: Boolean?): Int

    @Query("SELECT COUNT(listId)  from list_item_table  WHERE list_key = :key")
    fun getRowCount(key: Long): LiveData<Int?>?

}

