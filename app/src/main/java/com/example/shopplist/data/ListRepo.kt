package com.example.shopplist.data

import android.app.Application
import androidx.lifecycle.LiveData

class ListRepo(application: Application) {
    private val listsDao = GroceriesDataBase.getDatabase(application).groceryListDao()



    suspend fun delete(){
        return listsDao.deleteGrocery()
    }

    fun getAllGroceriesByPrice(sortBy: SortBy = SortBy.PRIORITY): LiveData<List<Groceries>> {
        return if(sortBy == SortBy.PRIORITY){
            listsDao.getAllGroceriesByPriority()
        }else if(sortBy == SortBy.PRICE){
            listsDao.getAllGroceriesByPrice()
        }else{
            listsDao.getAllGroceriesByQuantity()
        }
    }


    suspend fun makePriorityLow(priority: Int,id:Long){
        return listsDao.makePriorityLos(priority,id)
    }


    fun getAlLPrices():LiveData<List<Float>>{
        return listsDao.getAllPrices()
    }

    fun getTotalQuantiy():LiveData<List<Int>>{
        return listsDao.getTotalQuantity()
    }
}