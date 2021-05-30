package com.example.shopplist.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Groceries::class],version = 1)
abstract class GroceriesDataBase:RoomDatabase() {

    abstract fun groceryListDao() : GroceriesListsDao
    abstract fun groceryDetailDao() : DetailGrocery

    companion object{
        private var instance:GroceriesDataBase?=null
        fun getDatabase(context: Context) = instance
            ?: synchronized(this){
                Room.databaseBuilder(
                    context.applicationContext,
                    GroceriesDataBase::class.java,
                    "grocery_database"
                ).build().also {
                    instance=it
                }
            }
    }
}