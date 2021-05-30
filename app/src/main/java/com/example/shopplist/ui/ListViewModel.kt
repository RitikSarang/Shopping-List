package com.example.shopplist.ui

import android.app.Application
import androidx.lifecycle.*
import com.example.shopplist.data.Groceries
import com.example.shopplist.data.ListRepo
import com.example.shopplist.data.Priority
import com.example.shopplist.data.SortBy
import kotlinx.coroutines.launch

class ListViewModel(application: Application):AndroidViewModel(application){
    private val repo = ListRepo(application)

    private val _sortOrder = MutableLiveData(SortBy.PRIORITY)


    val groceries:LiveData<List<Groceries>> = Transformations.switchMap(_sortOrder){
        repo.getAllGroceriesByPrice(_sortOrder.value!!)
    }



    fun changeSortOrder(sortBy: SortBy){
        _sortOrder.value = sortBy
    }

    fun delete(){
        viewModelScope.launch {
            repo.delete()
        }
    }


    fun makePriorityLow(priority: Int,id:Long){
        viewModelScope.launch {
            repo.makePriorityLow(priority,id)
            _sortOrder.value = SortBy.PRIORITY
        }
    }

    fun getAllPrice():LiveData<List<Float>>{
            return repo.getAlLPrices()
    }

    fun getTotalQuantity():LiveData<List<Int>>{
        return repo.getTotalQuantiy()
    }

}