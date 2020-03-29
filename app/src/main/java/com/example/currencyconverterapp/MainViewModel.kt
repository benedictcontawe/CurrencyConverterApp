package com.example.currencyconverterapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.currencyconverterapp.model.repository.CurrencyRepository
import com.example.currencyconverterapp.model.room.table.CurrencyTable
import com.example.currencyconverterapp.model.web.entity.CurrencyRequestModel
import com.example.currencyconverterapp.model.web.entity.CurrencyResponseModel
import com.google.gson.Gson

class MainViewModel : AndroidViewModel {

    private lateinit var repository : CurrencyRepository

    constructor(application: Application) : super(application) {
        repository = CurrencyRepository.getInstance(application)
    }

    fun getCurrency() : LiveData<CurrencyTable> {
        return repository.getFirst()
    }

    fun requestCurrency(fromAmount : String, fromCurrency : String, toCurrency : String) {
        val currencyRequestModel : CurrencyRequestModel = CurrencyRequestModel(fromAmount,fromCurrency,toCurrency)
        repository.requestCurrency(currencyRequestModel)
    }

    @Deprecated("Use readGSON method in ExampleUnitTest class")
    fun readGSON() : String {
        val gson : Gson = Gson()
        var json : String = "{\"amount\":\"40468\",\"currency\":\"JPY\"}"
        val currencyResponseModel: CurrencyResponseModel = gson.fromJson(json, CurrencyResponseModel::class.java)
        currencyResponseModel.Amount
        currencyResponseModel.Currency

        return  "${currencyResponseModel.Amount} ${currencyResponseModel.Currency}"
    }
}