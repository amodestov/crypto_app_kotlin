package com.aleksandrmodestov.crypto_app_kotlin.pojo

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.aleksandrmodestov.crypto_app_kotlin.api.ApiFactory.BASE_IMAGE_URL
import com.aleksandrmodestov.crypto_app_kotlin.utils.convertTimestampToTime
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "full_price_list")
data class CoinPriceInfo(

    @PrimaryKey @SerializedName("FROMSYMBOL") @Expose val fromSymbol: String,

    @SerializedName("TOSYMBOL") @Expose val toSymbol: String?,

    @SerializedName("LASTMARKET") @Expose val lastMarket: String?,

    @SerializedName("PRICE") @Expose val price: Double?,

    @SerializedName("LASTUPDATE") @Expose val lastUpdate: Long?,

    @SerializedName("HIGHDAY") @Expose val highDay: Double?,

    @SerializedName("LOWDAY") @Expose val lowDay: Double?,

    @SerializedName("IMAGEURL") @Expose val imageUrl: String?
) {
    fun getFormattedTime(): String {
        return convertTimestampToTime(lastUpdate)
    }

    fun getFullImageUrl(): String {
        return BASE_IMAGE_URL + imageUrl
    }
}