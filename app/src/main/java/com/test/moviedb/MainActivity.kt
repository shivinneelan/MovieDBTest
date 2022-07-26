package com.test.moviedb

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.test.moviedb.Constants.FRAGMENT_MOVIE_LIST
import com.test.moviedb.listeners.OnFragmentInteractionListener
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), OnFragmentInteractionListener {
    var isFirst = true


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        replaceFragment(MoviePagingListFragment.newInstance(), FRAGMENT_MOVIE_LIST)

    }

    override fun replaceFragment(fragment: Fragment, tag: String, isAddToStack: Boolean) {
        supportFragmentManager.beginTransaction().apply {
            if (!isFirst && isAddToStack)
                this.addToBackStack(tag)
            else
                isFirst = false
            replace(container.id, fragment, tag).commit()
        }
    }
}

