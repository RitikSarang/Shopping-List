package com.example.shopplist.ui

import android.app.Activity
import android.app.Application
import android.content.Context
import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.Spanned
import android.text.style.StrikethroughSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.shopplist.R
import com.example.shopplist.data.Groceries
import com.example.shopplist.data.Priority
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.fragment_add_grocery.*
import kotlinx.android.synthetic.main.list_item.*
import kotlinx.android.synthetic.main.list_item.txt_quantity
import kotlin.math.log10

class GroceriesAdapter(private val listener: (Long) -> Unit) :
    ListAdapter<Groceries, GroceriesAdapter.ViewHolder>(DiffUtilCallBack()) {

    inner class ViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView), LayoutContainer {
        init {
            itemView.setOnClickListener {
                listener.invoke(getItem(adapterPosition).id)
            }
        }

        fun bind(item: Groceries, position: Int) {
            val low = itemView.context.getColor(R.color.low)
            val medium = itemView.context.getColor(R.color.medium)
            val high = itemView.context.getColor(R.color.high)
            val done = itemView.context.getColor(R.color.done)


            with(item) {
                when (priority) {
                    Priority.HIGH.ordinal -> {
                        txt_name.text = "Q.${position + 1} $name"
                        txt_name.setTextColor(high)
                        txt_price.text =
                            itemView.context.getString(R.string.price, price.toString())
                        txt_price.setTextColor(high)
                        txt_quantity.text = "Q:$quantity"
                        txt_quantity.setTextColor(high)
                    }
                    Priority.MEDIUM.ordinal -> {
                        txt_name.text = "Q.${position + 1} $name"
                        txt_name.setTextColor(medium)
                        txt_price.text =
                            itemView.context.getString(R.string.price, price.toString())
                        txt_price.setTextColor(medium)
                        txt_quantity.text = "Q:$quantity"
                        txt_quantity.setTextColor(medium)
                    }
                    Priority.LOW.ordinal -> {
                        txt_name.text = "Q.${position + 1} $name"
                        txt_name.setTextColor(low)
                        txt_price.text =
                            itemView.context.getString(R.string.price, price.toString())
                        txt_price.setTextColor(low)
                        txt_quantity.text = "Q:$quantity"
                        txt_quantity.setTextColor(low)

                    }
                    Priority.Done.ordinal -> {
                        val formattedName = "Q.${position + 1}" + name
                        val formattedPrice =
                            itemView.context.getString(R.string.price, price.toString())
                        val formattedQuantity = "Q:" + quantity
                        val sname = SpannableString(formattedName)
                        val sprice = SpannableString(formattedPrice)
                        val squantity = SpannableString(formattedQuantity)
                        val strike = StrikethroughSpan()
                        sname.setSpan(
                            strike,
                            0,
                            formattedName.length,
                            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                        )
                        sprice.setSpan(
                            strike,
                            0,
                            formattedPrice.length,
                            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                        )
                        squantity.setSpan(
                            strike,
                            0,
                            formattedQuantity.length,
                            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                        )


                        txt_name.text = sname
                        txt_name.setTextColor(done)

                        txt_price.text = sprice
                        txt_price.setTextColor(done)
                        txt_quantity.text = squantity
                        txt_quantity.setTextColor(done)


                    }
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), position)
    }

}


private class DiffUtilCallBack : DiffUtil.ItemCallback<Groceries>() {
    override fun areItemsTheSame(oldItem: Groceries, newItem: Groceries): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Groceries, newItem: Groceries): Boolean {
        return oldItem == newItem
    }

}