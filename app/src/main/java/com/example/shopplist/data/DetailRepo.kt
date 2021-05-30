package com.example.shopplist.data

import android.app.Application

class DetailRepo(application: Application) {
    private val detailDao = GroceriesDataBase.getDatabase(application).groceryDetailDao()

    suspend fun insertList(groceries: Groceries){
        return detailDao.insertGrocery(groceries)
    }

    suspend fun updateQuantity(quantity:Int,id:Long){
        return detailDao.updateQuantity(quantity,id)
    }
}