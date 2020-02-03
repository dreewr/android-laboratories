package deco.koindemo.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import deco.koindemo.data.DataRepositoryFactory
import deco.koindemo.model.Currency

class CurrenciesViewModel constructor(
    private val dataRepositoryFactory: DataRepositoryFactory
) : ViewModel() {

    val currenciesLiveData = MutableLiveData<List<Currency>>()

    fun observeCurrencies(): LiveData<List<Currency>>{
        return currenciesLiveData
    }
   fun retrieveCurrencies(jsonString:String){
       val data = dataRepositoryFactory.retrieveLocalSource().getCurrencies(jsonString)

       currenciesLiveData.postValue(data)
   }
}