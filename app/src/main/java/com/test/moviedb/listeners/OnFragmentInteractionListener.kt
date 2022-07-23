package com.test.moviedb.listeners

import androidx.fragment.app.Fragment

interface OnFragmentInteractionListener {
    fun replaceFragment(fragment: Fragment, tag: String, isAddToStack: Boolean = true)
}