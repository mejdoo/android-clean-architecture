package com.mejdoo.clean.util

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mejdoo.clean.presentation.model.PostItem
import com.mejdoo.clean.presentation.ui.list.PostListAdapter

/**
 *  This is a binding adapter for the xml attribute 'app:data'
 *  It's used to bind the data of the recyclerView
 */

@BindingAdapter("data")
fun setRecyclerViewData(recyclerView: RecyclerView, data: MutableList<PostItem>?) {

    if (data != null) {
        recyclerView.adapter = PostListAdapter(data)
        recyclerView.adapter?.notifyDataSetChanged()
    }

}