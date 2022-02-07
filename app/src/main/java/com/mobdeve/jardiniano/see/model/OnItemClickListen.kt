package com.mobdeve.jardiniano.see.model

import android.view.View

interface OnItemClickListen {

    fun onItemClick(view: View?, position: Int)
    fun onItemLongClick(view: View?, position: Int)
}