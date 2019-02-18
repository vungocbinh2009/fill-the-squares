package com.binh.games.fillthesquares.fragment


import android.os.Bundle
import android.support.v7.preference.PreferenceFragmentCompat
import com.binh.games.fillthesquares.R

class GameSettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.fragment_game_settings)
    }
}
