package com.example.shopplist.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query


@Dao
interface GroceriesListsDao {

    @Query("SELECT * FROM Groceries ORDER BY price DESC")
    fun getAllGroceriesByPrice():LiveData<List<Groceries>>

    @Query("SELECT * FROM Groceries ORDER BY priority DESC")
    fun getAllGroceriesByPriority():LiveData<List<Groceries>>

    @Query("SELECT * FROM Groceries ORDER BY quantity DESC")
    fun getAllGroceriesByQuantity():LiveData<List<Groceries>>


    @Query("DELETE FROM Groceries")
    suspend fun deleteGrocery()


    @Query("UPDATE Groceries SET priority=:priority WHERE `id`=:id ")
    suspend fun makePriorityLos(priority: Int,id:Long)

    @Query("SELECT price FROM Groceries")
    fun getAllPrices():LiveData<List<Float>>

    @Query("SELECT quantity FROM Groceries")
    fun getTotalQuantity():LiveData<List<Int>>

}