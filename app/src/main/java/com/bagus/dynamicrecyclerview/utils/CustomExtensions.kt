package com.bagus.dynamicrecyclerview.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

infix fun ViewGroup.inflate(layoutRes: Int): View =
    LayoutInflater.from(context).inflate(layoutRes, this, false)


fun RecyclerView.setVerticalLayout(aReverseLayout: Boolean = false) {
    layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, aReverseLayout)
}


