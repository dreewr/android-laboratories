package deco.demo.recyclerview.data

import android.widget.ArrayAdapter
import java.util.ArrayList

class Item(val id: String, val name: String) {

    companion object {

        fun getOldList(): ArrayList<Item> = arrayListOf(
            Item("0", "Zero"),
            Item("1", "One"),
            Item("2", "Two"),
            Item("3", "Three"),
            Item("4", "Four"),
            Item("5", "Five"),
            Item("6", "Six")

        )

        fun getNewList(): ArrayList<Item> = arrayListOf(
            Item("5", "Five"),
            Item("0", "Zero"),
            Item("1", "One"),
            Item("3", "Three"),
            Item("4", "Four"),
            Item("2", "Two"),
            Item("6", "Six")

        )
    }
}