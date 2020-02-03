package deco.koindemo.data

import androidx.lifecycle.ViewModel
import javax.sql.CommonDataSource

class DataRepositoryFactory constructor(
    private val localDataSource: DataRepository,
    private val remoteDataSource: DataRepository){

    fun retrieveRemoteSource(): DataRepository{
        return remoteDataSource
    }

    fun retrieveLocalSource(): DataRepository{
        return localDataSource
    }


}