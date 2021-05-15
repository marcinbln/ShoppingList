package com.example.android.shoppingList.activelists

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.android.shoppingList.database.ShoppingList
import com.example.android.shoppingList.database.ShoppingListDatabaseDao
import com.example.android.shoppingList.utils.LiveDataEvents
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

    var _eventsToObserve: MutableLiveData<LiveDataEvents> = MutableLiveData()

    val eventsToObserve: MutableLiveData<LiveDataEvents>
        get() = _eventsToObserve

    fun snackbarReset() {
        _eventsToObserve.value = LiveDataEvents.snackBarEvent(false)
    }

    // Boolean and methods to control showing list name dialog
    fun onFabClicked() {
        _eventsToObserve.value = LiveDataEvents.dialogEvent(true)
    }

    fun dialogReset() {
        _eventsToObserve.value = LiveDataEvents.dialogEvent(false)
    }

    fun onShoppingListClicked(id: Long) {
        _eventsToObserve.value = LiveDataEvents.navigateToDetailViewEvent(id)
    }

    fun detailNavigationReset() {
        _eventsToObserve.value = LiveDataEvents.navigateToDetailViewEvent(null)
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
        _eventsToObserve.value = LiveDataEvents.snackBarEvent(true)
    }
}

