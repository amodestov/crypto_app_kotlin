package com.aleksandrmodestov.crypto_app_kotlin

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.aleksandrmodestov.crypto_app_kotlin.databinding.ActivityCoinDetailBinding
import com.squareup.picasso.Picasso

class CoinDetailActivity : AppCompatActivity() {

    private lateinit var viewModel: CoinViewModel
    private lateinit var binding: ActivityCoinDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCoinDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (!intent.hasExtra(EXTRA_FROM_SYMBOL)) {
            finish()
            return
        }
        val fromSymbol = intent.getStringExtra(EXTRA_FROM_SYMBOL)
        viewModel = ViewModelProvider(this)[CoinViewModel::class.java]
        viewModel.getDetailedInfo(fromSymbol!!).observe(this) {
            with(binding) {
                with(it) {
                    textViewFromSymbol.text = fromSymbol
                    textViewToSymbol.text = toSymbol
                    textViewPrice.text = price.toString()
                    textViewMinPrice.text = lowDay.toString()
                    textViewMaxPrice.text = highDay.toString()
                    textViewLastMarket.text = lastMarket
                    textViewLastUpdate.text = getFormattedTime()
                    Picasso.get().load(getFullImageUrl()).into(imageViewLogoCoin)
                }
            }
        }
    }

    companion object {
        private const val EXTRA_FROM_SYMBOL = "fSym"
        fun getIntent(context: Context, fromSymbol: String): Intent {
            val intent = Intent(context, CoinDetailActivity::class.java)
            intent.putExtra(EXTRA_FROM_SYMBOL, fromSymbol)
            return intent
        }
    }
}