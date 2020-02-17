package deco.demo.recyclerview

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import deco.demo.recyclerview.data.Item
import kotlinx.android.synthetic.main.item_linear.view.*

class SimpleAdapter : RecyclerView.Adapter<SimpleAdapter.ViewHolder>() {

    var items: ArrayList<Item> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_linear, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return items.count()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = items[position]
        holder.idText.text = item.id
        holder.nameText.text = item.name

    }

    fun swapItems(newItems: List<Item>) {
        var diffResult = DiffUtil.calculateDiff(MyDiffCallBack(items, newItems))
        items.clear()
        items.addAll(newItems)
        /*Instead of notifyDataSetChanged()*/
        diffResult.dispatchUpdatesTo(this)
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var idText: TextView = view.tv_id
        var nameText: TextView = view.tv_name
    }

    /*Uses two lists to examplify content changes*/
    inner class MyDiffCallBack constructor(
        private val oldList: List<Item>,
        private val newList: List<Item>
    ) : DiffUtil.Callback() {

        override fun getOldListSize(): Int {

            return oldList.size
        }

        override fun getNewListSize(): Int {
            return newList.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            Log.d(SimpleAdapter::class.java.canonicalName,
                "areItemsTheSame is ${newList[newItemPosition] == oldList[oldItemPosition]}")
            return newList[newItemPosition] == oldList[oldItemPosition]
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            Log.d(SimpleAdapter::class.java.canonicalName,
                "areContentsTheSame is ${newList[newItemPosition].id == oldList[oldItemPosition].id}")
            return newList[newItemPosition].id == oldList[oldItemPosition].id
        }

        /*This method can be used by a custom item animator to apply specific item animations*/
        override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
            Log.d(SimpleAdapter::class.java.canonicalName, "getChangedPayload")
            return super.getChangePayload(oldItemPosition, newItemPosition)
        }

    }
}