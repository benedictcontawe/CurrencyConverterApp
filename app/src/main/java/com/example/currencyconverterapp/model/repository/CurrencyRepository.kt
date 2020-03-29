package com.example.currencyconverterapp.model.repository

import android.app.Application
import android.os.AsyncTask
import android.util.Log
import androidx.lifecycle.LiveData
import com.example.currencyconverterapp.model.web.entity.CurrencyRequestModel
import com.example.currencyconverterapp.model.web.entity.CurrencyResponseModel
import com.example.currencyconverterapp.model.room.dao.CurrencyDAO
import com.example.currencyconverterapp.model.room.database.CurrencyDatabase
import com.example.currencyconverterapp.model.room.table.CurrencyTable
import com.example.currencyconverterapp.model.web.CurrencyAPI
import com.example.currencyconverterapp.model.web.NetworkClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CurrencyRepository : BaseRepository {

    private lateinit var currencyDAO : CurrencyDAO

    private lateinit var currencyAPI : CurrencyAPI

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
        val database = CurrencyDatabase.getInstance(application.applicationContext)
        this.currencyDAO = database!!.currencyDAO()

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


                Log.e("CurrencyRepository","${currencyResponseModels.Amount} ${currencyResponseModels.Currency}")

                update(
                    CurrencyTable(
                        currencyRequestModel.fromAmount?:"0.00",
                        currencyRequestModel.fromCurrency?:"Null",
                        currencyResponseModels.Amount?:"0.00",
                        currencyResponseModels.Currency?:"Null"
                    )
                )
            }

            override fun onFailure(call: Call<CurrencyResponseModel>, throwable: Throwable) {
                Log.e("CurrencyRepository","Callback $throwable")
            }

        })
    }
    //region CRUD Operation Basic
    override fun insert(currencyTable: CurrencyTable) {
        InsertMediaAsyncTask(currencyDAO).execute(currencyTable)
        //currencyDAO?.let { InsertMediaAsyncTask(it).execute(currencyTable) }
    }

    override fun update(currencyTable: CurrencyTable) {
        UpdateMediaAsyncTask(currencyDAO).execute(currencyTable)
        //currencyDAO?.let { UpdateMediaAsyncTask(it).execute(currencyTable) }
    }

    override fun delete(currencyTable: CurrencyTable) {
        DeleteMediaAsyncTask(currencyDAO).execute(currencyTable)
        //currencyDAO?.let { DeleteMediaAsyncTask(it).execute(currencyTable) }
    }

    override fun deleteAll() {
        DeleteAllMediaAsyncTask(currencyDAO).execute()
        //currencyDAO.let { DeleteAllMediaAsyncTask(it).execute() }
    }
    //endregion
    //region  Query
    public fun reset() {
        deleteAll()
        insert(
            CurrencyTable(
                "0.00",
                "0.00",
                "0.00",
                "0.00"
            )
        )
    }

    public fun getFirst() : LiveData<List<CurrencyTable>> {
        return currencyDAO.getFirst()
    }

    public fun getAll() : CurrencyTable {
        return currencyDAO.getAll()
    }
    //endregion
    //region Async Task Class For Repository
    private class InsertMediaAsyncTask internal constructor(private val currencyDAO: CurrencyDAO) :
        AsyncTask<CurrencyTable, Void, Void>() {

        override fun doInBackground(vararg currencyEntities: CurrencyTable): Void? {
            currencyDAO.insert(currencyEntities[0])
            return null
        }
    }

    private class UpdateMediaAsyncTask internal constructor(private val currencyDAO: CurrencyDAO) :
        AsyncTask<CurrencyTable, Void, Void>() {

        override fun doInBackground(vararg currencyEntities: CurrencyTable): Void? {
            currencyDAO.update(currencyEntities[0])
            return null
        }
    }

    private class DeleteMediaAsyncTask internal constructor(private val currencyDAO: CurrencyDAO) :
        AsyncTask<CurrencyTable, Void, Void>() {

        override fun doInBackground(vararg currencyEntities: CurrencyTable): Void? {
            currencyDAO.delete(currencyEntities[0])
            return null
        }
    }

    private class DeleteAllMediaAsyncTask internal constructor(private val currencyDAO: CurrencyDAO) :
        AsyncTask<CurrencyTable, Void, Void>() {

        override fun doInBackground(vararg currencyEntities: CurrencyTable): Void? {
            currencyDAO.deleteAll()
            return null
        }
    }
    //endregion
}