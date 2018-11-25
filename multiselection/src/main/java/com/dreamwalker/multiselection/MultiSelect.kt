package com.dreamwalker.multiselection

import android.support.v7.widget.RecyclerView
import com.dreamwalker.multiselection.adapter.BaseLeftAdapter
import com.dreamwalker.multiselection.adapter.BaseRightAdapter

interface MultiSelect<I : Comparable<I>> {

    val recyclerLeft: RecyclerView
    val recyclerRight: RecyclerView

    val selectedItems: List<I>?

    var leftAdapter: BaseLeftAdapter<I, out RecyclerView.ViewHolder>?
    var rightAdapter: BaseRightAdapter<I, out RecyclerView.ViewHolder>?

    fun select(position: Int)
    fun deselect(position: Int)

    fun showSelectedPage()
    fun showNotSelectedPage()

    fun setSidebarWidthDp(iconWidthDp: Float)

}


