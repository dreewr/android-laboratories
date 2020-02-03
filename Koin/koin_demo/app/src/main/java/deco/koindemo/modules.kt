package deco.koindemo

import com.google.gson.Gson
import deco.koindemo.data.DataRepository
import deco.koindemo.data.DataRepositoryFactory
import deco.koindemo.data.LocalDataRepository
import deco.koindemo.data.RemoteDataRepository
import deco.koindemo.presentation.CurrenciesAdapter
import deco.koindemo.presentation.CurrenciesViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val applicationModule = module{
    single { Gson() }
    single { UrlHelper() }
    factory { CurrenciesAdapter() }
    factory<DataRepository>(named("local")) { LocalDataRepository( get())}
    factory<DataRepository>(named("remote")) { RemoteDataRepository( get()) }
    //Defino que um dos DataREpository é Local e outro é Remote
    factory{ DataRepositoryFactory(get(named("local")), get(named("remote")))}
    viewModel { CurrenciesViewModel(get()) }
}