package com.binh.games.numberandmaze.fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.binh.games.numberandmaze.R

/**
 * A simple [Fragment] subclass.
 *
 */
class Board6x6Fragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_board6x6, container, false)
    }


}