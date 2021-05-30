package com.example.shopplist.data

import androidx.room.Entity
import androidx.room.PrimaryKey


enum class Priority{
    Done,LOW,MEDIUM,HIGH
}

enum class SortBy{
    PRICE,QUANTITY,PRIORITY
}


@Entity(tableName = "Groceries")
data class Groceries(
    @PrimaryKey(autoGenerate = true) val id:Long,
    val name:String,
    val quantity:Int,
    val price:Float,
    val priority: Int
)