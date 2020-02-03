package deco.koindemo.data

import com.google.gson.Gson
import deco.koindemo.model.Currency


//This will be injected
class RemoteDataRepository(private val gson: Gson) : DataRepository {

    override fun getCurrencies(jsonString: String):List<Currency>{
        return gson.fromJson(jsonString, Array<Currency>::class.java).toList()
    }

}