package com.binh.games.fillthesquares.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.binh.games.fillthesquares.R
import com.binh.games.fillthesquares.databinding.ActivityPrivacyPolicyBinding

class PrivacyPolicyActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPrivacyPolicyBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_privacy_policy)
        binding.webView.loadUrl("https://sites.google.com/view/vbinhdev98ssitesprivacypolicy")

        binding.okButton.setOnClickListener {
            finish()
        }
    }
}
