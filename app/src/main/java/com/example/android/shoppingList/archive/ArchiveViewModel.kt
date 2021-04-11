package com.example.android.shoppingList.archive

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.android.shoppingList.database.ShoppingListDatabaseDao
import com.example.android.shoppingList.utils.optionsMenu
import kotlinx.coroutines.launch

class ArchiveViewModel(
        val database: ShoppingListDatabaseDao,
        application: Application) : AndroidViewModel(application) {

    // Retrieve all archived lists from the database
    val shoppingLists = database.getArchivedLists()

    // Boolean and methods to control showing the snackbar
    private var _showSnackbarEvent = MutableLiveData<Boolean>()

    val showSnackBarEvent: LiveData<Boolean>
        get() = _showSnackbarEvent

    fun doneShowingSnackbar() {
        _showSnackbarEvent.value = false
    }

    // Boolean and methods to control navigating to the detail view
    private val _navigateToListDetails = MutableLiveData<Long>()
    fun onListDetailsNavigated() {
        _navigateToListDetails.value = null
    }

    val navigateToListDetails
        get() = _navigateToListDetails

    fun onShoppingListClicked(id: Long) {
        _navigateToListDetails.value = id
    }

    // Method called from ArchiveFragment to handle overflow menu selection
    fun actionSelected(option: optionsMenu) {
        when (option) {
            optionsMenu.DELETE_ALL_ARCHIVED_LISTS -> deleteAllArchivedList()
        }
    }

    // Delete all archived lists from the database, executed on overflow menu delete option selection
    private fun deleteAllArchivedList() {
        viewModelScope.launch {
            database.clearArchive()
        }
        _showSnackbarEvent.value = true

    }
}
