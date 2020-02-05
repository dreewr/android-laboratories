package deco.koindemo.presentation

import android.content.Context
import android.net.Uri
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import deco.koindemo.R
import deco.koindemo.UrlHelper
import deco.koindemo.model.Currency
import kotlinx.android.synthetic.main.view_currency.view.*
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.core.qualifier.named

class CurrencyView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0)
    : LinearLayout(context, attrs, defStyleAttr), KoinComponent{

    val urlHelper: UrlHelper by inject()
//    val currencyUrl: String =

    init{
        View.inflate(context, R.layout.view_currency, this)
    }

    fun setCurrency(currency: Currency){
        text_name.text = currency.name
        text_symbol.text = currency.symbol

        /*Comportamento da view quando clicada*/
        setOnClickListener{
             urlHelper.launchCurrencyUrl(context,currency.slug)
        }
    }
}