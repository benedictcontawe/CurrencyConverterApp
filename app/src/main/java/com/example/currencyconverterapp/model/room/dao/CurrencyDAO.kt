package com.example.currencyconverterapp.model.room.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.currencyconverterapp.model.room.table.CurrencyTable

@Dao //Data Access Object
interface CurrencyDAO {

    @Insert
    fun insert(currencyTable : CurrencyTable)

    @Update
    fun update(currencyTable : CurrencyTable)

    @Delete
    abstract fun delete(currencyTable : CurrencyTable)

    @Query("DELETE FROM currency_table")
    abstract fun deleteAll()

    @Query("SELECT * FROM currency_table WHERE Id = 1")//@Query("SELECT * FROM currency_table ORDER BY Id ASC LIMIT 1")
    fun getFirst() : LiveData<List<CurrencyTable>>

    @Query("SELECT * FROM currency_table")
    fun getAll() : CurrencyTable
}