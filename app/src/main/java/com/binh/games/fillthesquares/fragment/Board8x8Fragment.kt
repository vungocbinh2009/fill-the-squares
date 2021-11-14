package com.binh.games.fillthesquares.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableLayout
import androidx.fragment.app.Fragment
import com.binh.games.fillthesquares.databinding.FragmentBoard8x8Binding

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
        val view = FragmentBoard8x8Binding.inflate(layoutInflater, container, false)
        table = view.table8x8
        return view.root
    }
}
