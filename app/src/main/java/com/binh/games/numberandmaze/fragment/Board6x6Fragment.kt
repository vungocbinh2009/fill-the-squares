package com.binh.games.numberandmaze.fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableLayout

import com.binh.games.numberandmaze.R
import kotlinx.android.synthetic.main.fragment_board6x6.view.*

/**
 * Fragment này biểu diễn bảng có 6x6 ô.
 *
 * interface IBoardFragment được implement cung cấp 1 số chức năng cho fragment.
 */
class Board6x6Fragment : Fragment(), IBoardFragment {

    override lateinit var table: TableLayout
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_board6x6, container, false)
        table = view.table6x6
        return view
    }
}
