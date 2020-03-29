package com.example.currencyconverterapp.model.web

import com.example.currencyconverterapp.model.web.entity.CurrencyResponseModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface CurrencyAPI {

    @GET("/currency/commercial/exchange/{fromAmount}-{fromCurrency}/{toCurrency}/latest")
    fun getCurrencyDatils(
        @Path("fromAmount") fromAmount: String,
        @Path("fromCurrency") fromCurrency: String,
        @Path("toCurrency") toCurrency: String
    ): Call<CurrencyResponseModel>
}