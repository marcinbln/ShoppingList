package com.example.android.shoppingList.listdetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.shoppingList.database.ListItem
import com.example.android.shoppingList.database.ListItemDatabaseDao
import com.example.android.shoppingList.database.ShoppingList
import com.example.android.shoppingList.utils.optionsMenu
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailViewModel(
        private val shoppingListKey: Long = 0L,
        dataSource: ListItemDatabaseDao) : ViewModel() {

    // Retrieve all list items from the database
    val items = dataSource.getItemsByListKey(shoppingListKey)

    val database = dataSource

    var shoppingList = MutableLiveData<ShoppingList?>()

    val dbRowCount = database.getRowCount(shoppingListKey)


    init {
        initializeSelectedList()
    }

    private fun initializeSelectedList() {
        viewModelScope.launch {
            shoppingList.value = database.getCurrentList(shoppingListKey)
        }
    }

    // Boolean and methods to control up button navigation
    private val _navigateUp = MutableLiveData<Boolean?>()

    val navigateUp: LiveData<Boolean?>
        get() = _navigateUp

    fun onListArchivedAndDeleted() {
        _navigateUp.value = true
    }

    // Boolean and methods to control showing list item dialog
    private var _showDialog = MutableLiveData<Boolean>()
    val showDialog: LiveData<Boolean>
        get() = _showDialog

    fun onFabClicked() {
        _showDialog.value = true
    }

    fun doneShowingDialog() {
        _showDialog.value = false
    }

    // doneEnteringItemName is called from DetailFragment once item name is entered
    // then insert suspend method saves the item on the background thread
    fun doneEnteringItem(name: String, quantity: Int) {
        viewModelScope.launch {
            // Create a new list item and insert it into database
            val listItem = ListItem().apply {
                this.itemName = name
                this.quantity = quantity
                this.listKey = shoppingListKey

            }
            insert(listItem)
        }
    }

    private suspend fun insert(listItem: ListItem) {
        withContext(Dispatchers.IO) {
            database.insert(listItem)
        }
    }

    // Method called from DetailFragment to handle overflow menu selection
    fun actionSelected(option: optionsMenu) {
        when (option) {
            optionsMenu.DELETE_LIST -> deleteCurrentList()
            optionsMenu.ARCHIVE_LIST -> archiveCurrentList()
        }
    }

    // Delete open list from the database, executed on overflow menu "Delete list" selection
    private fun deleteCurrentList() {
        viewModelScope.launch {
            database.deleteListWithID(shoppingListKey)
        }
        onListArchivedAndDeleted()
    }

    // Archive open list, executed on overflow menu "Archive list" selection
    private fun archiveCurrentList() {
        viewModelScope.launch {
            database.updateRow(shoppingListKey, true)
        }
        onListArchivedAndDeleted()
    }

    // Called from DetailFragment when 'tick' image is tapped
    fun onImageClicked(id: Long) {
        addToBasket(id)
    }

    // Update database to add / remove item from the basket
    private fun addToBasket(id: Long) {
        viewModelScope.launch {
            val isInBasket = database.checkInBasket(id)
            if (isInBasket) {
                database.updateItem(id, false)
            } else {
                database.updateItem(id, true)
            }
        }
    }

    // Called from DetailFragment when 'bin' image is tapped
    fun onItemDeleteClicked(id: Long) {
        deleteItem(id)
    }

    // Delete from the database list item with the selected ID
    private fun deleteItem(id: Long) {
        viewModelScope.launch {
            database.deleteItemWithID(id)
        }
    }


}

 
