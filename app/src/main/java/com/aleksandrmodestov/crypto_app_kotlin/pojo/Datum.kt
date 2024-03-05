package com.aleksandrmodestov.crypto_app_kotlin.pojo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Datum(

    @SerializedName("CoinInfo") @Expose val coinInfo: CoinInfo?
)