package com.dmitryzenevich.exchangerate.ui.bindingadapters

import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.databinding.BindingAdapter

@BindingAdapter("onItemSelected")
fun AutoCompleteTextView.setOnItemSelectedListener(listener: ((Int) -> Unit)?) {
    onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
        listener?.invoke(position)
    }
}

@BindingAdapter("selectedText")
fun AutoCompleteTextView.setSelectedText(text: String?) {
    setText(text, false)
}
@BindingAdapter("entries")
fun AutoCompleteTextView.setEntries(entries: List<String>?) {
    entries?.let {
        setAdapter(
            ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, it)
        )
    }
}