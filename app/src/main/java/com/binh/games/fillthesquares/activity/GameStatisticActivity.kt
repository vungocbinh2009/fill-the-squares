package com.binh.games.fillthesquares.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Window
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.binh.games.fillthesquares.MainActivity
import com.binh.games.fillthesquares.R
import com.binh.games.fillthesquares.databinding.ActivityGameStatisticBinding
import com.binh.games.fillthesquares.other.InjectConstants
import com.binh.games.fillthesquares.viewmodel.GameStatisticViewModel
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.direct
import org.kodein.di.generic.instance

class GameStatisticActivity : AppCompatActivity(), KodeinAware {
    override val kodein: Kodein by closestKodein()

    private lateinit var gameStatViewModel: GameStatisticViewModel

    private lateinit var binding: ActivityGameStatisticBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar?.hide()

        super.onCreate(savedInstanceState)

        binding = ActivityGameStatisticBinding.inflate(layoutInflater)
        setContentView(binding.root)

        when (intent.getStringExtra(MainActivity.GAME_STATISTIC_MODE)) {
            GameSelectionActivity.CLASSIC_GAME_6X6 -> {
                binding.gameModeTextView.setText(R.string.classicGame6x6Board)
            }
            GameSelectionActivity.CLASSIC_GAME_8X8 -> {
                binding.gameModeTextView.setText(R.string.classicGame8x8Board)
            }
            GameSelectionActivity.CLASSIC_GAME_6X6_HARD -> {
                binding.gameModeTextView.setText(R.string.classicGame6x6BoardHard)
            }
            GameSelectionActivity.CLASSIC_GAME_8X8_HARD -> {
                binding.gameModeTextView.setText(R.string.classicGame8x8BoardHard)
            }
        }
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            gameStatViewModel = ViewModelProvider(this)[GameStatisticViewModel::class.java]
            gameStatViewModel.build(intent.getStringExtra(MainActivity.GAME_STATISTIC_MODE)!!, kodein.direct.instance(InjectConstants.GAME_STATISTIC_DATABASE))
            updateStat()
            binding.removeStatisticButton.setOnClickListener {
                val dialog = AlertDialog.Builder(this)
                dialog.setMessage("Remove this ?")
                        .setPositiveButton("Cancel") {_, _ ->}
                        .setNegativeButton("OK") { _, _ ->
                            gameStatViewModel.openDatabase()
                            gameStatViewModel.removeAllGames(intent.getStringExtra(MainActivity.GAME_STATISTIC_MODE)!!)
                            gameStatViewModel.closeDatabase()
                            updateStat()
                            for (i in 0..9) {
                                getTextViewInTable(binding.highScoreTable, i / 5, i % 5).text = "?"
                            }
                        }
                        .show()
            }
        }
    }

    private fun getTextViewInTable(table: TableLayout ,row: Int, column: Int) : TextView {
        val tableRow = table.getChildAt(row) as TableRow
        return tableRow.getChildAt(column) as TextView
    }

    @SuppressLint("SetTextI18n")
    private fun updateStat() {
        gameStatViewModel.openDatabase()
        gameStatViewModel.getTotalGamePlayed().observe(this, {
            binding.numberOfGamesTextView.text = it.toString()
        })
        gameStatViewModel.getHighestScore().observe(this, {
            binding.worstScoreTextView.text = it?.toString() ?: "?"
        })
        gameStatViewModel.getAverageScore().observe(this, {
            binding.averageScoreTextView.text = "%.1f".format(it)
        })
        gameStatViewModel.getLowestScore().observe(this, {
            binding.bestScoreTextView.text = it?.toString() ?: "?"
        })
        gameStatViewModel.getTopScore(10).observe(this, {
            for (element in it!!) {
                getTextViewInTable(binding.highScoreTable, it.indexOf(element) / 5, it.indexOf(element) % 5).text = element.score.toString()
            }
        })
        gameStatViewModel.closeDatabase()
    }
}