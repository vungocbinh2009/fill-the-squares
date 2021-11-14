package com.binh.games.fillthesquares.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableLayout
import androidx.fragment.app.Fragment
import com.binh.games.fillthesquares.databinding.FragmentBoard6x6Binding

/**
 * Fragment này biểu diễn bảng có 6x6 ô.
 *
 * interface IBoardFragment được implement cung cấp 1 số chức năng cho fragment.
 */
class Board6x6Fragment : Fragment(), IBoardFragment {

    override lateinit var table: TableLayout

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = FragmentBoard6x6Binding.inflate(layoutInflater, container, false)
        table = view.table6x6
        return view.root
    }
}
