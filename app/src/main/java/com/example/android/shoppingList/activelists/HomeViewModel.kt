package com.example.android.shoppingList.activelists

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.android.shoppingList.database.ShoppingList
import com.example.android.shoppingList.database.ShoppingListDatabaseDao
import com.example.android.shoppingList.utils.optionsMenu
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * ViewModel for HomeFragment.
 */
class HomeViewModel(
        val database: ShoppingListDatabaseDao,
        application: Application) : AndroidViewModel(application) {

    // Retrieve all active lists from the database
    val shoppingLists = database.getAllActiveLists()
    val dbRowCount = database.getRowCount()


    // Boolean and methods to control showing the snackbar
    private var _showSnackbarEvent = MutableLiveData<Boolean>()

    val showSnackBarEvent: LiveData<Boolean>
        get() = _showSnackbarEvent

    fun doneShowingSnackbar() {
        _showSnackbarEvent.value = false
    }

    // Boolean and methods to control showing list name dialog
    private var _showDialog = MutableLiveData<Boolean>()

    val showDialog: LiveData<Boolean>
        get() = _showDialog

    fun onFabClicked() {
        _showDialog.value = true
    }

    fun doneShowingDialog() {
        _showDialog.value = false
    }

    // Boolean and methods to control navigating to the detail view
    private val _navigateToListDetails = MutableLiveData<Long>()

    val navigateToListDetails
        get() = _navigateToListDetails

    fun onShoppingListClicked(id: Long) {
        _navigateToListDetails.value = id
    }

    fun onListDetailsNavigated() {
        _navigateToListDetails.value = null
    }

    // doneEnteringListName is called from HomeFragment once list name is entered
    // then insert suspend method saves the list in the background thread
    fun doneEnteringListName(name: String) {
        viewModelScope.launch {
            // Create a new ShoppingList and save it with the name from the dialog
            val newShoppingList = ShoppingList().apply { this.listName = name }
            insert(newShoppingList)
        }
    }

    private suspend fun insert(shoppingList: ShoppingList) {
        withContext(Dispatchers.IO) {
            database.insert(shoppingList)
        }
    }

    // Method called from HomeFragment to handle overflow menu selection
    fun actionSelected(option: optionsMenu) {
        when (option) {
            optionsMenu.DELETE_ALL_ACITVE_LISTS -> deleteAllActiveList()
        }
    }

    // Delete all active lists from the database, executed on overflow menu delete option selection
    private fun deleteAllActiveList() {
        viewModelScope.launch {
            database.clearAllActive()
        }
        _showSnackbarEvent.value = true
    }
}

