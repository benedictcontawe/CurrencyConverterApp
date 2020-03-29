package com.example.currencyconverterapp.model.web.entity

class CurrencyRequestModel {

    var fromAmount: String? = null
    var fromCurrency: String? = null
    var toCurrency: String? = null

    constructor(fromAmount : String, fromCurrency : String, toCurrency : String){
        this.fromAmount = fromAmount
        this.fromCurrency = fromCurrency
        this.toCurrency = toCurrency
    }
}