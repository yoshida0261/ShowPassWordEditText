package com.stah.showpasswordedittext

import android.content.Context
import android.view.LayoutInflater
import android.widget.FrameLayout


class ShowPasswordEditText2(context: Context) : FrameLayout(context) {

    init {
        val inflater = LayoutInflater.from(context)
        inflater.inflate(R.layout.visible_pass_edittext, this, true)
        // View.inflate(context, R.layout.visible_pass_edittext, this)

    }
}