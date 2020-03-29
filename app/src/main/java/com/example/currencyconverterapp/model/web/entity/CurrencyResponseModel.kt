package com.example.currencyconverterapp.model.web.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class CurrencyResponseModel {

    @SerializedName("amount")
    @Expose
    var Amount : String? = null

    @SerializedName("currency")
    @Expose
    var Currency : String? = null
}