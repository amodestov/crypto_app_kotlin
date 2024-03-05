package com.aleksandrmodestov.crypto_app_kotlin.api

import com.aleksandrmodestov.crypto_app_kotlin.pojo.CoinInfoListOfData
import com.aleksandrmodestov.crypto_app_kotlin.pojo.CoinPriceInfoRawData
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("top/totalvolfull")
    fun getTopCoinsInfo(
        @Query(QUERY_PARAM_API_KEY) apiKey: String = API_KEY,
        @Query(QUERY_PARAM_LIMIT) limit: Int = LIMIT,
        @Query(QUERY_PARAM_TO_SYMBOL) toSymbol: String = CURRENCY,
    ): Single<CoinInfoListOfData>

    @GET("pricemultifull")
    fun getFullPriceList(
        @Query(QUERY_PARAM_API_KEY) apiKey: String = API_KEY,
        @Query(QUERY_PARAM_FROM_SYMBOLS) fromSymbol: String,
        @Query(QUERY_PARAM_TO_SYMBOLS) toSymbol: String = CURRENCY
    ): Single<CoinPriceInfoRawData>

    companion object {
        private const val LIMIT = 10
        private const val API_KEY = "a3acf5e39781f64abca80941d230813e19b04bfcb9987d7be6e91e5d718463d6"
        private const val QUERY_PARAM_API_KEY = "api_key"
        private const val QUERY_PARAM_LIMIT = "limit"
        private const val QUERY_PARAM_TO_SYMBOL = "tsym"
        private const val QUERY_PARAM_TO_SYMBOLS = "tsyms"
        private const val QUERY_PARAM_FROM_SYMBOLS = "fsyms"
        private const val CURRENCY = "USD"
    }
}