package com.test.moviedb

import android.content.Context
import android.os.Bundle

import androidx.fragment.app.Fragment
import com.test.moviedb.listeners.OnFragmentInteractionListener


open class BaseFragment : Fragment() {
    lateinit var listener: OnFragmentInteractionListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            listener = context as OnFragmentInteractionListener
        } catch (e: Exception) {
            throw  ClassCastException("Fragment must initialize OnFragmentInteractionListener");
        }
    }


}