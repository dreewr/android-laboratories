package deco.demo.recyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.*
import deco.demo.recyclerview.data.Item
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val simpleAdapter: SimpleAdapter = SimpleAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
         setLinearRecycler()
        /*setGridRecycler()*/
        /*setStaggeredRecycler()*/
        button.setOnClickListener{
            simpleAdapter.swapItems(Item.getNewList())
        }
    }

    private fun setLinearRecycler(){

        recycler_linear.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recycler_linear.adapter = simpleAdapter
      /*  recycler_linear.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL))*/
        simpleAdapter.items = Item.getOldList()
        simpleAdapter.notifyDataSetChanged()
        Toast.makeText(this, simpleAdapter.itemCount.toString() , Toast.LENGTH_LONG).show()
    }

    private fun setGridRecycler(){
        recycler_linear.layoutManager = GridLayoutManager(this, 3)

        recycler_linear.adapter = simpleAdapter

        simpleAdapter.items = Item.getOldList()

        simpleAdapter.notifyDataSetChanged()
        Toast.makeText(this, simpleAdapter.itemCount.toString() , Toast.LENGTH_LONG).show()


    }

    private fun setStaggeredRecycler(){
        recycler_linear.layoutManager = StaggeredGridLayoutManager(3,
            StaggeredGridLayoutManager.HORIZONTAL)

        recycler_linear.adapter = simpleAdapter

        simpleAdapter.items = Item.getOldList()

        simpleAdapter.notifyDataSetChanged()
        Toast.makeText(this, simpleAdapter.itemCount.toString() , Toast.LENGTH_LONG).show()
    }


}
