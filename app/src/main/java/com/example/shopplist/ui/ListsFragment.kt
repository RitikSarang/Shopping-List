package com.example.shopplist.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shopplist.ui.ListsFragmentDirections
import com.example.shopplist.R
import com.example.shopplist.data.Groceries
import com.example.shopplist.data.Priority
import com.example.shopplist.data.SortBy
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_lists.*


class ListsFragment : Fragment(R.layout.fragment_lists) {

    lateinit var viewModel: ListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this).get(ListViewModel::class.java)
        setHasOptionsMenu(true)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        with(recyclerView){
            layoutManager = LinearLayoutManager(activity)
            adapter = GroceriesAdapter{
                viewModel.makePriorityLow(Priority.Done.ordinal,it)
                //Toast.makeText(context, "Clicked $it", Toast.LENGTH_SHORT).show()
            }
        }

        fb_addGroceryItem.setOnClickListener {
            findNavController().navigate(ListsFragmentDirections.actionListsFragmentToAddGroceryFragment(0))
        }


        viewModel.groceries.observe(viewLifecycleOwner, Observer {
            (recyclerView.adapter as GroceriesAdapter).submitList(it)
        })

        viewModel.getAllPrice().observe(viewLifecycleOwner, Observer {
            txtTotal.text = getString(R.string.totalPrice,calToal(it).toString())

        })

        viewModel.getTotalQuantity().observe(viewLifecycleOwner, Observer {
            txtQuantity.text = getString(R.string.quantity_1,calTotalQuantity(it).toString())
        })
    }

    private fun calTotalQuantity(it: List<Int>): Int {
        var total=0
        it.forEach {
            total+=it
        }
        return total
    }

    private fun calToal(it: List<Float>):Float{
        var total=0F
        it.forEach {
            total+=it
        }
        return total
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        return inflater.inflate(R.menu.menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_sort_priority -> {
                item.isChecked=true
                viewModel.changeSortOrder(SortBy.PRIORITY)
                true
            }
            R.id.menu_sort_price -> {
                item.isChecked=true
                viewModel.changeSortOrder(SortBy.PRICE)
                true
            }
            R.id.menu_sort_quantity -> {
                item.isChecked=true
                viewModel.changeSortOrder(SortBy.QUANTITY)
                true
            }
            R.id.menu_delete -> {
                viewModel.delete()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    }
}