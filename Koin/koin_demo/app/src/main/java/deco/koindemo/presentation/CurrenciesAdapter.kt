package deco.koindemo.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import deco.koindemo.R
import deco.koindemo.model.Currency
import kotlinx.android.synthetic.main.item_currency.view.*

class CurrenciesAdapter: RecyclerView.Adapter<CurrenciesAdapter.ViewHolder>() {

    var currencies: List<Currency> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_currency, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return currencies.count()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        holder.currencyView.setCurrency(currencies[position])
        val currency = currencies[position]
        holder.currencyView.setCurrency(currencies[position])

    }

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
       val currencyView: CurrencyView = view.view_currency
    }
}