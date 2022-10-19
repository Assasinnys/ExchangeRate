package com.dmitryzenevich.exchangerate.ui.adapter

interface Handler

interface TypeHandler : Handler {
    fun getItemType(item: Any, position: Int): BaseType?
}

interface LayoutHandler : Handler {
    fun getItemLayout(item: Any, position: Int): Int
}

interface StableId {
    val stableId: Long
}
