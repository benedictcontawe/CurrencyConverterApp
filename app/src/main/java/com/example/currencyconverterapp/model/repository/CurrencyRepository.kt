package com.example.currencyconverterapp.model.repository

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.currencyconverterapp.model.web.entity.CurrencyRequestModel
import com.example.currencyconverterapp.model.web.entity.CurrencyResponseModel
import com.example.currencyconverterapp.model.room.table.CurrencyTable
import com.example.currencyconverterapp.model.web.CurrencyAPI
import com.example.currencyconverterapp.model.web.NetworkClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CurrencyRepository : BaseRepository {

    //private lateinit var currencyDAO : CurrencyDAO

    private lateinit var currencyAPI : CurrencyAPI

    private val liveAPI : MutableLiveData<CurrencyTable> = MutableLiveData()

    companion object {
        @Volatile private var currencyRepository : CurrencyRepository? = null

        fun getInstance(application : Application) : CurrencyRepository {
            /*
            if (currencyRepository == null) {
                return CurrencyRepository(application)
            }
            return currencyRepository!!
            */
            return  currencyRepository?:CurrencyRepository(application)
        }
    }

    constructor(application: Application) {
        //Obtain an instance of Room by calling the static method.
        //val database = CurrencyDatabase.getInstance(application.applicationContext)
        //this.currencyDAO = database!!.currencyDAO()

        this.currencyAPI = NetworkClient.createService(CurrencyAPI::class.java)
    }

    fun requestCurrency(currencyRequestModel: CurrencyRequestModel) {
        val call = currencyAPI.getCurrencyDatils(currencyRequestModel.fromAmount?:"0.00",currencyRequestModel.fromCurrency?:"EUR",currencyRequestModel.toCurrency?:"USD")

        call.enqueue(object : Callback<CurrencyResponseModel> {

            override fun onResponse(call: Call<CurrencyResponseModel>, response: Response<CurrencyResponseModel>) {
                if (!response.isSuccessful) {
                    return
                }

                response.body()

                val currencyResponseModels : CurrencyResponseModel = response.body()!!

                liveAPI.setValue(
                    setCurrenctCurrency(
                        currencyRequestModel.fromAmount,
                        currencyRequestModel.fromCurrency,
                        currencyResponseModels.Amount,
                        currencyResponseModels.Currency
                    )
                )

                Log.e("CurrencyRepository","${currencyResponseModels.Amount} ${currencyResponseModels.Currency}")
            }

            override fun onFailure(call: Call<CurrencyResponseModel>, throwable: Throwable) {
                Log.e("CurrencyRepository","Callback $throwable")
                liveAPI.setValue(setCurrenctCurrency(null,null,null,null))
            }

        })
    }

    public fun getFirst() : MutableLiveData<CurrencyTable> {
        return liveAPI
    }

    private fun setCurrenctCurrency(fromAmount : String?, fromCurrency : String?, Amount : String?, Currency : String?) : CurrencyTable {
        return CurrencyTable(
            fromAmount?:"0",
            fromCurrency?:"0",
            Amount?:"0",
            Currency?:"0"
        )
    }
}