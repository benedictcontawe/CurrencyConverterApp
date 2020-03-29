package com.example.currencyconverterapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.currencyconverterapp.model.room.table.CurrencyTable
import com.example.currencyconverterapp.databinding.MainBinder

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding : MainBinder
    private lateinit var viewModel : MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        binding.setViewModel(viewModel)
    }

    override fun onStart() {
        super.onStart()

        setEventListeners()
        setLiveDataObservers()

        viewModel.readGSON()
    }

    private fun setLiveDataObservers(){

        viewModel.getCurrency().observe(this, object : Observer<CurrencyTable> {
            override fun onChanged(data: CurrencyTable) {
                //You have converted [fromAmount] [fromCurrency] to [toAmount] [toCurrency]. Commission Fee - 0.70 [fromCurrency].
                data.let {
                    binding.tvStatus.setText("You have converted " +
                            "${data.fromAmount} " +
                            "${data.fromCurrency} " +
                            "to " +
                            "${data.toAmount} " +
                            "${data.toCurrency}" +
                            ". Commission Fee - 0.70 [fromCurrency].")
                }
                Toast.makeText(this@MainActivity, "Converted!", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun setEventListeners() {
        binding.btnConvert.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when(view.id){
            binding.btnConvert.id -> {
                Toast.makeText(this, "CONVERT", Toast.LENGTH_SHORT).show()
                viewModel.requestCurrency(binding.etAmount.text.toString(),binding.spinnerFromCurrency.selectedItem.toString(),binding.spinnerToCurrency.selectedItem.toString())
            }
        }
    }
}