package com.example.shopplist.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.shopplist.data.DetailRepo
import com.example.shopplist.data.Groceries
import kotlinx.coroutines.launch

class DetailGroceryViewModel(application: Application) : AndroidViewModel(application) {
    private val detailRepo = DetailRepo(application)

    private val _groceryId = MutableLiveData(0L)
    private val _quantity = MutableLiveData(1)

    val groceryId: LiveData<Long>
        get() = _groceryId
    val quantity: LiveData<Int>
        get() = _quantity


    fun insertGrocery(groceries: Groceries) {
        viewModelScope.launch {
            detailRepo.insertList(groceries)
        }
    }

    fun updateQuantity(quantity: Int, id: Long) {
        viewModelScope.launch {
            if (_quantity.value!! > 1) {
                detailRepo.updateQuantity(quantity, id)
            }
        }
    }

    fun increaseQuantity(quantity: Int){
        _quantity.value = _quantity.value!! + quantity
    }

    fun decreaseQuantity(quantity: Int){
        if(_quantity.value!! > 1){
            _quantity.value = _quantity.value!! - quantity
        }
    }
}