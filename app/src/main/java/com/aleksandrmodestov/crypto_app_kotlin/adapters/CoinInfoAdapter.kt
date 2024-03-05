package com.aleksandrmodestov.crypto_app_kotlin.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aleksandrmodestov.crypto_app_kotlin.R
import com.aleksandrmodestov.crypto_app_kotlin.databinding.ItemCoinInfoBinding
import com.aleksandrmodestov.crypto_app_kotlin.pojo.CoinPriceInfo
import com.squareup.picasso.Picasso

class CoinInfoAdapter(private val context: Context) :
    RecyclerView.Adapter<CoinInfoAdapter.CoinInfoViewHolder>() {

    var coinInfoList: List<CoinPriceInfo> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var onCoinClickListener: OnCoinClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinInfoViewHolder {
        val binding =
            ItemCoinInfoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CoinInfoViewHolder(binding)
    }

    override fun getItemCount() = coinInfoList.size

    override fun onBindViewHolder(holder: CoinInfoViewHolder, position: Int) {
        val coin = coinInfoList[position]
        with(holder.binding) {
            with(coin) {
                val symbolsTemplate = context.resources.getString(R.string.symbols_template)
                val lastUpdateTemplate = context.resources.getString(R.string.last_update_template)
                textViewSymbols.text = String.format(symbolsTemplate, fromSymbol, toSymbol)
                textViewPrice.text = price.toString()
                textViewLastUpdateTime.text = String.format(lastUpdateTemplate, getFormattedTime())
                Picasso.get()
                    .load(getFullImageUrl())
                    .into(imageViewLogoCoin)
            }
        }
        holder.itemView.setOnClickListener { onCoinClickListener?.onCoinClick(coin) }
    }

    inner class CoinInfoViewHolder(val binding: ItemCoinInfoBinding) :
        RecyclerView.ViewHolder(binding.root)

    interface OnCoinClickListener {
        fun onCoinClick(coinPriceInfo: CoinPriceInfo)
    }
}