package com.example.shopplist.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.shopplist.ui.AddGroceryFragmentDirections
import com.example.shopplist.R
import com.example.shopplist.data.Groceries
import com.example.shopplist.data.Priority
import com.example.shopplist.data.SortBy
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_add_grocery.*
import kotlin.math.log

class AddGroceryFragment : Fragment(R.layout.fragment_add_grocery) {

    lateinit var viewModel: DetailGroceryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this).get(DetailGroceryViewModel::class.java)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        btn_save.setOnClickListener {
            saveTask(it)

        }
        btn_add.setOnClickListener {
            viewModel.increaseQuantity(1)

        }
        btn_sub.setOnClickListener {
            viewModel.quantity.observe(viewLifecycleOwner, Observer {
                btn_sub.isEnabled = viewModel.quantity.value!! > 1
            })

            viewModel.decreaseQuantity(1)
        }

        val spinnervalues = mutableListOf<String>()
        /*Priority.values().forEach {
            spinnervalues.add(it.name)
        }*/
        Priority.values().forEachIndexed { index, priority ->
            if(index>0) {
                spinnervalues.add(priority.name)
            }
        }


        sp_priority.adapter = ArrayAdapter(
            requireContext(),
            R.layout.support_simple_spinner_dropdown_item,
            spinnervalues
        )
        sp_priority.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                updateViews(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }
        viewModel.quantity.observe(viewLifecycleOwner, Observer {
            txt_quantity.text = getString(R.string.quantity_1, it.toString())
            btn_sub.isEnabled = viewModel.quantity.value!! > 1

        })

    }

    private fun updateViews(position: Int) {

        when (position+1) {
            Priority.HIGH.ordinal -> {
                et_groceryName.setTextColor(ContextCompat.getColor(requireContext(), R.color.high))
                et_groceryPrice.setTextColor(ContextCompat.getColor(requireContext(), R.color.high))
            }
            Priority.MEDIUM.ordinal -> {
                et_groceryName.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.medium
                    )
                )
                et_groceryPrice.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.medium
                    )
                )
            }
            Priority.LOW.ordinal -> {
                et_groceryName.setTextColor(ContextCompat.getColor(requireContext(), R.color.low))
                et_groceryPrice.setTextColor(ContextCompat.getColor(requireContext(), R.color.low))

            }
          /*  Priority.Done.ordinal -> {
                et_groceryName.setTextColor(ContextCompat.getColor(requireContext(), R.color.done))
                et_groceryPrice.setTextColor(ContextCompat.getColor(requireContext(), R.color.done))
            }*/
        }
    }

    private fun saveTask(view: View) {
        val etgroceryName = et_groceryName.text.toString()
        val quantity = viewModel.quantity.value
        if (et_groceryPrice.text.toString().trim().isNotBlank() && etgroceryName.isNotBlank()) {
            val etgroceryPrice = et_groceryPrice.text.toString().toInt() * quantity!!

            val sppriority = sp_priority.selectedItemPosition+1

            val grocery = Groceries(
                viewModel.groceryId.value!!,
                etgroceryName,
                quantity,
                etgroceryPrice.toFloat(),
                sppriority
            )
            viewModel.insertGrocery(grocery)
            findNavController().navigate(AddGroceryFragmentDirections.actionAddGroceryFragmentToListsFragment())
        } else {
            if (etgroceryName.isBlank()) {
                Snackbar.make(view, "Please fill name field", Snackbar.LENGTH_SHORT).show()
            } else {
                Snackbar.make(view, "Please fill price field", Snackbar.LENGTH_SHORT).show()
            }
        }

    }
}