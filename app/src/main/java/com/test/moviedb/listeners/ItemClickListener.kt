package com.test.moviedb.listeners

interface ItemClickListener {
    fun <T : Any> onItemClick(obj: T)

}