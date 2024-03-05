package com.aleksandrmodestov.crypto_app_kotlin

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.aleksandrmodestov.crypto_app_kotlin.api.ApiFactory
import com.aleksandrmodestov.crypto_app_kotlin.database.AppDatabase
import com.aleksandrmodestov.crypto_app_kotlin.pojo.CoinPriceInfo
import com.aleksandrmodestov.crypto_app_kotlin.pojo.CoinPriceInfoRawData
import com.google.gson.Gson
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class CoinViewModel(application: Application) : AndroidViewModel(application) {

    private val db = AppDatabase.getInstance(application)
    private val compositeDisposable = CompositeDisposable()

    companion object {
        private const val TAG = "CoinViewModel"
        private const val DEFAULT_CURRENCY = "BTC"
        private const val TIMEOUT = 10L
    }

    val priceList = db.coinPriceInfoDao().getPriceList()

    init {
        loadData()
    }

    private fun loadData() {
        val disposable = ApiFactory.apiService.getTopCoinsInfo()
            .map {
                it.data?.map { datum -> datum.coinInfo?.name }?.joinToString(",")
                    ?: DEFAULT_CURRENCY
            }
            .flatMap { ApiFactory.apiService.getFullPriceList(fromSymbol = it) }
            .map { getPriceListFromRawData(it)!! }
            .delaySubscription(TIMEOUT, TimeUnit.SECONDS)
            .repeat()
            .retry()
            .subscribeOn(Schedulers.io())
            .subscribe({
                db.coinPriceInfoDao().insertPriceList(it)
            }, {
                Log.d(TAG, it.toString())
            })
        compositeDisposable.add(disposable)
    }

    private fun getPriceListFromRawData(
        coinPriceInfoRawData: CoinPriceInfoRawData
    ): List<CoinPriceInfo>? {
        val result = ArrayList<CoinPriceInfo>()
        val jsonObject = coinPriceInfoRawData.coinPriceInfoJsonObject ?: return null
        val coinKeySet = jsonObject.keySet()
        for (coinKey in coinKeySet) {
            val currencyJson = jsonObject.getAsJsonObject(coinKey)
            val currencyKeySet = currencyJson.keySet()
            for (currencyKey in currencyKeySet) {
                val priceInfo = Gson().fromJson(
                    currencyJson.getAsJsonObject(currencyKey),
                    CoinPriceInfo::class.java
                )
                result.add(priceInfo)
            }
        }
        return result
    }

    fun getDetailedInfo(fromSymbol: String): LiveData<CoinPriceInfo> {
        return db.coinPriceInfoDao().getPriceInfoAboutCoin(fromSymbol)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}