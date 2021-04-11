package com.example.android.shoppingList.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ShoppingListDatabaseDao {

    @Insert
    suspend fun insert(night: ShoppingList)

    @Query("DELETE FROM shopping_lists_table WHERE archived=0")
    suspend fun clearAllActive()

    @Query("DELETE FROM shopping_lists_table WHERE archived=1")
    suspend fun clearArchive()

    @Query("SELECT * FROM shopping_lists_table WHERE archived=0 ORDER BY listId DESC")
    fun getAllActiveLists(): LiveData<List<ShoppingList>>

    @Query("SELECT * FROM shopping_lists_table WHERE archived=1 ORDER BY listId DESC")
    fun getArchivedLists(): LiveData<List<ShoppingList>>

    @Query("SELECT COUNT(listId) FROM shopping_lists_table WHERE archived=0 ")
    fun getRowCount(): LiveData<Int?>?

    @Query("SELECT COUNT(listId) FROM shopping_lists_table WHERE archived=1")
    fun getRowCountArchived(): LiveData<Int?>?
}

