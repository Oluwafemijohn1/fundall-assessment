package com.example.fundallassessment.utils

import android.view.View

fun View.isVisible(isVisible: Boolean): Unit {
    this.visibility = if (isVisible) View.VISIBLE else View.GONE
}