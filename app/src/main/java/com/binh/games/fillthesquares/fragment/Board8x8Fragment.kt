package com.binh.games.fillthesquares.fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableLayout

import com.binh.games.fillthesquares.R
import kotlinx.android.synthetic.main.fragment_board8x8.view.*

/**
 * Fragment này biểu diễn bảng có 8x8 ô.
 *
 * interface IBoardFragment được implement cung cấp 1 số chức năng cho fragment.
 */
class Board8x8Fragment : Fragment(), IBoardFragment {

    override lateinit var table: TableLayout

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_board8x8, container, false)
        table = view.table8x8
        return view
    }
}
