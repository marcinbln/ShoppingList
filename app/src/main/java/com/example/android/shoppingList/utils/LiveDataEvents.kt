package com.example.android.shoppingList.utils

sealed class LiveDataEvents {
    data class dialogEvent(val showDialog: Boolean) : LiveDataEvents()
    data class snackBarEvent(val shnowSnackbar: Boolean) : LiveDataEvents()
    data class navigateToDetailViewEvent(val shoppingList: Long?) : LiveDataEvents()
}


