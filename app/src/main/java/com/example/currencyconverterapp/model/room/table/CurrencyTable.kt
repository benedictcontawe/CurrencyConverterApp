package com.example.currencyconverterapp.model.room.table

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "currency_table")
public class CurrencyTable {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "Id")
    var id : Int? = null

    @ColumnInfo(name = "CurrentAmount")
    var fromAmount : String? = null

    @ColumnInfo(name = "CurrentCurrency")
    var fromCurrency : String? = null

    @ColumnInfo(name = "ConvertedAmount")
    var toAmount : String? = null

    @ColumnInfo(name = "ConvertedCurrency")
    var toCurrency : String? = null

    constructor(fromAmount : String, fromCurrency : String, toAmount : String, toCurrency : String) {
        this.id = 1
        this.fromAmount = fromAmount
        this.fromCurrency = fromCurrency
        this.toAmount = toAmount
        this.toCurrency = toCurrency
    }
}