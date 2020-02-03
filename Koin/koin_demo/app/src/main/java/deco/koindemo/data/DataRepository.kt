package deco.koindemo.data

import deco.koindemo.model.Currency

interface DataRepository {

    fun getCurrencies(jsonString: String):List<Currency>

}