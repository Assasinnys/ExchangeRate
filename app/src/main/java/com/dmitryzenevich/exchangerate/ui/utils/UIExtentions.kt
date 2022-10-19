package com.dmitryzenevich.exchangerate.ui.utils

import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.dmitryzenevich.exchangerate.ui.adapter.ListState
import com.dmitryzenevich.exchangerate.ui.adapter.RecyclerViewItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

fun AppCompatActivity.findNavController(fragmentId: Int) =
    (supportFragmentManager.findFragmentById(fragmentId) as NavHostFragment).navController

val StateFlow<ListState>.currentList
    get() = value.list

val StateFlow<ListState>.mutableList
    get() = value.list.toMutableList()

fun MutableStateFlow<ListState>.clear() {
    value = ListState.Clear
}

fun MutableStateFlow<ListState>.updateList(newList: List<RecyclerViewItem>) {
    value = ListState.Update(newList)
}

fun MutableStateFlow<ListState>.addPage(newPageList: List<RecyclerViewItem>) {
    val data = mutableList.apply {
        addAll(newPageList)
    }
    updateList(data)
}

fun MutableStateFlow<ListState>.addItem(item: RecyclerViewItem, position: Int? = null) {
    val data = mutableList.apply { position?.let{ add(it, item) } ?: add(item)}
    updateList(data)
}

fun MutableStateFlow<ListState>.removeItemsIf(predicate: (RecyclerViewItem) -> Boolean) {
    mutableList.run {
        if (removeIf(predicate)) updateList(this)
    }
}

fun MutableStateFlow<ListState>.addAllAtPosition(position: Int, list: List<RecyclerViewItem>) {
    val data = mutableList.apply { addAll(position, list) }
    updateList(data)
}
