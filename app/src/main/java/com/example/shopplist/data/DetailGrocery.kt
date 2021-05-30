package com.example.shopplist.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.shopplist.data.Groceries

@Dao
interface

DetailGrocery {

    @Query("SELECT * FROM Groceries WHERE `id`=:id")
    fun getSingleGrocery(id:Long):LiveData<Groceries>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertGrocery(groceries: Groceries)

    @Update
    suspend fun updateGrocery(groceries: Groceries)


    @Query("UPDATE Groceries SET quantity=:quantity WHERE `id`=:id")
    suspend fun updateQuantity(quantity:Int,id:Long)


}