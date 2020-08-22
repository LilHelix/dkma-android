package com.helix.dontkillmyapp.utils

import android.widget.EditText
import androidx.appcompat.widget.SearchView
import androidx.appcompat.R
import androidx.core.widget.addTextChangedListener
import com.helix.dontkillmyapp.extensions.orEmpty

data class SearchQuery(val query: CharSequence) {
    val isEmpty: Boolean
        get() = query.isEmpty()
}

fun SearchView.setup(listener: (SearchQuery) -> Unit) {
    val editText = findViewById<EditText>(R.id.search_src_text)
    editText.addTextChangedListener(onTextChanged = { query, _, _, _ ->
        listener.invoke(SearchQuery(query.orEmpty()))
    })
}