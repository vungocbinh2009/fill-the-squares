package com.binh.games.fillthesquares.activity

import android.annotation.SuppressLint
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import com.binh.games.fillthesquares.MainActivity
import com.binh.games.fillthesquares.R
import com.binh.games.fillthesquares.other.InjectConstant
import com.binh.games.fillthesquares.viewmodel.GameStatisticViewModel
import kotlinx.android.synthetic.main.activity_game_statistic.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.direct
import org.kodein.di.generic.instance

class GameStatisticActivity : AppCompatActivity(), KodeinAware {
    override val kodein: Kodein by closestKodein()

    private lateinit var gameStatViewModel: GameStatisticViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar?.hide()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_statistic)

        when (intent.getStringExtra(MainActivity.GAME_STATISTIC_MODE)) {
            GameSelectionActivity.CLASSIC_GAME_6X6 -> {
                gameModeTextView.setText(R.string.classicGame6x6Board)
            }
            GameSelectionActivity.CLASSIC_GAME_8X8 -> {
                gameModeTextView.setText(R.string.classicGame8x8Board)
            }
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            gameStatViewModel = ViewModelProviders.of(this)[GameStatisticViewModel::class.java]
            gameStatViewModel.build(intent.getStringExtra(MainActivity.GAME_STATISTIC_MODE), kodein.direct.instance(InjectConstant.GAME_STATISTIC))
            gameStatViewModel.openDatabase()
            gameStatViewModel.getTotalGamePlayed().observe(this, Observer {
                numberOfGamesTextView.text = it.toString()
            })
            gameStatViewModel.getHighestScore().observe(this, Observer {
                highestScoreTextView.text = it.toString()
            })
            gameStatViewModel.getAverageScore().observe(this, Observer {
                averageScoreTextView.text = "%.1f".format(it)
            })
            gameStatViewModel.getLowestScore().observe(this, Observer {
                lowestScoreTextView.text = it.toString()
            })
            gameStatViewModel.getTopScore(10).observe(this, Observer {
                for (element in it!!) {
                    getTextViewInTable(highScoreTable, it.indexOf(element) / 5, it.indexOf(element) % 5).text = element.score.toString()
                }
            })
            gameStatViewModel.closeDatabase()
        }
    }

    private fun getTextViewInTable(table: TableLayout ,row: Int, column: Int) : TextView {
        val tableRow = table.getChildAt(row) as TableRow
        return tableRow.getChildAt(column) as TextView
    }
}